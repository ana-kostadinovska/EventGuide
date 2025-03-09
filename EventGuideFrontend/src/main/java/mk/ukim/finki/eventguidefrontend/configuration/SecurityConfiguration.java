package mk.ukim.finki.eventguidefrontend.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Autowired
    @Lazy
    private OAuth2AuthorizedClientService authorizedClientService;

    @Bean
    public FilterRegistrationBean<OAuth2TokenLoggingFilter> loggingFilter(OAuth2TokenLoggingFilter oauth2TokenLoggingFilter) {
        FilterRegistrationBean<OAuth2TokenLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(oauth2TokenLoggingFilter);
        registrationBean.addUrlPatterns("*");
        return registrationBean;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        CognitoLogoutHandler cognitoLogoutHandler = new CognitoLogoutHandler();
        cognitoLogoutHandler.setDefaultTargetUrl("/locals");

        http.csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/", "/locals", "/events", "/locals/{id}", "/events/{id}", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")  // Example role restriction
                        .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(successHandler)
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(this.oidcUserServiceWithRoles()))) // Corrected method
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(cognitoLogoutHandler)
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID"));
        return http.build();
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(ClientRegistrationRepository authorizedClientRepository) {
        return new InMemoryOAuth2AuthorizedClientService(authorizedClientRepository);
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new LoginUrlAuthenticationEntryPoint("/oauth2/authorization/cognito"); // Redirect to Cognito login
    }

    private OidcUserService oidcUserServiceWithRoles() {
        OidcUserService delegate = new OidcUserService();
        return new OidcUserService() {
            @Override
            public OidcUser loadUser(OidcUserRequest userRequest) {
                System.out.println("🎯 Entering custom OidcUserService...");

                OidcUser oidcUser = delegate.loadUser(userRequest);

                Collection<GrantedAuthority> authorities = fetchRolesFromBackend(oidcUser);
                System.out.println("✅ User Authorities: " + authorities);

                return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
            }
        };
    }
    private Collection<GrantedAuthority> fetchRolesFromBackend(OidcUser oidcUser) {
        String accessToken = oidcUser.getIdToken().getTokenValue(); // Get the access token
        String backendUrl = "http://localhost:9090/api/user/frontend"; // Use the correct endpoint

        System.out.println("🔍 Fetching roles from backend for user...");

        // Prepare HTTP request with Bearer token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken); // Set Authorization header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON)); // Expect JSON response
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        try {
            // Perform GET request to backend (expecting a Map response)
            ResponseEntity<Map> response = restTemplate.exchange(
                    backendUrl,
                    HttpMethod.GET,
                    requestEntity,
                    Map.class // Expect a JSON object (Map)
            );

            // Extract roles from the response
            if (response.getBody() != null && response.getBody().containsKey("roles")) {
                List<String> roles = (List<String>) response.getBody().get("roles");

                System.out.println("✅ Not parsed roles: " + roles.get(0));

                List<GrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList());

                System.out.println("✅ User Roles: " + authorities);
                return authorities;
            }

        } catch (Exception e) {
            System.err.println(" Error fetching roles: " + e.getMessage());
        }

        return List.of(); // Return empty list if request fails
    }
}

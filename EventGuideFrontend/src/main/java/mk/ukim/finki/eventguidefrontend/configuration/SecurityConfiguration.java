package mk.ukim.finki.eventguidefrontend.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

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
                        .requestMatchers("/", "/locals", "/events", "/locals/{id}", "/events/{id}").permitAll()
                        .anyRequest()
                        .authenticated())
                .oauth2Login(oauth2->oauth2.successHandler(successHandler))
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
        return new LoginUrlAuthenticationEntryPoint("/oauth2/authorization/cognito"); // Force redirect to Cognito login
    }
}
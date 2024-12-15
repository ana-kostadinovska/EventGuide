package mk.ukim.finki.eventguidefrontend.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Class to configure AWS Cognito as an OAuth 2.0 authorizer with Spring Security.
 * In this configuration, we specify our OAuth Client.
 * We also declare that all requests must come from an authenticated user.
 * Finally, we configure our logout handler.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

    @Bean
    public FilterRegistrationBean<OAuth2TokenLoggingFilter> loggingFilter(OAuth2TokenLoggingFilter oauth2TokenLoggingFilter) {
        FilterRegistrationBean<OAuth2TokenLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(oauth2TokenLoggingFilter);
        registrationBean.addUrlPatterns("/");  // Apply this filter to all URLs
        return registrationBean;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        CognitoLogoutHandler cognitoLogoutHandler = new CognitoLogoutHandler();

        http.csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/").permitAll()
                        .anyRequest()
                        .authenticated())
                .oauth2Login(oauth -> oauth.loginPage("/oauth2/authorization/cognito"))
                .logout(logout -> logout.logoutSuccessHandler(cognitoLogoutHandler));
        return http.build();
    }
    // Use the OAuth2AuthorizedClientRepository to persist the authorized client in session
    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(ClientRegistrationRepository authorizedClientRepository) {
        return new InMemoryOAuth2AuthorizedClientService(authorizedClientRepository);
    }
}
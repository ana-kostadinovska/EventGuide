package mk.ukim.finki.eventguidefrontend.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

@Component
@WebFilter("/*")  // This filter will be applied to all requests
public class OAuth2TokenLoggingFilter extends OncePerRequestFilter {

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final OAuth2AuthorizedClientManager authorizedClientManager;

    @Autowired
    public OAuth2TokenLoggingFilter(OAuth2AuthorizedClientService authorizedClientService,
                                    OAuth2AuthorizedClientManager authorizedClientManager) {
        this.authorizedClientService = authorizedClientService;
        this.authorizedClientManager = authorizedClientManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = (Authentication) request.getUserPrincipal();

        if (authentication == null) {
            System.out.println("Authentication is null");
        } else if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            String principalName = oauthToken.getName();
            System.out.println("Authentication: " + oauthToken.getPrincipal());
            System.out.println("Registration token " + oauthToken.getAuthorizedClientRegistrationId());
            System.out.println("Principal: " + principalName);
            OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                    oauthToken.getAuthorizedClientRegistrationId(), principalName);

            if (authorizedClient != null) {
                OAuth2AccessToken accessToken = authorizedClient.getAccessToken();

                if (accessToken != null && accessToken.getExpiresAt().isBefore(Instant.now().plusSeconds(300))) {
                    OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                            .withClientRegistrationId(oauthToken.getAuthorizedClientRegistrationId())
                            .principal(authentication)
                            .build();

                    OAuth2AuthorizedClient updatedAuthorizedClient = authorizedClientManager.authorize(authorizeRequest);

                    if (updatedAuthorizedClient != null) {
                        authorizedClientService.saveAuthorizedClient(updatedAuthorizedClient, authentication);
                        accessToken = updatedAuthorizedClient.getAccessToken();
                        System.out.println("New Access Token: " + accessToken.getTokenValue());
                    }
                }

                if (accessToken != null) {
                    System.out.println("Access Token: " + accessToken.getTokenValue());
                    System.out.println("Token Expiration: " + accessToken.getExpiresAt());
                    long remainingTime = accessToken.getExpiresAt().toEpochMilli() - System.currentTimeMillis();
                    System.out.println("Remaining Time (ms): " + remainingTime);
                }
            } else {
                System.out.println("Authorized client not found for principal: " + principalName);
            }
        } else {
            System.out.println("Authentication is not an instance of OAuth2AuthenticationToken");
        }

        filterChain.doFilter(request, response);
    }
}


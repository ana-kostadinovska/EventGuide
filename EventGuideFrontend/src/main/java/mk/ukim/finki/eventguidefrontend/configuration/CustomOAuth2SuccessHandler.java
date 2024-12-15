package mk.ukim.finki.eventguidefrontend.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String principalName = oauthToken.getName();

        System.out.println("Session Attributes: " + request.getSession().getAttributeNames());

        // Retrieve the OAuth2AuthorizedClient from the authorizedClientService


//        if (authorizedClient != null) {
//            // Get the OAuth2 Access Token
//            OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
//
//            // Print the access token and its expiration time
//            System.out.println("Access Token: " + accessToken.getTokenValue());
//            System.out.println("Token Expiration: " + accessToken.getExpiresAt());
//
//            // Optionally, calculate the remaining time for the token
//            long remainingTime = accessToken.getExpiresAt().toEpochMilli() - System.currentTimeMillis();
//            System.out.println("Remaining Time (ms): " + remainingTime);
//        } else {
//            System.out.println("Authorized client not found for principal: " + principalName);
//        }

        // Redirect to the desired page after successful login
        response.sendRedirect("/"); // You can change this to any other page
    }
}

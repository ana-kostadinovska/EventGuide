package mk.ukim.finki.eventguidefrontend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;

@Configuration
public class JwtConfig {

    @Bean
    public JwtDecoder jwtDecoder() {
        // Replace with your actual issuer URI
        String issuerUri = "https://cognito-idp.eu-north-1.amazonaws.com/eu-north-1_RyYnjZphF";

        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation(issuerUri);
        System.out.println("Time now " + java.time.Instant.now());

        // Disable any skew tolerance by customizing the token validator
        OAuth2TokenValidator<Jwt> withClockSkewValidator = new OAuth2TokenValidator<Jwt>() {
            @Override
            public OAuth2TokenValidatorResult validate(Jwt token) {
                // Do not allow any clock skew, perform strict expiration check
                System.out.println("Time now " + java.time.Instant.now());
                if (token.getExpiresAt().isBefore(java.time.Instant.now())) {
                    return OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_token", "Token has expired", null));
                }
                return OAuth2TokenValidatorResult.success();
            }
        };

        // Add the custom validator to disable clock skew tolerance
        jwtDecoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(withClockSkewValidator));

        return JwtDecoders.fromIssuerLocation(issuerUri);
    }
}

package mk.ukim.finki.eventguidefrontend.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class ApiService {

    private final WebClient webClient;
    private final OAuth2AuthorizedClientService authorizedClientService;

    public ApiService(WebClient.Builder webClientBuilder, OAuth2AuthorizedClientService authorizedClientService) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:9090/api").build();
        this.authorizedClientService = authorizedClientService;
    }

    public String fetchData(String endpoint, Authentication authentication) {
        String accessToken = getAccessToken(authentication);
        if (accessToken == null) {
            return "redirect:/login";
        }

        try {
            return webClient.get()
                    .uri(endpoint)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException ex) {
            return handleException(ex);
        }
    }

    public String postData(String endpoint, Object requestBody, Authentication authentication) {
        String accessToken = getAccessToken(authentication);
        if (accessToken == null) {
            return "redirect:/login";
        }

        try {
            return webClient.post()
                    .uri(endpoint)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException ex) {
            return handleException(ex);
        }
    }


    public String deleteData(String endpoint, Authentication authentication) {
        String accessToken = getAccessToken(authentication);
        if (accessToken == null) {
            return "redirect:/login";
        }

        try {
            return webClient.delete()
                    .uri(endpoint)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException ex) {
            return handleException(ex);
        }
    }

    public String updateData(String endpoint, Object requestBody, Authentication authentication) {
        String accessToken = getAccessToken(authentication);
        if (accessToken == null) {
            return "redirect:/login";
        }

        try {
            return webClient.put()
                    .uri(endpoint)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException ex) {
            return handleException(ex);
        }
    }

    private String getAccessToken(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            String principalName = oauthToken.getName();
            OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                    oauthToken.getAuthorizedClientRegistrationId(), principalName);

            if (authorizedClient != null) {
                OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
                if (accessToken != null && accessToken.getExpiresAt().isAfter(Instant.now())) {
                    return accessToken.getTokenValue();
                }
            }
        }
        return null;
    }

    private String handleException(WebClientResponseException ex) {
        HttpStatus status = HttpStatus.resolve(ex.getStatusCode().value());

        if (status == null) {
            return "redirect:/error";
        }

        return switch (status) {
            case UNAUTHORIZED -> "redirect:/login";
            case FORBIDDEN -> "redirect:/access-denied";
            case NOT_FOUND -> "redirect:/not-found";
            default -> "redirect:/error";
        };
    }

    public List<Map<String, Object>> parseJsonList(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (Exception e) {
            return List.of();
        }
    }

    public Map<String, Object> parseJsonMap(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (Exception e) {
            return Map.of();
        }
    }
}

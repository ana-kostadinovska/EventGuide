package mk.ukim.finki.eventguidefrontend.web;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {

    private final String backendUrl = "http://localhost:9090/api/users";

    @GetMapping
    public String getUsers(Model model) {
        RestTemplate restTemplate = new RestTemplate();

        String token = "Bearer " + "eyJraWQiOiJYdWhVM3VaeUhZU21XTGxhd0EwR0xXRWtxVWNVXC9Gc1M5NlpKejc1cmh1dz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJmMGNjZDlkYy1mMDkxLTcwN2QtNThjZC01NTg0N2RmMGQwYjkiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuZXUtbm9ydGgtMS5hbWF6b25hd3MuY29tXC9ldS1ub3J0aC0xX3NlQUNGWXFFSSIsInZlcnNpb24iOjIsImNsaWVudF9pZCI6IjZuY2FocjlnMnA3NDU4aG44dWJvMHI5OTgzIiwib3JpZ2luX2p0aSI6IjhiNGI4ODFjLTlkOTctNDQzZC04NTJiLWY2ZGY2ZTA0ZjM5MyIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoicGhvbmUgb3BlbmlkIGVtYWlsIiwiYXV0aF90aW1lIjoxNzM4NDQ4MTAxLCJleHAiOjE3Mzg0NDg0MDEsImlhdCI6MTczODQ0ODEwMSwianRpIjoiYzlhMDY3MTUtYzU2Yy00NThjLWIzNzgtZTJkYjRjNGViMzYxIiwidXNlcm5hbWUiOiJmMGNjZDlkYy1mMDkxLTcwN2QtNThjZC01NTg0N2RmMGQwYjkifQ.jBbFxHpZTsABzS-IdnN9UiIZJ9SbKzCA3UZez9NLwm5XwpkKdNoD1JIFYWqEXYgOKISaTmf5himiERHEn6xCQ52bRo4xgy-A5sGRk3E8p0XFNxrVeyFPiqdTjPN1WZ8Ztxc68ZTB5cxaHaNkRteKjMacWz-1Aa67sk5XBJ4Wdu2maRlGMiO2Ogx4YsOUDtBoaa1USj5OiURJH58Q1-mhyClbQAGiSptN1rAcPS43rjwO5W1dtqvboq2oC82eQ0WGkMFkdfRivoqYza8GjbHuG1MfTl7aTDk0g3mts1np5I1554u6z3EqzHw6Y1M-fhLk2g_MIAe3DQURo2igaRWAXA";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(backendUrl, HttpMethod.GET, entity, List.class);

        model.addAttribute("users", response.getBody());
        return "users";
    }

    @GetMapping("/{id}")
    public String getUserDetails(@PathVariable Long id, Model model) {
        RestTemplate restTemplate = new RestTemplate();

        String token = "Bearer " + "eyJraWQiOiJYdWhVM3VaeUhZU21XTGxhd0EwR0xXRWtxVWNVXC9Gc1M5NlpKejc1cmh1dz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJmMGNjZDlkYy1mMDkxLTcwN2QtNThjZC01NTg0N2RmMGQwYjkiLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuZXUtbm9ydGgtMS5hbWF6b25hd3MuY29tXC9ldS1ub3J0aC0xX3NlQUNGWXFFSSIsInZlcnNpb24iOjIsImNsaWVudF9pZCI6IjZuY2FocjlnMnA3NDU4aG44dWJvMHI5OTgzIiwib3JpZ2luX2p0aSI6IjhiNGI4ODFjLTlkOTctNDQzZC04NTJiLWY2ZGY2ZTA0ZjM5MyIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoicGhvbmUgb3BlbmlkIGVtYWlsIiwiYXV0aF90aW1lIjoxNzM4NDQ4MTAxLCJleHAiOjE3Mzg0NDg0MDEsImlhdCI6MTczODQ0ODEwMSwianRpIjoiYzlhMDY3MTUtYzU2Yy00NThjLWIzNzgtZTJkYjRjNGViMzYxIiwidXNlcm5hbWUiOiJmMGNjZDlkYy1mMDkxLTcwN2QtNThjZC01NTg0N2RmMGQwYjkifQ.jBbFxHpZTsABzS-IdnN9UiIZJ9SbKzCA3UZez9NLwm5XwpkKdNoD1JIFYWqEXYgOKISaTmf5himiERHEn6xCQ52bRo4xgy-A5sGRk3E8p0XFNxrVeyFPiqdTjPN1WZ8Ztxc68ZTB5cxaHaNkRteKjMacWz-1Aa67sk5XBJ4Wdu2maRlGMiO2Ogx4YsOUDtBoaa1USj5OiURJH58Q1-mhyClbQAGiSptN1rAcPS43rjwO5W1dtqvboq2oC82eQ0WGkMFkdfRivoqYza8GjbHuG1MfTl7aTDk0g3mts1np5I1554u6z3EqzHw6Y1M-fhLk2g_MIAe3DQURo2igaRWAXA";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(
                backendUrl + "/" + id, HttpMethod.GET, entity, Map.class
        );

        model.addAttribute("user", response.getBody());
        return "user-details";
    }
}

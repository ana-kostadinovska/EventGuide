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

        String token="Bearer "+"your-token-here";
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

        String token="Bearer "+"your-token-here";
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

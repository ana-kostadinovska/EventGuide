package mk.ukim.finki.eventguidefrontend.web;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/locals")
public class LocalController {

    private final String backendUrl = "http://localhost:9090/api/locals";

    @GetMapping
    public String getLocals(@RequestParam(required = false) String type, Model model) {
        RestTemplate restTemplate = new RestTemplate();

        String token = "Bearer " + "your-token-here";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        String url = backendUrl;
        if (type != null && !type.isEmpty()) {
            url += "/filter?type=" + type;
        }

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);

        model.addAttribute("locals", response.getBody());
        return "locals";
    }

    @GetMapping("/{id}")
    public String getLocalDetails(@PathVariable Long id, Model model) {
        RestTemplate restTemplate = new RestTemplate();

        String token = "Bearer " + "your-token-here";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(
                backendUrl + "/" + id, HttpMethod.GET, entity, Map.class
        );

        model.addAttribute("local", response.getBody());
        return "local-details";
    }
}

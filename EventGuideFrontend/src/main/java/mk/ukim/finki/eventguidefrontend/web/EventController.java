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
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/events")
public class EventController {
    private final String backendUrl = "http://localhost:9090/api/events";

    @GetMapping
    public String getEvents(Model model) {
        RestTemplate restTemplate = new RestTemplate();

        String token="Bearer "+"your-token-here";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(
                backendUrl, HttpMethod.GET, entity, List.class
        );

        model.addAttribute("events", response.getBody());
        return "events";
    }

    @GetMapping("/{id}")
    public String getEventDetails(@PathVariable Long id, Model model) {
        RestTemplate restTemplate = new RestTemplate();

        String token="Bearer "+"your-token-here";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(
                backendUrl + "/" + id, HttpMethod.GET, entity, Map.class
        );

        model.addAttribute("event", response.getBody());
        return "event-details";
    }
    @GetMapping("/interested/{userId}")
    public String getInterestedEvents(@PathVariable Long userId, Model model) {
        RestTemplate restTemplate = new RestTemplate();

        String token="Bearer "+"your-token-here";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(
                backendUrl + "/interested/" + userId, HttpMethod.GET, entity, List.class
        );

        model.addAttribute("interestedEvents", response.getBody());
        return "interested-events";
    }
}


package mk.ukim.finki.eventguidefrontend.web;

import mk.ukim.finki.eventguidefrontend.services.ApiService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/events")
public class EventController {

    private final String backendUrl = "http://localhost:9090/api/events";
    private final ApiService apiService;

    public EventController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping
    public String getEvents(Model model, Authentication authentication) {
        String response = apiService.fetchData("/events", authentication);
        model.addAttribute("events", apiService.parseJsonList(response));
        return "events";
    }
    @GetMapping("/{id}")
    public String getEventDetails(@PathVariable Long id, Model model, Authentication authentication) {
        String response = apiService.fetchData("/events/" + id, authentication);
        System.out.println(response);
        if (response.startsWith("redirect:")) {
            return response;
        }

        model.addAttribute("event", apiService.parseJsonMap(response));
        return "event-details";
    }

    @PostMapping("/add")
    public String addEvent(
            @RequestParam String name,
            @RequestParam String artist,
            @RequestParam String description,
            @RequestParam LocalDate date,
            @RequestParam LocalTime time,
            @RequestParam Long local_id,
            Authentication authentication
    ) {
        Map<String, Object> requestBody = Map.of(
                "name", name,
                "artist", artist,
                "description", description,
                "date", date.toString(),
                "time", time.toString(),
                "local_id", local_id
        );

        String response = apiService.postData("/events/add", requestBody, authentication);

        if (response.startsWith("redirect:")) {
            return response;
        }

        return "redirect:/locals/"+local_id;
    }
}


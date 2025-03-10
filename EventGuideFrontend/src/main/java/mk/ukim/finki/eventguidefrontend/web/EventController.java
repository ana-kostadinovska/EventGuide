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
        String responseAllEvents = apiService.fetchData("/events", authentication);
        String responseUserEvents = apiService.fetchData("/events/userInterestedEvents", authentication);
        model.addAttribute("events", apiService.parseJsonList(responseAllEvents));
        model.addAttribute("interestedEvents", apiService.parseJsonList(responseUserEvents));
        model.addAttribute("pageTitle", "Events");
        model.addAttribute("cssFile", "events.css");
        model.addAttribute("bodyContent", "events");
        return "template";
    }
    @GetMapping("/{id}")
    public String getEventDetails(@PathVariable Long id, Model model, Authentication authentication) {
        String response = apiService.fetchData("/events/" + id, authentication);
        System.out.println(response);
        if (response.startsWith("redirect:")) {
            return response;
        }

        model.addAttribute("event", apiService.parseJsonMap(response));
        model.addAttribute("pageTitle", "Events");
        model.addAttribute("cssFile", "event-details.css");

        model.addAttribute("bodyContent", "event-details");
        return "template";
    }

    @PostMapping("/add")
    public String addEvent(
            @RequestParam String name,
            @RequestParam String artist,
            @RequestParam String description,
            @RequestParam LocalDate date,
            @RequestParam LocalTime time,
            @RequestParam Long local_id,
            @RequestParam String referenceUrl,
            Authentication authentication
    ) {
        Map<String, Object> requestBody = Map.of(
                "name", name,
                "artist", artist,
                "description", description,
                "date", date.toString(),
                "time", time.toString(),
                "local_id", local_id,
                "referenceUrl", referenceUrl
        );

        String response = apiService.postData("/events/add", requestBody, authentication);

        if (response.startsWith("redirect:")) {
            return response;
        }

        return "redirect:/locals/"+local_id;
    }

    @PostMapping("/delete/{id}")
    public String deleteLocal(@PathVariable Long id, Authentication authentication) {
        String response = apiService.deleteData("/events/delete/" + id, authentication);

        return "redirect:/events";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id,
                               Model model,
                               Authentication authentication) {

        String eventResponse = apiService.fetchData("/events/" + id, authentication);
        if (eventResponse.startsWith("redirect:")) {
            return eventResponse;
        }

        String eventsResponse = apiService.fetchData("/events", authentication);
        if (eventsResponse.startsWith("redirect:")) {
            return eventsResponse;
        }

        Map<String, Object> event = apiService.parseJsonMap(eventResponse);

        model.addAttribute("event", event);

        model.addAttribute("pageTitle", "Edit event");
        model.addAttribute("cssFile", "add-event.css");
        model.addAttribute("bodyContent", "edit-event");
        return "template";
    }

    @PostMapping("/edit/{id}")
    public String editEvent(@PathVariable Long id,
                            @RequestParam String name,
                            @RequestParam String artist,
                            @RequestParam String description,
                            @RequestParam LocalDate date,
                            @RequestParam LocalTime time,
                            @RequestParam Long local_id,
                            @RequestParam String referenceUrl,
                            Authentication authentication) {
        Map<String, Object> updatedData = Map.of(
                "name", name,
                "artist", artist,
                "description", description,
                "date", date.toString(),
                "time", time.toString(),
                "local_id", local_id,
                "referenceUrl", referenceUrl
        );

        String response = apiService.updateData("/events/edit/" + id, updatedData, authentication);

        if (response.startsWith("redirect:")) {
            return response;
        }

        return "redirect:/locals/"+local_id;
    }

    @PostMapping("/interested/{id}")
    public String toggleEvent(@PathVariable Long id, Authentication authentication) {
        String response = apiService.postData("/events/interested/" + id,"", authentication);

        if (response.startsWith("redirect:")) {
            return response;
        }

        return "redirect:/events";
    }
}


package mk.ukim.finki.eventguidefrontend.web;

import mk.ukim.finki.eventguidefrontend.services.ApiService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ApiService apiService;

    public AdminController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping
    public String getPendingEvents(Model model, Authentication auth) {
        String endpoint = "/admin/pending-events";

        System.out.println("Sending request to " + endpoint);

        String response = apiService.fetchData(endpoint, auth);

        if (response.startsWith("redirect:")) {
            System.out.println("Response error " + response);
            return response;
        }

        System.out.println("Response ok " + response);

        model.addAttribute("events", apiService.parseJsonList(response));
        model.addAttribute("pageTitle", "Admin Pending Events");
        model.addAttribute("cssFile", "admin-pending-events.css");

        model.addAttribute("bodyContent", "admin-pending-events");

        return "template";
    }

    @PostMapping("/approve/{id}")
    public String approveEvent (@PathVariable Long id, Authentication auth) {
        var endpoint = "/admin/setStatus/" + id;
        Map<String, Object> requestBody = Map.of(
                "event_id", id,
                "status", "APPROVED"
        );
        String response = apiService.postData(endpoint, requestBody, auth);

        System.out.println("RESPONSE" + response);
        if (response.startsWith("redirect:")) {
            return response;
        }


        return "redirect:/admin";
    }

    @PostMapping("/reject/{id}")
    public String rejectEvent (@PathVariable Long id, Authentication auth) {
        var endpoint = "/admin/setStatus/" + id;
        Map<String, Object> requestBody = Map.of(
                "event_id", id,
                "status", "REJECTED"
        );
        String response = apiService.postData(endpoint, requestBody, auth);

        if (response.startsWith("redirect:")) {
            return response;
        }

        return "redirect:/admin";
    }

    @PostMapping("/discard/{id}")
    public String discardEvent (@PathVariable Long id, Authentication auth) {
        var endpoint = "/admin/setStatus/" + id;
        Map<String, Object> requestBody = Map.of(
                "event_id", id,
                "status", "DISCARDED"
        );
        String response = apiService.postData(endpoint, requestBody, auth);

        if (response.startsWith("redirect:")) {
            return response;
        }

        return "redirect:/admin";
    }

    @PostMapping("/edit/{id}")
    public String editEvent(@PathVariable Long id,
                            @RequestParam String name,
                            @RequestParam String artist,
                            @RequestParam String description,
                            @RequestParam String date,
                            @RequestParam String time,
                            @RequestParam Long local_id,
                            @RequestParam String referenceUrl,
                            Authentication authentication) {
        Map<String, Object> updatedData = Map.of(
                "name", name,
                "artist", artist,
                "description", description,
                "date", date,
                "time", time,
                "referenceUrl", referenceUrl,
                "local_id", local_id
                );

        String response = apiService.updateData("/events/edit/" + id, updatedData, authentication);

        if (response.startsWith("redirect:")) {
            return response;
        }

        return "redirect:/admin";
    }

}

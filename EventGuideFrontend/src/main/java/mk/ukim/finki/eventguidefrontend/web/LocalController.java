package mk.ukim.finki.eventguidefrontend.web;

import mk.ukim.finki.eventguidefrontend.services.ApiService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/locals")
public class LocalController {

    private final String backendUrl = "http://localhost:9090/api/locals";
    private final ApiService apiService;

    public LocalController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping
    public String getLocals(@RequestParam(required = false) String type, Model model, Authentication authentication) {
        String endpoint = (type != null && !type.isEmpty()) ? "/locals/filter?type=" + type : "/locals";
        String response = apiService.fetchData(endpoint, authentication);

        String url = backendUrl;
        if (type != null && !type.isEmpty()) {
            url += "/filter?type=" + type;
        }

        model.addAttribute("locals", apiService.parseJsonList(response));
        return "locals";
    }

    @GetMapping("/{id}")
    public String getLocalDetails(@PathVariable Long id, Model model, Authentication authentication) {
        String response = apiService.fetchData("/locals/" + id, authentication);

        if (response.startsWith("redirect:")) {
            return response;
        }

        model.addAttribute("local", apiService.parseJsonMap(response));
        return "local-details";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, Authentication authentication) {

        String localResponse = apiService.fetchData("/locals/" + id, authentication);
        if (localResponse.startsWith("redirect:")) {
            return localResponse;
        }

        String eventsResponse = apiService.fetchData("/events", authentication);
        if (eventsResponse.startsWith("redirect:")) {
            return eventsResponse;
        }

        Map<String, Object> local = apiService.parseJsonMap(localResponse);
        List<Map<String, Object>> allEvents = apiService.parseJsonList(eventsResponse);

        model.addAttribute("local", local);
        model.addAttribute("allEvents", allEvents);

        return "edit-local";
    }

    @PostMapping("/edit/{id}")
    public String editLocal(@PathVariable Long id,
                            @RequestParam String name,
                            @RequestParam String location,
                            @RequestParam String workingHours,
                            @RequestParam String contactNumber,
                            @RequestParam String type,
                            Authentication authentication,
                            Model model) {

        Map<String, Object> updatedData = Map.of(
                "name", name,
                "location", location,
                "workingHours", workingHours,
                "contactNumber", contactNumber,
                "type", type
        );

        String response = apiService.updateData("/locals/edit/" + id, updatedData, authentication);

        if (response.startsWith("redirect:")) {
            return response;
        }

        return "redirect:/locals";
    }

    @GetMapping("/add")
    public String showAddForm() {
        return "add-local";
    }

    @PostMapping("/add")
    public String addLocal(
                            @RequestParam String name,
                            @RequestParam String location,
                            @RequestParam String workingHours,
                            @RequestParam String contactNumber,
                            @RequestParam String type,
                            Authentication authentication,
                            Model model) {

        Map<String, Object> updatedData = Map.of(
                "name", name,
                "location", location,
                "workingHours", workingHours,
                "contactNumber", contactNumber,
                "type", type
        );

        String response = apiService.postData("/locals/add"
                , updatedData, authentication);

        if (response.startsWith("redirect:")) {
            return response;
        }

        return "redirect:/locals";
    }
}

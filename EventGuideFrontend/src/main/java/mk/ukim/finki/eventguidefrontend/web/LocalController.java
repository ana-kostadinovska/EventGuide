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
    public String getLocals(@RequestParam(required = false) String type, @RequestParam(required = false) Integer page, Model model, Authentication authentication) {
        int pageNumber = (page != null) ? page : 0;
        String endpoint = "/locals";

        if (type != null && !type.isEmpty()) {
            endpoint = "/locals/filter?type=" + type;
        }

        endpoint += endpoint.contains("?") ? "&page=" + pageNumber : "?page=" + pageNumber;
        String response = apiService.fetchData(endpoint, authentication);
        model.addAttribute("locals", apiService.parseJsonList(response));
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", 4);
        model.addAttribute("type", type != null ? type : "");

        model.addAttribute("pageTitle", "Home");
        model.addAttribute("cssFile", "home.css");
        model.addAttribute("bodyContent", "home");
        return "template";
    }

    @GetMapping("/{id}")
    public String getLocalDetails(@PathVariable Long id, Model model, Authentication authentication) {
        String response = apiService.fetchData("/locals/" + id, authentication);

        if (response.startsWith("redirect:")) {
            return response;
        }

        model.addAttribute("local", apiService.parseJsonMap(response));
        model.addAttribute("pageTitle", "Local details");
        model.addAttribute("cssFile", "local-details.css");

        model.addAttribute("bodyContent", "local-details");
        return "template";
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
        model.addAttribute("pageTitle", "Edit local");
        model.addAttribute("cssFile", "add-local.css");
        model.addAttribute("bodyContent", "edit-local");
        return "template";
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
    public String showAddForm(Model model) {
        model.addAttribute("pageTitle", "Add local");
        model.addAttribute("cssFile", "add-local.css");
        model.addAttribute("bodyContent", "add-local");
        return "template";
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

    @PostMapping("/delete/{id}")
    public String deleteLocal(@PathVariable Long id, Authentication authentication) {
        String response = apiService.deleteData("/locals/delete/" + id, authentication);

        return "redirect:/locals";
    }

    @GetMapping("{id}/events/add")
    public String showAddEventForm(@PathVariable Long id, Model model, Authentication authentication) {
        model.addAttribute("localId", id);
        model.addAttribute("pageTitle", "Add event");
        model.addAttribute("cssFile", "add-event.css");
        model.addAttribute("bodyContent", "add-event");
        return "template";
    }
}

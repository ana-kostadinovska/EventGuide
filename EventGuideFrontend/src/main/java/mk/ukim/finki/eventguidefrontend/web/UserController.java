package mk.ukim.finki.eventguidefrontend.web;

import mk.ukim.finki.eventguidefrontend.services.ApiService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {

    private final String backendUrl = "http://localhost:9090/api/user";
    private final ApiService apiService;

    public UserController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/events")
    public String getUserProfile(Model model, Authentication authentication) {

        String responseUserEvents = apiService.fetchData("/user/interested-events", authentication);
        List<Map<String, Object>> userEvents = apiService.parseJsonList(responseUserEvents);

        System.out.println(responseUserEvents);
//        String responseUserRequestedEvents = apiService.fetchData("/user/requested-events", authentication);
//        List<Map<String, Object>> userRequestedEvents = apiService.parseJsonList(responseUserEvents);

        model.addAttribute("userEvents", userEvents);
//        model.addAttribute("userRequestedEvents", userRequestedEvents);
        model.addAttribute("pageTitle", "User Interested Events");
        model.addAttribute("cssFile", "user-interest.css");
        model.addAttribute("bodyContent", "user-interest");

        return "template";
    }
}

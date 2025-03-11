package mk.ukim.finki.eventguide.web;

import mk.ukim.finki.eventguide.model.Event;
import mk.ukim.finki.eventguide.model.EventRequest;
import mk.ukim.finki.eventguide.model.dto.UserDTO;
import mk.ukim.finki.eventguide.service.implementation.EventServiceImpl;
import mk.ukim.finki.eventguide.service.implementation.UserServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImpl userService;
    private final EventServiceImpl eventService;

    public UserController(UserServiceImpl userService, EventServiceImpl eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @GetMapping("/frontend")
    public Map<String, Object> getFrontendUserData(Authentication authentication) {
        var userData = userService.getFrontendUserData(authentication);
        return userData;
    }
}

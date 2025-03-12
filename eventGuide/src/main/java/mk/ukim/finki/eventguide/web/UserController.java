package mk.ukim.finki.eventguide.web;

import mk.ukim.finki.eventguide.model.Event;
import mk.ukim.finki.eventguide.model.User;
import mk.ukim.finki.eventguide.model.dto.InterestedEvent;
import mk.ukim.finki.eventguide.service.EventService;
import mk.ukim.finki.eventguide.service.UserService;
import mk.ukim.finki.eventguide.service.implementation.EventServiceImpl;
import mk.ukim.finki.eventguide.service.implementation.UserServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final EventService eventService;

    public UserController(UserServiceImpl userService, EventServiceImpl eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @GetMapping("/frontend")
    public Map<String, Object> getFrontendUserData(Authentication authentication) {
        var userData = userService.getFrontendUserData(authentication);
        return userData;
    }

    @GetMapping("/interested-events")
    public List<InterestedEvent> getUserInterestedEvents(Authentication authentication) {
        Optional<User> userOptional = userService.getLoggedInUser(authentication);

        User user = userOptional.get();

        return userService.getEventDetailsForUser(user.getId());
    }
}

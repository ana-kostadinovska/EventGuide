package mk.ukim.finki.eventguide.web;

import mk.ukim.finki.eventguide.model.dto.UserDTO;
import mk.ukim.finki.eventguide.service.implementation.UserServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/frontend")
    public Map<String, Object> getFrontendUserData(Authentication authentication) {
        var userData = userService.getFrontendUserData(authentication);
        return userData;
    }
}

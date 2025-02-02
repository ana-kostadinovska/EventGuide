package mk.ukim.finki.eventguide.web;

import mk.ukim.finki.eventguide.model.Event;
import mk.ukim.finki.eventguide.model.User;
import mk.ukim.finki.eventguide.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        return this.userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return this.userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<User> save(
            @RequestParam String username,
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String email
    ) {
        return this.userService.save(username, name, surname, email, List.of()) // Initialize with an empty list
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<User> edit(@PathVariable Long id,
                                     @RequestParam String username,
                                     @RequestParam String name,
                                     @RequestParam String surname,
                                     @RequestParam String email) {
        return this.userService.edit(id, username, name, surname, email, List.of())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        this.userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // NEW: Add interest to an event
    @PostMapping("/{userId}/interest/{eventId}")
    public ResponseEntity<User> addInterest(@PathVariable Long userId, @PathVariable Long eventId) {
        return this.userService.addInterest(userId, eventId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    // NEW: Remove interest from an event
    @DeleteMapping("/{userId}/interest/{eventId}")
    public ResponseEntity<User> removeInterest(@PathVariable Long userId, @PathVariable Long eventId) {
        return this.userService.removeInterest(userId, eventId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}

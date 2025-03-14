package mk.ukim.finki.eventguide.web;

import mk.ukim.finki.eventguide.model.Event;
import mk.ukim.finki.eventguide.model.EventRequest;
import mk.ukim.finki.eventguide.model.User;
import mk.ukim.finki.eventguide.model.dto.EventAddRequest;
import mk.ukim.finki.eventguide.service.EventService;
import mk.ukim.finki.eventguide.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:8080")
public class EventRestController {

    private final EventService eventService;
    private final UserService userService;

    public EventRestController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping
    private List<Event> findAll() {
        return this.eventService.findByStatus(EventRequest.RequestStatus.APPROVED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> findById(@PathVariable Long id) {
        return this.eventService.findById(id)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/interested/{id}")
    public ResponseEntity<Map<String, Integer>> toggleInterested(@PathVariable Long id, Authentication authentication) {
        Optional<User> userOptional = userService.getLoggedInUser(authentication);

        User user = userOptional.get();

        var updatedUser = userService.addInterest(user.getId(), id);
        if (updatedUser.isPresent()) {
            Map<String, Integer> response = Map.of("interestedCount", 2);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/add")
    public ResponseEntity<Event> save(
            @RequestBody EventAddRequest request, Authentication authentication
    ) {
        Optional<User> userOptional = userService.getLoggedInUser(authentication);


        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        User user = userOptional.get();

        return this.eventService.save(request, user)
                .map(event -> ResponseEntity.ok().body(event))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<Event> save(@PathVariable Long id,
                                      @RequestBody EventAddRequest request) {
        return this.eventService.edit(id, request.name, request.artist, request.description, request.date, request.time, request.local_id)
                .map(event -> ResponseEntity.ok().body(event))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        this.eventService.deleteById(id);
        if (this.eventService.findById(id).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/search")
    public List<Event> searchEvents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location) {
        return this.eventService.searchEvents(name, location);
    }

    @GetMapping("/filter")
    public List<Event> filterEventsByDate(@RequestParam LocalDate date) {
        return this.eventService.filterEventsByDate(date);
    }

    @GetMapping("/userInterestedEvents")
    public ResponseEntity<List<Event>> getInterestedEvents(Authentication authentication) {
        Optional<User> userOptional = userService.getLoggedInUser(authentication);

        User user = userOptional.get();
        return ResponseEntity.ok(eventService.getInterestedEvents(user.getId()));
    }
}

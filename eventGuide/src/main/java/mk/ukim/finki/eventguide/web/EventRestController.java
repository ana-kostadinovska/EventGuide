package mk.ukim.finki.eventguide.web;

import mk.ukim.finki.eventguide.model.Event;
import mk.ukim.finki.eventguide.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:8080")
public class EventRestController {

    private final EventService eventService;

    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    private List<Event> findAll() {
        return this.eventService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> findById(@PathVariable Long id) {
        return this.eventService.findById(id)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Updated interested endpoint
    @PostMapping("/interested/{id}")
    public ResponseEntity<Map<String, Integer>> toggleInterested(@PathVariable Long id) {
        Event updatedEvent = eventService.interested(id);  // Get the updated event after incrementing interested count
        Map<String, Integer> response = Map.of("interestedCount", updatedEvent.getInterested());
        return ResponseEntity.ok(response);  // Return just the updated count in the response
    }


    @PostMapping("/add")
    public ResponseEntity<Event> save(
            @RequestParam String name,
            @RequestParam String artist,
            @RequestParam String description,
            @RequestParam LocalDate date,
            @RequestParam LocalTime time,
            @RequestParam Long local_id
    ) {
        return this.eventService.save(name, artist, description, date, time, local_id)
                .map(event -> ResponseEntity.ok().body(event))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Event> save(@PathVariable Long id,
                                      @RequestParam String name,
                                      @RequestParam String artist,
                                      @RequestParam String description,
                                      @RequestParam LocalDate date,
                                      @RequestParam LocalTime time,
                                      @RequestParam Long local_id) {
        return this.eventService.edit(id, name, artist, description, date, time, local_id)
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

    @GetMapping("/interested/{userId}")
    public ResponseEntity<List<Event>> getInterestedEvents(@PathVariable Long userId) {
        return ResponseEntity.ok(eventService.getInterestedEvents(userId));
    }
}

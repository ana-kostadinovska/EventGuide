package mk.ukim.finki.eventguide.web;

import mk.ukim.finki.eventguide.model.Event;
import mk.ukim.finki.eventguide.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/events")
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
        return this.eventService.edit(id,name, artist, description, date, time, local_id)
                .map(event -> ResponseEntity.ok().body(event))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        this.eventService.deleteById(id);
        if(this.eventService.findById(id).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

}

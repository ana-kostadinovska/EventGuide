package mk.ukim.finki.eventguide.web;

import mk.ukim.finki.eventguide.model.*;
import mk.ukim.finki.eventguide.service.LocalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/locals")
@CrossOrigin(origins = "http://localhost:8080")
public class LocalRestController {

    private final LocalService localService;

    public LocalRestController(LocalService localService) {
        this.localService = localService;
    }

    @GetMapping
    public List<Local> findAll() {
        return this.localService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Local> findById(@PathVariable Long id) {
        return this.localService.findById(id)
                .map(local -> ResponseEntity.ok().body(local))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Local> save(
            @RequestParam String name,
            @RequestParam String location,
            @RequestParam String workingHours,
            @RequestParam int contact_number,
            @RequestParam LocalType type,
            @RequestParam List<Event> events
    ) {
        return this.localService.save(name, location, workingHours, contact_number, type, events)
                .map(local -> ResponseEntity.ok().body(local))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Local> edit(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String location,
            @RequestParam String workingHours,
            @RequestParam int contact_number,
            @RequestParam LocalType type,
            @RequestParam List<Event> events
    ) {
        return this.localService.edit(id,name, location, workingHours, contact_number, type, events)
                .map(local -> ResponseEntity.ok().body(local))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        this.localService.deleteById(id);
        if(this.localService.findById(id).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}/events")
    public ResponseEntity<List<Event>> getEventsByLocalId(@PathVariable Long id) {
        return this.localService.findById(id)
                .map(local -> ResponseEntity.ok().body(local.getEvents()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

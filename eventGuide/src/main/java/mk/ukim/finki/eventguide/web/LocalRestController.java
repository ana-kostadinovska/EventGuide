package mk.ukim.finki.eventguide.web;

import mk.ukim.finki.eventguide.model.*;
import mk.ukim.finki.eventguide.model.dto.LocalUpdateRequest;
import mk.ukim.finki.eventguide.service.EventService;
import mk.ukim.finki.eventguide.service.LocalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping("/api/locals")
@CrossOrigin(origins = "http://localhost:8080")
public class LocalRestController {

    private final LocalService localService;
    private final EventService eventService;

    public LocalRestController(LocalService localService, EventService eventService) {
        this.localService = localService;
        this.eventService = eventService;
    }

    @GetMapping
    public List<Local> findPaginatedLocals(@RequestParam int page) {
        return this.localService.findPaginatedLocals(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Local> findById(@PathVariable Long id) {
        var local = this.localService.findById(id);
        if(local.isPresent()) {
            var localEvents = local.get().getEvents().stream().filter(event -> event.getStatus() == EventRequest.RequestStatus.APPROVED).toList();
            local.get().setEvents(localEvents);
            return ResponseEntity.ok(local.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Local> save(
            @RequestBody LocalUpdateRequest request
    ) {
        return this.localService.save(request.getName(), request.getLocation(), request.getWorkingHours(), request.getWorkingHours(),request.getWebsite(), request.getImage(), request.getType(), null)
                .map(local -> ResponseEntity.ok().body(local))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Local> edit(
            @PathVariable Long id,
            @RequestBody LocalUpdateRequest request
    ) {
        return this.localService.edit(
                        id,
                        request.getName(),
                        request.getLocation(),
                        request.getWorkingHours(),
                        request.getContactNumber(),
                        request.getWebsite(),
                        request.getImage(),
                        request.getType()
                ).map(local -> ResponseEntity.ok().body(local))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        this.localService.deleteById(id);
        if (this.localService.findById(id).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}/events")
    public ResponseEntity<List<Event>> getEventsByLocalId(@PathVariable Long id) {
        Optional<Local> local = this.localService.findById(id);

        if(local.isPresent()) {
            List<Event> events = this.eventService.findByStatus(EventRequest.RequestStatus.APPROVED);
            return ResponseEntity.ok().body(events);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filter")
    public List<Local> getLocalsByType(@RequestParam LocalType type, @RequestParam int page) {
        return localService.findByType(type, page);
    }
}

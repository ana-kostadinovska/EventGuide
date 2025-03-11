package mk.ukim.finki.eventguide.web;

import mk.ukim.finki.eventguide.model.Event;
import mk.ukim.finki.eventguide.model.EventRequest;
import mk.ukim.finki.eventguide.service.EventService;
import mk.ukim.finki.eventguide.service.LocalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
    private final LocalService localService;
    private final EventService eventService;

    public AdminRestController(LocalService localService, EventService eventService) {
        this.localService = localService;
        this.eventService = eventService;
    }

    @GetMapping("/pending-events")
    private List<Event> getPendingEvents() {
        return this.eventService.findByStatus(EventRequest.RequestStatus.PENDING);
    }

    @PostMapping("/setStatus/{id}")
    public ResponseEntity<?> updateEventStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> requestBody) {

        Optional<Event> eventOptional = eventService.findById(id);
        if (eventOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Event event = eventOptional.get();

        try {
            // Convert string to enum
            EventRequest.RequestStatus newStatus = EventRequest.RequestStatus.valueOf(requestBody.get("status"));
            if(newStatus == EventRequest.RequestStatus.APPROVED) {
                eventService.approveEvent(id);
            }
            if(newStatus == EventRequest.RequestStatus.REJECTED) {
                eventService.rejectEvent(id);
            }
            if(newStatus == EventRequest.RequestStatus.DISCARDED) {
                eventService.discardEvent(id);
            }
            return ResponseEntity.ok("Event status updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status provided.");
        }
    }
}

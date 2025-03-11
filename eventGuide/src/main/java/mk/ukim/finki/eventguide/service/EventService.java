package mk.ukim.finki.eventguide.service;

import mk.ukim.finki.eventguide.model.Event;
import mk.ukim.finki.eventguide.model.EventRequest;
import mk.ukim.finki.eventguide.model.Local;
import mk.ukim.finki.eventguide.model.User;
import mk.ukim.finki.eventguide.model.dto.EventAddRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface EventService {

    List<Event> findAll();
    List<Event> findByStatus(EventRequest.RequestStatus status);
    Optional<Event> findById(Long id);
    Optional<Event> save(EventAddRequest eventAddRequest, User user);
    Optional<Event> edit(Long id,String name, String artist, String description, LocalDate date, LocalTime time, Long local_id);
    void deleteById(Long id);
    List<Event> searchEvents(String name, String location);
    List<Event> filterEventsByDate(LocalDate date);
    Event interested(Long id, User user);
    List<Event> getInterestedEvents(Long userId);
    Optional<Event> rejectEvent(Long id);
    Optional<Event> approveEvent(Long id);
    Optional<Event> discardEvent(Long id);
}

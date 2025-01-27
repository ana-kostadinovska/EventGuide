package mk.ukim.finki.eventguide.service;

import mk.ukim.finki.eventguide.model.Event;
import mk.ukim.finki.eventguide.model.Local;
import mk.ukim.finki.eventguide.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> findAll();
    Optional<Event> findById(Long id);
    Optional<Event> save(String name, String artist, String description, LocalDate date, LocalTime time, Long local_id);
    Optional<Event> edit(Long id,String name, String artist, String description, LocalDate date, LocalTime time, Long local_id);
    void deleteById(Long id);
}

package mk.ukim.finki.eventguide.service;

import mk.ukim.finki.eventguide.model.Event;

import java.util.Optional;

public interface EventService {

    Optional<Event> findById(Long id);

    void deleteById(Long id);
}

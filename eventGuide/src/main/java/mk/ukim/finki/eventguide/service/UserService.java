package mk.ukim.finki.eventguide.service;

import mk.ukim.finki.eventguide.model.Event;
import mk.ukim.finki.eventguide.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> save(String username, String name, String surname, String email, List<Event> events);

    Optional<User> edit(Long id, String username, String name, String surname, String email, List<Event> events);
    void deleteById(Long id);
}

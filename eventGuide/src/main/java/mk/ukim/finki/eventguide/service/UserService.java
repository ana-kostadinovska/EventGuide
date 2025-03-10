package mk.ukim.finki.eventguide.service;

import mk.ukim.finki.eventguide.model.Event;
import mk.ukim.finki.eventguide.model.User;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    void checkAndAddUser(String sub, String username, String name, String surname, String email);
    Optional<User> edit(Long id, String username, String name, String surname, String email, List<Event> events);
    void deleteById(Long id);
    Optional<User> addInterest(Long userId, Long eventId);
    Optional<User> removeInterest(Long userId, Long eventId);
    Optional<User> getLoggedInUser(Authentication authentication);
    Optional<User> findBySub(String sub);
    Map<String, Object> getFrontendUserData(Authentication authentication);
}

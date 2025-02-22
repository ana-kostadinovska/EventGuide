package mk.ukim.finki.eventguide.service.implementation;

import jakarta.transaction.Transactional;
import mk.ukim.finki.eventguide.model.Event;
import mk.ukim.finki.eventguide.model.User;
import mk.ukim.finki.eventguide.repository.EventRepository;
import mk.ukim.finki.eventguide.repository.UserRepository;
import mk.ukim.finki.eventguide.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public UserServiceImpl(UserRepository userRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

//    @Override
//    public Optional<User> save(String username, String name, String surname, String email, List<Event> events) {
//        User user=new User(username,name,surname,email,events);
//        return Optional.of(this.userRepository.save(user));
//    }


    @Override
    public Optional<User> edit(Long id, String username, String name, String surname, String email, List<Event> events) {
        User user = userRepository.findById(id).get();
        user.setUsername(username);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        return Optional.of(this.userRepository.save(user));
    }
    @Override
    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<User> addInterest(Long userId, Long eventId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Event> eventOptional = eventRepository.findById(eventId);

        if (userOptional.isPresent() && eventOptional.isPresent()) {
            User user = userOptional.get();
            Event event = eventOptional.get();

            user.getInterest().add(event);
            event.getInterestedUsers().add(user);

            event.setInterested(event.getInterestedUsers().size()); // Update interest count

            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> removeInterest(Long userId, Long eventId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Event> eventOptional = eventRepository.findById(eventId);

        if (userOptional.isPresent() && eventOptional.isPresent()) {
            User user = userOptional.get();
            Event event = eventOptional.get();

            user.getInterest().remove(event);
            event.getInterestedUsers().remove(user);

            event.setInterested(event.getInterestedUsers().size()); // Update interest count

            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }

    public void checkAndAddUser(String sub, String username, String name, String surname, String email) {
        Optional<User> existingUser = userRepository.findBySub(sub);
        if (existingUser.isEmpty()) {
            User newUser = new User(sub, username, name, surname, email, List.of());
            userRepository.save(newUser);
        }
    }

    public Optional<User> findBySub(String sub) {
        return userRepository.findBySub(sub);
    }


    @Override
    public Optional<User> getLoggedInUser(Authentication authentication) {
        if (authentication instanceof JwtAuthenticationToken jwtAuthToken) {
            String sub = jwtAuthToken.getToken().getClaimAsString("sub");
            return userRepository.findBySub(sub);
        }
        return Optional.empty();
    }
}

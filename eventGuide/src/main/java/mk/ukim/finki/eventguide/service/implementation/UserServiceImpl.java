package mk.ukim.finki.eventguide.service.implementation;

import jakarta.transaction.Transactional;
import mk.ukim.finki.eventguide.model.Event;
import mk.ukim.finki.eventguide.model.Role;
import mk.ukim.finki.eventguide.model.User;
import mk.ukim.finki.eventguide.model.dto.UserDTO;
import mk.ukim.finki.eventguide.model.projections.UserWithRolesProjection;
import mk.ukim.finki.eventguide.repository.EventRepository;
import mk.ukim.finki.eventguide.repository.RoleRepository;
import mk.ukim.finki.eventguide.repository.UserRepository;
import mk.ukim.finki.eventguide.service.UserService;
import org.hibernate.Hibernate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, EventRepository eventRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> edit(Long id, String username, String name, String surname, String email, List<Event> events) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(username);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        return Optional.of(userRepository.save(user));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<User> addInterest(Long userId, Long eventId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Event> eventOptional = eventRepository.findById(eventId);

        if (userOptional.isPresent() && eventOptional.isPresent()) {
            User user = userOptional.get();
            Event event = eventOptional.get();

            if (user.getInterest().contains(event)) {
                user.getInterest().remove(event);
                event.getInterestedUsers().remove(user);
            } else {
                user.getInterest().add(event);
                event.getInterestedUsers().add(user);
            }

            event.setInterested(event.getInterestedUsers().size());
            eventRepository.save(event);
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

            event.setInterested(event.getInterestedUsers().size());

            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void checkAndAddUser(String sub, String username, String name, String surname, String email) {
        Optional<User> existingUser = userRepository.findBySub(sub);
        if (existingUser.isEmpty()) {
            Role userRole = roleRepository.findByName("USER").orElse(null);
            if (userRole == null) {
                userRole = roleRepository.save(new Role("ROLE_USER"));
            }

            Set<Role> roles = Collections.singleton(userRole);
            User newUser = new User(sub, username, name, surname, email);
            newUser.setRoles(roles);
            userRepository.save(newUser);
        }
    }


    @Override
    public Optional<User> findBySub(String sub) {
        return userRepository.findBySub(sub);
    }

    @Override
    @Transactional
    public Optional<User> getLoggedInUser(Authentication authentication) {
        if (authentication instanceof JwtAuthenticationToken jwtAuthToken) {
            String sub = jwtAuthToken.getToken().getClaimAsString("sub");
            Optional<User> user = userRepository.findBySub(sub);

            user.ifPresent(u -> u.getRoles().size()); // Initialize roles
            return user;
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Map<String, Object> getFrontendUserData(Authentication authentication) {
        if (authentication == null) return Map.of();

        String sub = authentication.getName();
        List<UserWithRolesProjection> results = userRepository.findBySubWithRolesNative(sub);

        if (results.isEmpty()) return Map.of();

        UserWithRolesProjection first = results.get(0);

        Set<String> roles = results.stream()
                .map(UserWithRolesProjection::getRoleName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return Map.of(
                "username", first.getUsername(),
                "email", first.getEmail(),
                "roles", roles
        );
    }


    public UserDTO getUserWithRoles(Authentication authentication) {
        if (authentication == null) throw new RuntimeException("User not authenticated");
        System.out.println("User sub " + authentication.getName());
        String sub = authentication.getName();
        var user = userRepository.findBySub(sub);

        if (user.isPresent()) {
            var roles = user.get().getRoles();
            var roleNames = roles.stream().map(Role::getName).toList();
            return new UserDTO(user.get().getId(), user.get().getSub(), user.get().getUsername(), user.get().getEmail(), roleNames);
        } else {
            throw new RuntimeException("User not found");
        }
    }

}

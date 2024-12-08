package mk.ukim.finki.eventguide.service;

import mk.ukim.finki.eventguide.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);

    void deleteById(Long id);
}

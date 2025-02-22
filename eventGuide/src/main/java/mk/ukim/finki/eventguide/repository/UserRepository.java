package mk.ukim.finki.eventguide.repository;

import mk.ukim.finki.eventguide.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySub(String sub);
}

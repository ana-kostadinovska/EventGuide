package mk.ukim.finki.eventguide.repository;

import mk.ukim.finki.eventguide.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

package mk.ukim.finki.eventguide.repository;

import mk.ukim.finki.eventguide.model.Local;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalRepository extends JpaRepository<Local, Long> {
}

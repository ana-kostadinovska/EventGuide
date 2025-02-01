package mk.ukim.finki.eventguide.repository;

import mk.ukim.finki.eventguide.model.Local;
import mk.ukim.finki.eventguide.model.LocalType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalRepository extends JpaRepository<Local, Long> {
    List<Local> findByType(LocalType type);
}

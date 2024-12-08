package mk.ukim.finki.eventguide.repository;

import mk.ukim.finki.eventguide.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}

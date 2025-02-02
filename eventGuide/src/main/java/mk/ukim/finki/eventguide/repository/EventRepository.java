package mk.ukim.finki.eventguide.repository;

import mk.ukim.finki.eventguide.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByInterestedUsers_Id(Long userId);
}

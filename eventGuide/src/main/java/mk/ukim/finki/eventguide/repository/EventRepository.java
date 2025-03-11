package mk.ukim.finki.eventguide.repository;

import mk.ukim.finki.eventguide.model.Event;
import mk.ukim.finki.eventguide.model.EventRequest;
import mk.ukim.finki.eventguide.model.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByNameContainingIgnoreCase(String name);

    List<Event> findByLocalLocationContainingIgnoreCase(String location);

    List<Event> findByDate(LocalDate date);

    List<Event> findByInterestedUsers_Id(Long userId);

    List<Event> findByStatus(EventRequest.RequestStatus status);
}

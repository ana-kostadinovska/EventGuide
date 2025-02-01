package mk.ukim.finki.eventguide.service;

import mk.ukim.finki.eventguide.model.Event;
import mk.ukim.finki.eventguide.model.Local;
import mk.ukim.finki.eventguide.model.LocalType;
import org.hibernate.jdbc.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocalService {
    List<Local> findAll();
    Optional<Local> findById(Long id);
    Optional<Local> save(String name, String location, String workingHours, String contact_number, LocalType type, List<Event>events);
    Optional<Local> edit(Long id,String name, String location, String workingHours, String contact_number, LocalType type, List<Event>events);
    void deleteById(Long id);
    List<Local> findByType(LocalType type);
}

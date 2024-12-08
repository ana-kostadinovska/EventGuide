package mk.ukim.finki.eventguide.service;

import mk.ukim.finki.eventguide.model.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalService {

    Optional<Local> findById(Long id);

    void deleteById(Long id);
}

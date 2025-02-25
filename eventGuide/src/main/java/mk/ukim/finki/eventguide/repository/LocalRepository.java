package mk.ukim.finki.eventguide.repository;

import mk.ukim.finki.eventguide.model.Local;
import mk.ukim.finki.eventguide.model.LocalType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalRepository extends JpaRepository<Local, Long> {
    Page<Local> findByType(LocalType type,Pageable pageable);
    Page<Local> findAll(Pageable pageable);
}

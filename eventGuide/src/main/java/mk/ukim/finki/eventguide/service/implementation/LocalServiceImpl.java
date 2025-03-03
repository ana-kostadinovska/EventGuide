package mk.ukim.finki.eventguide.service.implementation;

import mk.ukim.finki.eventguide.model.*;
import mk.ukim.finki.eventguide.repository.LocalRepository;
import mk.ukim.finki.eventguide.service.LocalService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocalServiceImpl implements LocalService {

    private final LocalRepository localRepository;

    public LocalServiceImpl(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    @Override
    public List<Local> findAll() {
        return this.localRepository.findAll();
    }

    @Override
    public Optional<Local> findById(Long id) {
        return this.localRepository.findById(id);
    }

    @Override
    public Optional<Local> save(String name, String location, String workingHours, String contact_number, String website, LocalType type, List<Event> events) {
        Local local = new Local(name, location, workingHours, contact_number, website, type, events);
        return Optional.of(this.localRepository.save(local));
    }

    @Override
    public Optional<Local> edit(Long id, String name, String location, String workingHours, String contact_number, LocalType type) {
        Local local = localRepository.findById(id).get();
        local.setName(name);
        local.setLocation(location);
        local.setWorkingHours(workingHours);
        local.setContact_number(contact_number);
        local.setType(type);
        return Optional.of(this.localRepository.save(local));
    }

    @Override
    public void deleteById(Long id) {
        this.localRepository.deleteById(id);
    }

    @Override
    public List<Local> findByType(LocalType type, int page) {
        Pageable pageable = PageRequest.of(page, 9);
        return localRepository.findByType(type, pageable).getContent();
    }

    @Override
    public List<Local> findPaginatedLocals(int page) {
        Pageable pageable = PageRequest.of(page, 9);
        return this.localRepository.findAll(pageable).getContent();
    }
}

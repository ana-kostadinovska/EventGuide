package mk.ukim.finki.eventguide.service.implementation;

import mk.ukim.finki.eventguide.model.*;
import mk.ukim.finki.eventguide.repository.LocalRepository;
import mk.ukim.finki.eventguide.service.LocalService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Optional<Local> save(String name, String location, String workingHours, int contact_number, LocalType type, List<Event> events) {
        Local local=new Local(name,location,workingHours,contact_number,type,events);
        return Optional.of(this.localRepository.save(local));
    }

    @Override
    public Optional<Local> edit(Long id, String name, String location, String workingHours, int contact_number, LocalType type, List<Event> events) {
        Local local=localRepository.findById(id).get();
        local.setName(name);
        local.setLocation(location);
        local.setWorkingHours(workingHours);
        local.setContact_number(contact_number);
        local.setType(type);
        local.setEvents(events);
        return Optional.of(this.localRepository.save(local));
    }

    @Override
    public void deleteById(Long id) {
        this.localRepository.deleteById(id);
    }
}

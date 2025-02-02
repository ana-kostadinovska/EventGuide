package mk.ukim.finki.eventguide.service.implementation;

import mk.ukim.finki.eventguide.model.Event;
import mk.ukim.finki.eventguide.model.Local;
import mk.ukim.finki.eventguide.model.LocalType;
import mk.ukim.finki.eventguide.model.User;
import mk.ukim.finki.eventguide.repository.EventRepository;
import mk.ukim.finki.eventguide.repository.LocalRepository;
import mk.ukim.finki.eventguide.repository.UserRepository;
import mk.ukim.finki.eventguide.service.EventService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final LocalRepository localRepository;

    public EventServiceImpl(EventRepository eventRepository, LocalRepository localRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.localRepository = localRepository;
    }

    @Override
    public List<Event> findAll() {
        return this.eventRepository.findAll();
    }

    @Override
    public Optional<Event> findById(Long id) {
        return this.eventRepository.findById(id);
    }

    @Override
    public Optional<Event> save(String name, String artist, String description, LocalDate date, LocalTime time, Long local_id) {
        Local local=this.localRepository.findById(local_id).get();
        Event event = new Event(name, artist, description, date, time, local);
        return Optional.of(this.eventRepository.save(event));
    }

    @Override
    public Optional<Event> edit(Long id, String name, String artist, String description, LocalDate date, LocalTime time, Long local_id) {
        Event event = this.eventRepository.findById(id).get();
        event.setName(name);
        event.setArtist(artist);
        event.setDescription(description);
        event.setDate(date);
        event.setTime(time);
        Local local=this.localRepository.findById(local_id).get();
        event.setLocal(local);
        return Optional.of(this.eventRepository.save(event));
    }

    @Override
    public void deleteById(Long id) {
        this.eventRepository.deleteById(id);
    }

    @Override
    public List<Event> searchEvents(String name, String location) {
        Set<Event> result = new HashSet<>();

        if (name != null && !name.isEmpty()) {
            result.addAll(this.eventRepository.findByNameContainingIgnoreCase(name));
        }

        if (location != null && !location.isEmpty()) {
            result.addAll(this.eventRepository.findByLocalLocationContainingIgnoreCase(location));
        }

        if (name == null && location == null) {
            return this.eventRepository.findAll();
        }

        return new ArrayList<>(result);
    }

    @Override
    public List<Event> filterEventsByDate(LocalDate date) {
        return this.eventRepository.findByDate(date);
    
    @Override   
    public Event interested(Long id) {
        Event event=findById(id).get();
        event.setInterested(event.getInterested()+1);
        return eventRepository.save(event);
    }
    @Override
    public List<Event> getInterestedEvents(Long userId) {
        return eventRepository.findByInterestedUsers_Id(userId);
    }

}

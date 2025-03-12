package mk.ukim.finki.eventguide.service.implementation;

import mk.ukim.finki.eventguide.model.Event;
import mk.ukim.finki.eventguide.model.EventRequest;
import mk.ukim.finki.eventguide.model.Local;
import mk.ukim.finki.eventguide.model.User;
import mk.ukim.finki.eventguide.model.dto.EventAddRequest;
import mk.ukim.finki.eventguide.repository.EventRepository;
import mk.ukim.finki.eventguide.repository.LocalRepository;
import mk.ukim.finki.eventguide.repository.UserRepository;
import mk.ukim.finki.eventguide.service.EventService;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final LocalRepository localRepository;
    private final UserRepository userRepository;

    public EventServiceImpl(EventRepository eventRepository, LocalRepository localRepository, UserRepository userRepository, UserRepository userRepository1) {
        this.eventRepository = eventRepository;
        this.localRepository = localRepository;
        this.userRepository = userRepository1;
    }

    @Override
    public List<Event> findAll() {
        return this.eventRepository.findAll();
    }

    @Override
    public List<Event> findByStatus(EventRequest.RequestStatus status) {
        return this.eventRepository.findByStatus(status);
    }

    @Override
    public Optional<Event> findById(Long id) {
        return this.eventRepository.findById(id);
    }

    @Override
    public Optional<Event> save(EventAddRequest eventAddRequest, User user) {
        Local local = this.localRepository.findById(eventAddRequest.local_id).get();

        var tce = user.getTotalCreatedEvents() + 1;
        user.setTotalCreatedEvents(tce);
        userRepository.save(user);

        Event event = new Event(eventAddRequest.name, eventAddRequest.artist, eventAddRequest.description, eventAddRequest.date, eventAddRequest.time, eventAddRequest.referenceUrl, local, user);
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
        Local local = this.localRepository.findById(local_id).get();
        event.setLocal(local);
        if(event.getStatus() != EventRequest.RequestStatus.APPROVED){
            event.setStatus(EventRequest.RequestStatus.APPROVED);
            var eventCreator = event.getUser();
            var score = eventCreator.getScore();
            score += 100;
            eventCreator.setScore(score);
            userRepository.save(eventCreator);
        }
        return Optional.of(this.eventRepository.save(event));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Event event = this.eventRepository.findById(id).get();

        Set<User> interestedUsers = event.getInterestedUsers();
        for (User user : interestedUsers) {
            user.getInterest().remove(event);
        }

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
    }

    @Override
    public Event interested(Long id, User user) {
        Event event = findById(id).get();
        return eventRepository.save(event);
    }

    @Override
    public List<Event> getInterestedEvents(Long userId) {
        return eventRepository.findByInterestedUsers_Id(userId);
    }

    @Override
    public Optional<Event> rejectEvent(Long id) {
        var eventOptional = eventRepository.findById(id);
        if(eventOptional.isPresent()) {
            var event = eventOptional.get();
            var eventCreator = event.getUser();
            var score = eventCreator.getScore();
            score += 0;
            eventCreator.setScore(score);
            userRepository.save(eventCreator);
            eventRepository.delete(event);
            return Optional.of(event);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Event> approveEvent(Long id) {
        var eventOptional = eventRepository.findById(id);
        if(eventOptional.isPresent()) {
            var event = eventOptional.get();
            event.setStatus(EventRequest.RequestStatus.APPROVED);
            eventRepository.save(event);

            var eventCreator = event.getUser();
            var score = eventCreator.getScore();
            score += 100;
            eventCreator.setScore(score);

            userRepository.save(eventCreator);
            return Optional.of(event);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Event> discardEvent(Long id) {
        var eventOptional = eventRepository.findById(id);
        if(eventOptional.isPresent()) {
            var event = eventOptional.get();
            var eventCreator = event.getUser();
            var score = eventCreator.getScore();
            score += 80;
            eventCreator.setScore(score);
            userRepository.save(eventCreator);
            eventRepository.delete(event);
            return Optional.of(event);
        } else {
            return Optional.empty();
        }
    }

}

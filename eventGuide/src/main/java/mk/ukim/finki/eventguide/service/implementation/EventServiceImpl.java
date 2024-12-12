package mk.ukim.finki.eventguide.service.implementation;

import mk.ukim.finki.eventguide.model.Event;
import mk.ukim.finki.eventguide.repository.EventRepository;
import mk.ukim.finki.eventguide.service.EventService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Optional<Event> findById(Long id) {
        return this.eventRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.eventRepository.deleteById(id);
    }

}

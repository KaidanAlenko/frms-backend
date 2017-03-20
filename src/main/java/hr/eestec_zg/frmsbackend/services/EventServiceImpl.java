package hr.eestec_zg.frmsbackend.services;

import hr.eestec_zg.frmsbackend.domain.EventRepository;
import hr.eestec_zg.frmsbackend.domain.models.Event;
import hr.eestec_zg.frmsbackend.exceptions.EventNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) { this.eventRepository = eventRepository; }

    @Override
    public void createEvent(Event event) {

        if (event == null) {
            throw new IllegalArgumentException("Event not defined");
        }

        eventRepository.createEvent(event);
    }

    @Override
    public void updateEvent(Event event) {

        if (event == null) {
            throw new IllegalArgumentException("Event not defined");
        }

        Event event1 = getEventById(event.getId());
        if (event1 == null) {
            throw new EventNotFoundException();
        }

        eventRepository.updateEvent(event);
    }

    @Override
    public void deleteEvent(Long eventId) {

        if (eventId == null) {
            throw new IllegalArgumentException("Event Id not defined");
        }

        Event event = eventRepository.getEvent(eventId);
        if (event == null) {
            throw new IllegalArgumentException("Event not found in database");
        }

        eventRepository.deleteEvent(event);
    }

    @Override
    public Event getEventById(Long eventId) {

        if (eventId == null) {
            throw new IllegalArgumentException("Event Id not defined");
        }

        Event event = eventRepository.getEvent(eventId);
        if (event == null) {
            throw new EventNotFoundException();
        }

        return event;
    }

    @Override
    public Event getEventByName(String name) {

        if (name == null) {
            throw new IllegalArgumentException("Event name not defined");
        }

        Event event = eventRepository.getEventByName(name);
        if (event == null) {
            throw new EventNotFoundException();
        }

        return event;
    }

    @Override
    public List<Event> getEventsByYear(String year) {

        if (year == null) {
            throw new IllegalArgumentException("Event year not defined");
        }

        List<Event> events = eventRepository.getEventsByYear(year);
        if (events == null) {
            throw new EventNotFoundException();
        }

        return events;
    }

    @Override
    public List<Event> getEvents() {
        return eventRepository.getEvents();
    }
}

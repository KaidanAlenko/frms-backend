package hr.eestec_zg.frmsbackend.domain;

import hr.eestec_zg.frmsbackend.domain.models.Event;

import java.util.List;
import java.util.function.Predicate;

public interface EventRepository {
    void createEvent(Event event);

    void updateEvent(Event event);

    void deleteEvent(Event event);

    Event getEvent(Long id);

    Event getEventByName(String name);

    List<Event> getEventsByYear(String year);

    List<Event> getEvents(Predicate<Event> condition);

    default List<Event> getEvents() {
        return getEvents(e -> true);
    }
}

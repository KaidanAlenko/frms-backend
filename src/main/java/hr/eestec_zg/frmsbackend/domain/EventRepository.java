package hr.eestec_zg.frmsbackend.domain;

import hr.eestec_zg.frmsbackend.domain.models.Event;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public interface EventRepository {
    void createEvent(Event event);

    void updateEvent(Event event);

    void deleteEvent(Event event);

    Event getEvent(CriteriaQuery<Event> criteria);

    List<Event> getEvents(CriteriaQuery<Event> criteria);
}

package hr.eestec_zg.frmsbackend.domain;

import hr.eestec_zg.frmsbackend.domain.models.Event;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
@Transactional
public class EventRepositoryImpl extends AbstractRepository<Long, Event> implements EventRepository {

    @Override
    public void createEvent(Event event) {
        persist(event);
    }

    @Override
    public void updateEvent(Event event) {
        update(event);
    }

    @Override
    public void deleteEvent(Event event) {
        delete(event);
    }

    @Override
    public Event getEvent(CriteriaQuery<Event> criteria) {
        try {
            return getSession().createQuery(criteria).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Event> getEvents(CriteriaQuery<Event> criteria) {
        return getSession().createQuery(criteria).getResultList();
    }
}

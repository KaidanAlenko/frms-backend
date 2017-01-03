package hr.eestec_zg.frmsbackend.domain;

import hr.eestec_zg.frmsbackend.domain.models.Event;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
@Transactional
public class DatabaseBackedEventRepository extends AbstractRepository<Long, Event> implements EventRepository {

    private static final String NAME = "name";
    private static final String SHORT_NAME = "shortName";
    private static final String YEAR = "year";

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
    public Event getEvent(Long id) {
        return getByKey(id);
    }

    @Override
    public Event getEventByName(String name) {
        String searchTerm = "%" + name.toLowerCase() + "%";
        CriteriaBuilder cb = criteriaBuilder();
        CriteriaQuery<Event> query = cb.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);
        query.where(
                cb.or(
                        cb.like(cb.lower(root.get(NAME)), searchTerm),
                        cb.like(cb.lower(root.get(SHORT_NAME)), searchTerm)));
        return getEvent(query.select(root));
    }

    @Override
    public List<Event> getEventsByYear(String year) {
        CriteriaBuilder cb = criteriaBuilder();
        CriteriaQuery<Event> query = cb.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);
        query.where(cb.equal(root.get(YEAR), year));
        return getEvents(query.select(root));
    }

    @Override
    public List<Event> getEvents(Predicate<Event> condition) {
        CriteriaQuery<Event> query = criteriaBuilder().createQuery(Event.class);
        Root<Event> root = query.from(Event.class);
        return getEvents(query.select(root))
                .stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

    private Event getEvent(CriteriaQuery<Event> query) {
        try {
            return getSession().createQuery(query).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    private List<Event> getEvents(CriteriaQuery<Event> query) {
        return getSession().createQuery(query).getResultList();
    }
}

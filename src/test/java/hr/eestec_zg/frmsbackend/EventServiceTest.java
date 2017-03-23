package hr.eestec_zg.frmsbackend;

import hr.eestec_zg.frmsbackend.domain.models.Event;
import hr.eestec_zg.frmsbackend.exceptions.EventNotFoundException;
import hr.eestec_zg.frmsbackend.services.EventService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class EventServiceTest extends TestBase {

    @Autowired
    private EventService eventService;

    private Event event;
    private Event event3;

    @Before
    public void setTestData() {
        event = new Event("span", "S", "2017");
        Event event2 = new Event("infobip", "IB", "2017");
        eventRepository.createEvent(event);
        eventRepository.createEvent(event2);
    }

    @Test
    public void testGetEventByName() {
        Event event3 = eventService.getEventByName("span");
        assertNotNull(event3);
    }

    @Test
    public void testGetEventById() {
        Event event3 = eventService.getEventById(event.getId());
        assertEquals(event, event3);
    }

    @Test(expected = EventNotFoundException.class)
    public void testGetEventByNameFail() {
        eventService.getEventByName("pasn");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentException() {
        eventService.deleteEvent(null);
    }

    @Test(expected = EventNotFoundException.class)
    public void testEventNotFoundExceptionDelete() {
        Event event3 = eventService.getEventByName("pasn");
        eventService.deleteEvent(event3.getId());
    }

    @Test(expected = EventNotFoundException.class)
    public void testEventNotFoundExceptionUpdate() {
        Event event3 = new Event("spaasdasdadn", "S", "2017");

        eventService.updateEvent(event3);
    }

    @Test
    public void testUpdateEvent() {
        Event event3 = eventService.getEventByName("span");
        event3.setShortName("bla");
        eventService.updateEvent(event3);

        Event event4 = eventService.getEventByName("span");
        String year = event4.getYear();
        String shortName = event4.getShortName();
        assertEquals("2017", year);
        assertEquals("bla", shortName);
    }

    @Test
    public void testGetEventsByYear() {
        event3 = new Event("globallogic", "GL", "2015");
        eventService.createEvent(event3);

        List<Event> events = eventService.getEventsByYear("2015");
        event = eventService.getEventById(event3.getId());

        assertEquals(1, events.size());
        assertEquals(event3, event);
        assertTrue("There is no event with name " + event3.getName() + " stored", events.contains(event3));
    }

    @Test
    public void testGetEventsByYearFail() {
        List<Event> events = eventService.getEventsByYear("1991");
        assertEquals(0, events.size());
    }

    @Test
    public void testGetEvents() {
        List<Event> events = eventService.getEvents();
        assertEquals(2, events.size());
    }

    @Test
    public void testCreateDeleteEvent() {
        List<Event> events = eventService.getEventsByYear("2017");
        assertEquals(2, events.size());

        Long a = event.getId();
        eventService.deleteEvent(a);
        a++;
        eventService.deleteEvent(a);

        events = eventService.getEventsByYear("2017");
        assertEquals(0, events.size());

        event3 = new Event("globallogic", "GL", "2017");
        eventService.createEvent(event3);
        events = eventService.getEventsByYear("2017");

        assertEquals(1, events.size());
        assertTrue("There is no event with name " + event3.getName() + " stored", events.contains(event3));

    }

}

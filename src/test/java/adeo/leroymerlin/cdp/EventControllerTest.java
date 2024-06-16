package adeo.leroymerlin.cdp;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.util.List;

public class EventControllerTest extends AbstractTest{
    @Mock
    private EventService eventService;
    @InjectMocks
    private EventController eventController;
    @Mock
    List<Event> events;
    @Mock
    Event event;
    @Test
    public void shouldFindAllEventsOk(){
        Mockito.when(eventService.getEvents()).thenReturn(events);
        Assertions.assertThat(eventController.findEvents()).isEqualTo(events);
    }
    @Test
    public void shouldFindEventsByQueryOk(){
        String query = "Query to find";
        Mockito.when(eventService.getFilteredEvents(query)).thenReturn(events);
        Assertions.assertThat(eventController.findEvents(query)).isEqualTo(events);
    }

    @Test
    public void shouldUpdateEventCommentOk(){
        eventController.updateEvent(1L, event);
        Mockito.verify(eventService).updateEvent(1L, event);
    }
    @Test
    public void shouldDeleteEventOk(){
        eventController.deleteEvent(1L);
        Mockito.verify(eventService).delete(1L);
    }
}

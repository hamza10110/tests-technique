package adeo.leroymerlin.cdp;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class EventServiceIT extends AbstractIT{
    @Autowired
    EventService eventService;
    @Autowired
    EventRepository eventRepository;

    @Test
    public void shouldFindAllEventsOk(){
        Assertions.assertThat(eventService.getEvents().size()).isEqualTo(5);
    }
    @Test
    public void shouldUpdateEventReviewOk()  {
        Event event = eventRepository.findById(1000L).get();
        event.setComment("new comment");
        event.setNbStars(5);

        eventService.updateEvent(1000L, event);

        Event updatedEvent = eventRepository.findById(1000L).get();
        Assertions.assertThat(updatedEvent.getComment()).isEqualTo("new comment");
        Assertions.assertThat(updatedEvent.getNbStars()).isEqualTo(5);
    }
    @Test
    public void shouldDeleteEventOk(){
        List<Event> eventsBeforeDelete = eventRepository.findAllBy();
        eventService.delete(1000L);
        List<Event> eventsAfterDelete = eventRepository.findAllBy();

        Assertions.assertThat(eventsBeforeDelete.size()).isEqualTo(5);
        Assertions.assertThat(eventsAfterDelete.size()).isEqualTo(4);
    }
    @Test
    public void shouldGetEventsByQueryOk(){
        Assertions.assertThat(eventService.getFilteredEvents("queen").size()).isEqualTo(5);
    }
    @Test
    public void shouldGetEventsByQueryCaseInsensitiveOk(){
        Assertions.assertThat(eventService.getFilteredEvents("Abi").size()).isEqualTo(1);
    }
    @Test
    public void shouldGetEmptyEventsWhenNotFoundOk(){
        Assertions.assertThat(eventService.getFilteredEvents("QUUU")).isEmpty();
    }
}

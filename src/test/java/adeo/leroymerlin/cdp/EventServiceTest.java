package adeo.leroymerlin.cdp;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.*;

public class EventServiceTest extends AbstractTest{
    @Mock
    EventRepository eventRepository;
    @InjectMocks
    EventService eventService;
    @Mock
    List<Event> events;

    @Test
    public void shouldFindAllEventsOk(){
        Mockito.when(eventRepository.findAllBy()).thenReturn(events);
        Assertions.assertThat(eventService.getEvents()).isEqualTo(events);
    }
    @Test
    public void shouldGetEventsByQueryOk(){
        String query = "default";
        Event newEvent = buildMockEvent();
        Mockito.when(eventRepository.findAllBy()).thenReturn((Collections.singletonList(newEvent)));
        Assertions.assertThat(eventService.getFilteredEvents(query)).hasSize(1);
    }
    @Test
    public void shouldGetEventsByQueryCaseInsensitiveOk(){
        String query = "meMBE";
        Event savedEvent = buildMockEvent();
        Mockito.when(eventRepository.findAllBy()).thenReturn((Collections.singletonList(savedEvent)));
        Assertions.assertThat(eventService.getFilteredEvents(query)).hasSize(1);
    }
    @Test
    public void shouldGetEmptyEventsWhenNotFoundOk(){
        String query = "MM";
        Event newEvent = buildMockEvent();
        Mockito.when(eventRepository.findAllBy()).thenReturn((Collections.singletonList(newEvent)));
        Assertions.assertThat(eventService.getFilteredEvents(query)).isEmpty();
    }
    @Test
    public void shouldUpdateEventReviewOk(){
        Event savedEvent= new Event();
        Event updatedEvent = buildMockEvent();
        Mockito.when(eventRepository.findById(1L)).thenReturn(Optional.of(savedEvent));
        eventService.updateEvent(1L, updatedEvent);

        Mockito.verify(eventRepository).save( savedEvent);
        Assertions.assertThat(savedEvent.getComment()).isEqualTo("default comment");
        Assertions.assertThat(savedEvent.getNbStars()).isEqualTo(4);
    }
    @Test
    public void shouldDeleteEventOk(){
        eventService.delete(1L);
        Mockito.verify(eventRepository).delete(1L);
    }
    private Event buildMockEvent(){
        Event event = new Event();
        event.setId(1L);
        event.setTitle("default title");
        event.setComment("default comment");
        event.setNbStars(4);
        event.setImgUrl("img/1000.jpeg");
        event.setBands(new HashSet<>(Collections.singletonList(buildMockBand())));
        return event;
    }

    private Band buildMockBand() {
        Band band = new Band();
        band.setName("default band's name");
        band.setMembers(new HashSet<>(Collections.singletonList(buildMockMember())));
        return band;
    }

    private Member buildMockMember() {
        Member member = new Member();
        member.setName("default member's name");
        return member;
    }
}

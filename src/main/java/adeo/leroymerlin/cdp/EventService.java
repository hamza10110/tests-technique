package adeo.leroymerlin.cdp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAllBy();
    }

    public void delete(Long id) {
        eventRepository.delete(id);
    }
    public void updateEvent(Long id, Event event) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        eventOptional.ifPresent(eventToUpdate -> {
            eventToUpdate.setComment(event.getComment());
            eventToUpdate.setNbStars(event.getNbStars());
            eventRepository.save(eventToUpdate);
        });
    }

    public List<Event> getFilteredEvents(String query) {
        List<Event> events = eventRepository.findAllBy();
        // Filter the events list in pure JAVA here
        Predicate<Band> bandPredicate = band -> band.getMembers().stream().anyMatch(member -> member.getName().toLowerCase().contains(query.toLowerCase()));
        Predicate<Event> eventPredicate = event -> event.getBands().stream().anyMatch(bandPredicate);
        return events.stream().filter(eventPredicate).map(this::calculateCounts).collect(Collectors.toList());
    }

    private Event calculateCounts(Event event) {
        event.setCount(event.getBands().size());
        event.getBands().forEach(band -> band.setCount(band.getMembers().size()));
        return event;
    }
}

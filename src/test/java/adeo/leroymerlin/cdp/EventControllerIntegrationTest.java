package adeo.leroymerlin.cdp;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventControllerIntegrationTest extends AbstractIntegrationTest {
   @Autowired
   EventRepository eventRepository;
   @Test
   public void shouldFindAllEventsOk() throws Exception {
     mockMvc.perform(get("/api/events/"))
             .andExpect(status().isOk())
             .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(5)));
   }
    @Test
    public void shouldUpdateEventReviewOk() throws Exception {
       String updatedEvent= "{\"comment\": \"new comment\", \"nbStars\":4}";
        mockMvc.perform(put("/api/events/1000")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedEvent))
                .andExpect(status().isOk());

        Event event = eventRepository.findById(1000L).get();
        Assertions.assertThat(event.getComment()).isEqualTo("new comment");
        Assertions.assertThat(event.getNbStars()).isEqualTo(4);
    }
    @Test
    public void shouldDeleteEventOk() throws Exception {
        mockMvc.perform(delete("/api/events/1000"))
                .andExpect(status().isOk());
        Assertions.assertThat(eventRepository.findAllBy()).hasSize(4);
        Assertions.assertThat(eventRepository.findById(1000L).isPresent()).isFalse();
    }
    @Test
    public void shouldGetEventsByQueryOk() throws Exception {
        mockMvc.perform(get("/api/events/search/queen"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(5)));
    }
    @Test
    public void shouldGetEventsByQueryCaseInsensitiveOk() throws Exception {
        mockMvc.perform(get("/api/events/search/Abi"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }
    @Test
    public void shouldGetEmptyEventsWhenNotFoundOk() throws Exception {
        mockMvc.perform(get("/api/events/search/QUUU"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }
}

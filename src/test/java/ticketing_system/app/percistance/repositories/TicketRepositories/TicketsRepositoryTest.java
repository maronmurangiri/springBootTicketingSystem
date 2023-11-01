//package ticketing_system.app.percistance.repositories.TicketRepositories;
//
//import org.assertj.core.api.BDDAssertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import ticketing_system.app.percistance.Entities.TicketEntities.Tickets;
//
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class TicketsRepositoryTest {
//
//    @Mock
//    private TicketRepository ticketRepository;
//
//    @Test
//    public void testGetTicketByTicketId() {
//        /*Given*/
//        Long ticketId = 1L;
//        Tickets expectedTicket = new Tickets();
//
//        /*When and Then*/
//        when(ticketRepository.getTicketByTicketId(ticketId)).thenReturn(expectedTicket);
//
//        Tickets actualTicket = ticketRepository.getTicketByTicketId(ticketId);
//
//        verify(ticketRepository, times(1)).getTicketByTicketId(ticketId);
//
//        BDDAssertions.assertThat(actualTicket).isEqualTo(expectedTicket);
//    }
//
//    // Add more tests for your repository methods as needed
//}
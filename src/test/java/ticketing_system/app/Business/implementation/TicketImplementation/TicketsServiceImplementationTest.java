//package ticketing_system.app.Business.implementation.TicketImplementation;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//import ticketing_system.app.Business.servises.TicketServices.implementation.TicketImplementation.TicketServiceImplementation;
//import ticketing_system.app.percistance.Entities.TicketEntities.Tickets;
//import ticketing_system.app.percistance.Enums.TicketPriority;
//import ticketing_system.app.percistance.Enums.TicketStatus;
//import ticketing_system.app.percistance.repositories.TicketRepositories.TicketRepository;
//import ticketing_system.app.preesentation.data.TicketData.TicketDTO;
//
//import java.sql.Timestamp;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class TicketsServiceImplementationTest {
//
//    @Mock
//    private TicketRepository ticketRepository;
//    @Mock
//    private ModelMapper modelMapper;
//
//    @InjectMocks
//    private TicketServiceImplementation ticketService;
//
//
//    @Test
//    public void testCreateTicket() {
//        // Create a sample DTO and entity
//        TicketDTO ticketDTO = new TicketDTO();
////        ticketDTO.setTicketID(1L);
//        ticketDTO.setTicketName("No service");
//        Tickets ticketEntity = new Tickets();
//        ticketEntity.setTicketId(1L);
//
//        TicketDTO firstTicket = new TicketDTO("No service", "No service on site", TicketStatus.OPEN,
//                TicketPriority.LOW, "2023-10-17 16:13:31");
//       Tickets ticket2 = new Tickets("No service", "No service on site", TicketStatus.OPEN,
//               TicketPriority.LOW, Timestamp.valueOf("2023-10-17 16:13:31"));
//
//
//
//        // Mock behavior for modelMapper and ticketRepository
//        when(modelMapper.map(ticketDTO, Tickets.class)).thenReturn(ticketEntity);
//        when(ticketRepository.save(ticketEntity)).thenReturn(ticketEntity);
//        when(modelMapper.map(ticketEntity, TicketDTO.class)).thenReturn(ticketDTO);
//
//        // Mock behavior for modelMapper and ticketRepository
//        when(modelMapper.map(firstTicket, Tickets.class)).thenReturn(ticket2);
//        when(ticketRepository.save(ticket2)).thenReturn(ticket2);
//        when(modelMapper.map(ticket2, TicketDTO.class)).thenReturn(firstTicket);
//
//        // Perform the service method
//        TicketDTO createdTicket = ticketService.createTicket(ticketDTO);
//        TicketDTO firstTicketCreated = ticketService.createTicket(firstTicket);
//
//        // Verify interactions and assertions
//        verify(modelMapper, times(1)).map(ticketDTO, Tickets.class);
//        verify(ticketRepository, times(1)).save(ticketEntity);
//        assertThat(createdTicket).isEqualTo(ticketDTO);
//        assertThat(firstTicketCreated).isEqualTo(firstTicket);
//
//
//    }
//
//    @Test
//    void getTicket() {
//        Long ticketId = 1L;
//        TicketDTO expectedTicket = new TicketDTO();
//        Tickets ticketEntity = new Tickets();
//        ticketEntity.setTicketId(1L);
//
//        /*When and Then*/
//        when(ticketRepository.getTicketByTicketId(ticketId)).thenReturn(ticketEntity);
//        when(modelMapper.map(ticketEntity, TicketDTO.class)).thenReturn(expectedTicket);
//
//
//        // Perform the service method
//        TicketDTO actualTicket = ticketService.getTicket(ticketId);
//
//        // Verify interactions and assertions
//        verify(ticketRepository, times(1)).getTicketByTicketId(ticketId);
//        verify(modelMapper, times(1)).map(ticketEntity, TicketDTO.class);
//        assertThat(actualTicket).isEqualTo(expectedTicket);
//
//    }
//
//    @Test
//    void updateTicket() {
//        // Create a sample DTO and entity
//        Long ticketId = 1L;
//        TicketDTO ticketToUpdate = new TicketDTO();
//
//        Tickets ticketEntity = new Tickets();
//        ticketEntity.setTicketId(ticketId);
//
//        /*When and Then*/  // Mock behavior for modelMapper and ticketRepository
//        when(modelMapper.map(ticketToUpdate, Tickets.class)).thenReturn(ticketEntity);
//        when(ticketRepository.getTicketByTicketId(ticketId)).thenReturn(ticketEntity);
//        when(ticketRepository.save(ticketEntity)).thenReturn(ticketEntity);
//        when(modelMapper.map(ticketEntity, TicketDTO.class)).thenReturn(ticketToUpdate);
//
//
//        // Perform the service method
//        TicketDTO actualTicket = ticketService.updateTicket(ticketId, ticketToUpdate);
//
//        // Verify interactions and assertions
//        verify(modelMapper, times(1)).map(ticketToUpdate, Tickets.class);
//        verify(ticketRepository, times(1)).getTicketByTicketId(ticketId);
////        verify(ticketRepository, times(1)).save(ticketEntity);
//        assertThat(actualTicket).isEqualTo(ticketToUpdate);
//    }
//
//    @Test
//    void deleteTicket() {
//        Long ticketId = 1L;
//
//        // Perform the service method
//       ticketService.deleteTicket(ticketId);
//
//        // Verify interactions and assertions
//        verify(ticketRepository, times(1)).deleteById(ticketId);
//        assertThat(ticketRepository.getTicketByTicketId(ticketId)).isNull();
//    }
//    @Test
//    void updateTicketStatus() {
//        // Create a sample DTO and entity
//        Long ticketId = 1L;
//        TicketStatus ticketStatus = TicketStatus.OPEN;
//        TicketDTO updatedTicket = new TicketDTO();
//        Tickets ticketEntity = new Tickets();
//        ticketEntity.setTicketId(ticketId);
//
//        /*When and Then*/  // Mock behavior for modelMapper and ticketRepository
//        when(ticketRepository.getTicketByTicketId(ticketId)).thenReturn(ticketEntity);
//        ticketEntity.setTicketStatus(ticketStatus);
//        when(ticketRepository.save(ticketEntity)).thenReturn(ticketEntity);
//        when(modelMapper.map(ticketEntity, TicketDTO.class)).thenReturn(updatedTicket);
//
//
//        // Perform the service method
//        TicketDTO actualTicket = ticketService.updateTicketStatus(ticketId, ticketStatus);
//
//        // Verify interactions and assertions
//        verify(ticketRepository, times(1)).getTicketByTicketId(ticketId);
////        verify(ticketRepository, times(1)).save(ticketEntity);
//        verify(modelMapper, times(1)).map(ticketEntity, TicketDTO.class);
//        assertThat(actualTicket).isEqualTo(updatedTicket);
//    }
//    @Test
//    void updateTicketPriorityLevel() {
//        // Create a sample DTO and entity
//        Long ticketId = 1L;
//        TicketPriority ticketPriority = TicketPriority.MEDIUM;
//        TicketDTO updatedTicket = new TicketDTO();
//        Tickets ticketEntity = new Tickets();
//        ticketEntity.setTicketId(ticketId);
//
//        /*When and Then*/  // Mock behavior for modelMapper and ticketRepository
//        when(ticketRepository.getTicketByTicketId(ticketId)).thenReturn(ticketEntity);
//        ticketEntity.setPriorityLevel(ticketPriority);
//        when(ticketRepository.save(ticketEntity)).thenReturn(ticketEntity);
//        when(modelMapper.map(ticketEntity, TicketDTO.class)).thenReturn(updatedTicket);
//
//
//        // Perform the service method
//        TicketDTO actualTicket = ticketService.updateTicketPriorityLevel(ticketId, ticketPriority);
//
//        // Verify interactions and assertions
//        verify(ticketRepository, times(1)).getTicketByTicketId(ticketId);
////        verify(ticketRepository, times(1)).save(ticketEntity);
//        verify(modelMapper, times(1)).map(ticketEntity, TicketDTO.class);
//        assertThat(actualTicket).isEqualTo(updatedTicket);
//    }
//}
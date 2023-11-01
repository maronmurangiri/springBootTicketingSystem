package ticketing_system.app.Business.servises.TicketServices;

import org.springframework.security.core.userdetails.UserDetails;
import ticketing_system.app.percistance.Entities.TicketEntities.Tickets;
import ticketing_system.app.percistance.Enums.TicketPriority;
import ticketing_system.app.percistance.Enums.TicketStatus;
import ticketing_system.app.preesentation.data.TicketData.TaskDTO;
import ticketing_system.app.preesentation.data.TicketData.TicketDTO;
import ticketing_system.app.preesentation.data.TicketData.TicketNormalDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketService {

    Tickets createTicket(TicketDTO ticketDTO, UserDetails userDetails);
    List<Tickets> getAllTickets();
    List<Tickets> getOpenTickets();
    List<Tickets> getClosedTickets();
    List<Tickets> getResolvedTickets();
    List<Tickets> getInProgressTickets();
    Tickets getTicketByTicketId(long ticketId);
    List<Tickets> getTicketByName(String ticketName);
    List<Tickets> getTicketsByTag(String tag);
    TicketNormalDTO updateTicketById(long ticketId, TicketDTO ticketDTO);
    void deleteTicketById(long ticketId);
    void addTaskToTicket(long ticketId, TaskDTO taskDTO,UserDetails userDetails);
    void deleteTaskFromTicket(long ticketId, long taskId);
    void completeTaskOfTicket(long ticketId, long taskId);
    Tickets assignTicketToAgent(Long ticketId, Long userId,UserDetails userDetails);

    Tickets updateTicketStatus(Long ticketId, TicketStatus ticketStatus,UserDetails userDetails);

    TicketNormalDTO updateTicketPriorityLevel(Long ticketId, TicketPriority ticketPriority);
    TicketNormalDTO updateTicketDeadlineDateTime(Long ticketId, LocalDateTime deadline);
}

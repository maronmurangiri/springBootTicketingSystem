package ticketing_system.app.Business.servises.TicketServices.utilities;

import ticketing_system.app.percistance.Entities.TicketEntities.Tickets;
import ticketing_system.app.preesentation.data.TicketData.TicketAgentDTO;
import ticketing_system.app.preesentation.data.TicketData.TicketDTO;
import ticketing_system.app.preesentation.data.TicketData.TicketNormalDTO;

/**
 * service to map tickets to DTOs.
 * @author kamar baraka.*/

public interface TicketMapper {

    Tickets mapToTicket(TicketDTO ticketDTO);
    TicketDTO mapToDTO(Tickets tickets);
    TicketNormalDTO mapToNormalDTO(Tickets tickets);
    TicketAgentDTO mapToAgentDTO(Tickets tickets);
}

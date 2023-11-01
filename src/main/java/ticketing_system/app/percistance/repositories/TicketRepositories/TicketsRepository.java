package ticketing_system.app.percistance.repositories.TicketRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ticketing_system.app.percistance.Entities.TicketEntities.Tickets;
import ticketing_system.app.percistance.Enums.Tags;
import ticketing_system.app.percistance.Enums.TicketStatus;

import java.util.List;

/**
 * tickets repository.
 * @author kamar baraka.*/


@Repository
public interface TicketsRepository extends JpaRepository<Tickets, Long> {

    List<Tickets> findAllByStatus(TicketStatus status);
    List<Tickets> findTicketsByTicketName(String ticketName);
    List<Tickets> findAllByTag(Tags tag);
}

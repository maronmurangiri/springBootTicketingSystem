package ticketing_system.app.exceptions;
/**
 * ticket exception service.
 * @author kamar baraka.*/

public interface TaskExceptionService {

    TicketNotFoundException ticketNotFound();
    TaskNotFoundException taskNotFound();
}
package ticketing_system.app.exceptions;

/**
 * thrown when no ticket found.
 * @author kamar baraka.*/

public class TicketNotFoundException extends TicketException{
    public TicketNotFoundException(String message) {
        super(message);
    }
}

package ticketing_system.app.exceptions;
/**
 * container for tasks exceptions.
 * @author kamar baraka.*/

public class TicketException extends RuntimeException{

    public TicketException(String message) {

        super(message);
    }
}

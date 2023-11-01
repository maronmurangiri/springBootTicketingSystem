package ticketing_system.app.exceptions;

public class TaskNotFoundException extends TicketException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
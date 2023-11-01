package ticketing_system.app.Business.implementation.ApplicationEvents.CustomEvents;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import ticketing_system.app.Business.implementation.ApplicationEvents.Events.AddTaskToTicketEvent;

@Data
public class CustomAddTaskToTicketEvent {
    private AddTaskToTicketEvent addTaskToTicketEvent;
    private UserDetails userDetails;

    public CustomAddTaskToTicketEvent(AddTaskToTicketEvent addTaskToTicketEvent, UserDetails userDetails) {
        this.addTaskToTicketEvent = addTaskToTicketEvent;
        this.userDetails = userDetails;
    }
}

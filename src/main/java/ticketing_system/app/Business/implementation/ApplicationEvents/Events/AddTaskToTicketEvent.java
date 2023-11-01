package ticketing_system.app.Business.implementation.ApplicationEvents.Events;

import org.springframework.context.ApplicationEvent;

public class AddTaskToTicketEvent extends ApplicationEvent {
    public AddTaskToTicketEvent(Object source) {
        super(source);
    }
}

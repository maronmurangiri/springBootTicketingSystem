package ticketing_system.app.Business.implementation.ApplicationEvents.Events;

import org.springframework.context.ApplicationEvent;

public class TicketCreatedEvent extends ApplicationEvent {
    public TicketCreatedEvent(Object source) {
        super(source);
    }
}

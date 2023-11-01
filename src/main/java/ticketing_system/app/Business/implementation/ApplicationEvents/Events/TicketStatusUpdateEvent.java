package ticketing_system.app.Business.implementation.ApplicationEvents.Events;

import org.springframework.context.ApplicationEvent;

public class TicketStatusUpdateEvent extends ApplicationEvent{

        public TicketStatusUpdateEvent(Object source) {
            super(source);
        }
    }


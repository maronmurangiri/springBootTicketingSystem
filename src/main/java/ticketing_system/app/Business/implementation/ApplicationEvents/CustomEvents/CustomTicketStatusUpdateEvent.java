package ticketing_system.app.Business.implementation.ApplicationEvents.CustomEvents;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import ticketing_system.app.Business.implementation.ApplicationEvents.Events.TicketCreatedEvent;
import ticketing_system.app.Business.implementation.ApplicationEvents.Events.TicketStatusUpdateEvent;

@Data
public class CustomTicketStatusUpdateEvent {
    private TicketStatusUpdateEvent ticketStatusUpdateEvent;
    private UserDetails userDetails;

    public CustomTicketStatusUpdateEvent(TicketStatusUpdateEvent ticketStatusUpdateEvent, @Validated UserDetails userDetails) {
        this.ticketStatusUpdateEvent = ticketStatusUpdateEvent;
        this.userDetails = userDetails;
    }
}

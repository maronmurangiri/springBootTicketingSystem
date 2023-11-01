package ticketing_system.app.Business.implementation.ApplicationEvents.CustomEvents;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import ticketing_system.app.Business.implementation.ApplicationEvents.Events.TicketCreatedEvent;

@Data
public class CustomTicketCreatedEvent {
    private TicketCreatedEvent ticketCreatedEvent;
    private   UserDetails userDetails;

    public CustomTicketCreatedEvent(TicketCreatedEvent ticketCreatedEvent, @Validated UserDetails userDetails) {
        this.ticketCreatedEvent = ticketCreatedEvent;
        this.userDetails = userDetails;
    }

}
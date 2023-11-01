package ticketing_system.app.Business.implementation.ApplicationEvents.CustomEvents;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import ticketing_system.app.Business.implementation.ApplicationEvents.Events.AgentAssignmentEvent;
import ticketing_system.app.Business.implementation.ApplicationEvents.Events.TicketCreatedEvent;

@Data
public class CustomAgentAssignmentEvents {
    private AgentAssignmentEvent agentAssignmentEvent;
    private UserDetails userDetails;

    public CustomAgentAssignmentEvents(AgentAssignmentEvent agentAssignmentEvent, @Validated UserDetails userDetails) {
        this.agentAssignmentEvent = agentAssignmentEvent;
        this.userDetails = userDetails;
    }
}

package ticketing_system.app.Business.implementation.ApplicationEvents.Events;

import org.springframework.context.ApplicationEvent;

public class AgentAssignmentEvent extends ApplicationEvent {
    public AgentAssignmentEvent(Object source) {
        super(source);
    }
}

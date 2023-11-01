package ticketing_system.app.Business.implementation.EventsListeners;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ticketing_system.app.Business.implementation.ApplicationEvents.CustomEvents.CustomAgentAssignmentEvents;
import ticketing_system.app.Business.implementation.ApplicationEvents.Events.AgentAssignmentEvent;
import ticketing_system.app.Business.implementation.EmailSenderService.EmailSenderService;
import ticketing_system.app.Business.implementation.ReportService.TicketReportService;
import ticketing_system.app.Business.implementation.ApplicationEvents.CustomEvents.CustomTicketCreatedEvent;
import ticketing_system.app.Business.implementation.ApplicationEvents.Events.TicketCreatedEvent;
import ticketing_system.app.percistance.Entities.TicketEntities.Tickets;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.percistance.repositories.userRepositories.UserRepository;

import java.io.FileNotFoundException;

@Service
public class AgentAssignmentEventsListener {
    @Autowired
    EmailSenderService senderService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketReportService reportService;
    @Autowired
    TicketEventsListener ticketEventsListener;
    @EventListener(CustomAgentAssignmentEvents.class)
    public void sendEmailOnAgentAssignment(CustomAgentAssignmentEvents event) {
        AgentAssignmentEvent agentAssignmentEvent = event.getAgentAssignmentEvent();
        Tickets ticket = (Tickets) agentAssignmentEvent.getSource();
        UserDetails userDetails = event.getUserDetails();
        Users loggedUser = userRepository.findByEmail(ticket.getTicketCreator());
        String userName = "";
        if (loggedUser!=null){
            System.out.println(userName);
            userName = loggedUser.getFirstname();
        }
        System.out.println(userName);
        // Send an email using the sender service

            String emailMessage = "Dear "+ userName+",\nOur support team is currently working on your ticket of Id: " + ticket.getTicketId()+"\nThe ticket is IN_PROGRESS state, We will keep you posted, update us on any further issue from your end" +
                    " \nHere is the ticket details: http://localhost:8080/api/v1/tickets/byId/"+ticket.getTicketId()+"\n Thank you for being patient with us, We value your satisfaction and feedback\n\n\n Regards, \nKeMaTCo LTD support Team";



        assert loggedUser != null;
        senderService.sendSimpleEmail(loggedUser.getUsername(), "TICKET ASSIGNMENT", emailMessage);

    }
}

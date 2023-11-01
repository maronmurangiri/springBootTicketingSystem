package ticketing_system.app.Business.implementation.EventsListeners;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ticketing_system.app.Business.implementation.ApplicationEvents.CustomEvents.CustomTicketCreatedEvent;
import ticketing_system.app.Business.implementation.ApplicationEvents.CustomEvents.CustomTicketStatusUpdateEvent;
import ticketing_system.app.Business.implementation.ApplicationEvents.Events.TicketCreatedEvent;
import ticketing_system.app.Business.implementation.ApplicationEvents.Events.TicketStatusUpdateEvent;
import ticketing_system.app.Business.implementation.EmailSenderService.EmailSenderService;
import ticketing_system.app.Business.implementation.ReportService.TicketReportService;
import ticketing_system.app.percistance.Entities.TicketEntities.Tickets;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.percistance.repositories.userRepositories.UserRepository;

import java.io.FileNotFoundException;

@Service
public class TicketStatusUpdateEventsListener {
    @Autowired
    EmailSenderService senderService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketReportService reportService;
    @EventListener(CustomTicketStatusUpdateEvent.class)
    public void sendEmailOnTicketStatusUpdate(CustomTicketStatusUpdateEvent event) {
        TicketStatusUpdateEvent ticketStatusUpdateEvent = event.getTicketStatusUpdateEvent();
        Tickets ticket = (Tickets) ticketStatusUpdateEvent.getSource();
        UserDetails userDetails = event.getUserDetails();
        Users loggedUser = userRepository.findByEmail(ticket.getTicketCreator());
        String userName = "";
        if (loggedUser!=null){
            System.out.println(userName);
            userName = loggedUser.getFirstname();
        }
        System.out.println(userName);
        // Send an email using the sender service
            String emailMessage = "Dear "+ userName+",\nTicket ID: " + ticket.getTicketId()+" has been successfully resolved." +
                    " \nHere is the ticket details: http://localhost:8080/api/v1/tickets/byId/"+ticket.getTicketId()+"\n Thank you for letting us be part of the solution, We appreciate your effort to reach us, For any inquiries, contact us as soon as possible\n\n\n Regards, \nKeMaTCo LTD support Team";

        assert loggedUser != null;
        System.out.println(loggedUser.getEmail());
        System.out.println(loggedUser.getUsername());
        senderService.sendSimpleEmail(loggedUser.getUsername(), "TICKET RESOLUTION", emailMessage);

    }
}

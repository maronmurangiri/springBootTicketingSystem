package ticketing_system.app.Business.implementation.EventsListeners;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ticketing_system.app.Business.implementation.EmailSenderService.EmailSenderService;
import ticketing_system.app.Business.implementation.ReportService.TicketReportService;
import ticketing_system.app.Business.implementation.ApplicationEvents.CustomEvents.CustomTicketCreatedEvent;
import ticketing_system.app.Business.implementation.ApplicationEvents.Events.TicketCreatedEvent;
import ticketing_system.app.percistance.Entities.TicketEntities.Tickets;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.percistance.repositories.userRepositories.UserRepository;

import java.io.FileNotFoundException;

@Service
public class TicketEventsListener {
    @Autowired
    EmailSenderService senderService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketReportService reportService;

    String email = "";
    @EventListener(CustomTicketCreatedEvent.class)
    public void sendEmailOnTicketCreation(CustomTicketCreatedEvent event) {
        TicketCreatedEvent ticketCreatedEvent = event.getTicketCreatedEvent();
        Tickets ticket = (Tickets) ticketCreatedEvent.getSource();
        UserDetails userDetails = event.getUserDetails();

        Users loggedUser = userRepository.findByEmail(ticket.getTicketCreator());
        String userName = "";
        if (loggedUser!=null){
            System.out.println(userName);
            userName = loggedUser.getFirstname();
        }
        System.out.println(userName);
        // Send an email using the sender service
        try {
            String reportUrl = String.valueOf(reportService.exportTicketReport(ticket.getTicketId()));
            String emailMessage = "Dear "+ userName+",\nWe have received your ticket with ID: " + ticket.getTicketId() +
                    " \nHere is the ticket details: http://localhost:8080/api/v1/tickets/byId/"+ticket.getTicketId()+"\n Thank you for creating a ticket with us, we will get back to you as soon as possible\n\n\n Regards, \nKeMaTCo LTD support Team";

            assert loggedUser != null;
            email =loggedUser.getUsername();
            senderService.sendSimpleEmail(loggedUser.getUsername(), "TICKET CREATED SUCCESSFULLY", emailMessage);
        } catch (FileNotFoundException | JRException e) {
            throw new RuntimeException(e);
        }
    }
}

package ticketing_system.app.preesentation.controler.TicketControllers;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import ticketing_system.app.Business.implementation.ReportService.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ticketing_system.app.Business.servises.TicketServices.TicketService;
import ticketing_system.app.Business.servises.TicketServices.utilities.TaskMapper;
import ticketing_system.app.Business.servises.TicketServices.utilities.TicketMapper;
import ticketing_system.app.percistance.Entities.TicketEntities.Tickets;
import ticketing_system.app.percistance.Enums.TicketPriority;
import ticketing_system.app.percistance.Enums.TicketStatus;
import ticketing_system.app.preesentation.data.TicketData.*;


import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * the ticket controller.
 * @author kamar baraka.*/

@Log4j2
@RestController
@RequestMapping(value = {"/api/v1/tickets"})
@RequiredArgsConstructor
@Tag(name = "Ticket Api", description = "the ticket API exposing ticket operations")
public class TicketController {

    /*inject dependencies*/
    private final TaskMapper taskMapper;
    private final TicketMapper ticketMapper;
    private final TicketService ticketService;

    @Autowired
    private  TicketReportService ticketReportService;

    @Autowired
    private AgentAdminTicketReportService agentAdminTicketReportService;

    @Autowired
    private UserTicketReportService userTicketReportService;

    @Autowired
    private AddTaskReportService addTaskReportService;

    @Autowired
    private AssignTicketReportService assignTicketReportService;

    @GetMapping
    //@Parameter(name = "user role",required = false)
    @PreAuthorize("hasAuthority('admin')  or hasAuthority('agent')")
    @Operation(tags = {"Ticket Api"},summary = "get all tickets",description = "get all tickets in the database")
    public ResponseEntity<?> getAllTickets() throws JRException, FileNotFoundException {


        /*List<Long> listIds = allTickets.stream().map(Tickets::getTicketId).toList();
        List<? extends DTOType> allTicketsDTO;
        /*get DTO based on roles*/
       /* switch (userRole){
            case "AGENT" -> allTicketsDTO = allTickets.stream().map(ticketMapper::mapToAgentDTO).toList();
            case "ADMIN" -> allTicketsDTO = allTickets;
            case "USER" -> allTicketsDTO = allTickets.stream().map(ticketMapper::mapToNormalDTO).toList();
            default -> allTicketsDTO = null;

        }

       // assert allTicketsDTO != null;
        final int[] index = {0};
        /*create the Entity model
        List<? extends EntityModel<? extends DTOType>> entityModels = allTicketsDTO.stream().map(EntityModel::of).toList();
        /*add links
        entityModels = entityModels.stream().peek(entity ->
        {

            entity.add(WebMvcLinkBuilder.linkTo(TicketController.class).
                    slash("byId").slash(listIds.get(index[0])).withRel("ticket_details"));
            index[0]++;
        }).toList();*/

        /*create and return a response
        return ResponseEntity.ok(entityModels);*/
        /*get all tickets*/
        List<Tickets> allTickets = ticketService.getAllTickets();
        return  ticketReportService.exportReport();

    }

    @GetMapping(value = {"byName/{name}"})
    @PreAuthorize("hasAuthority('admin')  or hasAuthority('agent') or hasAuthority('user')")
    @Operation(summary = "get ticket", description = "get ticket by name")
    public ResponseEntity<?> getTicketByName(@PathVariable("name") String name) throws JRException, FileNotFoundException {

        /*get ticket by name*/
        List<Tickets> ticketByName = ticketService.getTicketByName(name);
        /*declare a ticket*/
        for (Tickets tickets : ticketByName){
            long id = tickets.getTicketId();
            ticketService.getTicketByTicketId(id);
            return ticketReportService.exportTicketReport(id);
        }
        /*return the response*/
       return null;

    }

    @GetMapping(value = {"byId/{ticketId}"})
    @Operation(summary = "get ticket", description = "get a ticket by ID")
    @PreAuthorize("hasAuthority('admin')  or hasAuthority('agent') or hasAuthority('user')")
    public ResponseEntity<?> getTicketById(@PathVariable("ticketId")
                                                              long ticketId) throws JRException, FileNotFoundException {

        /*get a ticket by the ID*/
        Tickets ticketByTicketId = ticketService.getTicketByTicketId(ticketId);
        DTOType ticketDTO;
        /*get DTO based on the role*/
       /* switch (userRole){

            case "USER" -> ticketDTO = ticketByTicketId;
            case "AGENT" -> ticketDTO = ticketByTicketId;
            case "ADMIN" -> ticketDTO = ticketByTicketId;
            default -> ticketDTO = null;
        }
        assert ticketDTO != null;
        /*create an entity model
        EntityModel<DTOType> dtoType = EntityModel.of(ticketByTicketId);
        /*add a link*/


        /*return the response*/
        return ticketReportService.exportTicketReport(ticketId);
    }

    /**
     * create ticket
     */
    @PostMapping
    @Operation(tags = {"Ticket Api"},summary = "create ticket",description = "create a ticket" )
    @PreAuthorize("hasAuthority('admin')  or hasAuthority('agent') or hasAuthority('user')")
    public ResponseEntity<?> createTicket(@Validated @RequestBody final TicketDTO ticketDTO, @AuthenticationPrincipal UserDetails userDetails) throws JRException, FileNotFoundException, MalformedURLException {

        /*create the ticket*/
        Tickets ticket = ticketService.createTicket(ticketDTO,userDetails);
         Long ticketId = ticket.getTicketId();

        ResponseEntity<?> reportResponse = ticketReportService.exportTicketReport(ticketId);


        /*create the response*/
       /* EntityModel<CreatedDTO> response = EntityModel.of(new CreatedDTO("successfully Created"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TicketController.class).getTicketById(
                        "USER", ticket.getTicketId()
                )).withRel("the ticket"));*/


        // Generate the PDF report and store it in 'reportBytes'
        if (userDetails.getAuthorities().toString().equalsIgnoreCase("user")){
            return  userTicketReportService.exportTicketReport(ticketId);
        }
        else {
            return agentAdminTicketReportService.exportTicketReport(ticketId);
        }


    }

    @PostMapping(value = {"task/{id}"})
    @PreAuthorize("hasAuthority('admin')  or hasAuthority('agent') or hasAuthority('user')")
    public ResponseEntity<?> addTaskToTicket(@AuthenticationPrincipal UserDetails userDetails,@PathVariable("id") final long ticketId ,
                                                                @RequestBody TaskDTO task) throws JRException, FileNotFoundException {
        log.warn("passed {}", task);

        /*create the task*/
        ticketService.addTaskToTicket(ticketId, task,userDetails);
        /*create the response*/
        EntityModel<DTOType> response = EntityModel.of(new CreatedDTO("task added successfully. click the link to see"));
        /*create links*/
       /* Link ticketLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TicketController.class).
                getTicketById("USER", ticketId)).withRel("ticket");*/

        /*add links*/
       // response.add(ticketLink);

        log.warn("created {}", task);

        /*create response*/
      //  return ResponseEntity.status(201).body(response);
        return addTaskReportService.exportTicketReport(ticketId);
    }

    @PutMapping(value = "/update")
    @Operation(summary = "update ticket", description = "Update a ticket by ID")
    @PreAuthorize("hasAuthority('admin')  or hasAuthority('agent') or hasAuthority('user')")
    public ResponseEntity<?> updateTicketById(@RequestParam(value = "ticket_id")Long ticketId, @RequestBody TicketDTO ticketDTO) throws JRException, FileNotFoundException {
        ticketService.updateTicketById(ticketId, ticketDTO);
        return ticketReportService.exportTicketReport(ticketId);
    }

    @DeleteMapping(value = "/delete")
    @Operation(summary = "delete ticket", description = "Delete a ticket by ID")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<EntityModel<CreatedDTO>> deleteTicketById(@RequestParam(value = "ticket_id")Long ticketId){
        ticketService.deleteTicketById(ticketId);

        EntityModel<CreatedDTO> response = EntityModel.of(new CreatedDTO("Ticket successfully deleted"));

        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/assign")
    @Operation(summary = "assign ticket", description = "Assign a ticket to an agent")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> assignTicketToAgent(@AuthenticationPrincipal UserDetails userDetails,@RequestParam(value = "ticket_id") Long ticketId,
                                                              @RequestParam(value = "user_id") Long userId,
                                                              @RequestParam(value = "ticketPriorityLevel") String ticketPriorityLevel,
                                                              @RequestParam(value = "deadline(dd/MM/yyyy HH:mm)")String deadlineDateTime) throws JRException, FileNotFoundException {



        // Create a DateTimeFormatter with date and time pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime deadline = LocalDateTime.parse(deadlineDateTime, formatter);
        // Format the LocalDate with time
       // String formattedDate = deadlineDateTime.format(formatter);

        ticketService.assignTicketToAgent(ticketId,userId,userDetails);
        ticketService.updateTicketStatus(ticketId,TicketStatus.IN_PROGRESS,userDetails);
        ticketService.updateTicketPriorityLevel(ticketId, TicketPriority.valueOf(ticketPriorityLevel));
        ticketService.updateTicketDeadlineDateTime(ticketId,deadline);

        return assignTicketReportService.exportTicketReport(ticketId);
        //return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.assignTicketToAgent(ticketId, userId));
    }
    @PutMapping(value = "/update-status")
    @PreAuthorize("hasAuthority('admin')  or hasAuthority('agent') or hasAuthority('user')")
    @Operation(summary = "update ticket status", description = "Retrieve and update ticket status by ticket id")
    public ResponseEntity<?> updateTicketStatus(@AuthenticationPrincipal UserDetails userDetails,@RequestParam(value = "ticketId") Long ticketId, @RequestParam(value = "ticketStatus") String ticketStatus) throws JRException, FileNotFoundException {
            ticketService.updateTicketStatus(ticketId, TicketStatus.valueOf(ticketStatus),userDetails);
            return assignTicketReportService.exportTicketReport(ticketId);
    }
    //@PutMapping(value = "/update-priority-level")
    @PreAuthorize("hasAuthority('admin')  or hasAuthority('agent') or hasAuthority('user')")
    @Operation(summary = "update ticket priority", description= "Retrieve and update ticket priority")
    public ResponseEntity<TicketNormalDTO> updateTicketPriorityLevel(@RequestParam(value = "ticketId") Long ticketId, @RequestParam(value = "ticketPriorityLevel") String ticketPriorityLevel){
        return ResponseEntity.ok(ticketService.updateTicketPriorityLevel(ticketId, TicketPriority.valueOf(ticketPriorityLevel))) ;
    }

}
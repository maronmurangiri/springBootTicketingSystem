package ticketing_system.app.percistance.Entities.TicketEntities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ticketing_system.app.percistance.Entities.userEntities.Positions;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.percistance.Enums.Tags;
import ticketing_system.app.percistance.Enums.TicketPriority;
import ticketing_system.app.percistance.Enums.TicketStatus;
import ticketing_system.app.preesentation.data.TicketData.DTOType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * tickets class to hold ticket data.
 * @author kamar baraka.*/


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tickets implements DTOType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(insertable = false)
    private long ticketId;
    @Column(nullable = false)
    private String ticketName;
    private String description;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Tags tag;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Tasks> tasks;
    @Column(name = "deadline_date")
    private LocalDateTime deadline;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status = TicketStatus.OPEN;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketPriority priority = TicketPriority.MEDIUM;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "user_id", insertable = true, updatable = true)
    private Users agentAssigned;

    @Column(name = "ticket_creator")
    private String ticketCreator;
    @Column()
    private final LocalDateTime createdOn = LocalDateTime.now();
    private LocalDateTime updatedOn = LocalDateTime.now();

   // private Tasks task;

}



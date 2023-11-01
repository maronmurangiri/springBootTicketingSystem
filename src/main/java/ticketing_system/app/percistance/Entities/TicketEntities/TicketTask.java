package ticketing_system.app.percistance.Entities.TicketEntities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tickets_tasks")
public class TicketTask {

    @Id
    @Column(name = "tickets_ticket_id")
    private Long ticketId;

    @Column(name = "tasks_task_id")
    private Long taskId;
}

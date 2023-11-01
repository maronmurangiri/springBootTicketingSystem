package ticketing_system.app.percistance.Entities.TicketEntities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

/**
 * class to represent a task.
 * @author kamar baraka.*/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tasks {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private long taskId;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private boolean complete = false;
}
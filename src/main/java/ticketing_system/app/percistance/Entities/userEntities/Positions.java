package ticketing_system.app.percistance.Entities.userEntities;

import jakarta.persistence.*;
import lombok.Data;
import ticketing_system.app.percistance.Entities.userEntities.Department;

import java.sql.Timestamp;


/**
 * The `Positions` class represents a position entity in the ticketing system.
 * It is mapped to the "position" table in the database and contains information
 * about a position, including its name, creation, and update details, as well as
 * the department to which it belongs.
 *
 * <p>The class is annotated with JPA (Java Persistence API) annotations to specify
 * its mapping to database columns and its relationship with the `Department` entity.
 *
 * <p>Attributes:
 * - `positionId`: A unique identifier for the position.
 * - `positionName`: The name of the position.
 * - `createdBy`: The user ID of the creator of the position.
 * - `createdOn`: The timestamp when the position was created.
 * - `updatedBy`: The user ID of the user who last updated the position.
 * - `updatedOn`: The timestamp of the last update to the position.
 * - `department`: The department to which the position belongs.
 *
 * <p>Relationships:
 * - The `department` attribute represents a many-to-one relationship with the
 *   `{@link Department}` entity, indicating the department to which the position is assigned.
 *
 * <p>The class provides constructors for creating `Positions` instances with
 * various attributes. It also includes a `toString` method for generating a
 * string representation of a `Positions` object.
 *
 * @author Maron
 * @version 1.0
 */
@Entity
@Data
@Table(name = "positions")
public class Positions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_id")
    private Long positionId;

    @Column(name = "position_name")
    private String positionName;

    @Column(name="created_by")
    private int createdBy;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "updated_by")
    private Integer UpdatedBy;

    @Column(name = "updated_on")
    private Timestamp updatedOn;

    @ManyToOne(targetEntity = Department.class)
    @JoinColumn(name = "department_id")
    private Department department;

    public Positions() {

    }

    /**
     * Constructs a `Positions` instance with specified attributes.
     *
     * @param positionId   The unique identifier for the position.
     * @param positionName The name of the position.
     * @param createdBy    The user ID of the creator of the position.
     * @param createdOn    The timestamp when the position was created.
     * @param updatedBy    The user ID of the user who last updated the position.
     * @param updatedOn    The timestamp of the last update to the position.
     * @param department   The department to which the position belongs.
     */
    public Positions(Long positionId, String positionName, int createdBy, Timestamp createdOn, int updatedBy, Timestamp updatedOn, Department department) {
        this.positionId = positionId;
        this.positionName = positionName;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        UpdatedBy = updatedBy;
        this.updatedOn = updatedOn;
        this.department = department;
    }


    /**
     * Generates a string representation of the `Positions` object.
     *
     * @return A string representing the `Positions` object's attributes.
     */
    @Override
    public String toString() {
        return "Positions{" +
                "positionId=" + positionId +
                ", positionName='" + positionName + '\'' +
                ", createdBy=" + createdBy +
                ", createdOn=" + createdOn +
                ", UpdatedBy=" + UpdatedBy +
                ", updatedOn=" + updatedOn +
                ", departmentId=" + department +
                '}';
    }

}

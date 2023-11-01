package ticketing_system.app.percistance.Entities.userEntities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

/**
 * The `Department` class represents a department entity in the ticketing system.
 * It is mapped to the "department" table in the database and contains information
 * about a department, including its name, description, director, creation, and
 * update details.
 *
 * <p>The class is annotated with JPA (Java Persistence API) annotations to specify
 * its mapping to database columns and its relationships with other entities.
 *
 * <p>Attributes:
 * - `departmentId`: A unique identifier for the department.
 * - `departmentName`: The name of the department.
 * - `departmentDescription`: A description of the department.
 * - `departmentDirector`: The user who serves as the director of the department.
 * - `createdBy`: The user ID of the creator of the department.
 * - `createdOn`: The timestamp when the department was created.
 * - `updatedBy`: The user ID of the user who last updated the department.
 * - `updatedOn`: The timestamp of the last update to the department.
 *
 * <p>Relationships:
 * - The `departmentDirector` attribute represents a one-to-one relationship with
 *   the `{@link Users}` entity, indicating the user who is the director of the department.
 *
 * <p>The class provides constructors for creating `Department` instances with
 * various attributes. It also includes getter and setter methods for accessing
 * and modifying the attributes.
 *
 * @author Maron
 * @version 1.0
 */
@Entity
@Data
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "department_description")
    private String departmentDescription;

    @OneToOne(targetEntity = Users.class) // Cascade operations to Address entity
    @JoinColumn(name = "user_id")
    private Users departmentDirector;


    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "updated_on")
    private Timestamp updatedOn;

    public Department() {


    }


    /**
     * Constructs a `Department` instance with specified attributes.
     *
     * @param departmentId          The unique identifier for the department.
     * @param departmentName        The name of the department.
     * @param departmentDescription The description of the department.
     * @param departmentDirector    The user who serves as the director of the department.
     * @param createdBy             The user ID of the creator of the department.
     * @param createdOn             The timestamp when the department was created.
     * @param updatedBy             The user ID of the user who last updated the department.
     * @param updatedOn             The timestamp of the last update to the department.
     */
    public Department(Long departmentId, String departmentName, String departmentDescription, Users departmentDirector, int createdBy, Timestamp createdOn, int updatedBy, Timestamp updatedOn) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentDescription = departmentDescription;
        this.departmentDirector = departmentDirector;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.updatedBy = updatedBy;
        this.updatedOn = updatedOn;
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", departmentDescription='" + departmentDescription + '\'' +
                ", departmentDirector=" + departmentDirector +
                ", createdBy=" + createdBy +
                ", createdOn=" + createdOn +
                ", updatedBy=" + updatedBy +
                ", updatedOn=" + updatedOn +
                '}';
    }

}
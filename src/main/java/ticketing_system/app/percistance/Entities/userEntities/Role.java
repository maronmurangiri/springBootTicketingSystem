package ticketing_system.app.percistance.Entities.userEntities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

/**
 * The `Role` class represents a role entity in the ticketing system.
 * It is mapped to the "role" table in the database and contains information
 * about a role, including its name, creation, and update details.
 *
 * <p>The class is annotated with JPA (Java Persistence API) annotations to specify
 * its mapping to database columns.
 *
 * <p>Attributes:
 * - `roleId`: A unique identifier for the role.
 * - `roleName`: The name of the role.
 * - `createdBy`: The user ID of the creator of the role.
 * - `createdOn`: The timestamp when the role was created.
 * - `updatedBy`: The user ID of the user who last updated the role.
 * - `updatedOn`: The timestamp of the last update to the role.
 *
 * <p>The class provides constructors for creating `Role` instances with
 * various attributes. It also includes a `toString()` method for generating
 * a string representation of the `Role` object.
 *
 * @author Maron
 * @version 1.0
 */
@Entity
@Data
@Table(name = "authorities")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "created_by")
    private int createdBy;

    @Column(name="created_on")
    private Timestamp createdOn;

    @Column(name="updated_by")
    private Integer updatedBy;

    @Column(name = "updated_on")
    private Timestamp updatedOn;

    /**
     * Constructs a `Role` instance with specified attributes.
     *
     * @param roleId     The unique identifier for the role.
     * @param roleName   The name of the role.
     * @param createdBy  The user ID of the creator of the role.
     * @param createdOn  The timestamp when the role was created.
     * @param updatedBy  The user ID of the user who last updated the role.
     * @param updatedOn  The timestamp of the last update to the role.
     */
    public Role(Long roleId, String roleName, int createdBy, Timestamp createdOn, int updatedBy, Timestamp updatedOn) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.updatedBy = updatedBy;
        this.updatedOn = updatedOn;
    }

    public Role() {

    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", createdBy=" + createdBy +
                ", createdOn=" + createdOn +
                ", updatedBy=" + updatedBy +
                ", updatedOn=" + updatedOn +
                '}';
    }
}

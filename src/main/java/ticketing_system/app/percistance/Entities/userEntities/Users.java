package ticketing_system.app.percistance.Entities.userEntities;

import jakarta.persistence.*;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ticketing_system.app.percistance.Entities.userEntities.Positions;
import ticketing_system.app.percistance.Entities.userEntities.Role;

import java.sql.Timestamp;
import java.util.Collection;

/**
 * The `Users` class represents a user entity in the ticketing system.
 * It is mapped to the "user" table in the database and contains information
 * about a user, including their first name, surname, email, password, role,
 * positions, and creation/update details.
 *
 * <p>The class is annotated with JPA (Java Persistence API) annotations to specify
 * its mapping to database columns.
 *
 * <p>Attributes:
 * - `id`: A unique identifier for the user.
 * - `firstname`: The first name of the user.
 * - `surname`: The surname of the user.
 * - `password`: The password of the user.
 * - `{@position}`: The positions associated with the user.
 * - `{@link Role}`: The role associated with the user.
 * - `createdBy`: The user ID of the creator of the user.
 * - `createdOn`: The timestamp when the user was created.
 * - `updatedBy`: The user ID of the user who last updated the user.
 * - `updatedOn`: The timestamp of the last update to the user.
 * - `email`: The email address of the user.
 *
 * <p>The class provides constructors for creating `Users` instances with
 * various attributes. It also includes getters and setters for accessing and
 * modifying the attributes of the `Users` object.
 *
 * @author Maron
 * @version 1.0
 */

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "users")
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", insertable = false, updatable = false)
    //@ApiModelProperty(value = "User id")
    private Long id;

    @Column(name = "first_name")
   // @ApiModelProperty(value = "User first name")
    private String firstname;

    //@ApiModelProperty(value = "User surname")
    @Column(name = "surname")
    private String surname;

    @Column(name = "password")
    //@ApiModelProperty(value = "User password")
    private String password;


   // @ApiModelProperty(value = "User position")
    @ManyToOne(targetEntity = Positions.class)
    @JoinColumn(name = "position_id")
    private Positions positions;

    @ManyToOne(targetEntity = Role.class)
    @JoinColumn(name = "role_id")
    //@ApiModelProperty(value = "User role")
    private Role role;


    @Column(name = "created_by")
   // @ApiModelProperty(value = "User creator")
    private int createdBy;

    @Column(name = "created_on")
    //@ApiModelProperty(value = "User creation time")
    private Timestamp createdOn;

    //@ApiModelProperty(value = "User Updater")
    @Column(name = "updated_by")
    private Long updatedBy;

    //@ApiModelProperty(value = "User updation time")
    @Column(name = "updated_on")
    private Timestamp updatedOn;

    @Column(name = "username")
   // @ApiModelProperty(value = "User email")
    private String email;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "verification_code",length = 64)
    private String verificationCode;


    public Users(Long id,String verificationCode,boolean isEnabled, String firstname, String surname, String email, String password, Positions positions, Role role, int createdBy, Timestamp createdOn, Long updatedBy, Timestamp updatedOn) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.password = password;
        this.positions = positions;
        this.role = role;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.updatedBy = updatedBy;
        this.updatedOn = updatedOn;
        this.email = email;
        this.isEnabled = isEnabled;
        this.verificationCode = verificationCode;
    }

    public Users() {

    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", positions=" + positions +
                ", role=" + role +
                ", createdBy=" + createdBy +
                ", createdOn=" + createdOn +
                ", updatedBy=" + updatedBy +
                ", updatedOn=" + updatedOn +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

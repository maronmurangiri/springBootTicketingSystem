package ticketing_system.app.percistance.repositories.userRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ticketing_system.app.percistance.Entities.userEntities.Role;


/**
 * The `RoleRepository` interface is a Spring Data JPA repository for managing Role entities.
 * It extends the JpaRepository interface to provide various methods for performing CRUD (Create, Read, Update, Delete) operations on Role entities.
 *
 * <p>Methods available in this repository:
 * - `findRoleByRoleName(String roleName)`: Retrieves a Role entity by its role name.
 *
 * <p>This interface is used to interact with the database and perform database operations related to Role entities.
 *
 * @author Maron
 * @version 1.0
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findRoleByRoleName(String roleName);
}

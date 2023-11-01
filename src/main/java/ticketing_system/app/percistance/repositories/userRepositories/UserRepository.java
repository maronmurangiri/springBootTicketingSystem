package ticketing_system.app.percistance.repositories.userRepositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ticketing_system.app.percistance.Entities.userEntities.Role;
import ticketing_system.app.percistance.Entities.userEntities.Users;

/**
 * The `UserRepository` interface is a Spring Data JPA repository for managing User entities.
 * It extends the JpaRepository interface to provide various methods for performing CRUD (Create, Read, Update, Delete) operations on User entities.
 *
 * <p>Methods available in this repository:
 * - `findByEmail(String email)`: Retrieves a User entity by its email.
 *
 * <p>This interface is used to interact with the database and perform database operations related to User entities.
 *
 * @author Maron
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Users findByEmail(String email);
    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.role = :newRole WHERE u.email = :email")
    void updateRoleByEmail(@Param("email") String email, @Param("newRole") Role newRole);

    @Query("SELECT u FROM Users u WHERE u.verificationCode = ?1")
    Users findByVerificationCode(String code);

}

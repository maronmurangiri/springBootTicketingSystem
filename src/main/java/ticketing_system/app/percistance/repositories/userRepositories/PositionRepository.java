package ticketing_system.app.percistance.repositories.userRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ticketing_system.app.percistance.Entities.userEntities.Positions;

import java.util.Optional;

/**
 * The `PositionRepository` interface is a Spring Data JPA repository for managing Position entities.
 * It extends the JpaRepository interface to provide various methods for performing CRUD (Create, Read, Update, Delete) operations on Position entities.
 *
 * <p>Methods available in this repository:
 * - `findPositionByPositionName(String positionName)`: Retrieves an Optional containing a Position entity by its position name.
 *
 * <p>This interface is used to interact with the database and perform database operations related to Position entities.
 *
 * @author Maron
 * @version 1.0
 * */
@Repository
public interface PositionRepository extends JpaRepository<Positions,Long> {
    Optional<Positions> findPositionByPositionName(String positionName);
}

package ticketing_system.app.percistance.repositories.userRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ticketing_system.app.percistance.Entities.userEntities.Department;

/**
        * The `DepartmentRepository` interface is a Spring Data JPA repository for managing Department entities.
        * It extends the JpaRepository interface to provide various methods for performing CRUD (Create, Read, Update, Delete) operations on Department entities.
        *
        * <p>Methods available in this repository:
        * - `findByDepartmentName(String departmentName)`: Retrieves a Department entity by its department name.
        *
        * <p>This interface is used to interact with the database and perform database operations related to Department entities.
        *
        * @author Maron
        * @version 1.0
        */
@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
    Department findByDepartmentName(String departmentName);
}

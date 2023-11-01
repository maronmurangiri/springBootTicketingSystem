package ticketing_system.app.percistance.repositories.TicketRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ticketing_system.app.percistance.Entities.TicketEntities.Tasks;

/**
 * the task repository.
 * @author kamar baraka.*/


@Repository
public interface TasksRepository extends PagingAndSortingRepository<Tasks, Long>, JpaRepository<Tasks, Long> {
}

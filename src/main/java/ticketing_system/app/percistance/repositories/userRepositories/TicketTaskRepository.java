package ticketing_system.app.percistance.repositories.userRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ticketing_system.app.percistance.Entities.TicketEntities.TicketTask;

@Repository
public interface TicketTaskRepository extends JpaRepository<TicketTask,Long>{

}
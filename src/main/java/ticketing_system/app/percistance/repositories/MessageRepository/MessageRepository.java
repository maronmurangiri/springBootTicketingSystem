package ticketing_system.app.percistance.repositories.MessageRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ticketing_system.app.percistance.Entities.Message.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    // Custom query methods if necessary
}


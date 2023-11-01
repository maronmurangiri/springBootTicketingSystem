package ticketing_system.app.Business.servises.MessageService;

import org.springframework.http.ResponseEntity;
import ticketing_system.app.percistance.Entities.Message.Message;

import java.util.List;

public interface MessagingService {
    ResponseEntity<?> sendMessage(Long senderId, Long receiverId, String content);
    ResponseEntity<?> getMessages(Long userId);

    void deleteMessage(Long messageId);


    ResponseEntity<?> editMessage(Long messageId, String content);
    ResponseEntity<?> searchMessages(String keyword);
}

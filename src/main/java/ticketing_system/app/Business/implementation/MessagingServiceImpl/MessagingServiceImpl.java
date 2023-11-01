package ticketing_system.app.Business.implementation.MessagingServiceImpl;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ticketing_system.app.Business.implementation.ReportService.MessageReportService;
import ticketing_system.app.Business.servises.MessageService.MessagingService;
import ticketing_system.app.percistance.Entities.Message.Message;
import ticketing_system.app.percistance.repositories.MessageRepository.MessageRepository;


import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class MessagingServiceImpl implements MessagingService {

    java.sql.Timestamp currentTimestamp = java.sql.Timestamp.from(Instant.now());
    String pattern = "yyyy-MM-dd HH:mm:ss";
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    String formattedTimestamp = currentTimestamp.toLocalDateTime().format(dateTimeFormatter);
    java.sql.Timestamp currentTimestampFormatted = Timestamp.valueOf(formattedTimestamp);

    private final MessageRepository messageRepository;

    @Autowired
    private MessageReportService messageReportService;

    @Autowired
    public MessagingServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public ResponseEntity<?> sendMessage(Long senderId, Long receiverId, String content) {
        // Implement the logic to send a message
        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(content);
        message.setTimestamp(new Timestamp(System.currentTimeMillis()));
        Message messageSaved = messageRepository.save(message);
        try {
            return messageReportService.exportMessageReport(messageSaved.getId());
        } catch (FileNotFoundException e) {
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ResponseEntity<?> editMessage(Long messageId, String content) {
        // Implement the logic to edit a message by ID
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setContent(content);
            message.setTimestamp(new Timestamp(System.currentTimeMillis()));
             messageRepository.save(message);
            try {
                return messageReportService.exportMessageReport(messageId);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (JRException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("Message not found");
        }
    }

    @Override
    public ResponseEntity<?> searchMessages(String keyword) {
         /*messageRepository.findAll().stream()
                .filter(message -> message.getContent().contains(keyword))
                .collect(Collectors.toList());*/
        try {
            return messageReportService.exportReportByKeyword(keyword);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public ResponseEntity<?> getMessages(Long userId) {
        // Implement the logic to retrieve messages for a specific user
         /*messageRepository.findAll().stream()
                .filter(message -> message.getReceiverId().equals(userId))
                .collect(Collectors.toList());*/
        try {
            return messageReportService.exportReportByUser(userId);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void deleteMessage(Long messageId) {
        // Implement the logic to delete a message by ID
        messageRepository.deleteById(messageId);

    }
}
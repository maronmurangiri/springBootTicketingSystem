package ticketing_system.app.preesentation.controler.massageController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ticketing_system.app.Business.servises.MessageService.MessagingService;
import ticketing_system.app.percistance.Entities.Message.Message;

import java.util.List;

@Tag(name="Massaging")
@RestController
@RequestMapping("api/v1/messages")
public class MessagingController {

    private final MessagingService messagingService;

    @Autowired
    public MessagingController(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    @PostMapping("/send")
    @Operation(summary = "sends massages ")
    @PreAuthorize("hasAuthority('admin')  or hasAuthority('agent') or hasAuthority('user')")
    public ResponseEntity<?> sendMessage(@RequestParam("senderId") Long senderId,
                                               @RequestParam("receiverId") Long receiverId,
                                               @RequestParam("content") String content) {
        try {
            return messagingService.sendMessage(senderId, receiverId, content);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/inbox/{userId}")
    @Operation(summary = "Recieves massages ")
    @PreAuthorize("hasAuthority('admin')  or hasAuthority('agent') or hasAuthority('user')")
    public ResponseEntity<?> getMessages(@PathVariable("userId") Long userId) {
        try {
            return messagingService.getMessages(userId);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{messageId}")
    @Operation(summary = "deletes massages")
    @PreAuthorize("hasAuthority('admin')  or hasAuthority('agent') or hasAuthority('user')")
    public ResponseEntity<String> deleteMessage(@PathVariable("messageId") Long messageId) {
        try {
            messagingService.deleteMessage(messageId);
            return ResponseEntity.ok("Message deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    @Operation(summary = "queries massages")
    @PreAuthorize("hasAuthority('admin')  or hasAuthority('agent') or hasAuthority('user')")
    public ResponseEntity<?> searchMessages(@RequestParam("keyword") String keyword) {
        try {
             return  messagingService.searchMessages(keyword);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{messageId}")
    @Operation(summary = "edits massages")
    @PreAuthorize("hasAuthority('admin')  or hasAuthority('agent') or hasAuthority('user')")
    public ResponseEntity<?> editMessage(@PathVariable("messageId") Long messageId,
                                               @RequestParam("content") String content) {
        try {
            return messagingService.editMessage(messageId, content);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
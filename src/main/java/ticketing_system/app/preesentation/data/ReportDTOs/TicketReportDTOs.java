package ticketing_system.app.preesentation.data.ReportDTOs;

import lombok.*;
import ticketing_system.app.percistance.Enums.Tags;
import ticketing_system.app.percistance.Enums.TicketPriority;
import ticketing_system.app.percistance.Enums.TicketStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Value
@Getter
@Setter
@RequiredArgsConstructor
public class TicketReportDTOs {
     Long ticketId;
     String ticketName;
     String description;
     TicketStatus status;
     String agentAssigned;
     LocalDateTime createdOn;
     LocalDateTime deadline;
     List<String> task;


}

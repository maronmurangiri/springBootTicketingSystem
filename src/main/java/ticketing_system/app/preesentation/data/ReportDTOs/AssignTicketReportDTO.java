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
public class AssignTicketReportDTO {
    Long ticketId;
    String ticketName;
    TicketStatus status;
    TicketPriority priority;
    String agentAssigned;
    Tags tag;
    List<String> task;
    LocalDateTime createdOn;
    LocalDateTime deadline;


}

package ticketing_system.app.preesentation.data.ReportDTOs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;
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
public class UserTicketReportDTO {
    String ticketName;
    String description;
    TicketStatus status;

}

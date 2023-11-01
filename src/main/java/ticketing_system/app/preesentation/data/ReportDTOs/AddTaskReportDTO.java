package ticketing_system.app.preesentation.data.ReportDTOs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;
import ticketing_system.app.percistance.Enums.TicketStatus;

import java.util.List;

@Value
@Getter
@Setter
@RequiredArgsConstructor
public class AddTaskReportDTO {
    Long ticketId;
    String ticketName;
    String description;
    TicketStatus status;
    List<String> task;
}

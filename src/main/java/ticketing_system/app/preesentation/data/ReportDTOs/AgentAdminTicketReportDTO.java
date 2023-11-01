package ticketing_system.app.preesentation.data.ReportDTOs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;
import ticketing_system.app.percistance.Enums.TicketStatus;

@Value
@Getter
@Setter
@RequiredArgsConstructor
public class AgentAdminTicketReportDTO {

        Long ticketId;
        String ticketName;
        String description;
        TicketStatus status;


    }

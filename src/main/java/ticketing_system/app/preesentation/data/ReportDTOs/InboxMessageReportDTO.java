package ticketing_system.app.preesentation.data.ReportDTOs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.sql.Timestamp;

@Value
@RequiredArgsConstructor
@Getter
@Setter
public class InboxMessageReportDTO {
        Long messageId;
        String content;
        String sender;
        Timestamp timeMessageSent;
    }


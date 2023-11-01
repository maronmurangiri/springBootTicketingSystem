package ticketing_system.app.preesentation.data.ReportDTOs;

import lombok.*;

import java.sql.Timestamp;


@Value
@RequiredArgsConstructor
@Getter
@Setter
public class MessageReportDTOs {
    Long messageId;
    String content;
    String sender;
    String receiver;
    Timestamp timeMessageSent;
}

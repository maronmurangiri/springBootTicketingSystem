package ticketing_system.app.preesentation.data.ReportDTOs;



import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import ticketing_system.app.percistance.Entities.userEntities.Users;

import java.sql.Timestamp;

@Value
@Getter
@Setter
@RequiredArgsConstructor
public class UpdatedUserReportDTO {
    Long id;
    String firstName;
    String surname;
    String email;
    String roleName;
    String positionName;
    String updatedBy;
    Timestamp updatedOn;

}

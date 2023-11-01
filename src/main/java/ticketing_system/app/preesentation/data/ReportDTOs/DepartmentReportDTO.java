package ticketing_system.app.preesentation.data.ReportDTOs;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import ticketing_system.app.percistance.Entities.userEntities.Department;
import ticketing_system.app.percistance.Entities.userEntities.Users;

import java.sql.Timestamp;


@Data
@Value
@RequiredArgsConstructor
@Getter
@Setter
public class DepartmentReportDTO {
     Long departmentId;
     String departmentName;

     String director;

     String createdBy;

     Timestamp createdOn;

}

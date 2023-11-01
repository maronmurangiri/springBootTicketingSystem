package ticketing_system.app.preesentation.data.ReportDTOs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.sql.Timestamp;

@Value
@Getter
@Setter
@RequiredArgsConstructor
public class RoleReportDTO {
     Long roleId;
     String roleName;
     String createdBy;
     Timestamp createdOn;
}

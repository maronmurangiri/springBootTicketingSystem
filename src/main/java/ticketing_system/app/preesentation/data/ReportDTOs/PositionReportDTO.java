package ticketing_system.app.preesentation.data.ReportDTOs;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import ticketing_system.app.percistance.Entities.userEntities.Positions;

import java.sql.Timestamp;

@Data
@Value
@RequiredArgsConstructor
public class PositionReportDTO {
     Long positionId;
     String positionName;
     String departmentName;
     String createdBy;
     Timestamp createdOn;

}

package ticketing_system.app.Business.servises.userServices;

import ticketing_system.app.percistance.Entities.userEntities.Positions;
import ticketing_system.app.preesentation.data.userDTOs.PositionDTO;

import java.util.List;

public interface PositionService {
    Positions createPosition(String positionCreatedEmail,String departmentName,PositionDTO positionDTO);
    List<PositionDTO> retrievePositions();

    PositionDTO retrievePositionById(Long positionId);

    Positions updatePosition(Long positionId,String userCreatedEmail,String departmentName, PositionDTO positionDTO);
    Positions retrievePositionByName(String positionName);

    boolean deletePositionById(Long positionId);
}

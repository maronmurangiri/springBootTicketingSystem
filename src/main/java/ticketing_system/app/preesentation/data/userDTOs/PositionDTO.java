package ticketing_system.app.preesentation.data.userDTOs;

import lombok.Data;


/**
 * The `PositionDTO` class is a Data Transfer Object (DTO) that represents position-related data.
 * It is used to transfer position-related information between different layers of the application, such as between the presentation layer and the business logic layer.
 *
 * This class provides a lightweight representation of a position and typically contains only the necessary attributes for data exchange, excluding any business logic.
 *
 * Attributes:
 * - `positionName`: The name of the position.
 *
 * Data transfer objects are often used to encapsulate data and send it between components, which can help improve application performance and separation of concerns.
 *
 * @author Maron
 * @version 1.0
 * */
@Data
public class PositionDTO {
    private String positionName;

}

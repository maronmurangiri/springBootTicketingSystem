package ticketing_system.app.preesentation.data.userDTOs;

import lombok.Data;

/**
 * The `DepartmentDTO` class is a Data Transfer Object (DTO) that represents department data.
 * It is used to transfer department-related information between different layers of the application, such as between the presentation layer and the business logic layer.
 *
 * This class provides a lightweight representation of a department and typically contains only the necessary attributes for data exchange, excluding any business logic.
 *
 * Attributes:
 * - `departmentName`: The name of the department.
 * - `description`: A description or additional information about the department.
 *
 * Data transfer objects are often used to encapsulate data and send it between components, which can help improve application performance and separation of concerns.
 *
 * @author Maron
 * @version 1.0
 */
@Data
public class DepartmentDTO {
    private String departmentName;
    private String description;

}

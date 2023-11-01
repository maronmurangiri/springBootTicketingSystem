package ticketing_system.app.Business.servises.userServices;

import ticketing_system.app.percistance.Entities.userEntities.Department;
import ticketing_system.app.preesentation.data.userDTOs.DepartmentDTO;

import java.util.List;

public interface DepartmentService {
    Department createDepartment(String departmentCreatedEmail, String departmentDirectorEmail,DepartmentDTO departmentDTO);
    List<DepartmentDTO> retrieveDepartments();

    DepartmentDTO retrieveDepartmentById(Long departmentId);

    DepartmentDTO updateDepartment(Long departmentId,String departmentCreatedEmail,String departmentDirectorEmail, DepartmentDTO departmentDTO);

    Department retrieveDepartmentByName(String departmentName);

    boolean deleteDepartmentById(Long departmentId);
}

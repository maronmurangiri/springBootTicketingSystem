package ticketing_system.app.Business.servises.userServices;

import ticketing_system.app.percistance.Entities.userEntities.Role;
import ticketing_system.app.preesentation.data.userDTOs.RoleDTO;

import java.util.List;

public interface RoleService {
    Role createRole(String roleCreatedEmail, RoleDTO roleDTO);
    List<RoleDTO> retrieveRoles();

    RoleDTO retrieveRoleById(Long roleId);

    Role updateRole(Long roleId,String roleUpdatedEmail, RoleDTO roleDTO);
    Role retrieveRoleByName(String roleName);


    boolean deleteRoleById(Long roleId);
}

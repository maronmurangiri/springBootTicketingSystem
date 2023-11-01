package ticketing_system.app.Business.implementation.userServiceImplementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ticketing_system.app.Business.servises.userServices.RoleService;
import ticketing_system.app.percistance.Entities.userEntities.Role;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.percistance.repositories.userRepositories.RoleRepository;
import ticketing_system.app.preesentation.data.userDTOs.RoleDTO;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The `RoleServiceImpl` class is an implementation of the `{@link RoleService}` interface and is responsible for managing roles and permissions.
 * It provides methods for creating, retrieving, updating, and deleting roles.
 *
 * <p>This class uses the `ModelMapper` for converting between DTOs and entity objects and the `JwtTokenProviderImpl` for user authorization.
 *
 * <p>Dependencies:
 * - `ModelMapper`: Used for object mapping between DTOs and entity objects.
 * - `RoleService`: Provides the required business logic for role management.
 * - `RoleRepository`: Provides access to role data stored in the database.
 * - `UserImpematation`: Provides user-related functionality and information retrieval.
 * - `JwtTokenProviderImpl`: Handles JWT-based user authorization.
 *
 * <p>Example Usage:
 * RoleServiceImpl roleService = new RoleServiceImpl(roleRepository, userImpematation, modelMapper, tokenProvider);
 * RoleDTO roleDTO = new RoleDTO(); // Initialize with role data
 * Role createdRole = roleService.createRole(roleCreatedEmail, token, roleDTO);
 *
 * List<RoleDTO> roles = roleService.retrieveRoles(token);
 * RoleDTO retrievedRole = roleService.retrieveRoleById(roleId, token);
 * Role updatedRole = roleService.updateRole(roleId, roleUpdatedEmail, token, roleDTO);
 * Role role = roleService.retrieveRoleByName(roleName);
 * boolean deleted = roleService.deleteRoleById(roleId, token);
 *
 * @author Maron
 * @version 1.0
 */
@Service
public class RoleServiceImpl implements RoleService {

    Timestamp currentTimestamp = Timestamp.from(Instant.now());
    String pattern = "yyyy-MM-dd HH:mm:ss";
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    String formattedTimestamp = currentTimestamp.toLocalDateTime().format(dateTimeFormatter);
    Timestamp currentTimestampFormatted = Timestamp.valueOf(formattedTimestamp);

    @Autowired
    private RoleRepository roleRepository;

    @Lazy
    @Autowired
    private UserImpematation userImpematation;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtTokenProviderImpl tokenProvider;

    public RoleDTO convertToDto(Role role){

        return modelMapper.map(role, RoleDTO.class);
    }

    public Role convertToRole(RoleDTO roleDTO){

        return modelMapper.map(roleDTO, Role.class);
    }

    public List<RoleDTO> convertToListDTOs(List<Role> roleList) {
        return roleList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Role createRole(String roleCreatedEmail,RoleDTO roleDTO) {
        if (roleDTO.getRoleName().isBlank() || roleDTO.getRoleName() == null) {
            throw new IllegalArgumentException("Item name can neither be null nor blank");
        }

        if (roleDTO == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        Role role = convertToRole(roleDTO);
        //set created on
        role.setCreatedOn(currentTimestampFormatted);


        //set created by
        Users createdByUsers = userImpematation.retrieveUserByEmail(roleCreatedEmail);
        System.out.println(createdByUsers);
        role.setCreatedBy(createdByUsers.getCreatedBy());


        return roleRepository.save(role);

    }

    @Override
    public List<RoleDTO> retrieveRoles() {

            return convertToListDTOs(roleRepository.findAll());
    }

    @Override
    public RoleDTO retrieveRoleById(Long roleId) {
        if (roleRepository.existsById(roleId)) {
                return this.convertToDto(roleRepository.findById(roleId).get());
        }
        throw new IllegalArgumentException("Role not found");
    }

    @Override
    public Role updateRole(Long roleId, String roleUpdatedEmail,RoleDTO roleDTO) {
        if (roleRepository.existsById(roleId)){
                Role role = convertToRole(roleDTO);

                //set role id
                role.setRoleId(roleId);
                //set created on
                role.setUpdatedOn(currentTimestampFormatted);


                //set created by
                Users createdByUsers = userImpematation.retrieveUserByEmail(roleUpdatedEmail);
                System.out.println(createdByUsers);
                role.setUpdatedBy(createdByUsers.getCreatedBy());
                return roleRepository.save(role);
        }
        throw new IllegalArgumentException("Role not found");

    }

    @Override
    public Role retrieveRoleByName(String roleName) {

        return roleRepository.findRoleByRoleName(roleName);
    }

    @Override
    public boolean deleteRoleById(Long roleId) {
        if (roleRepository.existsById(roleId)){
            roleRepository.deleteById(roleId);
            return true;

        }
        throw new IllegalArgumentException("Role not found");

    }
}

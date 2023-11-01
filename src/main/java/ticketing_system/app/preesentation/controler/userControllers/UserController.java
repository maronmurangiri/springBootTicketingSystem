package ticketing_system.app.preesentation.controler.userControllers;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import ticketing_system.app.Business.implementation.ReportService.UpdateUserReportService;
import ticketing_system.app.Business.implementation.ReportService.UserReportService;
import ticketing_system.app.Business.implementation.userServiceImplementations.UserImpematation;
import ticketing_system.app.Business.servises.UserService;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.preesentation.data.ReportDTOs.UpdatedUserReportDTO;
import ticketing_system.app.preesentation.data.ReportDTOs.UserReportDTO;
import ticketing_system.app.preesentation.data.userDTOs.UserDTO;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;


/**
 * The `UserController` class provides a REST API for managing users within the ticketing system.
 * It offers endpoints for creating, updating, retrieving, and deleting users.
 *
 * <p>Dependencies:
 * - `UserImpematation`: Handles user-related operations.
 *
 * <p>Example Usage:
 * UserController userController = new UserController(userImplementation);
 * ResponseEntity<?> createdUser = userController.createUser(authorizationHeader, userCreatedEmail, positionName, roleName, userDTO);
 * ResponseEntity<?> updatedUser = userController.updateUser(userId, authorizationHeader, userUpdatedEmail, positionName, roleName, userDTO);
 * ResponseEntity<?> allUsers = userController.retrieveUsers(authorizationHeader);
 * ResponseEntity<?> userById = userController.retrieveUserById(authorizationHeader, userId);
 * ResponseEntity<?> deletedUser = userController.deleteUserById(userId, authorizationHeader);
 *
 * @author Maron
 * @version 1.0
 */
@RestController
@RequestMapping("api/v1/user")
@Tag(name = "CRUD Rest APIs for User")
//@Tag(name = "user api", description = "perform CRUD operations on user")
public class UserController {
    private UserImpematation userImplementation;


    @Autowired
    private  UserImpematation userService;

    @Autowired
    private UserReportService userReportService;

    @Autowired
    private UpdateUserReportService updateUserReportService;


    @Autowired
    public UserController(UserImpematation userImplementation) {
        this.userImplementation = userImplementation;
    }

    @GetMapping("/user/authorities")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Collection<?>> getUserAuthorities(@RequestParam String username) {
        Collection<?> authorities = userImplementation.getAuthoritiesForUser(username);
        return ResponseEntity.ok(authorities);
    }
    @Operation(description = "Create user REST API")
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> createUser(@RequestParam("UserCreatorEmail") String userCreatorEmail, @RequestParam("positionName") String positionName,@RequestParam("roleName") String roleName, @RequestBody UserDTO userDTO,HttpServletRequest request){
        try {
            //String token = authorizationHeader;
            System.out.println(userCreatorEmail);
            Users user = userImplementation.createUser(userCreatorEmail, roleName, positionName, userDTO,getSiteURL(request));
            Long userId = user.getId();
            return userReportService.exportUserReport(userId);
        }
         catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Operation(description = "Update user by Id REST API")
    @PutMapping("/update/{userId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> updateUser(@PathVariable("userId") Long userId,@RequestParam("userUpdatedEmail") String userUpdatedEmail,@RequestParam("positionName") String positionName,@RequestParam("roleName") String roleName, @RequestBody UserDTO userDTO){
        try {
            //String token = authorizationHeader;
            System.out.println(userId);
           Users user = userImplementation.updateUser(userId,userUpdatedEmail,roleName,positionName,userDTO);
            Long userUpdatedId = user.getId();
            return updateUserReportService.exportUserReport(userUpdatedId);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("/elevateUserRole")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> updateUserRole(@RequestParam("email") String email, @RequestParam("UserNewRoleName") String newRoleName) throws JRException, FileNotFoundException {
        Users user = userImplementation.updateUserRole(email,newRoleName);
        Long userId = user.getId();
        return userReportService.exportUserReport(userId);
    }

    @Operation(description = "Get all users REST API")
    @GetMapping("/retrieve")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> retrieveUsers() throws JRException, FileNotFoundException {
       // String token = authorizationHeader;
         userImplementation.retrieveUsers();
        return userReportService.exportReport();
    }

    @Operation(description = "Get user by Id REST API")
    @GetMapping("/retrieveById/{userId}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('agent')")
    public ResponseEntity<?> retrieveUserById(@PathVariable("userId") Long userId){
        try {
           // String token = authorizationHeader;
            Users user = userImplementation.retrieveUserById(userId);
            Long userRetrievedId = user.getId();
            return userReportService.exportUserReport(userRetrievedId);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(description = "Delete user by Id REST API")
    @DeleteMapping("/deleteById/{userId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") Long userId){
        try {
            //String token = authorizationHeader;
            boolean isDeleted = userImplementation.deleteUserById(userId);
            if (isDeleted){
                return ResponseEntity.ok("User deleted successfully");
            }
            else {
                return ResponseEntity.internalServerError().build();
            }

        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}

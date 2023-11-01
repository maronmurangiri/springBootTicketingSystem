package ticketing_system.app.Business.servises.userServices;

import jakarta.mail.MessagingException;
import ticketing_system.app.percistance.Entities.userEntities.Role;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.preesentation.data.userDTOs.UserDTO;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {
    Users createUser(String createdByEmail,String positionName, String roleName,UserDTO userDTO,String siteURL);
    Users createUserIn(String roleName, String positionName, UserDTO userDTO,String siteURL);
    List<Users> retrieveUsers();
    Users updateUserRole(String email, String newRoleName);

    Users retrieveUserById(Long userId);
    Users retrieveUserByEmail(String email);

    Users updateUser(Long userId,String userUpdatedEmail,String positionName,String roleName,UserDTO userDTO);

    boolean deleteUserById(Long userId);
    Users authenticateUser(String email, String password);
     void sendVerificationEmail(Users user, String siteURL) throws MessagingException, UnsupportedEncodingException;
}

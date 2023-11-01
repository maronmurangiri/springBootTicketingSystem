package ticketing_system.app.Business.servises.userServices;

import ticketing_system.app.preesentation.data.userDTOs.UserDTO;

public interface User_service {

    UserDTO createUser(UserDTO userDTO);
   // void deleteUserById(long id);
}

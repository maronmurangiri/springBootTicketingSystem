package ticketing_system.app.Business.servises;

import ticketing_system.app.preesentation.data.UserDTO;

public interface User_service {

    UserDTO createUser(UserDTO userDTO);
    void deleteUserById(long id);
}
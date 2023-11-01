package ticketing_system.app.preesentation.data.userDTOs;

import lombok.Data;

@Data
public class UserSignRequestDTO {
    String email;
    String password;
}

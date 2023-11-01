package ticketing_system.app.preesentation.data.userDTOs;

import lombok.Data;

@Data
public class UserResponseDTO {
    String email;
    String  role;
    String token;
}

package ticketing_system.app.preesentation.controler.userControllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ticketing_system.app.Business.implementation.userServiceImplementations.JwtTokenProviderImpl;
import ticketing_system.app.Business.implementation.userServiceImplementations.UserImpematation;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.preesentation.data.userDTOs.UserDTO;
import ticketing_system.app.preesentation.data.userDTOs.UserResponseDTO;
import ticketing_system.app.preesentation.data.userDTOs.UserSignRequestDTO;

/**
 * The `AuthController` class provides a REST API for user authentication and login.
 * It offers an endpoint for user login using their email and password, and it returns a JSON Web Token (JWT) upon successful authentication.
 *
 * <p>Dependencies:
 * - `UserImpematation`: Handles user-related operations, including authentication.
 * - `JwtTokenProviderImpl`: Generates JWT tokens for authenticated users.
 *
 * <p>Example Usage:
 * AuthController authController = new AuthController(userImpematation, tokenProvider);
 * UserSignRequestDTO userSignRequest = new UserSignRequestDTO("user@example.com", "password");
 * ResponseEntity<?> loginResponse = authController.signIn(userSignRequest);
 *
 * @author Maron
 * @version 1.0
 */
//@RestController
//@RequestMapping("/api/v1/auth")
//@Tag(name = "Login endpoint",description = "authenticate user")
public class AuthController {

    @Lazy
    @Autowired
    UserImpematation userImpematation;

    @Autowired
    JwtTokenProviderImpl tokenProvider;


    //@ApiOperation(value = "Login REST API")
    @Operation(description = "Login REST API")
    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody UserSignRequestDTO userSignRequest) {
        String email = userSignRequest.getEmail();
        String password = userSignRequest.getPassword();
        Users user = userImpematation.authenticateUser(email,password);

        if (user==null){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        String token = tokenProvider.generateToken(user);
        UserResponseDTO response =  new UserResponseDTO();
        response.setEmail(email);
        response.setRole(String.valueOf(user.getRole()));
        response.setToken(token);

        return ResponseEntity.ok(response);
    }

}

package ticketing_system.app.Business.implementation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ticketing_system.app.Business.implementation.userServiceImplementations.JwtTokenProviderImpl;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.percistance.repositories.userRepositories.UserRepository;

import java.security.NoSuchAlgorithmException;

class JwtTokenProviderImplTest {

    private JwtTokenProviderImpl jwtTokenProvider;
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() throws NoSuchAlgorithmException {
       // jwtTokenProvider = new JwtTokenProviderImpl(new JwtKeyProviderImpl(),userRepository);
    }

    @Test
    void generateToken() {
        Users users = new Users();
        users.setEmail("maron@gmail.com");
       // users.setRole(new Role("admin"));

        String token = jwtTokenProvider.generateToken(users);
        Assertions.assertNotNull(token);

    }

    @Test
    void testGetUserFromToken() {
       // String token = generateToken();
       // Users parsedUser = jwtTokenProvider.getUserFromToken(token);
      //  Assertions.assertNotNull(parsedUser);

       // Assertions.assertEquals(user.getEmail(), parsedUser.getEmail());
        //Assertions.assertEquals(user.getRole(), parsedUser.getRole());


    }
}
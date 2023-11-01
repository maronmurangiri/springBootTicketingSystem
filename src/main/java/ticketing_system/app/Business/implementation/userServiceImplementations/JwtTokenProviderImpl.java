package ticketing_system.app.Business.implementation.userServiceImplementations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ticketing_system.app.Business.servises.userServices.JwtTokenProvider;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.percistance.repositories.userRepositories.UserRepository;

import java.security.*;
import java.util.Date;

/**
 * JwtTokenProviderImpl is responsible for generating and parsing JWT (JSON Web Token) tokens.
 * It provides methods for generating tokens based on user information and extracting user details
 * from a token.
 *
 * <p>This class uses RSA cryptographic keys for signing and verifying tokens, as well as setting token expiration times.
 * It relies on the {@link JwtKeyProviderImpl} for access to cryptographic keys.
 *
 * <p><p>The JwtTokenProviderImpl class offers the following functionality:
 *
 * - Generation of JWT tokens with user claims such as subject and role.
 * - Validation and parsing of JWT tokens to retrieve user details.
 *
 * <p>It is essential for securing user authentication and authorization in the application.
 *
 * <p>Dependencies:
 * - {@link JwtKeyProviderImpl} : Provides access to cryptographic keys required for token generation and validation.
 * - {@link UserRepository} : Used to retrieve user details from the database based on information contained in the token.
 *
 * <p>Configuration:
 * - The token expiration time is configured through the "jwt.expirationTime" property.
 *
 *
 * <p>Example Usage:
 * JwtTokenProviderImpl tokenProvider = new JwtTokenProviderImpl(jwtKeyProvider, userRepository);
 * String token = tokenProvider.generateToken(user);
 * Users extractedUser = tokenProvider.getUserFromToken(token);
 *
 * @author Maron
 * @version 1.0
 *
 */

@Service
public class JwtTokenProviderImpl implements JwtTokenProvider {

    private KeyPair keyPair;


    /**
     * The logger instance for logging. Used to record log messages related to token processing.
     */
    public static Logger logger = LogManager    .getLogger(JwtTokenProviderImpl.class);

    @Lazy
    @Autowired
    private UserImpematation userImpematation;
    /**
     * A declaration of an instance variable  JwtKeyProviderImpl for accessing cryptographic keys.
     */


    @Autowired
    JwtKeyProviderImpl keyProvider;
    /**
     * A declaration of an instance variable UserRepository for fetching user information.
     */
    @Autowired
    private UserRepository userRepository;


    /**
     * Constructs a JwtTokenProviderImpl instance with the specified dependencies.
     *
     //* @param jwtKeyProvider An instance of JwtKeyProviderImpl for cryptographic key management.
   //  * @param userRepository An instance of UserRepository for user data retrieval.
     */

    /*public JwtTokenProviderImpl() {
       keyPair =generateRSASigningKeyPair();
    }*/

    //@Autowired
    /*public JwtTokenProviderImpl() {
       keyProvider =new JwtKeyProviderImpl();
    }*/


    /**
     * The expiration time for JWT (JSON Web Token) tokens, in milliseconds.
     * This value is retrieved from the application configuration using the
     * {@code @Value} annotation and the specified property name "${jwt.expirationTime}".
     * It determines the time duration for which a generated JWT token will be considered valid.
     * When a token's expiration time is reached, it will no longer be considered valid.
     */
    @Value("${jwt.expirationTime}")
    private long expirationTime;

    /**
     * Generates a JWT token for a given users with specified claims and an expiration time.
     *
    // * @param users The users for whom the token is generated.
     * @return A JWT token as a String.
     * @throws RuntimeException If an error occurs during token generation.
     */

    @Override
    public String generateToken(Users users) {
        try {
            System.out.println(users);
            PrivateKey signingKey = keyProvider.getKeyPair().getPrivate();
            Claims claims = Jwts.claims().setSubject(users.getEmail());
            claims.put("role", users.getRole());

            Date now = new Date();
            Date expiryTime = new Date(now.getTime() + expirationTime);
            System.out.println(keyProvider.getKeyPair().getPrivate());
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(expiryTime)
                    .signWith(keyProvider.getKeyPair().getPrivate(), SignatureAlgorithm.RS256)
                    .compact();
            System.out.println(token);
            return token;
        }
        catch (Exception e){
            logger.error("error generating token"+e);
            throw new RuntimeException("Error generating token");
        }

    }

    /**
     * Retrieves a user from a JWT token by parsing the token and querying the UserRepository.
     *
     * @param token The JWT token to parse and extract user information.
     * @return The Users object extracted from the token.
     * @throws RuntimeException If an error occurs during token parsing or if the user is not found in the repository.
     */
    @Override
    public Users getUserFromToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(keyProvider.getKeyPair().getPublic())
                    .build()
                    .parseClaimsJws(token);

           Claims claims = claimsJws.getBody();

            String userEmail = claims.getSubject();
            Users users = userRepository.findByEmail(userEmail);

            System.out.println(userEmail);
            if (users != null) {
                return users;
            } else {
                throw new RuntimeException("Users not found");
            }
        }catch (Exception e){
            logger.error("Error introspecting token"+e);
            throw new RuntimeException("Invalid token!");
        }
    }
    public String loggedInUserRole(String token) {

        Users loggedInUser = getUserFromToken(token);
        String loggedInUserRole = loggedInUser.getRole().getRoleName();
        return loggedInUserRole;
    }
}

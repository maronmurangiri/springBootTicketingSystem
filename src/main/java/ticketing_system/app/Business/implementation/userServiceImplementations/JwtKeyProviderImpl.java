package ticketing_system.app.Business.implementation.userServiceImplementations;

import lombok.Data;
import org.springframework.stereotype.Service;
import ticketing_system.app.Business.servises.userServices.JwtKeyProvider;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * Implementation of the {@link JwtKeyProvider} interface that generates RSA key pairs for JWT signing.
 * This class uses the RSA algorithm to generate key pairs with a key size of 2048 bits.
 *
 * <p>The generated key pair can be used for JWT signing operations, such as creating and verifying JWTs.
 * It is recommended to securely store and manage the generated key pair, especially the private key.
 *
 * @author Maron
 * @version 1.0
 * @since 2023-10-05
 */

@Service
@Data
public class JwtKeyProviderImpl implements JwtKeyProvider {
    private KeyPair keyPair;

    /**
     * Constructs a new JwtKeyProviderImpl instance and generates an RSA signing key pair.
     * The generated key pair is stored in the 'keyPair' field of this class.
     */
    public JwtKeyProviderImpl() {
        keyPair = generateRSASigningKeyPair();
    }

    /**
     * Generates an RSA signing key pair with a key size of 2048 bits.
     *
     * @return The generated RSA key pair.
     * @throws RuntimeException if there is an issue generating the key pair, e.g., due to NoSuchAlgorithmException.
     */
    @Override
    public KeyPair generateRSASigningKeyPair() {

        try {
            //creating instance of key pair generator using RSA algorithm
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

            //setting key size
            keyPairGenerator.initialize(2048);

            //generating and returning key pair
            keyPair = keyPairGenerator.generateKeyPair();

            return keyPair;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

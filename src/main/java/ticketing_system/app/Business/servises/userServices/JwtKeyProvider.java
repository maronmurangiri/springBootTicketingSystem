package ticketing_system.app.Business.servises.userServices;

import java.security.KeyPair;

public interface JwtKeyProvider {
    KeyPair generateRSASigningKeyPair();
}

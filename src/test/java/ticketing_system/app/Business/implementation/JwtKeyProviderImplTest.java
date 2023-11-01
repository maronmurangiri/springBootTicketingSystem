package ticketing_system.app.Business.implementation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ticketing_system.app.Business.implementation.userServiceImplementations.JwtKeyProviderImpl;

import java.security.KeyPair;
import java.security.PublicKey;

class JwtKeyProviderImplTest {


    private JwtKeyProviderImpl jwtKeyProvider;

    @BeforeEach
    public void setUp(){
        this.jwtKeyProvider= new JwtKeyProviderImpl();
    }

    @Test
    void testGenerateRSASigningKeyPair() {
        KeyPair keyPair = jwtKeyProvider.generateRSASigningKeyPair();

        Assertions.assertNotNull(keyPair);
        Assertions.assertNotNull(keyPair.getPrivate());
        Assertions.assertNotNull(keyPair.getPublic());

    }

    @Test
    void testKeyPairIsGeneratedOnConstruction() {
        Assertions.assertNotNull(jwtKeyProvider.getKeyPair());
    }

    @Test
    void testPublicKeyIsAccessible() {
        PublicKey publicKey = jwtKeyProvider.getKeyPair().getPublic();
        Assertions.assertNotNull(publicKey);
    }

}
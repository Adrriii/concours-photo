package services;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AuthenticationServiceTest {
    @Test
    public void testHash() throws NoSuchAlgorithmException {
        AuthenticationService service = new AuthenticationService();

        String toHash = "Robin";

        String hashed = service.hash(toHash);
        System.out.println("Hash " + toHash + " to " + hashed);

        assertEquals(service.hash(toHash), hashed);
        assertNotEquals(service.hash("Robim"), hashed);
    }
}
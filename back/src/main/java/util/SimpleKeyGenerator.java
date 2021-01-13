package util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class SimpleKeyGenerator implements KeyGenerator {
    @Override
    public Key generateKey() {
        String keyString = "SuperSecretKeyThatNoOneKnowWithMoreThan512Bits_SuperSecretKeyThatNoOneKnowWithMoreThan512Bits";
        // Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
        return Keys.hmacShaKeyFor(keyString.getBytes());
        // return key;
    }
}

package util;

import io.jsonwebtoken.security.Keys;
import java.security.Key;

public class SimpleKeyGenerator implements KeyGenerator {
    @Override
    public Key generateKey() {
        String keyString = "SuperSecretKeyThatNoOneKnowWithMoreThan512Bits_SuperSecretKeyThatNoOneKnowWithMoreThan512Bits";
        return Keys.hmacShaKeyFor(keyString.getBytes());
    }
}

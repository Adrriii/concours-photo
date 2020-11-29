package services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import jdk.internal.net.http.websocket.Message;

public class AuthentificationService {
    @Inject static UserDao userDao;
    
    public static String hash(String toHash) {
        final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        final byte[] hashBytes = digest.digest(toHash.getBytes(StandardCharsets.UTF_8));

        return hashBytes.toString();
    }

    public static User registerUser(User user, String passwordHash) {
        
        try {
            userDao.getByUsername(user.username);

            return null;
        } catch (Exception e) {
            
            return userDao.insert(user, AuthenAuthentificationService.hash(passwordHash));
        }
    }

    public static User loginUser(String username, String passwordHash) {
        
        try {
            User user = userDao.getByLogin(username, AuthenAuthentificationService.hash(passwordHash));

            return user;
        } catch (Exception e) {
            
            return null;
        }
    }
}
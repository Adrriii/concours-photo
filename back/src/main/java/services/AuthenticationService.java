package services;

import dao.UserDao;
import dao.UserSettingDao;
import model.User;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AuthenticationService {
    @Inject static UserDao userDao;
    @Inject static UserSettingDao userSettingDao;

    private AuthenticationService() {}

    public static String hash(String toHash) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        final byte[] hashBytes = digest.digest(toHash.getBytes(StandardCharsets.UTF_8));

        return Arrays.toString(hashBytes);
    }

    public static User registerUser(String username, String passwordHash) throws Exception {
        
        try {
            userDao.getByUsername(username);

            return null;
        } catch (Exception e) {
            User newUser = new User(username, null);
            newUser = userDao.insert(newUser, AuthenticationService.hash(passwordHash));
            userSettingDao.insertDefaultsForUser(newUser.id);

            return newUser;
        }
    }

    public static User loginUser(String username, String passwordHash) {
        try {
            return userDao.getByLogin(username, AuthenticationService.hash(passwordHash));
        } catch (Exception e) {
            return null;
        }
    }
}
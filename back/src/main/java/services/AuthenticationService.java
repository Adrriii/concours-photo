package services;

import dao.UserDao;
import dao.UserSettingDao;
import model.User;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Optional;

public class AuthenticationService {
    @Inject UserDao userDao;
    @Inject UserSettingDao userSettingDao;

    public AuthenticationService() {}

    public String hash(String toHash) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        final byte[] hashBytes = digest.digest(toHash.getBytes(StandardCharsets.UTF_8));

        return Arrays.toString(hashBytes);
    }

    public Optional<User> registerUser(String username, String passwordHash) throws Exception {
        
        try {
            userDao.getByUsername(username);
            return Optional.empty();

        } catch (Exception e) {
            User newUser = new User(username, null);
            newUser = userDao.insert(newUser, hash(passwordHash));

            return Optional.of(newUser);
        }
    }

    public Optional<User> loginUser(String username, String passwordHash) {
        try {
            return Optional.of(userDao.getByLogin(username, hash(passwordHash)));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
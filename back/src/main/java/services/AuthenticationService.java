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

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public String hash(String toHash) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        final byte[] hashBytes = digest.digest(toHash.getBytes(StandardCharsets.UTF_8));

        return bytesToHex(hashBytes);
    }

    public Optional<User> registerUser(String username, String passwordHash) throws Exception {
        
        try {
            userDao.getByUsername(username);
            return Optional.empty();

        } catch (Exception e) {
            e.printStackTrace();
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
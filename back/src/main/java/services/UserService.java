package services;

import dao.UserDao;

import model.User;
import model.UserPublic;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import java.util.Optional;

public class UserService {
    @Inject
    UserDao userDao;

    public Optional<User> getUserByName(String username) {
        try {
            return Optional.ofNullable(userDao.getByUsername(username));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<User> getUserFromRequestContext(ContainerRequestContext context) {
        String username = (String) context.getProperty("username");
        return getUserByName(username);
    }


    public Optional<UserPublic> getById(int id) {
        UserPublic user;

        try {
            user = userDao.getById(id).getPublicProfile();
        } catch (Exception e) {
            return Optional.empty();
        }

        return Optional.of(user);
    }
}

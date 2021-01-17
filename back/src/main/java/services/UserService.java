package services;

import dao.UserDao;
import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import model.User;
import model.UserPublic;



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


    public Optional<User> getById(int id) {
        User user;

        try {
            user = userDao.getById(id);
        } catch (Exception e) {
            return Optional.empty();
        }

        return Optional.of(user);
    }

    public Optional<User> update(User user) throws Exception {
        try {
            userDao.getById(user.id);
        } catch (Exception e) {
            return Optional.empty();
        }

        return Optional.of(userDao.update(user));
    }   
}

package services;

import dao.UserDao;
import model.UserPublic;

import javax.inject.Inject;
import java.util.Optional;

public class UserService {
    @Inject UserDao userDao;

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

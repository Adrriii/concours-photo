package dao;

import model.User;

import java.util.Optional;

public interface UserDao {
    User getById(int id) throws Exception;
    User getByUsername(String username) throws Exception;

    User insert(User user, String hash) throws Exception;
    void update(User user) throws Exception;
    void delete(User user) throws Exception;

    User getByLogin(String username, String hash) throws Exception;
    void updateHash(User user, String hash) throws Exception;
}
package dao;

import model.User;

public interface UserDao extends Searchable<User> {
    User getById(int id) throws Exception;
    User getByUsername(String username) throws Exception;

    User insert(User user, String hash) throws Exception;
    User update(User user) throws Exception;
    void delete(User user) throws Exception;

    User getByLogin(String username, String hash) throws Exception;
    void updateHash(User user, String hash) throws Exception;
    void updateUserScore(int id) throws Exception;
    void updateUsersRanks() throws Exception;
}
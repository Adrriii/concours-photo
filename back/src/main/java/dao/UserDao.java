package dao;

import dao.sql.SqlDao;

public interface UserDao {
    User getById(int id) throws Exception;
    User getByUsername(String username) throws Exception;

    void insert(User user) throws Exception;
    void update(User user) throws Exception;

    void updateSetting(Setting setting, UserSetting value) throws Exception;

    User getByLogin(String username, String hash) throws Exception;
    void updateHash(User user, String hash) throws Exception;
}
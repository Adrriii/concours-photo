package dao;

import dao.sql.SqlDao;

public interface UserDao {
    User getById(int id) throws Exception;
}
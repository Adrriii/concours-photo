package dao.sql;

import java.util.Arrays;

public class SqlUserDao extends SqlDao<User> implements UserDao {
    @Override
    public User getById(int id) {
        String statement = "SELECT * FROM user WHERE id=?";
        List<Object> opt = Arrays.asList(id);

        return queryFirstObject(statement, opt);
    }
}
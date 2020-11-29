package dao.sql;

import model.User;

import java.util.Arrays;

public class SqlUserDao extends SqlDao<User> implements UserDao {

    @Override
    protected User createObjectFromResult(ResultSet resultSet) throws SQLException {
        int userId = getInteger(resultSet, "id");
        HashMap<Setting, UserSetting> userSettings = new SqlUserSettingDAO().getAllForUser(userId);

        return new User(resultSet.getString("username"), userSettings, userId);
    }

    @Override
    public User getById(int id) throws SQLException {
        String statement = "SELECT * FROM user WHERE id=?";
        List<Object> opt = Arrays.asList(id);

        return queryFirstObject(statement, opt);
    }

    @Override
    public User getByUsername(String username) throws SQLException {
        String statement = "SELECT * FROM user WHERE u.username=?";
        List<Object> opt = Arrays.asList(username);

        return queryFirstObject(statement, opt);
    }

    @Override
    public void insert(User user, String hash) throws SQLException {
        if(user.id != null) throw new SQLException(UserDaoException.ID_PROVIDED);

        String statement = "INSERT INTO user (username, sha) VALUES (?,?)";
        List<Object> opt = Arrays.asList(user.username, hash);

        int userId = doInsert(statement, opt);

        return new User(user.username, );
    }

    @Override
    public void update(User user) throws SQLException {
        if(user.id == null) throw new SQLException(UserDaoException.ID_NOT_PROVIDED);

        String statement = "UPDATE user SET username=? WHERE id=?";
        List<Object> opt = Arrays.asList(user.id, user.username);

        exec(statement, opt);
    }

    @Override
    public User getByLogin(String username, String hash) throws SQLException {
        String statement = "SELECT * FROM user as WHERE u.username=? AND u.sha=?";
        List<Object> opt = Arrays.asList(username, hash);

        return queryFirstObject(statement, opt);
    }

    @Override
    public void updateHash(User user, String hash) throws SQLException {
        if(user.id == null) throw new SQLException(UserDaoException.ID_NOT_PROVIDED);

        String statement = "UPDATE user SET sha ? WHERE user=?";
        List<Object> opt = Arrays.asList(user.id, hash);

        exec(statement, opt);
    }

}

enum UserDaoException {
    ID_PROVIDED("The user ID was provided when it was not required"),
    ID_NOT_PROVIDED("The user ID was not provided when it was required")
}
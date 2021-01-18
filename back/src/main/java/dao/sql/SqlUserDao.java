package dao.sql;

import dao.UserDao;
import model.SettingName;
import model.User;
import model.UserSetting;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SqlUserDao extends SqlDao<User> implements UserDao {

    @Override
    protected User createObjectFromResult(ResultSet resultSet) throws SQLException {
        Integer userId = getInteger(resultSet, "id");
        HashMap<SettingName, UserSetting> userSettings = new SqlUserSettingDao().getAllForUser(userId);

        return new User(resultSet.getString("username"), 
                        userSettings, 
                        getInteger(resultSet, "victories"), 
                        getInteger(resultSet, "score"), 
                        getInteger(resultSet, "userlevel"),
                        getParticipationCount(userId),
                        resultSet.getString("photo_url"),
                        resultSet.getString("delete_url"), 
                        getInteger(resultSet, "rank"),
                        userId);
    }

    @Override
    public User getById(int id) throws SQLException {
        String statement = "SELECT * FROM user WHERE id=?";
        List<Object> opt = Arrays.asList(id);

        return queryFirstObject(statement, opt);
    }

    @Override
    public User getByUsername(String username) throws SQLException {
        String statement = "SELECT * FROM user WHERE username=?";
        List<Object> opt = Arrays.asList(username);

        return queryFirstObject(statement, opt);
    }

    @Override
    public User insert(User user, String hash) throws SQLException {
        if(user.id != null) throw new SQLException(String.valueOf(UserDaoException.ID_PROVIDED));

        String statement = "INSERT INTO user (username, victories, score, userlevel, sha) VALUES (?,?,?,?,?)";
        List<Object> opt = Arrays.asList(user.username, user.victories, user.score, user.userlevel, hash);

        int userId = doInsert(statement, opt);
        new SqlUserSettingDao().insertDefaultsForUser(userId);
        
        updateUsersRanks();
        return new User(user.username, new SqlUserSettingDao().getAllForUser(userId), user.victories, user.score, user.userlevel, user.participations, user.photo, user.photoDelete, user.rank, userId);
    }

    @Override
    public User update(User user) throws SQLException {
        if(user.id == null) throw new SQLException(String.valueOf(UserDaoException.ID_NOT_PROVIDED));

        String statement = "UPDATE user SET username=?, userlevel=?, victories=?, score=?, photo_url=?, delete_url=? WHERE id=?";
        List<Object> opt = Arrays.asList(user.username, user.userlevel, user.victories, user.score, user.photo, user.photoDelete, user.id);

        for(UserSetting userSetting : user.settings.values()) {
            new SqlUserSettingDao().update(userSetting);
        }

        
        exec(statement, opt);
        updateUsersRanks();
        return getById(user.id);
    }

    @Override
    public void delete(User user) throws SQLException {
        if(user.id == null) throw new SQLException(String.valueOf(UserDaoException.ID_NOT_PROVIDED));

        String statement = "DELETE FROM user WHERE id=?";
        List<Object> opt = Arrays.asList(user.id);

        exec(statement, opt);
        updateUsersRanks();
    }

    @Override
    public User getByLogin(String username, String hash) throws SQLException {
        String statement = "SELECT * FROM user WHERE username=? AND sha=?";
        List<Object> opt = Arrays.asList(username, hash);

        return queryFirstObject(statement, opt);
    }

    @Override
    public void updateHash(User user, String hash) throws SQLException {
        if(user.id == null) throw new SQLException(String.valueOf(UserDaoException.ID_NOT_PROVIDED));

        String statement = "UPDATE user SET sha ? WHERE user=?";
        List<Object> opt = Arrays.asList(user.id, hash);

        exec(statement, opt);
    }

    @Override
    public List<User> search(String searchString) throws SQLException {
        searchString = "%"+searchString+"%";
        String statement = "SELECT * FROM user WHERE username LIKE ?";
        List<Object> opt = Arrays.asList(searchString);

        return queryAllObjects(statement, opt);
    }

    private Integer getParticipationCount(Integer user) throws SQLException {
        String statement = "SELECT COUNT(*) FROM post WHERE author = ?";
        List<Object> opt = Arrays.asList(user);

        try {
            return queryFirstInt(statement, opt);
        } catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void updateUserScore(int id) throws SQLException {
        String statement = "UPDATE user SET score = (SELECT SUM(score) FROM post WHERE author = ?) WHERE id = ?";
        List<Object> opt = Arrays.asList(id, id);

        exec(statement, opt);
    }

    public void updateUsersRanks() throws SQLException {
        String statement = "CALL update_ranks()";
        exec(statement);
    }
}

enum UserDaoException {
    ID_PROVIDED("The user ID was provided when it was not required"),
    ID_NOT_PROVIDED("The user ID was not provided when it was required");

    String value;

    UserDaoException(String error) {
        this.value = error;
    }
}



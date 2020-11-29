package dao.sql;

import dao.UserSettingDao;
import model.Setting;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlUserSettingDao extends SqlDao<UserSetting> implements UserSettingDao {

    @Override
    protected UserSetting createObjectFromResult(ResultSet resultSet) throws SQLException {
        return new UserSetting(
            getInteger(resultSet, "user"),
            resultSet.getString("value"),
            resultSet.getBoolean("public")
        );
    }

    @Override
    public HashMap<Setting, UserSetting> getAllForUser(int userId) throws SQLException {
        HashMap<Setting, UserSetting> userSettings = new HashMap<>();

        String statement = "SELECT * FROM user_setting as us, setting as s WHERE us.setting = s.id AND user = ?";
        List<Object> opt = Arrays.asList(userId);

        queryAllObjects(statement, opt).forEach(set -> userSettings.put(set.getString("setting", createObjectFromResult(set)));

        return userSettings;
    }

    @Override
    public void insertDefaultsForUser(int userId) throws SQLException {
        String statement = "SELECT * FROM setting";

        new SqlSettingDao().queryAllObjects(statement).forEach(setting -> {
            String statementNested = "INSERT INTO user_setting (user, setting, public, value) VALUES (?,?,?,?)";
            List<Object> opt = Arrays.asList(userId, setting.id, false, setting.default_value);

            exec(statementNested, opt);
        })
    }
}
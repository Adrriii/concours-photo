package dao.sql;

import dao.UserSettingDao;
import model.SettingName;
import model.UserSetting;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SqlUserSettingDao extends SqlDao<UserSetting> implements UserSettingDao {

    @Override
    protected UserSetting createObjectFromResult(ResultSet resultSet) throws SQLException {
        return new UserSetting(
            getInteger(resultSet, "user"),
            resultSet.getBoolean("public"),
            resultSet.getString("value")
        );
    }

    @Override
    public HashMap<SettingName, UserSetting> getAllForUser(int userId) throws SQLException {
        HashMap<SettingName, UserSetting> userSettings = new HashMap<>();

        String statement = "SELECT * FROM user_setting as us, setting as s WHERE us.setting = s.id AND user = ?";
        List<Object> opt = Arrays.asList(userId);

        for (UserSetting userSetting : queryAllObjects(statement, opt)) {
                userSettings.put(SettingName.valueOf(set.getString("label")), userSetting);
        }

        return userSettings;
    }

    @Override
    public void insertDefaultsForUser(int userId) throws SQLException {
        String statement = "SELECT * FROM setting";

        new SqlSettingDao().queryAllObjects(statement).forEach(setting -> {
            String statementNested = "INSERT INTO user_setting (user, setting, public, value) VALUES (?,?,?,?)";
            List<Object> opt = Arrays.asList(userId, setting.id, false, setting.defaultValue);

            exec(statementNested, opt);
        });
    }
}
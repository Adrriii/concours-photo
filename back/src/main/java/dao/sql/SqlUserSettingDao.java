package dao.sql;

import dao.UserSettingDao;
import model.Setting;
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
            resultSet.getString("value"),
            SettingName.valueOf(resultSet.getString("setting").toUpperCase())
        );
    }

    @Override
    public HashMap<SettingName, UserSetting> getAllForUser(int userId) throws SQLException {
        HashMap<SettingName, UserSetting> userSettings = new HashMap<>();

        String statement = "SELECT * FROM user_setting as us, setting as s WHERE us.setting = s.label AND user = ?";
        List<Object> opt = Arrays.asList(userId);

        for (UserSetting userSetting : queryAllObjects(statement, opt)) {
            userSettings.put(userSetting.setting, userSetting);
        }

        return userSettings;
    }

    @Override
    public void insertDefaultsForUser(int userId) throws SQLException {
        String statement = "SELECT * FROM setting";

        for (Setting setting : new SqlSettingDao().queryAllObjects(statement)) {
            String statementNested = "INSERT INTO user_setting (user, setting, public, value) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE user=user";
            List<Object> opt = Arrays.asList(userId, setting.label.toString().toLowerCase(), setting.defaultPublic, setting.defaultValue);

            exec(statementNested, opt);
        }
    }

    @Override
    public void update(UserSetting userSetting) throws SQLException {
        String statement = "UPDATE user_setting SET public = ?, value = ? WHERE user = ? AND setting = ?";
        List<Object> opt = Arrays.asList(userSetting.isPublic, userSetting.value, userSetting.userId, userSetting.setting.toString().toLowerCase());

        exec(statement, opt);
    }
}
package dao.sql;

import dao.UserSettingDao;
import model.Setting;
import model.SettingName;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlSettingDao extends SqlDao<Setting> implements SettingDao {

    @Override
    protected Setting createObjectFromResult(ResultSet resultSet) throws SQLException {
        return new Setting(
            getInteger(resultSet, "id"),
            SettingName.valueOf(resultSet.getString("label")),
            resultSet.getString("default_value")
        );
    }
}
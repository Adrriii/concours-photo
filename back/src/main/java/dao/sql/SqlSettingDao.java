package dao.sql;

import dao.SettingDao;
import model.Setting;
import model.SettingName;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlSettingDao extends SqlDao<Setting> implements SettingDao {

    @Override
    protected Setting createObjectFromResult(ResultSet resultSet) throws SQLException {
        return new Setting(
            SettingName.valueOf(resultSet.getString("label").toUpperCase()),
            resultSet.getString("default_value")
        );
    }
}
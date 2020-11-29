package dao.sql;

import dao.UserSettingDao;
import model.Setting;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlSettingDao extends SqlDao<Setting> implements SettingDao {

    @Override
    protected Setting createObjectFromResult(ResultSet resultSet) throws SQLException {
        return new Setting(
            getInteger(resultSet, "id"),
            resultSet.getString("label"),
            resultSet.getString("default_value")
        );
    }
}
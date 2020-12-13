package dao.sql;

import dao.ThemeDao;
import model.Theme;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlThemeDao extends SqlDao<Theme> implements ThemeDao {
    @Override
    protected Theme createObjectFromResult(ResultSet resultSet) throws SQLException {
        int winnerId = resultSet.getInt("winner");
        User winner = new SqlUserDao().getById(winnerId);

        return new Theme(
                resultSet.getInt("id"),
                resultSet.getString("title"),
                resultSet.getString("photo_url"),
                resultSet.getString("state"),
                resultSet.getString("date"),
                winner
        );
    }

    @Override
    public Theme getById(int id) {
        return null;
    }
}

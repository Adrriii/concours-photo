package dao.sql;

import dao.ThemeDao;
import model.Theme;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SqlThemeDao extends SqlDao<Theme> implements ThemeDao {
    @Override
    protected Theme createObjectFromResult(ResultSet resultSet) throws SQLException {
        Integer winnerId = getInteger(resultSet, "winner");

        User winner = (winnerId == null)? null : new SqlUserDao().getById(winnerId);

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
    public Theme getById(int id) throws SQLException {
        String statement = "SELECT * from theme WHERE id=?";
        List<Object> opt = Arrays.asList(id);

        return queryFirstObject(statement, opt);
    }

    @Override
    public Theme insert(Theme theme) throws Exception {
        String statement = "INSERT INTO theme (title, state, photo_url, winner, date) VALUES (?, ?, ?, ?, ?)";
        List<Object> opt = Arrays.asList(
                theme.title,
                theme.state,
                theme.photo,
                (theme.winner == null)? null : theme.winner.id,
                theme.date
        );

        int insertedId = doInsert(statement, opt);
        return getById(insertedId);
    }

    @Override
    public void delete(int id) throws Exception {
        String statement = "DELETE FROM theme WHERE id=?";
        List<Object> opt = Arrays.asList(id);

        exec(statement, opt);
    }
}

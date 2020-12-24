package dao.sql;

import dao.ReactionsDao;
import model.Reactions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SqlReactionsDao extends SqlDao<Reactions> implements ReactionsDao {

    @Override
    protected Reactions createObjectFromResult(ResultSet resultSet) throws SQLException {
        return new Reactions(resultSet.getString("reaction"),
                            getInteger(resultSet, "total"));
    }

    @Override
    public List<Reactions> getAllReactionsForPost(int post) throws SQLException {
        String statement = "SELECT value as reaction,COUNT(value) as total FROM reaction WHERE post=? GROUP BY reaction";

        List<Object> opt = Arrays.asList(post);

        return queryAllObjects(statement, opt);
    }
}
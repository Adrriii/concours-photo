package dao.sql;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public abstract class SqlDao<T> {

    protected abstract T createObjectFromResult(ResultSet resultSet) throws SQLException;

    protected T queryFirstObject(String statement, List<Object> opt) throws SQLException {
        PreparedStatement preparedStatement = SqlDatabase.prepare(statement, opt);
        String sql = preparedStatement.toString();
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            T item = createObjectFromResult(resultSet);

            resultSet.close();
            preparedStatement.close();

            return item;
        }

        throw new SQLException("Could not query : " + sql);
    }

    protected List<T> queryAllObjects(String statement, List<Object> opt) throws SQLException {
        PreparedStatement preparedStatement = SqlDatabase.prepare(statement, opt);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<T> items = new ArrayList<>();

        while (resultSet.next()) {
            items.add(createObjectFromResult(resultSet));
        }

        resultSet.close();
        preparedStatement.close();

        return items;
    }

    protected T queryFirstObject(String statement) throws SQLException {
        return queryFirstObject(statement, null);
    }

    protected List<T> queryAllObjects(String statement) throws SQLException {
        return queryAllObjects(statement, null);
    }

    protected void exec(String statement, List<Object> items) throws SQLException {
        SqlDatabase.exec(statement, items);
    }

    protected void exec(String statement) throws SQLException {
        exec(statement, null);
    }

    protected int doInsert(String statement, List<Object> opt) throws SQLException {
        PreparedStatement preparedStatement = SqlDatabase.prepare(statement, opt);
        String sql = preparedStatement.toString();
        preparedStatement.execute();

        ResultSet generatedKey = preparedStatement.getGeneratedKeys();

        int id;
        // Try to get the generated id
        if (generatedKey.next()) {
            id = generatedKey.getInt(1);
            generatedKey.close();
            preparedStatement.close();
            return id;
        }

        // If not auto-incremented, still try to get last insert id
        generatedKey.close();
        preparedStatement.close();

        preparedStatement = SqlDatabase.prepare("SELECT @id");

        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            id = rs.getInt(1);
            rs.close();
            preparedStatement.close();
            return id;
        }


        throw new SQLException("Could not insert : " + sql);
    }

    protected Integer getInteger(ResultSet set, String columnName) throws SQLException {
        int value = set.getInt(columnName);
        return set.wasNull() ? null : value;
    }
}
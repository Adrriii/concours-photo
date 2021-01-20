package dao.sql;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public abstract class SqlDao<T> {

    protected abstract T createObjectFromResult(ResultSet resultSet) throws SQLException;

    protected Integer queryFirstInt(String statement, List<Object> opt) throws SQLException {
        PreparedStatement preparedStatement = SqlDatabase.prepare(statement, opt);
        String sql = preparedStatement.toString();
        
        ResultSet queryResult = preparedStatement.executeQuery();
        ResultSet generatedKey = preparedStatement.getGeneratedKeys();

        int id;
        if (generatedKey.next()) {
            id = generatedKey.getInt(1);
            generatedKey.close();
            queryResult.close();
            preparedStatement.close();
            return id;
        }
        if(queryResult.next()) {
            id = queryResult.getInt(1);
            generatedKey.close();
            queryResult.close();
            preparedStatement.close();
            return id;
        }

        generatedKey.close();
        queryResult.close();
        preparedStatement.close();

        throw new SQLException("Could not get result : " + sql);
    }

    protected T queryFirstObject(String statement, List<Object> opt) throws SQLException {
        PreparedStatement preparedStatement = SqlDatabase.prepare(statement, opt);
        String sql = preparedStatement.toString();
        System.out.println(preparedStatement.toString());
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
        System.out.println(preparedStatement.toString());
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

    protected Optional<T> queryFirstOptional(String statement, List<Object> opt) throws SQLException {
        List<T> results = queryAllObjects(statement, opt);

        if (results.size() == 0)
            return Optional.empty();

        return Optional.of(results.get(0));
    }

    protected Optional<T> queryFirstOptional(String statement) throws SQLException {
        return queryFirstOptional(statement, null); 
    }

    protected void exec(String statement, List<Object> items) throws SQLException {
        SqlDatabase.exec(statement, items);
    }

    protected void exec(String statement) throws SQLException {
        exec(statement, null);
    }

    protected int doInsert(String statement, List<Object> opt) throws SQLException {
        try {
            return queryFirstInt(statement, opt);
        } catch (SQLException SQLexception) {

            PreparedStatement preparedStatement = SqlDatabase.prepare("SELECT @id");

            ResultSet rs = preparedStatement.executeQuery();
            int id;
            if (rs.next()) {
                id = rs.getInt(1);
                rs.close();
                preparedStatement.close();
                if(id != 0) {
                    return id;
                }
            }

            throw SQLexception;
        }
    }

    protected Integer getInteger(ResultSet set, String columnName) throws SQLException {
        int value = set.getInt(columnName);
        return set.wasNull() ? null : value;
    }
}
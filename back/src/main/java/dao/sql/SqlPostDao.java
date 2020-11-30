package dao.sql;

import dao.PostDao;
import model.Post;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlPostDao extends SqlDao<Post> implements PostDao {

    @Override
    protected Post createObjectFromResult(ResultSet resultSet) throws SQLException {
        return null;
    }
}
package dao.sql;

import dao.PostDao;
import model.Label;
import model.Post;
import model.Theme;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SqlPostDao extends SqlDao<Post> implements PostDao {

    @Override
    protected Post createObjectFromResult(ResultSet resultSet) throws SQLException {
        int authorId = resultSet.getInt("author");
        User author = new SqlUserDao().getById(authorId);

        int themeId = resultSet.getInt("theme");
        Theme theme = new SqlThemeDao().getById(themeId);

        return new Post(
            resultSet.getString("title"),
            null,
            null,
            author,
            new Label(resultSet.getString("label")),
            theme,
            resultSet.getString("photo_url"),
            resultSet.getString("photo_delete"),
            resultSet.getInt("id")
        );
    }

    @Override
    public Post insert(Post post) throws SQLException {
        String statement = "INSERT INTO post (title, author, label, theme photo_url, delete_url) VALUES (?, ?, ?, ?, ?, ?)";
        List<Object> opt = Arrays.asList(
            post.title,
            post.author,
            post.label,
            post.theme,
            post.photo,
            post.photo_delete
        );

        int insertedId = doInsert(statement, opt);
        return getById(insertedId);
    }

    @Override
    public List<Post> getAllByTheme(int themeId) throws SQLException {
        String statement = "SELECT * FROM post WHERE theme = ?";

        List<Object> opt = Arrays.asList(themeId);

        return queryAllObjects(statement, opt);
    }

    @Override
    public Post getById(int integer) throws SQLException {
        String statement = "SELECT * FROM post WHERE id = ?";

        List<Object> opt = Arrays.asList(integer);

        return queryFirstObject(statement, opt);
    }

    @Override
    public void delete(Post post) throws SQLException {
        String statement = "DELETE FROM post WHERE id = ?";

        List<Object> opt = Arrays.asList(post.id);

        exec(statement, opt);
    }
}
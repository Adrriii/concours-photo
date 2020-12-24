package dao.sql;

import dao.CommentDao;
import model.Comment;
import model.Post;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SqlCommentDao extends SqlDao<Comment> implements CommentDao {
    @Override
    protected Comment createObjectFromResult(ResultSet resultSet) throws SQLException {
        int authorId = resultSet.getInt("author");
        int postId = resultSet.getInt("post");
        Integer parentId = getInteger(resultSet, "parent");

        User author = new SqlUserDao().getById(authorId);
        Post post = new SqlPostDao().getById(postId);
        Comment parent = (parentId == null)? null : getById(parentId);

        return new Comment(
                resultSet.getInt("id"),
                author,
                post,
                parent
        );
    }

    @Override
    public List<Comment> getAllForUser(int projectId) throws SQLException {
        String statement = "SELECT * FROM comment WHERE project=?";

        List<Object> opt = Arrays.asList(projectId);

        return queryAllObjects(statement, opt);
    }

    @Override
    public List<Comment> getAllForPost(int postId) throws SQLException {
        String statement = "SELECT * FROM comment WHERE post=?";

        List<Object> opt = Arrays.asList(postId);

        return queryAllObjects(statement, opt);
    }

    @Override
    public List<Comment> getChildren(int parentId) throws SQLException {
        String statement = "SELECT * FROM comment WHERE parent=?";

        List<Object> opt = Arrays.asList(parentId);

        return queryAllObjects(statement, opt);
    }

    @Override
    public Comment getById(int id) throws SQLException {
        String statement = "SELECT * FROM comment WHERE id=?";

        List<Object> opt = Arrays.asList(id);

        return queryFirstObject(statement, opt);
    }
}

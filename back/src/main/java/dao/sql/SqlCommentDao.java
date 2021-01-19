package dao.sql;

import dao.CommentDao;
import model.Comment;
import model.Post;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
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
        Date date = resultSet.getDate("d");

        return new Comment(
                author,
                post,
                parent,
                resultSet.getString("content"),
                date.toString(),
                resultSet.getInt("id")
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

    @Override
    public Comment insert(Comment comment) throws Exception {
        String statement = "INSERT INTO comment (author, post, parent, content) VALUES (?, ?, ?, ?)";

        List<Object> opt = Arrays.asList(
                comment.author.id,
                comment.post.id,
                (comment.parent == null)? null : comment.parent.id,
                comment.content
        );

        int commentId = doInsert(statement, opt);

        return new Comment(comment.author, comment.post, comment.parent, comment.content, comment.date, commentId);
    }

    @Override
    public Comment update(Comment comment) throws Exception {
        String statement = "UPDATE comment SET author=?, post=?, parent=?, content=? WHERE id=?";

        List<Object> opt = Arrays.asList(
                comment.author.id,
                comment.post.id,
                (comment.parent == null)? null : comment.parent.id,
                comment.content,
                comment.id
        );

        exec(statement, opt);

        return comment;
    }

    @Override
    public void delete(int commentId) throws Exception {
        String statement = "DELETE FROM comment WHERE id=?";
        List<Object> opt = Arrays.asList(commentId);

        exec(statement, opt);
    }

    @Override
    public List<Comment> search(String searchString) throws SQLException {
        searchString = "%"+searchString+"%";
        String statement = "SELECT * FROM comment WHERE content LIKE ?";
        List<Object> opt = Arrays.asList(searchString);

        return queryAllObjects(statement, opt);
    }
}

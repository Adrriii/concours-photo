package dao.sql;

import dao.PostDao;
import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class SqlPostDao extends SqlDao<Post> implements PostDao {

    @Override
    protected Post createObjectFromResult(ResultSet resultSet) throws SQLException {
        int authorId = resultSet.getInt("author");
        User author = new SqlUserDao().getById(authorId);

        int themeId = resultSet.getInt("theme");
        Theme theme = new SqlThemeDao().getById(themeId).orElseThrow(SQLException::new);

        int postId = resultSet.getInt("id");
        List<Reactions> reactions = new SqlReactionsDao().getAllReactionsForPost(postId);

        return new Post(
                resultSet.getString("title"),
                resultSet.getDate("d").toString(),
                null,
                reactions,
                author,
                new Label(resultSet.getString("label")),
                theme,
                resultSet.getString("photo_url"), resultSet.getString("delete_url"),
                getInteger(resultSet, "score"),
                getInteger(resultSet, "nb_votes"),
                getInteger(resultSet, "nb_comment"),
                postId);
    }

    @Override
    public Post insert(Post post) throws SQLException {
        String statement = "INSERT INTO post (title, author, label, theme, photo_url, delete_url) VALUES (?, ?, ?, ?, ?, ?)";
        List<Object> opt = Arrays.asList(post.title, post.author.id, post.label != null ? post.label.label : null,
                post.theme.id, post.photo, post.photoDelete);

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
    public List<Post> getAllForUser(int userId) throws SQLException {
        String statement = "SELECT * FROM post WHERE author = ?";

        List<Object> opt = Arrays.asList(userId);

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

    @Override
    public List<Post> search(String searchString) throws SQLException {
        searchString = "%" + searchString + "%";
        String statement = "SELECT * FROM post WHERE title LIKE ?";
        List<Object> opt = Arrays.asList(searchString);

        return queryAllObjects(statement, opt);
    }

    @Override
    public List<Post> getFeedSearch(String sort, String direction, Theme theme, Set<Label> labelSet, int offset, int limit) throws Exception {
        String labelSubStatement = null;
        List<Object> opt = new ArrayList<>();
        
        for(Label label : labelSet) {
            if(labelSubStatement != null) labelSubStatement += " OR ";
            else labelSubStatement = "";
            labelSubStatement += "p.label = ?";
            opt.add(label.label);
        }
        if(labelSubStatement == null) labelSubStatement = " ";
        else labelSubStatement = " AND ("+labelSubStatement+") ";

        String statement = "SELECT * FROM post as p, label as l, theme as t ";
        statement += "WHERE p.label = l.label AND p.theme = t.id" + labelSubStatement;
        statement += "AND p.theme = ? ";
        opt.add(theme.id);

        if(direction != "DESC" && direction != "ASC") throw new Exception("Invalid direction parameter");
        statement += "ORDER BY ? "+direction;

        switch(sort) {
            case "score":
                sort = "p.score";
                break;
            case "date":
                sort = "p.d";
                break;
            case "nbComment":
                sort = "p.nb_comment";
                break;
            case "nbVotes":
                sort = "p.nb_votes";
                break;
            default:
                throw new Exception("Invalid sort parameter");
        }

        opt.add(sort);

        statement += " LIMIT ? OFFSET ?";
        opt.add(limit);
        opt.add(offset);

        return queryAllObjects(statement, opt);
    }

    @Override
    public void increaseNbCommentBy(int postId, int toAdd) throws Exception {
        String statement = "UPDATE post SET nb_comment = nb_comment + " + toAdd + " WHERE id=?";
        List<Object> opt = Arrays.asList(postId);

        exec(statement, opt);
    }
}
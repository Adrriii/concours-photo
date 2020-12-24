package dao.sql;

import dao.ReactionDao;
import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SqlReactionDao extends SqlDao<Reaction> implements ReactionDao {

    @Override
    protected Reaction createObjectFromResult(ResultSet resultSet) throws SQLException {
        return new Reaction(getInteger(resultSet, "user"), 
                            getInteger(resultSet, "post"),
                            ReactionName.valueOf(resultSet.getString("value").toUpperCase()));
    }

    @Override
    public Reaction get(int user, int post) throws SQLException {
        String statement = "SELECT * FROM reaction WHERE user=? AND post=?";
        List<Object> opt = Arrays.asList(user, post);

        return queryFirstObject(statement, opt);
    }

    @Override
    public Reaction getForUser(int user) throws SQLException {
        String statement = "SELECT * FROM reaction WHERE user=?";
        List<Object> opt = Arrays.asList(user);

        return queryFirstObject(statement, opt);
    }

    @Override
    public Reaction getForPost(int post) throws SQLException {
        String statement = "SELECT * FROM reaction WHERE post=?";
        List<Object> opt = Arrays.asList(post);

        return queryFirstObject(statement, opt);
    }

    @Override
    public void react(User user, Post post, ReactionName reaction) throws SQLException {
        String statement = "REPLACE INTO reaction (user, post, value) VALUES (?,?,?)";
        List<Object> opt = Arrays.asList(user.id, post.id, reaction.toString().toLowerCase());

        exec(statement, opt);
    }

    @Override
    public void delete(User user, Post post) throws SQLException {
        String statement = "DELETE FROM reaction WHERE user=? AND post=?";
        List<Object> opt = Arrays.asList(user.id, post.id);

        exec(statement, opt);
    }

    @Override
    public Reactions getReactionsForPost(int post, ReactionName reaction) throws SQLException {
        String statement = "SELECT COUNT(value) FROM reaction WHERE post=? AND value=?";
        String reactionName = reaction.toString().toLowerCase();

        List<Object> opt = Arrays.asList(post, reactionName.toString().toLowerCase());

        return new Reactions(reactionName, queryFirstInt(statement, opt));
    }
}
package dao.sql;

import dao.ReactionDao;
import model.Reaction;
import model.Reactions;
import model.ReactionName;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SqlReactionDao extends SqlDao<Reaction> implements ReactionDao {

    @Override
    protected Reaction createObjectFromResult(ResultSet resultSet) throws SQLException {
        return new Reaction(getInteger(resultSet, "user"), 
                            getInteger(resultSet, "post"),
                            ReactionName.valueOf(resultSet.getString("reaction").toUpperCase()));
    }

    @Override
    public Reaction get(int user, int post) throws Exception {
        String statement = "SELECT * FROM reaction WHERE user=? AND post=?";
        List<Object> opt = Arrays.asList(user, post);

        return queryFirstObject(statement, opt);
    }

    @Override
    public Reaction getForUser(int user) throws Exception {
        String statement = "SELECT * FROM reaction WHERE user=?";
        List<Object> opt = Arrays.asList(user);

        return queryFirstObject(statement, opt);
    }

    @Override
    public Reaction getForPost(int post) throws Exception {
        String statement = "SELECT * FROM reaction WHERE post=?";
        List<Object> opt = Arrays.asList(post);

        return queryFirstObject(statement, opt);
    }

    @Override
    public Reactions getReactionsForPost(int post, ReactionName reaction) throws Exception {
        String statement = "SELECT COUNT(reaction) FROM reaction WHERE post=? AND reaction=?";
        String reactionName = reaction.toString().toLowerCase();

        List<Object> opt = Arrays.asList(post, reactionName);

        return new Reactions(reactionName, queryFirstInt(statement, opt));
    }
}
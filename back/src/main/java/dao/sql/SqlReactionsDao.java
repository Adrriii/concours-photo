package dao.sql;

import dao.ReactionsDao;
import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, List<UserPublic>> getSampleUsersForReactions(int post) throws SQLException {
        String statement = "SELECT * FROM post as p, user as u, reaction as r WHERE u.id = r.user AND p.id = r.post AND p.id = ?";
        List<Object> opt = Arrays.asList(post);

        PreparedStatement preparedStatement = SqlDatabase.prepare(statement, opt);
        ResultSet resultSet = preparedStatement.executeQuery();

        Map<String, List<UserPublic>> results = new HashMap<String, List<UserPublic>>();
        while (resultSet.next()) {
            String reaction = resultSet.getString("r.value");

            if(!results.containsKey(reaction)) {
                results.put(reaction, new ArrayList<UserPublic>());
            }
            if(results.get(reaction).size() < 5) {
                UserPublic user = new UserPublic(
                    resultSet.getString("u.username"),
                    null,
                    getInteger(resultSet, "u.victories"),
                    getInteger(resultSet, "u.score"),
                    null,
                    resultSet.getString("u.photo_url"),
                    getInteger(resultSet, "u.rank"),
                    getInteger(resultSet, "u.id")
                );
                
                results.get(reaction).add(user);
            }
        }

        resultSet.close();
        preparedStatement.close();

        return results;
    }
}
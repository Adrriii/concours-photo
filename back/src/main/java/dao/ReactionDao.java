package dao;

import model.Reaction;
import model.Reactions;
import model.ReactionName;

public interface ReactionDao {
    Reaction get(int user, int post) throws Exception;
    Reaction getForUser(int user) throws Exception;
    Reaction getForPost(int post) throws Exception;

    Reactions getReactionsForPost(int post, ReactionName reaction) throws Exception;
}
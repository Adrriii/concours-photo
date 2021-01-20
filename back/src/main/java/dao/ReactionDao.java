package dao;

import model.*;

import java.util.Optional;

public interface ReactionDao {
    Optional<Reaction> get(int user, int post) throws Exception;
    Reaction getForUser(int user) throws Exception;
    Reaction getForPost(int post) throws Exception;

    void react(User user, Post post, ReactionName reaction) throws Exception;
    void delete(User user, Post post) throws Exception;

    Reactions getReactionsForPost(int post, ReactionName reaction) throws Exception;
}
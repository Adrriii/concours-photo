package dao;

import java.util.List;

import model.Reactions;

public interface ReactionsDao {
    List<Reactions> getAllReactionsForPost(int post) throws Exception;
}
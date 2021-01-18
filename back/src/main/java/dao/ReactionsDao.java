package dao;

import java.util.List;
import java.util.Map;

import model.*;

public interface ReactionsDao {
    List<Reactions> getAllReactionsForPost(int post) throws Exception;
    Map<String, List<UserPublic>> getSampleUsersForReactions(int post) throws Exception;
}
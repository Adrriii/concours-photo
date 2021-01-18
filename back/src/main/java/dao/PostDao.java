package dao;

import model.*;

import java.util.List;
import java.util.Set;

public interface PostDao extends Searchable<Post> {
    Post insert(Post post) throws Exception;
    List<Post> getAllByTheme(int themeId) throws Exception;
    Post getById(int integer) throws Exception;
    List<Post> getAllForUser(int userId) throws Exception;
    void delete(Post post) throws Exception;
    List<Post> getFeedSearch(String sort, String direction, Theme theme, Set<Label> labelSet, int offset, int limit) throws Exception;

    void increaseNbCommentBy(int postId, int offset) throws Exception;
}
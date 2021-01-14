package dao;

import model.Post;

import java.util.List;

public interface PostDao extends Searchable<Post> {
    Post insert(Post post) throws Exception;
    List<Post> getAllByTheme(int themeId) throws Exception;
    Post getById(int integer) throws Exception;
    void delete(Post post) throws Exception;
}
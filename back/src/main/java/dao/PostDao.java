package dao;

import model.Post;

import java.sql.SQLException;
import java.util.List;

public interface PostDao {
    Post insert(Post post) throws Exception;
    List<Post> getAllByTheme(int themeId) throws Exception;
    Post getById(int integer) throws Exception;
    void delete(Post post) throws Exception;
}
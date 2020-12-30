package dao;

import model.Post;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PostDao {
    Post insert(Post post);
    List<Post> getAllByTheme(int themeId);
    Post getById(int integer);
    void delete(Post post) throws Exception;
}
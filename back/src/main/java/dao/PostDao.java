package dao;

import model.Post;

import java.util.List;
import java.util.Optional;

public interface PostDao {
    Post insert(Post post);
    List<Post> getAllByTheme(int themeId);
    Post getById(int integer);
}
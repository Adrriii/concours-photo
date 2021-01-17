package services;

import dao.PostDao;
import model.Post;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class PostService {
    @Inject PostDao postDao;

    public Optional<Post> addOne(Post post) throws Exception {
        if (post.id != null)
            return Optional.empty();

        return Optional.ofNullable(postDao.insert(post));
    }

    public Optional<Post> getById(int id) {
        Post post;

        try {
            post = postDao.getById(id);
        } catch (Exception e) {
            return Optional.empty();
        }

        return Optional.ofNullable(post);
    }

    public List<Post> getPostsByThemeId(int id) throws Exception {
        return postDao.getAllByTheme(id);
    }

    public List<Post> getPostsByAuthorId(int id) throws Exception {
        return postDao.getAllForUser(id);
    }
}

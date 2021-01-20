package services;

import dao.CommentDao;
import dao.PostDao;
import model.*;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;

import java.util.List;
import java.util.Optional;

public class CommentService {
    @Inject public CommentDao commentDao;
    @Inject public PostDao postDao;

    private void addOneCommentInPost(int postId) throws Exception {
        postDao.increaseNbCommentBy(postId, 1);
    }

    public List<Comment> getAllForPost(int postId) throws Exception {
        return commentDao.getAllForPost(postId);
    }

    public Optional<Comment> getById(int id) throws Exception {
        try {
            return Optional.of(commentDao.getById(id));
        } catch(Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Comment> replyToComment(User author, int parentId, Comment comment) throws Exception {
        Comment parent;

        try {
            parent = commentDao.getById(parentId);

            if (comment.parent != null && comment.parent.id.equals(parent.id))
                throw new Exception();

        } catch (Exception e) {
            return Optional.empty();
        }

        comment = new Comment(
                author,
                comment.post,
                parent,
                comment.content,
                comment.date,
                comment.id
        );

        addOneCommentInPost(comment.post.id);
        return Optional.of(commentDao.update(comment));
    }

    public Optional<Comment> replyToPost(User author, int postId, Comment comment) throws Exception {
        Post post;

        try {
            post = postDao.getById(postId);
        } catch (Exception e) {
            return Optional.empty();
        }

        comment = commentDao.insert(new Comment(
                author,
                post,
                comment.parent,
                comment.content,
                comment.date,
                comment.id
        ));

        addOneCommentInPost(comment.post.id);

        return Optional.of(comment);
    }

    public void deleteComment(User author, Comment comment) throws Exception {
        if(author.id != comment.author.id) return;
        commentDao.delete(comment.id);
    }

    public Optional<Comment> updateComment(User author, Comment comment) throws Exception {
        try {
            commentDao.getById(comment.id);
        } catch (Exception e) {
            return Optional.empty();
        }

        if(author.id != comment.author.id) return Optional.empty();

        return Optional.of(commentDao.update(comment));
    }
}

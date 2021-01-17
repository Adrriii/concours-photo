package services;

import dao.CommentDao;
import dao.PostDao;
import model.Comment;
import model.Post;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class CommentService {
    @Inject CommentDao commentDao;
    @Inject PostDao postDao;

    private void addOneCommentInPost(int postId) throws Exception {
        postDao.increaseNbCommentBy(postId, 1);
    }

    public List<Comment> getAllForPost(int postId) throws Exception {
        return commentDao.getAllForPost(postId);
    }

    public Optional<Comment> replyToComment(int parentId, Comment comment) throws Exception {
        Comment parent;

        try {
            parent = commentDao.getById(parentId);

            if (comment.parent != null && comment.parent.id.equals(parent.id))
                throw new Exception();

        } catch (Exception e) {
            return Optional.empty();
        }

        comment = new Comment(
                comment.author,
                comment.post,
                parent,
                comment.content,
                comment.id
        );

        addOneCommentInPost(comment.post.id);
        return Optional.of(commentDao.update(comment));
    }

    public Optional<Comment> replyToPost(int postId, Comment comment) throws Exception {
        Post post;

        try {
            post = postDao.getById(postId);
        } catch (Exception e) {
            return Optional.empty();
        }

        comment = commentDao.insert(new Comment(
                comment.author,
                post,
                comment.parent,
                comment.content,
                comment.id
        ));

        addOneCommentInPost(comment.post.id);

        System.out.println("Replying to post, increase comment");
        post = postDao.getById(comment.post.id);
        System.out.println("Now number of comment is " + post.nbComment);
        System.out.println(post);
        return Optional.of(comment);
    }

    public void deleteComment(int commentId) throws Exception {
        commentDao.delete(commentId);
    }

    public Optional<Comment> updateComment(Comment comment) throws Exception {
        try {
            commentDao.getById(comment.id);
        } catch (Exception e) {
            return Optional.empty();
        }

        return Optional.of(commentDao.update(comment));
    }
}

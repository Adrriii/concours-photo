package dao;

import model.Comment;

import java.util.List;

public interface CommentDao extends Searchable<Comment> {
    List<Comment> getAllForUser(int projectId) throws Exception;
    List<Comment> getAllForPost(int postId) throws Exception;
    List<Comment> getChildren(int parentId) throws Exception;

    Comment getById(int id) throws Exception;

    Comment insert(Comment comment) throws Exception;
    Comment update(Comment comment) throws Exception;

    void delete(int commentId) throws Exception;
}

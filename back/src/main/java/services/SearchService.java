package services;

import dao.CommentDao;
import dao.PostDao;
import dao.UserDao;

import model.*;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class SearchService {
    @Inject public UserDao userDao;
    @Inject public CommentDao commentDao;
    @Inject public PostDao postDao;

    public List<User> searchUser(String searchString) {
        try {
            return userDao.search(searchString);
        } catch(Exception ex) {
            ex.printStackTrace();
            return new ArrayList<User>();
        }
    }

    public List<Comment> searchComment(String searchString) {
        try {
            return commentDao.search(searchString);
        } catch(Exception ex) {
            ex.printStackTrace();
            return new ArrayList<Comment>();
        }
    }

    public List<Post> searchPost(String searchString) {
        try {
            return postDao.search(searchString);
        } catch(Exception ex) {
            ex.printStackTrace();
            return new ArrayList<Post>();
        }
    }
}

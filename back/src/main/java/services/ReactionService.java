package services;

import dao.PostDao;
import dao.ReactionDao;
import dao.UserDao;
import model.Post;
import model.ReactionName;
import model.User;

import javax.inject.Inject;

public class ReactionService {
    @Inject ReactionDao reactionDao;
    @Inject PostDao postDao;
    @Inject UserDao userDao;

    public boolean changeReactToPost(int postId, int userId, String reaction) {
        ReactionName reactionName = ReactionName.valueOf(reaction.toUpperCase());

        try {
            reactionDao.react(userDao.getById(userId), postDao.getById(postId), reactionName);
            return true;
        } catch (Exception error) {
            error.printStackTrace();
        }
        return false;
    }

    public boolean addReactToPost(int postId, int userId, String reaction) {
        return changeReactToPost(postId, userId, reaction);
    }

    public boolean cancelReactToPost(int postId, int userId) {
        try {
            reactionDao.delete(userDao.getById(userId), postDao.getById(postId));
            return true;
        } catch (Exception error) {
            error.printStackTrace();
        }
        return false;
    }
}

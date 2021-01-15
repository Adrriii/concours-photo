package dao.sql;

import org.junit.jupiter.api.Test;

import dao.*;
import model.*;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

class SqlReactionDaoTest {
    static PostDao postDao;
    static Post testPost;

    static ReactionDao dao;
    static ReactionsDao reactionsDao;

    static User testUser;

    @BeforeAll
    public static void init() throws Exception {
        postDao = new SqlPostDao();
        dao = new SqlReactionDao();
        reactionsDao = new SqlReactionsDao();

        User toInsertUser = new User("user"+new Date().toInstant().toEpochMilli());
        testUser = new SqlUserDao().insert(toInsertUser, "null");
        
        Post toInsertPost = new Post("post"+new Date().toInstant().toEpochMilli(), null, null, testUser, null, new SqlThemeDao().getById(1).orElseThrow(Exception::new), "url", "url");
        testPost = postDao.insert(toInsertPost);
    }

    @Test
    void testReact() {
        assertDoesNotThrow(() -> dao.react(testUser, testPost, ReactionName.LIKE));
        assertDoesNotThrow(() -> dao.react(testUser, testPost, ReactionName.DISLIKE));
        assertDoesNotThrow(() -> dao.react(testUser, testPost, ReactionName.DISLIKE));
        assertDoesNotThrow(() -> dao.react(testUser, testPost, ReactionName.LIKE));
        assertDoesNotThrow(() -> dao.react(testUser, testPost, ReactionName.DISLIKE));
        assertDoesNotThrow(() -> dao.react(testUser, testPost, ReactionName.LIKE));
        assertDoesNotThrow(() -> dao.react(testUser, testPost, ReactionName.LIKE));
    }

    @Test
    void testReactCountLike() throws Exception {
        assertDoesNotThrow(() -> dao.react(testUser, testPost, ReactionName.LIKE));

        List<Reactions> reactionsList = reactionsDao.getAllReactionsForPost(testPost.id);

        for(Reactions reactions : reactionsList) {
            switch(reactions.reaction) {
                case "like":
                    assertEquals(reactions.count, 1);
                    break;
                case "dislike":
                    assertEquals(reactions.count, 0);
                    break;
                default:
                    fail();
            }
        }
    }

    @Test
    void testReactCountDislike() throws Exception {
        assertDoesNotThrow(() -> dao.react(testUser, testPost, ReactionName.DISLIKE));

        List<Reactions> reactionsList = reactionsDao.getAllReactionsForPost(testPost.id);

        for(Reactions reactions : reactionsList) {
            switch(reactions.reaction) {
                case "like":
                    assertEquals(reactions.count, 0);
                    break;
                case "dislike":
                    assertEquals(reactions.count, 1);
                    break;
                default:
                    fail();
            }
        }
    }

    @Test
    void testReactCountDelete() throws Exception {
        assertDoesNotThrow(() -> dao.react(testUser, testPost, ReactionName.DISLIKE));
        assertDoesNotThrow(() -> dao.delete(testUser, testPost));

        List<Reactions> reactionsList = reactionsDao.getAllReactionsForPost(testPost.id);

        for(Reactions reactions : reactionsList) {
            switch(reactions.reaction) {
                case "like":
                    assertEquals(reactions.count, 0);
                    break;
                case "dislike":
                    assertEquals(reactions.count, 0);
                    break;
                default:
                    fail();
            }
        }
    }

    @AfterAll
    public static void clean() throws SQLException {
        new SqlUserDao().delete(testUser);
    }
}
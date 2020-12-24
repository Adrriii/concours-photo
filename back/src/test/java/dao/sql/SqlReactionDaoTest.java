package dao.sql;

import org.junit.jupiter.api.Test;

import dao.*;
import model.*;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

class SqlReactionDaoTest {
    static PostDao postDao;
    static Post testPost;

    static ReactionDao dao;
    static ReactionsDao reactionsDao;

    static User testUser;

    @BeforeAll
    public static void init() throws SQLException {
        postDao = new SqlPostDao();
        dao = new SqlReactionDao();
        reactionsDao = new SqlReactionsDao();

        User toInsert = new User("testUser");
        testUser = new SqlUserDao().insert(toInsert, "null");

        //Post toInsertPost = new Post("testPost", );
    }

    @Test
    void testSimpleInsertDelete() {
        SqlUserDao dao = new SqlUserDao();

        User user = assertDoesNotThrow(() -> dao.insert(new User("adri", null), "test"));
        assertDoesNotThrow(() -> dao.delete(user));
    }

    @AfterAll
    public static void clean() throws SQLException {
        new SqlUserDao().delete(testUser);
    }
}
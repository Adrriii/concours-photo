package dao.sql;

import model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SqlCommentDaoTest {
    static SqlCommentDao dao;
    static User user;
    static Post post;

    @AfterAll
    public static void afterAll() throws SQLException {
        new SqlUserDao().delete(user);
        new SqlPostDao().delete(post);
    }

    @BeforeAll
    public static void beforeAll() throws Exception {
        dao = new SqlCommentDao();
        // String title, String reacted, List<Reaction> reactions, User author, Label label, Theme theme, String photo, String photo_delete
        user = new User("Test User");
        user = new SqlUserDao().insert(user, "coucoujesuisunhash");

        // String title, String photo, String state, String date, User winner
        Theme theme = new Theme("My super theme", "nop", "current", "jamais", null);
        theme = new SqlThemeDao().insert(theme);

        post = new Post(
                "Test comment post",
                "like",
                null,
                user,
                null,
                theme,
                "nop",
                "delete"
        );
        post = new SqlPostDao().insert(post);
    }

    @Test
    public void TestInsert() {
        Comment newComment = new Comment(
            user, post, null, "Super ce test !"
        );

        Comment inserted = assertDoesNotThrow(() -> dao.insert(newComment));

        assertEquals(newComment.parent, inserted.parent);
        assertEquals(newComment.author, inserted.author);
        assertEquals(newComment.post, inserted.post);
        assertEquals(newComment.content, inserted.content);
    }

}
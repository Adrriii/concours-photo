package dao.sql;

import model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SqlCommentDaoTest {
    static SqlCommentDao dao;
    static User user;
    static Post post;


    @BeforeAll
    public static void beforeAll() throws Exception {
        dao = new SqlCommentDao();
        // String title, String reacted, List<Reaction> reactions, User author, Label label, Theme theme, String photo, String photo_delete
        user = new User("Test User");
        user = new SqlUserDao().insert(user, "coucoujesuisunhash");

        Label label = new Label("Test label");
        label = new SqlLabelDao().insert(label);

        // String title, String photo, String state, String date, User winner
        Theme theme = new Theme("My super theme", "nop", "current", "jamais", null);
        theme = new SqlThemeDao().insert(theme);

        post = new Post(
                "Test comment post",
                "like",
                Arrays.asList(new Reaction("coucou", 42)),
                user,
                label,
                theme,
                "nop",
                "delete"
        );
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
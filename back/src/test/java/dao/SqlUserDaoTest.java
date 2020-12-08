package dao.sql;

import org.junit.jupiter.api.Test;

import model.User;

import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class SqlUserDaoTest {

    @Test
    void testSimpleInsertDelete() {
        SqlUserDao dao = new SqlUserDao();

        User user = assertDoesNotThrow(() -> dao.insert(new User("adri", null), "test"));
        assertDoesNotThrow(() -> dao.delete(user));
    }
}
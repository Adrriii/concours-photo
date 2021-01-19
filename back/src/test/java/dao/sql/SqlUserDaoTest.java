package dao.sql;

import org.junit.jupiter.api.Test;

import model.User;

import static org.junit.jupiter.api.Assertions.*;

class SqlUserDaoTest {

    @Test
    void testSimpleInsertDelete() {
        SqlUserDao dao = new SqlUserDao();

        User user = assertDoesNotThrow(() -> dao.insert(new User("testuser"+System.currentTimeMillis()), "test"));
        assertDoesNotThrow(() -> dao.delete(user));
    }
}
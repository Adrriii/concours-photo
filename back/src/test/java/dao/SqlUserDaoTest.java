package dao;

import org.junit.jupiter.api.Test;

import dao.sql.SqlUserDao;
import model.User;

import static org.junit.jupiter.api.Assertions.*;

class SqlUserDaoTest {

    @Test
    void testSimpleInsertDelete() {
        SqlUserDao dao = new SqlUserDao();

        User user = assertDoesNotThrow(() -> dao.insert(new User("adri", null), "test"));
        assertDoesNotThrow(() -> dao.delete(user));
    }
}
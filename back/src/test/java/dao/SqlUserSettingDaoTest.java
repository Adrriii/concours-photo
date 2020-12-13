package dao;

import org.junit.jupiter.api.*;

import dao.sql.*;
import model.SettingName;
import model.UserSetting;
import model.User;

import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SqlUserSettingDaoTest {
    static SqlUserSettingDao dao;
    static SqlUserDao userDao;

    static User testUser;

    @BeforeAll
    public static void init() throws SQLException {
        dao = new SqlUserSettingDao();
        userDao = new SqlUserDao();

        User toInsert = new User("testUser");
        testUser = userDao.insert(toInsert, "null");
    }

    @Test
    void testGet() throws SQLException {
        assertDoesNotThrow(() -> dao.getAllForUser(testUser.id));
    }

    @Test
    void testUpdateForUser() throws SQLException {
        Map<SettingName, UserSetting> settings = testUser.settings;

        settings.put(SettingName.BIO, new UserSetting(testUser.id, true, System.currentTimeMillis()+"bio", SettingName.BIO));
        settings.put(SettingName.BIRTHDAY, new UserSetting(testUser.id, true, System.currentTimeMillis()+"bd", SettingName.BIRTHDAY));
        settings.put(SettingName.GENDER, new UserSetting(testUser.id, true, System.currentTimeMillis()+"gd", SettingName.GENDER));
        settings.put(SettingName.LOCATION, new UserSetting(testUser.id, true, System.currentTimeMillis()+"loc", SettingName.LOCATION));
        settings.put(SettingName.MAIL, new UserSetting(testUser.id, true, System.currentTimeMillis()+"mel", SettingName.MAIL));
        settings.put(SettingName.VICTORIES, new UserSetting(testUser.id, true, System.currentTimeMillis()+"5", SettingName.VICTORIES));
        settings.put(SettingName.POINTS, new UserSetting(testUser.id, true, System.currentTimeMillis()+"1500", SettingName.POINTS));

        User updateUser = new User(testUser.username, settings, testUser.id);
        assertDoesNotThrow(() -> userDao.update(updateUser));

        Map<SettingName, UserSetting> updatedSettings = dao.getAllForUser(updateUser.id);

        for(UserSetting userSetting : updatedSettings.values()) {
            assertTrue(userSetting.equals(settings.get(userSetting.setting)));
        }
    }

    @Test
    void testUpdate() throws SQLException {
        UserSetting setting = testUser.settings.get(SettingName.BIO);
        UserSetting updateSetting = new UserSetting(setting.userId, setting.isPublic, System.currentTimeMillis()+"bio", setting.setting);
        assertDoesNotThrow(() -> dao.update(updateSetting));
        UserSetting updatedSetting = dao.getAllForUser(testUser.id).get(SettingName.BIO);

        assertTrue(updateSetting.equals(updatedSetting));
        assertFalse(setting.equals(updatedSetting));
    }

    @AfterAll
    public static void clean() throws SQLException {
        userDao.delete(testUser);
    }
}
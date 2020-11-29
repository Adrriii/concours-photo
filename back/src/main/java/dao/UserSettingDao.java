package dao;

public class UserSettingDao {
    HashMap<Setting, UserSetting> getAllForUser(int userId) throws Exception;
    void insertDefaultsForUser(int userId) throws Exception;
}
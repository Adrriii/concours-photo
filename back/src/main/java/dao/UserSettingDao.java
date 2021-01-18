package dao;

import model.SettingName;
import model.UserSetting;

import java.util.HashMap;

public interface UserSettingDao {
    HashMap<SettingName, UserSetting> getAllForUser(int userId) throws Exception;
    void insertDefaultsForUser(int userId) throws Exception;
    void update(UserSetting userSetting) throws Exception;
}
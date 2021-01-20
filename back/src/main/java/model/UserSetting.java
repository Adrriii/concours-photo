package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSetting {
    public final Integer userId;
    public final Boolean isPublic;
    public final SettingName setting;
    public final String value;

    public UserSetting() {
        this(null,null,null,null);
    }

    public UserSetting(Integer userId, Boolean isPublic, String value, SettingName setting) {
        this.userId = userId;
        this.isPublic = isPublic;
        this.value = value;
        this.setting = setting;
    }

    @Override
    public String toString() {
        return "UserSetting ( userId: " + userId
        + ", setting: " + setting 
        + ", value: " + value 
        + ", isPublic: " + isPublic 
        + ")";
    }

    public boolean equals(UserSetting userSetting) {
        return userSetting.isPublic.equals(isPublic)
            && userSetting.userId.equals(userId)
            && userSetting.setting.equals(setting)
            && userSetting.value.equals(value);
    }
}
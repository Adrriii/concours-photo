package model;

public class UserSetting {
    public final Integer userId;
    public final Boolean isPublic;
    public final String setting;
    public final String value;

    public UserSetting() {
        this(null,null,null,null);
    }

    public UserSetting(Integer userId, Boolean isPublic, String value, String setting) {
        this.userId = userId;
        this.isPublic = isPublic;
        this.value = value;
        this.setting = setting;
    }

}
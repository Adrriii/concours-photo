package model;

public class UserSetting {
    public final Integer userId;
    public final Boolean isPublic;

    public final String value;

    public UserSetting() {
        this(null,null,null);
    }

    public UserSetting(Integer userId, Boolean isPublic, String value) {
        this.userId = userId;
        this.isPublic = isPublic;
        this.value = value;        
    }

}
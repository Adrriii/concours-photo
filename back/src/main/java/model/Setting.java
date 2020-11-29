package model;

public class Setting {
    public final SettingName label;
    public final String defaultValue;

    public Setting() {
        this(null,null);
    }

    public UserSetting(SettingName label, String defaultValue) {
        this.label = label;
        this.defaultValue = defaultValue;   
    }

}
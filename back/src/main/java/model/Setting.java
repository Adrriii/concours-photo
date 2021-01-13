package model;

public class Setting {
    public final SettingName label;
    public final String defaultValue;
    public final Boolean defaultPublic;

    public Setting() {
        this(null,null,null);
    }

    public Setting(SettingName label, String defaultValue, Boolean defaultPublic) {
        this.label = label;
        this.defaultValue = defaultValue; 
        this.defaultPublic = defaultPublic;   
    }

}
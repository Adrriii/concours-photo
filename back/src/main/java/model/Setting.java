package model;

public class Setting {
    public final String label;
    public final String default_value;

    public Setting() {
        this(null,null);
    }

    public UserSetting(String label, String default_value) {
        this.label = label;
        this.default_value = default_value;   
    }

}
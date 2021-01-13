package model;

import java.util.HashMap;
import java.util.Map;

public class UserPublic {
    public final Integer id;
    public final String username;
    public final Integer victories;
    public final Integer score;

    public final Map<SettingName, UserSetting> settings;

    public UserPublic() {
        this(null, new HashMap<>(), null, null, null);
    }

    public UserPublic(String username, Map<SettingName, UserSetting> settings, Integer victories, Integer score, Integer id) {
        this.id = id;
        this.username = username;
        this.settings = settings;
        this.victories = victories;
        this.score = score;
    }
}
package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAuth {
    public final String username;
    public final String passwordHash;

    public UserAuth() {
        this(null, null);
    }

    public UserAuth(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }
}

package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAuth {
    public final String username;
    public final String passwordHash;
    public final String email;

    public UserAuth() {
        this(null, null, null);
    }

    public UserAuth(String username, String passwordHash, String email) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
    }
}

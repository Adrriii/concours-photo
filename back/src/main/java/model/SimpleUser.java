package model;

public class SimpleUser {
    public final String username;
    public final String passwordHash;

    public SimpleUser() {
        this(null, null);
    }

    public SimpleUser(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }
}

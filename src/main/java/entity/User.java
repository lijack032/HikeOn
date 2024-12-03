package entity;

/**
 * Represents a user in the system.
 */
public class User {
    private final String username;
    private final String password; // Ideally, this would be hashed

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}


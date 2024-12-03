package entity;

import java.util.HashMap;
import java.util.Map;

/**
 * An in-memory implementation of the UserStore.
 */
public class InMemoryUserStore implements UserStore {
    private static InMemoryUserStore instance;
    private final Map<String, User> userMap;

    public InMemoryUserStore() {
        userMap = new HashMap<>();
    }

    public static synchronized InMemoryUserStore getInstance() {
        if (instance == null) {
            instance = new InMemoryUserStore();
        }
        return instance;
    }

    @Override
    public boolean addUser(User user) {
        if (userMap.containsKey(user.getUsername())) {
            return false; // Username already exists
        }
        userMap.put(user.getUsername(), user);
        return true;
    }

    @Override
    public User getUser(String username) {
        return userMap.get(username);
    }

    @Override
    public boolean containsUser(String username) {
        return userMap.containsKey(username);
    }
}

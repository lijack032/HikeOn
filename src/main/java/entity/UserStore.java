package entity;

public interface UserStore {
    boolean addUser(User user);

    User getUser(String username);

    boolean containsUser(String username);
}

package use_case.login;

import entity.InMemoryUserStore;
import entity.User;
import entity.UserStore;

/**
 * Handles the login use case.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final UserStore userStore;

    public LoginInteractor() {
        this.userStore = InMemoryUserStore.getInstance(); // Use singleton
    }

    @Override
    public LoginOutputData login(LoginInputData inputData) {
        User user = userStore.getUser(inputData.getUsername());
        if (user != null && user.getPassword().equals(inputData.getPassword())) {
            return new LoginOutputData(true, "Login successful. Welcome, " + inputData.getUsername() + "!");
        } else {
            return new LoginOutputData(false, "Invalid username or password.");
        }
    }
}

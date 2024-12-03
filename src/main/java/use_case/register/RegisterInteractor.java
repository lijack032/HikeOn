package use_case.register;

import entity.InMemoryUserStore;
import entity.User;
import entity.UserStore;

/**
 * Handles the registration use case.
 */
public class RegisterInteractor implements RegisterInputBoundary {
    private final UserStore userStore;

    public RegisterInteractor() {
        this.userStore = InMemoryUserStore.getInstance(); // Use singleton
    }

    @Override
    public RegisterOutputData register(RegisterInputData inputData) {
        if (userStore.containsUser(inputData.getUsername())) {
            return new RegisterOutputData(false, "Username already exists.");
        }
        if (inputData.getPassword().length() < 6) {
            return new RegisterOutputData(false, "Password must be at least 6 characters long.");
        }
        boolean added = userStore.addUser(new User(inputData.getUsername(), inputData.getPassword()));
        if (added) {
            return new RegisterOutputData(true, "Registration successful.");
        } else {
            return new RegisterOutputData(false, "Registration failed. Try again.");
        }
    }
}


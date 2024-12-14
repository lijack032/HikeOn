package interface_adapter.register;

import use_case.register.RegisterInputBoundary;
import use_case.register.RegisterInputData;
import use_case.register.RegisterOutputData;

/**
 * Handles registration requests from the UI.
 */
public class RegisterController {
    private final RegisterInputBoundary interactor;

    public RegisterController(RegisterInputBoundary interactor) {
        this.interactor = interactor;
    }

    public RegisterOutputData register(String username, String password) {
        return interactor.register(new RegisterInputData(username, password));
    }
}


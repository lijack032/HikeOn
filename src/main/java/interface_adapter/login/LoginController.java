package interface_adapter.login;

import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;
import use_case.login.LoginOutputData;

/**
 * Handles login requests from the UI.
 */
public class LoginController {
    private final LoginInputBoundary interactor;

    public LoginController(LoginInputBoundary interactor) {
        this.interactor = interactor;
    }

    public LoginOutputData login(String username, String password) {
        return interactor.login(new LoginInputData(username, password));
    }
}

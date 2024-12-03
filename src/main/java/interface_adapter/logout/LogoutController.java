package interface_adapter.logout;

import use_case.logout.LogoutInputBoundary;

/**
 * Handles logout requests from the UI.
 */
public class LogoutController {
    private final LogoutInputBoundary interactor;

    public LogoutController(LogoutInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void logout() {
        interactor.logout();
    }
}

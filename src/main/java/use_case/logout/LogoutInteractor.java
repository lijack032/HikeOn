package use_case.logout;

/**
 * Handles the logout use case.
 */
public class LogoutInteractor implements LogoutInputBoundary {
    @Override
    public void logout() {
        // For now, no session persistence, so no data to clear
        System.out.println("User logged out successfully.");
    }
}

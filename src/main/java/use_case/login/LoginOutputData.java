package use_case.login;

/**
 * Output data for the login use case.
 */
public class LoginOutputData {
    private final boolean success;
    private final String message;

    public LoginOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}



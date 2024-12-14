package use_case.register;

public class RegisterInputData {
    private final String username;
    private final String password;

    public RegisterInputData(String username, String password) {
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

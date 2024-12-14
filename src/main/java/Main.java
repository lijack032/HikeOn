import interface_adapter.login.LoginController;
import interface_adapter.register.RegisterController;
import use_case.login.LoginInteractor;
import use_case.register.RegisterInteractor;
import view.LoginPage;

public class Main {
    public static void main(String[] args) {

        final LoginInteractor loginInteractor = new LoginInteractor();
        final RegisterInteractor registerInteractor = new RegisterInteractor();

        final LoginController loginController = new LoginController(loginInteractor);
        final RegisterController registerController = new RegisterController(registerInteractor);

        final LoginPage loginPage = new LoginPage(loginController, registerController);
        loginPage.setVisible(true);
    }
}

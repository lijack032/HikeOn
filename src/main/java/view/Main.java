package view;

import interface_adapter.autentication.login.LoginController;
import interface_adapter.register.RegisterController;
import use_case.login.LoginInteractor;
import use_case.register.RegisterInteractor;

public class Main {

    public static void main(String[] args) {
        // No need to pass UserStore explicitly; Singleton handles persistence
        final LoginInteractor loginInteractor = new LoginInteractor();
        final RegisterInteractor registerInteractor = new RegisterInteractor();

        final LoginController loginController = new LoginController(loginInteractor);
        final RegisterController registerController = new RegisterController(registerInteractor);

        final LoginPage loginPage = new LoginPage(loginController, registerController);
        loginPage.setVisible(true);
    }
}

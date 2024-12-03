package view;

import interface_adapter.autentication.login.LoginController;

import interface_adapter.register.RegisterController;
import use_case.login.LoginOutputData;
import use_case.register.RegisterOutputData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Login and Registration Page for the HikeOn application.
 */
public class LoginPage extends JFrame {
    private final LoginController loginController;
    private final RegisterController registerController;

    public LoginPage(LoginController loginController, RegisterController registerController) {
        this.loginController = loginController;
        this.registerController = registerController;

        setTitle("Login to HikeOn");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                LoginOutputData result = loginController.login(username, password);

                if (result.isSuccess()) {
                    JOptionPane.showMessageDialog(null, result.getMessage());
                    dispose(); // Close the login page
                    MainFrame.launchMainFrame(); // Show MainFrame after successful login
                } else {
                    JOptionPane.showMessageDialog(null, result.getMessage(), "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                RegisterOutputData result = registerController.register(username, password);

                if (result.isSuccess()) {
                    JOptionPane.showMessageDialog(null, result.getMessage());
                } else {
                    JOptionPane.showMessageDialog(null, result.getMessage(), "Registration Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(registerButton);
    }
}

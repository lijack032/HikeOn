package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import interface_adapter.login.LoginController;
import interface_adapter.register.RegisterController;
import use_case.login.LoginOutputData;
import use_case.register.RegisterOutputData;

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

        final JLabel usernameLabel = new JLabel("Username:");
        final JTextField usernameField = new JTextField();
        final JLabel passwordLabel = new JLabel("Password:");
        final JPasswordField passwordField = new JPasswordField();
        final JButton loginButton = new JButton("Login");
        final JButton registerButton = new JButton("Register");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String username = usernameField.getText();
                final String password = new String(passwordField.getPassword());

                final LoginOutputData result = loginController.login(username, password);

                if (result.isSuccess()) {
                    JOptionPane.showMessageDialog(null, result.getMessage());
                    dispose();
                    MainFrame.launchMainFrame();
                }
                else {
                    JOptionPane.showMessageDialog(null, result.getMessage(),
                            "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String username = usernameField.getText();
                final String password = new String(passwordField.getPassword());

                final RegisterOutputData result = registerController.register(username, password);

                if (result.isSuccess()) {
                    JOptionPane.showMessageDialog(null, result.getMessage());
                }
                else {
                    JOptionPane.showMessageDialog(null, result.getMessage(),
                            "Registration Failed", JOptionPane.ERROR_MESSAGE);
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

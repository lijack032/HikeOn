package frontend.view.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChatbotPanel extends JPanel {
    private JTextArea chatArea;
    private JTextField userInputField;
    private JButton sendButton;

    public ChatbotPanel() {
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);

        userInputField = new JTextField();
        sendButton = new JButton("Send");

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(userInputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(chatScrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
    }

    public String getUserInput() {
        String input = userInputField.getText();
        userInputField.setText("");
        return input;
    }

    public void appendMessage(String message) {
        chatArea.append(message + "\n");
    }

    public void setSendButtonListener(ActionListener listener) {
        sendButton.addActionListener(listener);
    }
}

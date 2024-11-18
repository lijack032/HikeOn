package frontend.view.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * ChatbotPanel is a JPanel that contains a chat area, user input field, and send button.
 * 
 * @null
 */
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
        final JScrollPane chatScrollPane = new JScrollPane(chatArea);

        userInputField = new JTextField();
        sendButton = new JButton("Send");

        final JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(userInputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(chatScrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
    }

    /**
     * Retrieves the text from the user input field and clears it.
     * 
     * @return the text from the user input field
     */
    public String getUserInput() {
        final String input = userInputField.getText();
        userInputField.setText("");
        return input;
    }

    /**
     * Appends a message to the chat area.
     * 
     * @param message the message to append
     */
    public void appendMessage(String message) {
        chatArea.append(message + "\n");
    }

    /**
     * Sets the ActionListener for the send button.
     * 
     * @param listener the ActionListener to set
     */
    public void setSendButtonListener(ActionListener listener) {
        sendButton.addActionListener(listener);
    }
}

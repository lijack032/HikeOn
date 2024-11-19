package frontend.view.panels;

import javax.swing.*;
import java.awt.*;
public class ChatbotPanel extends JPanel {
    private final JTextArea conversationArea; // Text area to display conversation
    private final JTextField inputField;      // Text field for user input
    private final JButton sendButton;         // Button to send message

    public ChatbotPanel() {
        setLayout(new BorderLayout());

        // Initialize the conversation area
        conversationArea = new JTextArea(20, 40);
        conversationArea.setEditable(false);  // Make conversation area read-only
        conversationArea.setLineWrap(true);
        conversationArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(conversationArea);

        // Initialize the input field for user messages
        inputField = new JTextField(40);

        // Initialize the send button
        sendButton = new JButton("Send");

        // Add components to the panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Displays the conversation history in the conversation area.
     *
     * @param conversationText the full conversation as a single string
     */
    public void displayConversation(String conversationText) {
        conversationArea.setText(conversationText);
    }

    /**
     * Clears the conversation history from the panel.
     */
    public void clearConversation() {
        conversationArea.setText("");
    }

    /**
     * Adds a listener to the send button for sending user messages.
     * 
     * @param actionListener the listener to handle button click events
     */
    public void addSendButtonListener(java.awt.event.ActionListener actionListener) {
        sendButton.addActionListener(actionListener);
    }

    /**
     * Gets the text entered by the user in the input field.
     *
     * @return the user's input message
     */
    public String getUserInput() {
        return inputField.getText();
    }

    /**
     * Clears the input field after the user sends a message.
     */
    public void clearInputField() {
        inputField.setText("");
    }
}

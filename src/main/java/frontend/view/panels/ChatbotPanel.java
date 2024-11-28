package frontend.view.panels;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * ChatbotPanel is a JPanel that contains a conversation area, input field, and send button.
 * 
 * @null This class does not accept null values.
 */
public class ChatbotPanel extends JPanel {
    private static final int CONVERSATION_AREA_ROWS = 20;
    private static final int CONVERSATION_AREA_COLUMNS = 40;

    private static final int INPUT_FIELD_COLUMNS = 40;

    private final JTextArea conversationArea;
    private final JTextField inputField;
    private final JButton sendButton;

    public ChatbotPanel() {
        conversationArea = new JTextArea(CONVERSATION_AREA_ROWS, CONVERSATION_AREA_COLUMNS);
        conversationArea.setEditable(false);
        // Initialize the conversation area
        conversationArea.setEditable(false);
        conversationArea.setLineWrap(true);
        conversationArea.setWrapStyleWord(true);
        final JScrollPane scrollPane = new JScrollPane(conversationArea);
        inputField = new JTextField(INPUT_FIELD_COLUMNS);

        // Initialize the send button
        sendButton = new JButton("Send");

        // Add components to the panel
        final JPanel bottomPanel = new JPanel(new BorderLayout());
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

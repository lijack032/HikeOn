package frontend.view;

import javax.swing.*;
import backend.service.ChatbotService;
import backend.service.WeatherService;
import frontend.view.panels.ChatbotPanel;



/**
 * MainFrame class for the HikeOn application. This class is the main entry point for the application
 */

public class MainFrame extends JFrame {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private final ChatbotService chatbotService;
    private final String chatbotSessionId;

    public MainFrame() {
        setTitle("HikeOn");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize services
        WeatherService weatherService = new WeatherService();
        chatbotService = new ChatbotService();

        // Start a new chatbot session
        chatbotSessionId = chatbotService.startSession();

        // Create panels
        ChatbotPanel chatbotPanel = new ChatbotPanel();

        // Set up chatbot panel interaction
        chatbotPanel.addSendButtonListener(e -> {
            String userInput = chatbotPanel.getUserInput();
            if (userInput.isBlank()) {
                chatbotPanel.displayConversation("You: [Empty message]");
                return;
            }
            
            // Get chatbot response
            String botResponse = chatbotService.getChatbotResponse(chatbotSessionId, userInput);
            
            // Display the conversation in the panel
            chatbotPanel.displayConversation(
                chatbotPanel.getUserInput() + "\n" +
                botResponse
            );
            chatbotPanel.clearInputField();
        });

        // Add the panel to the frame
        setContentPane(chatbotPanel);

        // Center the frame on the screen
        setLocationRelativeTo(null);



        // Fetch weather data for a dynamic city
        final String city = "Toronto";
        weatherService.updateWeather(city);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}

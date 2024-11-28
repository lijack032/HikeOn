package frontend.view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import backend.service.WeatherService;
import frontend.view.panels.HomePanel;

/**
 * MainFrame class for the HikeOn application. This class is the main entry point for the application
 */
public class MainFrame extends JFrame {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;

    public MainFrame() {
        setTitle("HikeOn");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final WeatherService weatherService = new WeatherService();

        final HomePanel homePanel = new HomePanel();
        setContentPane(homePanel);

        // Center the frame on the screen
        setLocationRelativeTo(null);

        // Fetch weather data for a dynamic city
        final String city = "Toronto";
        weatherService.updateWeather(city);
    }

    /**
     * The main method to start the HikeOn application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            final MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}

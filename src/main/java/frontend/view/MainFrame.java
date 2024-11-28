package frontend.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * SecondMainFrame is the main frame for the HikeOn application.
 * 
 * @null
 */
public class MainFrame {

    private static final String FONT_NAME = "Arial";
    private static final int TITLE_FONT_SIZE = 24;
    private static final int FOOTER_FONT_SIZE = 12;
    private static final int TITLE_COLOR_RED = 34;
    private static final int TITLE_COLOR_GREEN = 139;
    private static final int TITLE_COLOR_BLUE = 34;
    private static final int LABEL_FONT_SIZE = 16;

    private static final int INSET_SIZE = 10;

    /**
    * The main method to launch the HikeOn application.
    *
    * @param args the command line arguments
    */
    public static void main(String[] args) {
        final JFrame frame = createMainFrame();
        frame.add(createTitlePanel(), BorderLayout.NORTH);
        frame.add(createCenterPanel(), BorderLayout.CENTER);
        frame.add(createFooterPanel(), BorderLayout.SOUTH);
        frame.setVisible(true);
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(getInsetSize(), getInsetSize(), getInsetSize(), getInsetSize());
    }

    private static JFrame createMainFrame() {
        final JFrame frame = new JFrame("HikeOn - Outdoor Event Planner");
        final int frameWidth = 600;
        final int frameHeight = 600;
        frame.setSize(frameWidth, frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        return frame;
    }

    private static JPanel createTitlePanel() {
        final JLabel titleLabel = new JLabel("HikeOn");
        titleLabel.setForeground(new Color(TITLE_COLOR_RED, TITLE_COLOR_GREEN, TITLE_COLOR_BLUE));
        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, TITLE_FONT_SIZE));
        titleLabel.setForeground(new Color(TITLE_COLOR_RED, TITLE_COLOR_GREEN, TITLE_COLOR_BLUE));
        final JPanel titlePanel = new JPanel();
        titlePanel.add(titleLabel);
        return titlePanel;
    }

    private static JPanel createCenterPanel() {
        final JPanel centerPanel = new JPanel(new GridBagLayout());
        final int borderSize = 20;
        centerPanel.setBorder(BorderFactory.createEmptyBorder(borderSize, borderSize, borderSize, borderSize));
        final GridBagConstraints gbc = createGridBagConstraints();

        addEventTypeComponents(centerPanel, gbc);
        addLocationComponents(centerPanel, gbc);
        addWeatherComponents(centerPanel, gbc);
        addButtons(centerPanel, gbc);

        return centerPanel;
    }

    public static int getInsetSize() {
        return INSET_SIZE;
    }

    private static GridBagConstraints createGridBagConstraints() {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(INSET_SIZE, INSET_SIZE, INSET_SIZE, INSET_SIZE);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private static void addEventTypeComponents(JPanel panel, GridBagConstraints gbc) {
        final JLabel eventLabel = new JLabel("Event Type:");
        eventLabel.setFont(new Font(FONT_NAME, Font.PLAIN, LABEL_FONT_SIZE));
        final String[] events = {"Hiking", "Picnic"};
        final JComboBox<String> eventComboBox = new JComboBox<>(events);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(eventLabel, gbc);
        gbc.gridx = 1;
        panel.add(eventComboBox, gbc);
    }

    private static void addLocationComponents(JPanel panel, GridBagConstraints gbc) {
        final JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
        final JTextField locationField = new JTextField(15);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(locationLabel, gbc);
        gbc.gridx = 1;
        panel.add(locationField, gbc);
    }

    private static void addWeatherComponents(JPanel panel, GridBagConstraints gbc) {
        final JLabel weatherLabel = new JLabel("Weather: ");
        weatherLabel.setFont(new Font(FONT_NAME, Font.BOLD, 16));
        weatherLabel.setForeground(Color.BLUE);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(weatherLabel, gbc);
    }

    private static void addButtons(JPanel panel, GridBagConstraints gbc) {
        final JButton fetchWeatherButton = createStyledButton("Get Weather", new Color(70, 130, 180),
                new Color(100, 149, 237));

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(fetchWeatherButton, gbc);

        final JButton googleMapsButton = createStyledButton("Find Nearby Hiking Trails", new Color(34, 139, 34),
                new Color(60, 179, 113));

        gbc.gridx = 0;
        final int buttonRow = 4;
        gbc.gridy = buttonRow;
        gbc.gridwidth = 2;
        panel.add(googleMapsButton, gbc);
    }

    private static JPanel createFooterPanel() {
        final JPanel footerPanel = new JPanel();
        final JLabel footerLabel = new JLabel("Stay safe and enjoy the outdoors!");
        footerLabel.setFont(new Font(FONT_NAME, Font.ITALIC, FOOTER_FONT_SIZE));
        footerPanel.add(footerLabel);
        return footerPanel;
    }

    private static JButton createStyledButton(String text, Color defaultColor, Color hoverColor) {
        final JButton button = new JButton(text);
        return button;
    }
}

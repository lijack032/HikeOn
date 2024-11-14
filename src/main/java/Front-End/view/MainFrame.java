package frontend.view;

import javax.swing.*;
import frontend.view.panels.HomePanel;

public class MainFrame {

    public MainFrame() {
        setTitle("HikeOn");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        HomePanel homePanel = new HomePanel();
        setContentPane(homePanel);

        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
    
}

package frontend.view.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

import javax.swing.*;

import backend.service.LocationService;

/**
 * A text field with autocomplete functionality for location input.
 */
public class AutocompleteTextField extends JTextField {
    private static final int DEBOUNCE_DELAY_MS = 300;

    private final JPopupMenu suggestionsPopup;
    private final LocationService locationService;
    private final Map<String, List<String>> suggestionCache;
    private final ScheduledExecutorService debounceExecutor;
    private ScheduledFuture<?> debounceFuture;

    private String latestInput = "";
    private boolean isMouseHovering;

    /**
     * Constructor for the AutocompleteTextField.
     *
     * @param locationService the service used for fetching location suggestions
     */
    public AutocompleteTextField(LocationService locationService) {
        this.locationService = locationService;
        this.suggestionsPopup = new JPopupMenu();
        this.suggestionsPopup.setFocusable(false);
        this.suggestionCache = new HashMap<>();
        this.debounceExecutor = Executors.newSingleThreadScheduledExecutor();

        initializeKeyListener();
        initializePopupListener();
    }

    private void initializeKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                final String text = getText().trim();
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    suggestionsPopup.setVisible(false);
                }
                else if (text.length() > 2) {
                    debounceFetchSuggestions(text);
                }
                else {
                    suggestionsPopup.setVisible(false);
                }
            }
        });
    }

    private void initializePopupListener() {
        suggestionsPopup.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {
                // Do nothing
            }

            @Override
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e) {
                isMouseHovering = false;
            }

            @Override
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e) {
                isMouseHovering = false;
            }
        });
    }

    private void debounceFetchSuggestions(String input) {
        if (debounceFuture != null) {
            debounceFuture.cancel(false);
        }

        latestInput = input;

        debounceFuture = debounceExecutor.schedule(() -> {
            SwingUtilities.invokeLater(() -> fetchSuggestions(input));
        }, DEBOUNCE_DELAY_MS, TimeUnit.MILLISECONDS);
    }

    private void fetchSuggestions(String input) {
        if (input.equals(latestInput)) {
            List<String> suggestions = suggestionCache.get(input.toLowerCase());

            if (suggestions == null) {
                suggestions = locationService.getAutocompleteSuggestions(input);
                suggestionCache.put(input.toLowerCase(), suggestions);
            }

            if (!suggestions.isEmpty()) {
                updateSuggestions(suggestions);
            }
            else {
                suggestionsPopup.setVisible(false);
            }
        }
    }

    private void updateSuggestions(List<String> suggestions) {
        suggestionsPopup.removeAll();

        for (String suggestion : suggestions) {
            final JMenuItem item = createSuggestionItem(suggestion);
            suggestionsPopup.add(item);
        }

        suggestionsPopup.revalidate();
        suggestionsPopup.repaint();

        if (!suggestionsPopup.isVisible()) {
            suggestionsPopup.show(this, 0, getHeight());
        }
    }

    private JMenuItem createSuggestionItem(String suggestion) {
        final JMenuItem item = new JMenuItem(suggestion);
        item.setFocusable(false);

        item.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                isMouseHovering = true;
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                isMouseHovering = false;
            }
        });

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setText(suggestion);
                suggestionsPopup.setVisible(false);
            }
        });

        return item;
    }

    /**
     * Shuts down the executor service to free up resources.
     */
    public void cleanUp() {
        debounceExecutor.shutdown();
    }
}

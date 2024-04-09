import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;

public class ModelViewer {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("3D Model Viewer");
            frame.setLayout(new BorderLayout()); // Keep using BorderLayout for overall structure

            final JFXPanel fxPanel = new JFXPanel();
            frame.add(fxPanel, BorderLayout.CENTER); // 3D model viewing area remains at center

            // Create a toolbar for buttons instead of a simple panel
            JToolBar toolBar = new JToolBar();
            toolBar.setFloatable(false); // Make the toolbar non-floatable
            toolBar.setRollover(true); // Enable rollover effect for buttons

            // Create buttons with icons (replace "path/to/icon.png" with actual paths)
            JButton saveButton = new JButton(new ImageIcon("src/assets/icons8-save-30.png"));
            saveButton.setToolTipText("Save");
            JButton editButton = new JButton(new ImageIcon("src/assets/icons8-edit-30.png"));
            editButton.setToolTipText("Edit");
            JButton deleteButton = new JButton(new ImageIcon("src/assets/icons8-delete-24.png"));
            deleteButton.setToolTipText("Delete");
            JButton returnButton = new JButton(new ImageIcon("src/assets/icons8-return-24.png"));
            returnButton.setToolTipText("Return");

            // Add action listeners for buttons as before

            saveButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(frame,
                        "Model saved successfully.",
                        "Save Successful",
                        JOptionPane.INFORMATION_MESSAGE);
            });

            editButton.addActionListener(e -> frame.dispose());

            deleteButton.addActionListener(e -> {
                int result = JOptionPane.showConfirmDialog(frame,
                        "Confirm deletion of the model.",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    frame.dispose(); // Assume deletion logic here
                }
            });

            returnButton.addActionListener(e -> frame.dispose());

            // Add buttons to the toolbar
            toolBar.add(saveButton);
            toolBar.add(editButton);
            toolBar.add(deleteButton);
            toolBar.add(returnButton);

            // Add the toolbar to the top of the frame
            frame.add(toolBar, BorderLayout.NORTH);

            frame.setSize(900, 700); // Adjust window size
            frame.setResizable(false); // Make the window non-resizable
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            Platform.runLater(() -> initFX(fxPanel));
        });
    }

    private static void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        WebView webView = new WebView();
        webView.getEngine().load("https://sketchfab.com/models/b044f25f823c4d54ab32bdf16a205456/embed");

        Scene scene = new Scene(webView);
        fxPanel.setScene(scene);
    }
}

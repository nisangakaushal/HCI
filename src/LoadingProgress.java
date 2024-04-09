import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadingProgress extends JFrame {

    private JProgressBar progressBar;
    private JLabel loadingLabel;
    private Timer progressTimer;
    private Timer blinkTimer;

    public LoadingProgress() {
        setTitle("Generating Progress");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 350);
        setLocationRelativeTo(null); // Center the frame on the screen
        getContentPane().setBackground(new Color(240, 240, 240)); // Set light gray background color

        // Create a panel for the title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Generation in Progress");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Set font to bold Arial, adjust size as needed
        titlePanel.add(titleLabel);

        // Create a panel for the logo
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ImageIcon logoIcon = new ImageIcon("src/assets/Beige Brown Blue Chair Modern Furniture Logo.png"); // Change "logo.png" to your image file path
        JLabel logoLabel = new JLabel(logoIcon);
        logoPanel.add(logoLabel);

        // Create a panel for the progress bar
        JPanel progressBarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBarPanel.add(progressBar);

        // Create a panel for the loading label
        JPanel loadingLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loadingLabel = new JLabel("Generating...");
        loadingLabel.setForeground(new Color(0, 102, 204)); // Set text color to blue
        loadingLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font to bold Arial, adjust size as needed
        loadingLabelPanel.add(loadingLabel);

        // Add panels to the frame
        add(titlePanel, BorderLayout.NORTH);
        add(logoPanel, BorderLayout.CENTER);
        add(progressBarPanel, BorderLayout.CENTER);
        add(loadingLabelPanel, BorderLayout.SOUTH);

        // Simulate progress
        simulateProgress();

        setVisible(true);
    }

    private void simulateProgress() {
        int delay = 5; // Milliseconds
        progressTimer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = progressBar.getValue();
                if (value < progressBar.getMaximum()) {
                    progressBar.setValue(value + 1);
                } else {
                    progressTimer.stop(); // Stop the progress timer
                    loadingLabel.setText("Generated"); // Change label text to "Generated"
                    progressBar.setString("100%"); // Set progress bar text to "100%"
                    blinkTimer.stop(); // Stop the blink timer

                    // Show message dialog with custom button
                    showMessageDialogWithCustomButton();
                }
            }
        });
        progressTimer.start();

        // Blinking loading label
        int blinkDelay = 500; // Milliseconds
        blinkTimer = new Timer(blinkDelay, new ActionListener() {
            private boolean isVisible = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                loadingLabel.setVisible(isVisible);
                isVisible = !isVisible;
            }
        });
        blinkTimer.start();
    }


    private void showMessageDialogWithCustomButton() {
        // Custom button text
        final JOptionPane optionPane = new JOptionPane("Generation Complete!",
                JOptionPane.INFORMATION_MESSAGE,
                JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);

        final JDialog dialog = new JDialog(this, "Dialog", true);
        dialog.setContentPane(optionPane);

        // Adding "Show 3D View" button
        JButton show3DViewButton = new JButton("Show 3D View");
        show3DViewButton.addActionListener(e -> {
//            dialog.dispose(); // Close the dialog
            dispose();
            setVisible(false);

//            LoadingProgress.this.dispose(); // Close the current window
//            SwingUtilities.invokeLater(ModelViewer::new);
            ModelViewer.main(new String[]{});
        });

        optionPane.add(show3DViewButton);

        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoadingProgress::new);
    }
}

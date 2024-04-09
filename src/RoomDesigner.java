import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RoomDesigner {
    private JComboBox<String> shapeComboBox;
    private JComboBox<String> sizeComboBox;
    private JButton colorButton;
    private JButton loadButton;

    public RoomDesigner() {
        JFrame frame = new JFrame("Room Details");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3,2));

        // Shape selection
        shapeComboBox = new JComboBox<>(new String[]{"Square", "Rectangle", "Circle"}); // Add more shapes if needed
        //add default shape as Square
        shapeComboBox.setSelectedItem("Square");
        frame.add(new JLabel("Shape of your room: "));
        frame.add(shapeComboBox);

        // Size selection
        sizeComboBox = new JComboBox<>(new String[]{"Small", "Medium", "Large"}); // Add more sizes if needed
        //add default size as Small
        sizeComboBox.setSelectedItem("Small");
        frame.add(new JLabel("Size of your room: "));
        frame.add(sizeComboBox);

        // Color selection
        colorButton = new JButton("Pick the Room Color");
        colorButton.addActionListener(e -> {
            Color selectedColor = JColorChooser.showDialog(frame, "Choose Color", Color.WHITE);
            // Handle color selection
        });
        frame.add(colorButton);

        // Load button
        loadButton = new JButton("Load Design");

        loadButton.addActionListener(e -> {
            loadDesign();
            frame.dispose();
        });
        frame.add(loadButton);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void loadDesign() {
        String selectedShape = (String) shapeComboBox.getSelectedItem();
        String selectedSize = (String) sizeComboBox.getSelectedItem();
        Color selectedColor = colorButton.getBackground(); // You can use the selected color here

        // Initialize TwoDViwer form with selected values
        SwingUtilities.invokeLater(() -> {
            TwoDViwer viewer = new TwoDViwer(selectedShape, selectedSize, selectedColor);
            viewer.setVisible(true);

//             Pass selected values to the viewer for initialization
//            viewer.initialize(selectedShape, selectedSize, selectedColor);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RoomDesigner::new);
    }
}

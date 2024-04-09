import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class TwoDViwer extends JFrame{
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(TwoDViwer::new);
//    }

    private final DrawingPanel drawingPanel;
    private JPanel main2DPanel;
    private JButton lineButton;
    private JButton rectangleButton;
    private JButton circleButton;
    private JButton triangleButton;
    private JButton colorButton;
    private JButton clearButton;
    private JButton insertImageButton;
    private JButton scaleUpButton;
    private JButton scaleDownButton;
    private JButton proceedButton;
    private JButton saveButton;
    private JButton paintImageButton;
    private JSlider opacitySetter;
    private JButton prewieButton;
    private String selectedShape;
    private String selectedSize;
    private Color selectedColor;
//
    public TwoDViwer(String selectedShape, String selectedSize, Color selectedColor) {

        this.selectedShape = selectedShape;
        this.selectedSize = selectedSize;
        this.selectedColor = selectedColor;
//        public TwoDViwer() {

        setTitle("2D Viewer" + " - " + selectedShape + " - " + selectedSize );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        setLocationRelativeTo(null);

        drawingPanel = new DrawingPanel(selectedColor, selectedShape, selectedSize);
        main2DPanel.add(drawingPanel, BorderLayout.CENTER);
        add(main2DPanel);

        lineButton.addActionListener(e -> drawingPanel.setCurrentTool(DrawingTool.LINE));
        rectangleButton.addActionListener(e -> drawingPanel.setCurrentTool(DrawingTool.RECTANGLE));
        circleButton.addActionListener(e -> drawingPanel.setCurrentTool(DrawingTool.CIRCLE));
        triangleButton.addActionListener(e -> drawingPanel.setCurrentTool(DrawingTool.TRIANGLE));
        clearButton.addActionListener(e -> drawingPanel.setCurrentTool(DrawingTool.ERASE));
        colorButton.addActionListener(e -> drawingPanel.setCurrentColor(JColorChooser.showDialog(this, "Choose Color", drawingPanel.getCurrentColor())));
        insertImageButton.addActionListener(e -> {
            drawingPanel.setCurrentTool(DrawingTool.IMAGE);
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    BufferedImage image = ImageIO.read(selectedFile);
                    drawingPanel.insertImage(image);
                    drawingPanel.setOriginalImage(image);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        opacitySetter.addChangeListener(e -> {
            drawingPanel.setOpacity(opacitySetter.getValue());
        });

        paintImageButton.addActionListener(e -> {
            drawingPanel.setCurrentTool(DrawingTool.IMAGE);
            drawingPanel.setInsertedImage(drawingPanel.getOriginalImage());
            Color newColor = JColorChooser.showDialog(this, "Choose Color", drawingPanel.getCurrentColor());
            try{
                BufferedImage image = drawingPanel.getInsertedImage();
                BufferedImage coloredImage = drawingPanel.applyColorFilter(image, newColor);
                drawingPanel.insertImage(coloredImage);
            }catch (Exception error) {
                //show error message as a dialog containing the error message
                JOptionPane.showMessageDialog(this, "Please Select a 2D Object Before Re-Color it.", "Insertion Failed", JOptionPane.ERROR_MESSAGE);
            }

        });
//        add(paintButton, BorderLayout.SOUTH);

        scaleUpButton.addActionListener(e -> drawingPanel.scaleUpImage());
        scaleDownButton.addActionListener(e -> drawingPanel.scaleDownImage());

        saveButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showSaveDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    ImageIO.write(drawingPanel.getCanvas(), "PNG", selectedFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        prewieButton.addActionListener(e -> {
            DesignViewr preview = new DesignViewr(drawingPanel.getCanvasPreview());
            preview.setVisible(true);
            preview.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        });

        //load 3D viewer form when click proceedButton
        proceedButton.addActionListener(e -> {
            ModelViewer.main(new String[]{});
        });


        this.setVisible(true);

    }


}


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

public class DesignViewr extends JFrame {

    private final Viewer viewer;

    public DesignViewr(BufferedImage canvas) {

        setTitle("2D Design Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 800);
        setLocationRelativeTo(null);
        viewer = new Viewer(canvas);
        add(viewer);
        setVisible(true);
    }
}


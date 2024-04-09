import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class Viewer extends JPanel {

    private final Graphics2D graphics2D;
    private final BufferedImage canvas;

    public Viewer(BufferedImage preview) {
        this.canvas = preview;
        graphics2D = this.canvas.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        clearCanvas();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage((BufferedImage) canvas, 0, 0, this);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(canvas.getWidth(), canvas.getHeight());
    }

}

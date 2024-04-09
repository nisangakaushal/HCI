import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

class DrawingPanel extends JPanel {

    private final BufferedImage canvas;
    private final Graphics2D g2d;
    private DrawingTool currentTool;
    private Color currentColor;

    private Point startPoint;
    private Point endPoint;

    private BufferedImage insertedImage;
    private Point imagePosition;
    private Point imageDragStart;



    public DrawingPanel(Color selectedColor, String selectedShape, String selectedSize ) {
        //load the selected values in to the canvas
        this.canvas = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        g2d = this.canvas.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        clearCanvas();

        //set the selected color to the JPanel
//        currentColor = selectedColor;

        currentTool = DrawingTool.PENCIL;
        currentColor = Color.BLACK;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
                if (insertedImage != null && isPointInImage(startPoint)) {
                    imageDragStart = startPoint;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                endPoint = e.getPoint();
                drawShape(startPoint, endPoint);
                startPoint = null;
                endPoint = null;
                if (imageDragStart != null) {
                    imageDragStart = null;
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                endPoint = e.getPoint();
                if (imageDragStart != null) {
                    dragImage(endPoint);
                } else {
                    repaint();
                }
            }
        });
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(canvas, 0, 0, this);

        if (startPoint != null && endPoint != null) {
            g2d.setColor(currentColor);
            drawShape(g2d, startPoint, endPoint);
        }

        if (insertedImage != null) {
            int x = imagePosition.x;
            int y = imagePosition.y;
            g2d.drawImage(insertedImage, x, y, this);
            //draw a boader with the same color as the current color and border width 5
            g2d.setStroke(new BasicStroke(5));
            g2d.setColor(currentColor);
            g2d.drawRect(x, y, insertedImage.getWidth(), insertedImage.getHeight());
        }
    }

    private void drawShape(Graphics2D g2d, Point start, Point end) {
        g2d.setColor(currentColor); // Set the color to the current selected color
        switch (currentTool) {
            case PENCIL:
                g2d.drawLine(start.x, start.y, end.x, end.y);
                break;
            case LINE:
                g2d.drawLine(start.x, start.y, end.x, end.y);
                break;
            case RECTANGLE:
                int x = Math.min(start.x, end.x);
                int y = Math.min(start.y, end.y);
                int width = Math.abs(start.x - end.x);
                int height = Math.abs(start.y - end.y);
                g2d.drawRect(x, y, width, height);
                g2d.fillRect(x, y, width, height); // Fill the rectangle
                break;
            case CIRCLE:
                int radius = (int) Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.y - start.y, 2));
                g2d.drawOval(start.x - radius, start.y - radius, 2 * radius, 2 * radius);
                g2d.fillOval(start.x - radius, start.y - radius, 2 * radius, 2 * radius); // Fill the circle
                break;
            case TRIANGLE:
                int[] xPoints = {start.x, end.x, (start.x + end.x) / 2};
                int[] yPoints = {start.y, end.y, start.y};
                g2d.drawPolygon(xPoints, yPoints, 3);
                g2d.fillPolygon(xPoints, yPoints, 3); // Fill the triangle
                break;
//            case FILL:
//                fillShape(g2d, end);
//                break;

            case ERASE:
                clearCanvas();
                break;
        }
    }


    private void drawShape(Point start, Point end) {
        g2d.setColor(currentColor);
        switch (currentTool) {
            case PENCIL:
                g2d.drawLine(start.x, start.y, end.x, end.y);
                break;
            case LINE:
                g2d.drawLine(start.x, start.y, end.x, end.y);
                break;
            case RECTANGLE:
                int x = Math.min(start.x, end.x);
                int y = Math.min(start.y, end.y);
                int width = Math.abs(start.x - end.x);
                int height = Math.abs(start.y - end.y);
                g2d.drawRect(x, y, width, height);
                g2d.fillRect(x, y, width, height); // Fill the rectangle
                break;
            case CIRCLE:
                int radius = (int) Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.y - start.y, 2));
                g2d.drawOval(start.x - radius, start.y - radius, 2 * radius, 2 * radius);
                g2d.fillOval(start.x - radius, start.y - radius, 2 * radius, 2 * radius); // Fill the circle
                break;
            case TRIANGLE:
                int[] xPoints = {start.x, end.x, (start.x + end.x) / 2};
                int[] yPoints = {start.y, end.y, start.y};
                g2d.drawPolygon(xPoints, yPoints, 3);
                g2d.fillPolygon(xPoints, yPoints, 3); // Fill the triangle
                break;
//            case FILL:
//                fillShape(g2d, end);
//                break;

            case ERASE:
                clearCanvas();
                break;
        }
    }

    private void clearCanvas() {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        //remove inserted image also
        insertedImage = null;
        repaint();
    }

    public void setCurrentTool(DrawingTool tool) {
        currentTool = tool;
    }

    public void setCurrentColor(Color color) {
        currentColor = color;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public void insertImage(BufferedImage image) {
        insertedImage = image;
        int x = (getWidth() - image.getWidth()) / 2; // Center the image horizontally
        int y = (getHeight() - image.getHeight()) / 2; // Center the image vertically
        imagePosition = new Point(x, y);
        repaint();
    }

    private boolean isPointInImage(Point point) {
        if (insertedImage == null) return false;
        int x = imagePosition.x;
        int y = imagePosition.y;
        int width = insertedImage.getWidth();
        int height = insertedImage.getHeight();
        return point.x >= x && point.x <= x + width && point.y >= y && point.y <= y + height;
    }

    private void dragImage(Point endPoint) {
        if (insertedImage != null && imageDragStart != null) {
            int dx = endPoint.x - imageDragStart.x;
            int dy = endPoint.y - imageDragStart.y;
            imagePosition.translate(dx, dy);
            imageDragStart = endPoint;
            repaint();
        }
    }

    private static final double SCALE_FACTOR = 0.1; // Define a scale factor for resizing

    // Method to scale up the image
    public void scaleUpImage() {
        if (insertedImage != null) {
            int newWidth = (int) (insertedImage.getWidth() * (1 + SCALE_FACTOR));
            int newHeight = (int) (insertedImage.getHeight() * (1 + SCALE_FACTOR));
            resizeImage(newWidth, newHeight);
        }
    }

    // Method to scale down the image
    public void scaleDownImage() {
        if (insertedImage != null) {
            int newWidth = (int) (insertedImage.getWidth() * (1 - SCALE_FACTOR));
            int newHeight = (int) (insertedImage.getHeight() * (1 - SCALE_FACTOR));
            resizeImage(newWidth, newHeight);
        }
    }

    // Method to resize the image
    private void resizeImage(int newWidth, int newHeight) {
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(insertedImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        insertedImage = resizedImage;

        // Adjust image position to keep it centered
        int x = (getWidth() - newWidth) / 2;
        int y = (getHeight() - newHeight) / 2;
        imagePosition = new Point(x, y);
        repaint();
    }

    public BufferedImage getInsertedImage() {
        return insertedImage;
    }

    public void setInsertedImage(BufferedImage filteredImage) {
        insertedImage = filteredImage;
        repaint();
    }


    // Method to apply color filter to an image
    public BufferedImage applyColorFilter(BufferedImage originalImage, Color newColor) {
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();

            BufferedImage filteredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            // Iterate over each pixel in the original image
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Get the color of the pixel
                    int rgb = originalImage.getRGB(x, y);
                    Color color = new Color(rgb, true);

                    //add threshold to the color
                    int threshold = 255;
                    threshold = getOpacity()*threshold/100;
                    if (color.getRed() > threshold && color.getGreen() > threshold && color.getBlue() > threshold){
                        color = newColor;
                    }

                    // Set the color of the corresponding pixel in the filtered image
                    filteredImage.setRGB(x, y, color.getRGB());
                }
            }
            //show the image
            return filteredImage;
    }

    // Method to set the current color
    public RenderedImage getCanvas() {
        return canvas;
    }

    //Keep original image and return it
    private BufferedImage originalImage;
    public void setOriginalImage(BufferedImage image){
        originalImage = image;
    }
    public BufferedImage getOriginalImage(){
        return originalImage;
    }

    private int opacity = 100;
    public void setOpacity(int value) {
        opacity = value;
    }

    public int getOpacity() {
        return opacity;
    }

    public BufferedImage getCanvasPreview() {
        // Create a new BufferedImage to hold the preview
        BufferedImage preview = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        // Create a graphics context for the preview image
        Graphics2D g2dPreview = preview.createGraphics();

        // Paint the contents of the canvas onto the preview image
        paintComponent(g2dPreview);

        // Dispose of the graphics context
        g2dPreview.dispose();

        // Return the preview image
        return preview;
    }

}


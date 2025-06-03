package ExtraComponents;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

// Step 1: Create the custom JPanel class
public class ScalableImagePanel extends JPanel {
    private BufferedImage image;
    private double maxScale = 1.0; // Only scale down, never up
    
    public ScalableImagePanel(BufferedImage image) {
        this.image = image;
        // Set preferred size to original image size
        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        setOpaque(false);
//        setBackground(Color.LIGHT_GRAY); // Optional: background color
    }
    
    // Method to update the image if needed
    public void setImage(BufferedImage newImage) {
        this.image = newImage;
        if (newImage != null) {
            setPreferredSize(new Dimension(newImage.getWidth(), newImage.getHeight()));
        }
        repaint(); // Trigger a redraw
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Paint background
        
        if (image != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            
            // Enable smooth scaling
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                               RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, 
                               RenderingHints.VALUE_RENDER_QUALITY);
            
            // Calculate scale to fit within panel bounds (only scale down)
            double scaleX = (double) getWidth() / image.getWidth();
            double scaleY = (double) getHeight() / image.getHeight();
            double scale = Math.min(Math.min(scaleX, scaleY), maxScale);
            
            // Calculate scaled dimensions
            int scaledWidth = (int) (image.getWidth() * scale);
            int scaledHeight = (int) (image.getHeight() * scale);
            
            // Center the image in the panel
            int x = (getWidth() - scaledWidth) / 2;
            int y = (getHeight() - scaledHeight) / 2;
            
            // Draw the scaled image
            g2d.drawImage(image, x, y, scaledWidth, scaledHeight, null);
            g2d.dispose();
        }
    }
}

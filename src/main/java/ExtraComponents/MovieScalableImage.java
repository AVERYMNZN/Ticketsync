/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExtraComponents;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class MovieScalableImage extends JPanel {
    private BufferedImage image;
    private double maxScale = 1.0; // Only scale down, never up
    private double coverAspectRatio = 2.0 / 3.0; // Standard movie poster aspect ratio (width/height)
    
    public MovieScalableImage(BufferedImage image) {
        this.image = image;
        // Set preferred size based on cover aspect ratio
        if (image != null) {
            int preferredHeight = 300; // Default height
            int preferredWidth = (int) (preferredHeight * coverAspectRatio);
            setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        }
        setOpaque(false);
    }
    
    // Constructor with custom aspect ratio
    public MovieScalableImage(BufferedImage image, double aspectRatio) {
        this.image = image;
        this.coverAspectRatio = aspectRatio;
        if (image != null) {
            int preferredHeight = 300; // Default height
            int preferredWidth = (int) (preferredHeight * coverAspectRatio);
            setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        }
        setOpaque(false);
    }
    
    // Method to update the image if needed
    public void setImage(BufferedImage newImage) {
        this.image = newImage;
        repaint(); // Trigger a redraw
    }
    
    // Method to set custom aspect ratio
    public void setCoverAspectRatio(double aspectRatio) {
        this.coverAspectRatio = aspectRatio;
        repaint();
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
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                               RenderingHints.VALUE_ANTIALIAS_ON);
            
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            
            // Calculate the target dimensions based on panel's aspect ratio
            double panelAspectRatio = (double) panelWidth / panelHeight;
            double imageAspectRatio = (double) image.getWidth() / image.getHeight();
            
            // Calculate scale factor to fill the panel (crop if necessary)
            double scaleX = (double) panelWidth / image.getWidth();
            double scaleY = (double) panelHeight / image.getHeight();
            
            // Use the larger scale to ensure the image fills the panel completely
            // This will crop the image if the aspect ratios don't match
            double scale = Math.max(scaleX, scaleY);
            
            // Apply max scale limit (only scale down, never up)
            scale = Math.min(scale, maxScale);
            
            // Calculate scaled dimensions
            int scaledWidth = (int) (image.getWidth() * scale);
            int scaledHeight = (int) (image.getHeight() * scale);
            
            // Calculate crop area (center the image)
            int cropX = 0;
            int cropY = 0;
            int cropWidth = image.getWidth();
            int cropHeight = image.getHeight();
            
            // If we need to crop horizontally (landscape image)
            if (imageAspectRatio > panelAspectRatio) {
                // Image is wider than panel aspect ratio, crop sides
                cropWidth = (int) (image.getHeight() * panelAspectRatio);
                cropX = (image.getWidth() - cropWidth) / 2;
            }
            // If we need to crop vertically (portrait image that's too tall)
            else if (imageAspectRatio < panelAspectRatio) {
                // Image is taller than panel aspect ratio, crop top/bottom
                cropHeight = (int) (image.getWidth() / panelAspectRatio);
                cropY = (image.getHeight() - cropHeight) / 2;
            }
            
            // Draw the cropped and scaled image
            g2d.drawImage(image, 
                         0, 0, panelWidth, panelHeight,  // destination
                         cropX, cropY, cropX + cropWidth, cropY + cropHeight,  // source crop area
                         null);
            
            g2d.dispose();
        } else {
            // Draw placeholder if no image
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.DARK_GRAY);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            
            // Draw "No Image" text
            FontMetrics fm = g.getFontMetrics();
            String text = "No Image";
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();
            g.drawString(text, 
                        (getWidth() - textWidth) / 2, 
                        (getHeight() + textHeight) / 2 - fm.getDescent());
        }
    }
    
    // Utility method to get current aspect ratio
    public double getCurrentAspectRatio() {
        return coverAspectRatio;
    }
    
    // Utility method to check if image would be cropped
    public boolean willImageBeCropped() {
        if (image == null) return false;
        
        double imageAspectRatio = (double) image.getWidth() / image.getHeight();
        double panelAspectRatio = (double) getWidth() / getHeight();
        
        // Allow for small floating point differences
        return Math.abs(imageAspectRatio - panelAspectRatio) > 0.01;
    }
    
    // Static method to create common movie poster ratios
    public static MovieScalableImage createMoviePoster(BufferedImage image) {
        return new MovieScalableImage(image, 2.0 / 3.0); // Standard movie poster
    }
    
    public static MovieScalableImage createSquareCover(BufferedImage image) {
        return new MovieScalableImage(image, 1.0); // Square aspect ratio
    }
    
    public static MovieScalableImage createWideCover(BufferedImage image) {
        return new MovieScalableImage(image, 16.0 / 9.0); // Widescreen aspect ratio
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExtraComponents;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class MovieScalableImage extends JPanel {
    private BufferedImage image;
    private double maxScale = 1.0; // Only scale down, never up
    private double coverAspectRatio = 2.0 / 3.0; // Standard movie poster aspect ratio (width/height)
    private int cornerRadius = 10; // Default corner radius
    
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
    
    // Constructor with custom aspect ratio and corner radius
    public MovieScalableImage(BufferedImage image, double aspectRatio, int cornerRadius) {
        this.image = image;
        this.coverAspectRatio = aspectRatio;
        this.cornerRadius = cornerRadius;
        if (image != null) {
            int preferredHeight = 300; // Default height
            int preferredWidth = (int) (preferredHeight * coverAspectRatio);
            setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        }
        setOpaque(false);
    }
    
    // Method to update the corner radius
    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }
    
    // Method to get current corner radius
    public int getCornerRadius() {
        return cornerRadius;
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
            
            // Enable smooth scaling and antialiasing
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                               RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, 
                               RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                               RenderingHints.VALUE_ANTIALIAS_ON);
            
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            
            // Create rounded rectangle clip
            RoundRectangle2D roundedClip = new RoundRectangle2D.Float(
                0, 0, panelWidth, panelHeight, cornerRadius, cornerRadius);
            g2d.setClip(roundedClip);
            
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
            
            // Draw the cropped and scaled image (will be clipped to rounded rectangle)
            g2d.drawImage(image, 
                         0, 0, panelWidth, panelHeight,  // destination
                         cropX, cropY, cropX + cropWidth, cropY + cropHeight,  // source crop area
                         null);
            
            g2d.dispose();
        } else {
            // Draw rounded placeholder if no image
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Create rounded rectangle for placeholder
            RoundRectangle2D roundedRect = new RoundRectangle2D.Float(
                0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            
            // Fill with placeholder color
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fill(roundedRect);
            
            // Draw border
            g2d.setColor(Color.DARK_GRAY);
            g2d.draw(roundedRect);
            
            // Draw "No Image" text
            g2d.setColor(Color.DARK_GRAY);
            FontMetrics fm = g2d.getFontMetrics();
            String text = "No Image";
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();
            g2d.drawString(text, 
                        (getWidth() - textWidth) / 2, 
                        (getHeight() + textHeight) / 2 - fm.getDescent());
            
            g2d.dispose();
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
    
    // Static method to create common movie poster ratios with rounded corners
    public static MovieScalableImage createMoviePoster(BufferedImage image) {
        return new MovieScalableImage(image, 2.0 / 3.0, 10); // Standard movie poster with 10px radius
    }
    
    public static MovieScalableImage createSquareCover(BufferedImage image) {
        return new MovieScalableImage(image, 1.0, 10); // Square aspect ratio with 10px radius
    }
    
    public static MovieScalableImage createWideCover(BufferedImage image) {
        return new MovieScalableImage(image, 16.0 / 9.0, 10); // Widescreen aspect ratio with 10px radius
    }
    
    // New static methods with custom corner radius
    public static MovieScalableImage createMoviePoster(BufferedImage image, int cornerRadius) {
        return new MovieScalableImage(image, 2.0 / 3.0, cornerRadius);
    }
    
    public static MovieScalableImage createSquareCover(BufferedImage image, int cornerRadius) {
        return new MovieScalableImage(image, 1.0, cornerRadius);
    }
    
    public static MovieScalableImage createWideCover(BufferedImage image, int cornerRadius) {
        return new MovieScalableImage(image, 16.0 / 9.0, cornerRadius);
    }
}
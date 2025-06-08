/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExtraComponents;

import Modules.FontLoader;
import Modules.GridFSCardData;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author avery
 */
public class CardComponent extends JPanel{
    
    JLabel movieTitleLabel, movieDescriptionLabel, movieCostLabel;
    FontLoader fontLoader = new FontLoader();
    
    public CardComponent(GridFSCardData data) {
        setPreferredSize(new Dimension(185,240));
        setBackground(new Color(204, 196, 188));
        setLayout(null); // Use absolute positioning for precise control
        
        putClientProperty("FlatLaf.style", "arc: 15");
        
        initComponents(data);
        revalidate();
        repaint();
    }
    
    private void initComponents(GridFSCardData data) {
        // Debug: Check if data and image are not null
        System.out.println("Creating CardComponent for: " + data.getTitle());
        System.out.println("Image is null: " + (data.getImage() == null));
        
        if (data.getImage() != null) {
            System.out.println("Image dimensions: " + data.getImage().getWidth() + "x" + data.getImage().getHeight());
        }
        
        // Attempts to create an image for the card component
        try {
            BufferedImage image = data.getImage();
            
            if (image != null) {
                // Create a landscape-oriented image component
                // Using 16:9 aspect ratio for a more rectangular, landscape look
                MovieScalableImage scalableImg = new MovieScalableImage(image, 16.0 / 9.0);
                
                
                // Position the image at the top of the card with some padding
                // Leave space at the bottom for text (about 60-80 pixels)
                scalableImg.setBounds(10, 10, 165, 93); // Width: 165, Height: 93 (16:9 ratio)
                add(scalableImg);
                System.out.println("Successfully added MovieScalableImage with landscape aspect ratio");
                
                // Alternative: If you want a different rectangular ratio, you can use:
                // scalableImg = new MovieScalableImage(image, 3.0 / 2.0); // 3:2 ratio
                // scalableImg.setBounds(10, 10, 165, 110); // Adjust height accordingly
                
            } else {
                System.out.println("Image is null, cannot create MovieScalableImage");
                // Add a placeholder panel to show something
                JPanel placeholder = new JPanel();
                placeholder.setBackground(Color.LIGHT_GRAY);
                placeholder.setBounds(10, 10, 165, 93); // Same dimensions as the image
                add(placeholder);
            }
        } catch (Exception e) {
            System.out.println("Exception creating MovieScalableImage: " + e.getMessage());
            e.printStackTrace();
        }
//        System.out.println("title is " + data.getTitle());
        movieTitleLabel = new JLabel(data.getTitle());
        movieCostLabel = new JLabel(String.valueOf(data.getMovieCost()));
        movieDescriptionLabel = new JLabel("<html><p style='overflow-wrap: break-word; word-wrap: break-word;'>"
                                       + data.getDescription().trim()
                                       + "</p></html>");
        
        
        movieTitleLabel.setFont(fontLoader.loadTextFontBold(14f));
        movieDescriptionLabel.setFont(fontLoader.loadTextFont(12f));
        
        movieTitleLabel.setBounds(15, 110, 200, 20);
        movieDescriptionLabel.setBounds(15, 130, 150, 70);
        
        add(movieTitleLabel);
        add(movieDescriptionLabel);
    }
}

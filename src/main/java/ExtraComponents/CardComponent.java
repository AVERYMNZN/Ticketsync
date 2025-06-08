/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExtraComponents;

import Modules.CardData;
import Modules.GridFSCardData;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author avery
 */
public class CardComponent extends JPanel{
    
    public CardComponent(GridFSCardData data) {
        setPreferredSize(new Dimension(185,240));
        setBackground(Color.GRAY);
        
        putClientProperty("FlatLaf.style", "arc: 15");
        
        initComponents(data);
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
                MovieScalableImage scalableImg = new MovieScalableImage(image);
                scalableImg.setPreferredSize(new Dimension(100, 100));
                scalableImg.setBounds(10, 10, 150, 230);
                add(scalableImg);
                System.out.println("Successfully added MovieScalableImage");
            } else {
                System.out.println("Image is null, cannot create MovieScalableImage");
                // Add a placeholder panel to show something
                JPanel placeholder = new JPanel();
                placeholder.setBackground(Color.LIGHT_GRAY);
                placeholder.setBounds(10, 10, 150, 230);
                add(placeholder);
            }
        } catch (Exception e) {
            System.out.println("Exception creating MovieScalableImage: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

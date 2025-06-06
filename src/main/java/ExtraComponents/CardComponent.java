/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExtraComponents;

import Modules.CardData;
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
    
    public CardComponent(CardData data) {
        setPreferredSize(new Dimension(185,240));
        setBackground(Color.GRAY);
        
        putClientProperty("FlatLaf.style", "arc: 15");
        
        initComponents(data);
    }
    
    private void initComponents(CardData data) {
        // Attempts to create an image for the card component
        try {
            BufferedImage bf = ImageIO.read(new File(data.getPath()));
            ScalableImagePanel scalableImg = new ScalableImagePanel(bf);
            scalableImg.setPreferredSize(new Dimension(data.getWidth(), data.getHeight()));
            scalableImg.setBounds(10, 10, 150, 230);
            add(scalableImg);
        } catch (Exception e) {
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExtraComponents;

import Modules.FontLoader;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author avery
 */
public class SideBarButton extends JPanel {
    boolean btnPressed = false;
    JLabel btnTitle;
    
    public SideBarButton(String path, int size, String text) {
        
        putClientProperty("FlatLaf.style", "arc: 15");
        
        handleIcon(path, size);
        
        setPanel();
        initComponents(text);
        
        revalidate();
        repaint();
    }
    
    private void setPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(230, 60));
        setBackground(null);
        setOpaque(false);
    }
    
    private void initComponents(String text) {
        FontLoader fontLoader = new FontLoader();
        btnTitle = new JLabel(text);
        btnTitle.setBounds(70, 20, 120, 20);
        btnTitle.setFont(fontLoader.loadHintFont(28f));
        btnTitle.setForeground(Color.WHITE);
        add(btnTitle);
        
        
    }
    
    private void handleIcon(String path, int size) {
        try {
            BufferedImage ticketIcon = ImageIO.read(new File(path));
            ScalableImagePanel scalingTicketIcon = new ScalableImagePanel(ticketIcon);
            scalingTicketIcon.setPreferredSize(new Dimension(size, size));
            scalingTicketIcon.setBounds(20, 10, size, size);
            add(scalingTicketIcon);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }
    
    
}

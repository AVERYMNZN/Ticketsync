package com.mycompany.oop.system;
import Modules.FontLoader;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author avery
 */
public class Tester extends JFrame{
    public static void main(String[] args) {
        new Tester();
    }
    
    public Tester() {
        initComponents();
        setFrame();
    }
    
    private void initComponents() {
        JLabel testLabel = new JLabel("Welcome to TicketSync");
        
        // Fix the bounds - place label at (50, 50) with reasonable size
        testLabel.setBounds(50, 50, 400, 50);
        
        // Center the text
        testLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Load the font
        FontLoader loader = new FontLoader();
        testLabel.setFont(loader.loadTitleFont(28f));
        testLabel.setForeground(Color.GRAY);
        
        // Add a background color to see the label bounds (optional - for debugging)
        testLabel.setOpaque(true);
        testLabel.setBackground(Color.LIGHT_GRAY);
        
        add(testLabel);
        
        // Add another label to test if fonts are working
//        JLabel testLabel2 = new JLabel("Second test label");
//        testLabel2.setBounds(50, 120, 400, 50);
//        testLabel2.setHorizontalAlignment(SwingConstants.CENTER);
//        testLabel2.setFont(loader.loadHintFont(16f));
//        testLabel2.setForeground(Color.BLACK);
//        testLabel2.setOpaque(true);
//        testLabel2.setBackground(Color.YELLOW);
        
//        add(testLabel2);
    }
    
    private void setFrame() {
        setSize(1000, 1000);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExtraComponents;

import Modules.FontLoader;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author avery
 */
public class BookMovieComponent extends JFrame{
    
    FontLoader fontLoader = new FontLoader();
    
    JLabel bookTicketLabel, ticketQuantityLabel, costQuantity;
    JTextField movieTitleField, ticketQuantityField;
    JComboBox ticketQuantityBox;
    JButton bookMovieBtn;
    
    private String title;
    
    
    public BookMovieComponent(String title) {
        
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
            UIManager.put("TextComponent.arc", 15);
            
            UIManager.put("Button.arc", 999);
            UIManager.put("Component.focusedBorderColor", new Color(201, 40, 89));
            UIManager.put("Component.focusColor", new Color(201, 40, 89, 80));
            UIManager.put("ScrollPane.arc", 10);
            UIManager.put("ScrollPane.roundRect", true);
            
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        this.title = title;
        
        setSize(350, 400);
        setTitle("Book Movie");
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        setVisible(true);
        
        initComponents();
        revalidate();
        repaint();
    }
    
    private void initComponents() {
        bookTicketLabel = new JLabel("Book Ticket: ");
        ticketQuantityLabel = new JLabel("Ticket Quantity");
        
        movieTitleField = new JTextField(title);
        
        Integer[] numbers = {1, 2, 3, 4, 5};
        ticketQuantityBox = new JComboBox<>(numbers);
        
        bookMovieBtn = new JButton("Book Movie");
        
        movieTitleField.setEnabled(false);
        
        bookTicketLabel.setFont(fontLoader.loadTitleFont(14f));
        ticketQuantityLabel.setFont(fontLoader.loadTitleFont(14f));
        
        bookTicketLabel.setForeground(new Color(201, 40, 89));
        ticketQuantityLabel.setForeground(new Color(201, 40, 89));
        
        bookMovieBtn.setBackground(new Color(201, 40, 89));
        bookMovieBtn.setForeground(Color.WHITE);
        
        bookTicketLabel.setBounds(20, 13, 100, 20);
        ticketQuantityLabel.setBounds(20, 60, 100, 20);
        
        movieTitleField.setBounds(135, 5, 180,35);
        
        ticketQuantityBox.setBounds(135, 55, 180, 35);
        
        bookMovieBtn.setBounds(115, 300, 100, 40);
        
        add(bookTicketLabel); add(ticketQuantityLabel);
        add(movieTitleField); add(ticketQuantityBox);
        
        add(bookMovieBtn);
    }
    
    // Getters and Setters
    
}

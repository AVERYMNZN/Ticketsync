/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oop.system;
import ExtraComponents.ImagePanel;
import Modules.SVGIconLoader;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.util.SystemInfo;
import Modules.FontLoader;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import Modules.StringManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
/**
 *
 * @author avery
 */
public class LoginFrame extends JFrame{
    
    private Point mouseClickPoint;
    private JPanel headerPanel; // Store reference to header panel
    
    private JLabel topHint, welcome, usernameLabel, passwordLabel;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
            UIManager.put("TextComponent.arc", 15);
            UIManager.put("Button.arc", 999);
            
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        new LoginFrame();
        
    }
    
    public LoginFrame() {
        setFrame();
        initComponents();
        setupWindowDragging();
        applyRoundedShape();
    }
    
    private void initComponents() {
        FontLoader fontLoader = new FontLoader();
        
        ImagePanel imgPanel = new ImagePanel("src/main/resources/newBgImg.jpg");
        topHint = new JLabel(StringManager.get("app.topHint"));
        welcome = new JLabel(StringManager.get("app.loginTo"));
        usernameLabel = new JLabel(StringManager.get("app.usernameLabel"));
        passwordLabel = new JLabel(StringManager.get("app.passwordLabel"));
        
        usernameTextField = new JTextField();
        passwordField = new JPasswordField();
        
        loginButton = new JButton(StringManager.get("app.loginLabel"));
        
        topHint.setFont(fontLoader.loadHintFont(14f));
        welcome.setFont(fontLoader.loadTitleFont(28f));
        usernameLabel.setFont(fontLoader.loadHintFont(14f));
        passwordLabel.setFont(fontLoader.loadHintFont(14f));
        loginButton.setFont(fontLoader.loadButtonFont(18f));
        
        topHint.setForeground(new Color(201, 40, 89));
        welcome.setForeground(new Color(201, 40, 89));
        usernameLabel.setForeground(new Color(201, 40, 89));
        passwordLabel.setForeground(new Color(201, 40, 89));
        
        loginButton.setBackground(new Color(201, 40, 89));
        loginButton.setForeground(Color.WHITE);
        
      
        imgPanel.setBounds(426,0, 425, 550);
        topHint.setBounds(30, 25, 100, 20);
        welcome.setBounds(90, 100, 250, 30);
        
        usernameLabel.setBounds(115, 160, 100, 20);
        usernameTextField.setBounds(110, 180, 215, 40);
        
        passwordLabel.setBounds(115, 240, 100, 20);
        passwordField.setBounds(110, 260, 215, 40);
        
        loginButton.setBounds(110, 370, 210, 45);
        
        usernameTextField.setBackground(new Color(235, 235, 235));
        passwordField.setBackground(new Color(235, 235, 235));
        
        
        add(imgPanel);
        add(topHint);
        add(welcome);
        add(usernameLabel); add(passwordLabel);
        add(usernameTextField); add(passwordField);
        add(loginButton);
        
        
        revalidate();
        repaint();
        
    }
    
    private void setFrame() {
        setUndecorated(true); // Remove title bar
        
        // Configure FlatLaf properties for rounded appearance
        if (SystemInfo.isMacOS) {
            getRootPane().putClientProperty("apple.awt.windowTitleVisible", false);
        }
        
        setSize(850, 550);
        setTitle("TicketSync");
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create draggable header area (top 40 pixels)
        headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 850, 40);
        headerPanel.setBackground(new Color(0, 0, 0, 0)); // Transparent
        headerPanel.setOpaque(false);
        add(headerPanel);
        
        
        
        setVisible(true);
    }
    
    private void setupWindowDragging() {
        // Make only the header panel (top 40 pixels) draggable
        headerPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseClickPoint = e.getPoint();
            }
        });
        
        headerPanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (mouseClickPoint != null) {
                    Point screenPoint = e.getLocationOnScreen();
                    setLocation(screenPoint.x - mouseClickPoint.x, 
                              screenPoint.y - mouseClickPoint.y);
                }
            }
        });
    }
    
    private void applyRoundedShape() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (isDisplayable()) {
                    setShape(new RoundRectangle2D.Double(
                        0, 0, getWidth(), getHeight(), 20, 20));
                }
            }
        });
    }
    
    // Override setSize to maintain rounded shape if you ever resize
    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        applyRoundedShape();
    }
}
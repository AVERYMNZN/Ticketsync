package com.mycompany.oop.system;
import ExtraComponents.SideBar;
import ExtraComponents.SideBarButton;
import Modules.FontLoader;
import Modules.StringManager;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.util.SystemInfo;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainPage extends JFrame{
    
    JLabel ticketSyncSidebarTitle;
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
            UIManager.put("TextComponent.arc", 15);
            UIManager.put("Button.arc", 999);
            
            
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        new MainPage();
    }
    
    public MainPage() {
        
        setFrame();
        initComponents();
        
        revalidate();
        repaint();
    }
    
    private void setFrame() {
        setUndecorated(true);
        if (SystemInfo.isMacOS) {
            getRootPane().putClientProperty("apple.awt.windowTitleVisible", false);
        }
        
        setSize(1200, 800);
        setTitle("TicketSync");
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set white background
        getContentPane().setBackground(Color.WHITE);
        
        
        setVisible(true);
    }
    
    private void initComponents() {
        FontLoader fontLoader = new FontLoader();
        
        SideBar sidebar = new SideBar("src/main/resources/blurry-gradient-haikei.png");
        
        SideBarButton moviesBtn = new SideBarButton("src/main/resources/icons8-ticket-100.png", 38, StringManager.get("button.movies"));
        SideBarButton ordersBtn = new SideBarButton("src/main/resources/icons8-order-100.png", 38, StringManager.get("button.orders")); 
        SideBarButton scheduleBtn = new SideBarButton("src/main/resources/icons8-schedule-100.png", 38, StringManager.get("button.schedule"));
        
        
        moviesBtn.setBounds(20, 200, 230, 60);
        ordersBtn.setBounds(20, 280, 230, 60);
        scheduleBtn.setBounds(20, 360, 230, 60);
        
        sidebar.setBounds(0, 0, 270, 800);
        
        sidebar.add(moviesBtn);
        sidebar.add(ordersBtn);
        sidebar.add(scheduleBtn);
        
        add(sidebar);
    }
    
    private void applyRoundedShape() {
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isDisplayable()) {
                    setShape(new RoundRectangle2D.Double(
                        0, 0, getWidth(), getHeight(), 20, 20));
                }
                ((Timer)e.getSource()).stop();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        applyRoundedShape();
    }
}
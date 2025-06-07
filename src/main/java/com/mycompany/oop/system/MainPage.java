package com.mycompany.oop.system;
import ExtraComponents.AddMovieComponent;
import ExtraComponents.SideBar;
import ExtraComponents.SideBarButton;
import Modules.FontLoader;
import Modules.StringManager;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.util.SystemInfo;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainPage extends JFrame{
    
    FontLoader fontLoader = new FontLoader();
    
    JLabel ticketSyncSidebarTitle;
    SideBarButton moviesBtn, ordersBtn, scheduleBtn;
    JPanel hostPanel, moviesPanel, ordersPanel, schedulePanel;
    CardLayout cardLayout;
    JButton searchButton, addMovieButton;
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
            UIManager.put("TextComponent.arc", 15);
            UIManager.put("Button.arc", 999);
            UIManager.put("Component.focusedBorderColor", new Color(201, 40, 89));
            UIManager.put("Component.focusColor", new Color(201, 40, 89, 80));
            
            
            
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
        
        //Side bar
        
        
        SideBar sidebar = new SideBar("src/main/resources/blurry-gradient-haikei.png");
        
        moviesBtn = new SideBarButton("src/main/resources/icons8-ticket-100.png", 38, StringManager.get("button.movies"));
        ordersBtn = new SideBarButton("src/main/resources/icons8-order-100.png", 38, StringManager.get("button.orders")); 
        scheduleBtn = new SideBarButton("src/main/resources/icons8-schedule-100.png", 38, StringManager.get("button.schedule"));


        moviesBtn.setBounds(20, 200, 230, 60);
        ordersBtn.setBounds(20, 280, 230, 60);
        scheduleBtn.setBounds(20, 360, 230, 60);
        
        sidebar.setBounds(0, 0, 270, 800);
        
        sidebar.add(moviesBtn);
        sidebar.add(ordersBtn);
        sidebar.add(scheduleBtn);
        
        add(sidebar);
        
        // Content page
        cardLayout = new CardLayout();
        hostPanel = new JPanel(cardLayout);
        
        moviesPanel = new JPanel();
        initMoviesPanelComponents();
        ordersPanel = new JPanel();
        schedulePanel = new JPanel();
        
        hostPanel.setBounds(270, 0, 930, 800);
        hostPanel.setBackground(new Color(240, 235, 236));
        
        moviesPanel.setBounds(0, 0, 930, 800);
        moviesPanel.setBackground(new Color(245, 245, 239 ));
        ordersPanel.setBounds(0, 0, 930, 800);
        ordersPanel.setBackground(Color.PINK);
        schedulePanel.setBounds(0, 0, 930, 800);
        schedulePanel.setBackground(Color.GREEN);
        
        hostPanel.add(moviesPanel, "moviesPanel"); 
        hostPanel.add(ordersPanel, "ordersPanel"); 
        hostPanel.add(schedulePanel, "schedulePanel");
        
        add(hostPanel);
        
        eventHandlers();
    }
    
    private void initMoviesPanelComponents() {
       moviesPanel.setLayout(null);
       
       JPanel controlPanel = new JPanel();
       JPanel cardsPanel = new JPanel();
       JTextField searchBar = new JTextField();
       searchButton = new JButton("Search");
       addMovieButton = new JButton("Add movie");
       
       controlPanel.setLayout(null);
       
       controlPanel.putClientProperty("FlatLaf.style", "arc: 15");
       cardsPanel.putClientProperty("FlatLaf.style", "arc: 15");
       searchBar.putClientProperty("FlatLaf.style", "arc: 20");
       
       searchButton.setFont(fontLoader.loadHintFont(14));
       searchButton.setBackground(new Color(201, 40, 89));
       searchButton.setForeground(Color.WHITE);
       
       addMovieButton.setFont(fontLoader.loadHintFont(14));
       addMovieButton.setBackground(new Color(201, 40, 89));
       addMovieButton.setForeground(Color.WHITE);       
       
       controlPanel.setBounds(20, 20, 880, 50);
//       controlPanel.setBackground(Color.BLUE);
       cardsPanel.setBounds(20, 80, 880, 710);
       
       searchBar.setBounds(5, 7, 300, 35);
       searchButton.setBounds(310, 8, 90, 33);
       
       addMovieButton.setBounds(760, 8, 110, 33);
       
       moviesPanel.add(controlPanel);
       moviesPanel.add(cardsPanel);
       
       controlPanel.add(searchBar);
       controlPanel.add(searchButton);
       controlPanel.add(addMovieButton);
    }
    
    private void eventHandlers() {
        moviesBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cardLayout.show(hostPanel, "moviesPanel");
                moviesBtn.setBackground(new Color(199, 32, 74));
            }
        });
        
        ordersBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cardLayout.show(hostPanel, "ordersPanel");
                ordersBtn.setBackground(new Color(199, 32, 74));
            }
        });
        
        scheduleBtn.addMouseListener(new MouseAdapter() {
           @Override
           public void mousePressed(MouseEvent e) {
               cardLayout.show(hostPanel, "schedulePanel");
               scheduleBtn.setBackground(new Color(199, 32, 74));
           }
        });
        
        moviesBtn.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseReleased(MouseEvent e) {
               moviesBtn.setBackground(null);
           }
        });
        
        ordersBtn.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseReleased(MouseEvent e) {
               ordersBtn.setBackground(null);
           }
        });
        
        scheduleBtn.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseReleased(MouseEvent e) {
               scheduleBtn.setBackground(null);
           }
        });
        
        addMovieButton.addActionListener(e -> {
            AddMovieComponent addMovieComponent = new AddMovieComponent();
        });
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
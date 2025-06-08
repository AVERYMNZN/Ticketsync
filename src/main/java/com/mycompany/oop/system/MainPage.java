package com.mycompany.oop.system;
import ExtraComponents.AddMovieComponent;
import ExtraComponents.BookMovieComponent;
import ExtraComponents.CardComponent;
import ExtraComponents.SideBar;
import ExtraComponents.SideBarButton;
import Modules.FontLoader;
import Modules.GridFSCardData;
import Modules.StringManager;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.util.SystemInfo;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.bson.Document;

public class MainPage extends JFrame{
    
    FontLoader fontLoader = new FontLoader();
    
    JLabel ticketSyncSidebarTitle;
    SideBarButton moviesBtn, ordersBtn, scheduleBtn;
    JPanel hostPanel, moviesPanel, ordersPanel, schedulePanel, cardsPanel;
    CardLayout cardLayout;
    JButton searchButton, addMovieButton, refreshButton;
    
    private List<CardComponent> displayedCards = new ArrayList<>();
    
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
       cardsPanel = new JPanel();
       JTextField searchBar = new JTextField();
       searchButton = new JButton("Search");
       refreshButton = new JButton("Refresh");
       addMovieButton = new JButton("Add movie");
       
       controlPanel.setLayout(null);
       
       cardsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
       
       controlPanel.putClientProperty("FlatLaf.style", "arc: 15");
       cardsPanel.putClientProperty("FlatLaf.style", "arc: 15");
       searchBar.putClientProperty("FlatLaf.style", "arc: 20");
       
       refreshButton.setFont(fontLoader.loadHintFont(14));
       refreshButton.setBackground(new Color(201, 40, 89));
       refreshButton.setForeground(Color.WHITE);
       
       searchButton.setFont(fontLoader.loadHintFont(14));
       searchButton.setBackground(new Color(201, 40, 89));
       searchButton.setForeground(Color.WHITE);
       
       addMovieButton.setFont(fontLoader.loadHintFont(14));
       addMovieButton.setBackground(new Color(201, 40, 89));
       addMovieButton.setForeground(Color.WHITE);    
       
       controlPanel.setBounds(20, 20, 880, 50);
       cardsPanel.setBounds(20, 80, 880, 710);
       
       searchBar.setBounds(5, 7, 300, 35);
       searchButton.setBounds(310, 8, 90, 33);
       
       refreshButton.setBounds(660, 8, 90, 33);
       
       addMovieButton.setBounds(760, 8, 110, 33);
       
       moviesPanel.add(controlPanel);
       moviesPanel.add(cardsPanel);
       
       controlPanel.add(searchBar);
       controlPanel.add(searchButton);
       controlPanel.add(refreshButton);
       controlPanel.add(addMovieButton);
       
       updateCardsDisplay(fetchCards());
       
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
        
        refreshButton.addActionListener(e -> {
            try (MongoClient client = MongoClients.create("mongodb://localhost:27017")) {

            // Access db
            MongoDatabase movieDatabase = client.getDatabase("MovieImages");

            // Retrieve GridFS bucket
            GridFSBucket gridFSBucket = GridFSBuckets.create(movieDatabase);

            List<GridFSCardData> cardDataList = new ArrayList<>();

            GridFSFindIterable gridFSFiles = gridFSBucket.find();

            for (GridFSFile gridFSFile : gridFSFiles) {
                try {
                    System.out.println("Processing file: " + gridFSFile.getFilename());
                    System.out.println("File size: " + gridFSFile.getLength() + " bytes");

                    // Get file metadata
                    Document metadata = gridFSFile.getMetadata();
                    String filename = gridFSFile.getFilename();
                    String contentType = metadata != null ? metadata.getString("contentType") : "image/jpeg";

                    // Extract custom metadata
                    String title = metadata != null ? metadata.getString("movieTitle") : filename;
                    String description = metadata != null ? metadata.getString("movieDescription") : "";

                    // Fix the movieCost extraction
                    Long movieCost = 0L;
                    if (metadata != null) {
                        Object costObj = metadata.get("movieCost");
                        if (costObj instanceof Number) {
                            movieCost = ((Number) costObj).longValue();
                        }
                    }

                    // Download the image data
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    gridFSBucket.downloadToStream(gridFSFile.getObjectId(), outputStream);

                    byte[] imageBytes = outputStream.toByteArray();
                    System.out.println("Downloaded " + imageBytes.length + " bytes");

                    // Convert byte array to BufferedImage
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                    BufferedImage image = ImageIO.read(inputStream);

                    if (image != null) {
                        System.out.println("Successfully created BufferedImage: " + image.getWidth() + "x" + image.getHeight());
                    } else {
                        System.out.println("Failed to create BufferedImage - ImageIO.read returned null");
                        // Try to determine why
                        System.out.println("Content type: " + contentType);
                        System.out.println("First few bytes: ");
                        for (int i = 0; i < Math.min(10, imageBytes.length); i++) {
                            System.out.print(String.format("%02X ", imageBytes[i]));
                        }
                        System.out.println();
                    }

                    // Create GridFSCardData object
                    GridFSCardData cardData = new GridFSCardData(
                        gridFSFile.getObjectId().toString(),
                        title,
                        description,
                        image, // This might be null
                        contentType,
                        movieCost
                    );

                    cardDataList.add(cardData);

                    // Close streams
                    outputStream.close();
                    inputStream.close();

                } catch (Exception ex) {
                    System.err.println("Error processing GridFS file: " + gridFSFile.getFilename());
                    ex.printStackTrace();
                }
            }

            // Update the cards display
            updateCardsDisplay(cardDataList);

            System.out.println("Loaded " + cardDataList.size() + " movies from database");

        } catch (Exception ex) {
            System.err.println("Error connecting to MongoDB or retrieving data:");
            ex.printStackTrace();
        }
        });
        
        addMovieButton.addActionListener(e -> {
            AddMovieComponent addMovieComponent = new AddMovieComponent();
            addMovieComponent.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    updateCardsDisplay(fetchCards());
                }
            });
        });
        
        for (CardComponent card : displayedCards) {
            card.getBookTicketBtn().addActionListener(e -> {
                BookMovieComponent bookMovieComponent = new BookMovieComponent();
                System.out.println("You Clicked Me");
                revalidate();
                repaint();
            });
        }
    }
    
    private List<GridFSCardData> fetchCards() {
        try (MongoClient client = MongoClients.create("mongodb://localhost:27017")){
            MongoDatabase movieDatabase = client.getDatabase("MovieImages");

            // Retrieve GridFS bucket
            GridFSBucket gridFSBucket = GridFSBuckets.create(movieDatabase);

            List<GridFSCardData> newCardsDataList = new ArrayList<>();

            GridFSFindIterable gridFSFiles = gridFSBucket.find();
            
            for (GridFSFile gridFSFile : gridFSFiles) {
                try {
                    System.out.println("Processing file: " + gridFSFile.getFilename());
                    System.out.println("File size: " + gridFSFile.getLength() + " bytes");

                    // Get file metadata
                    Document metadata = gridFSFile.getMetadata();
                    String filename = gridFSFile.getFilename();
                    String contentType = metadata != null ? metadata.getString("contentType") : "image/jpeg";

                    // Extract custom metadata
                    String title = metadata != null ? metadata.getString("movieTitle") : filename;
                    String description = metadata != null ? metadata.getString("movieDescription") : "";
                    System.out.println("description is " + description);

                    // Fix the movieCost extraction
                    Long movieCost = 0L;
                    if (metadata != null) {
                        Object costObj = metadata.get("movieCost");
                        if (costObj instanceof Number) {
                            movieCost = ((Number) costObj).longValue();
                        }
                    }

                    // Download the image data
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    gridFSBucket.downloadToStream(gridFSFile.getObjectId(), outputStream);

                    byte[] imageBytes = outputStream.toByteArray();
                    System.out.println("Downloaded " + imageBytes.length + " bytes");

                    // Convert byte array to BufferedImage
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                    BufferedImage image = ImageIO.read(inputStream);

                    if (image != null) {
                        System.out.println("Successfully created BufferedImage: " + image.getWidth() + "x" + image.getHeight());
                    } else {
                        System.out.println("Failed to create BufferedImage - ImageIO.read returned null");
                        // Try to determine why
                        System.out.println("Content type: " + contentType);
                        System.out.println("First few bytes: ");
                        for (int i = 0; i < Math.min(10, imageBytes.length); i++) {
                            System.out.print(String.format("%02X ", imageBytes[i]));
                        }
                        System.out.println();
                    }
                    
                    System.out.println("CURRENTLY CREATING " + title);

                    // Create GridFSCardData object
                    GridFSCardData cardData = new GridFSCardData(
                        gridFSFile.getObjectId().toString(),
                        title,
                        description,
                        image, // This might be null
                        contentType,
                        movieCost
                    );

                    newCardsDataList.add(cardData);

                    // Close streams
                    outputStream.close();
                    inputStream.close();

                } catch (Exception ex) {
                    System.err.println("Error processing GridFS file: " + gridFSFile.getFilename());
                    ex.printStackTrace();
                }
            }
            
            return newCardsDataList;
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        return null;
    }
    
    private void updateCardsDisplay(List<GridFSCardData> cardDataList) {
        // Clear existing components
        cardsPanel.removeAll();
        displayedCards.clear();

        // Set layout for cards with proper spacing
        cardsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));

        // Create CardComponents from GridFSCardData
        for (GridFSCardData cardData : cardDataList) {
            try {
                CardComponent cardComponent = new CardComponent(cardData);
                
                displayedCards.add(cardComponent);
                
                cardsPanel.add(cardComponent);
            } catch (Exception ex) {
                System.err.println("Error creating card component for: " + cardData.getTitle());
                ex.printStackTrace();
            }
        }

        // Refresh the display
        cardsPanel.revalidate();
        cardsPanel.repaint();

        System.out.println("Display updated with " + cardDataList.size() + " cards");
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


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExtraComponents;

import Modules.FontLoader;
import Modules.StringManager;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import java.awt.Color;
import java.time.LocalDateTime;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.bson.Document;

/**
 *
 * @author avery
 */
public class BookMovieComponent extends JFrame{
    
    FontLoader fontLoader = new FontLoader();
    
    JLabel bookTicketLabel, ticketQuantityLabel, movieCostLabel;
    JTextField movieTitleField, ticketCostField;
    JComboBox ticketQuantityBox;
    JButton bookMovieBtn;
    
    private String title;
    private long movieCost;
    
    private MongoClient mongoClient;
    private MongoDatabase database;
    private GridFSBucket gridFSBucket;
    
    public BookMovieComponent(String title, long movieCost) {
        
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
        this.movieCost = movieCost;
        
        setSize(350, 400);
        setTitle(StringManager.get("app.BookMovie"));
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
        
        bookTicketLabel = new JLabel(StringManager.get("app.BookTicket"));
        ticketQuantityLabel = new JLabel(StringManager.get("app.TicketQuantity"));
        movieCostLabel = new JLabel(StringManager.get("app.TicketCost"));
        
        movieTitleField = new JTextField(title);
        movieTitleField.setEnabled(false);
        
        ticketCostField = new JTextField();
        ticketCostField.setEnabled(false);
        
        Integer[] numbers = {1, 2, 3, 4, 5};
        ticketQuantityBox = new JComboBox<>(numbers);
        
        bookMovieBtn = new JButton(StringManager.get("app.BookMovie"));
        
        bookTicketLabel.setFont(fontLoader.loadTitleFont(14f));
        ticketQuantityLabel.setFont(fontLoader.loadTitleFont(14f));
        movieCostLabel.setFont(fontLoader.loadTitleFont(14f));
        
        bookTicketLabel.setForeground(new Color(201, 40, 89));
        ticketQuantityLabel.setForeground(new Color(201, 40, 89));
        movieCostLabel.setForeground(new Color(201, 40, 89));
        
        bookMovieBtn.setBackground(new Color(201, 40, 89));
        bookMovieBtn.setForeground(Color.WHITE);
        
        bookTicketLabel.setBounds(20, 13, 100, 20);
        ticketQuantityLabel.setBounds(20, 60, 120, 20);
        movieCostLabel.setBounds(20, 110, 100, 20);
        
        movieTitleField.setBounds(145, 5, 180,35);
        ticketQuantityBox.setBounds(145, 55, 180, 35);
        
        ticketCostField.setBounds(145, 100, 180, 35);
        
        bookMovieBtn.setBounds(115, 300, 100, 40);
        
        add(bookTicketLabel); add(ticketQuantityLabel); add(movieCostLabel);
        add(movieTitleField); add(ticketQuantityBox); add(ticketCostField);
        
        add(bookMovieBtn);
        
        ticketCostField.setText(String.valueOf((Integer) ticketQuantityBox.getSelectedItem() * movieCost));
        
        handleEvents();
    }
    
    // Getters and Setters
    
    private void handleEvents() {
        bookMovieBtn.addActionListener(e -> {
            String title = movieTitleField.getText();
            int quantity = (Integer) ticketQuantityBox.getSelectedItem();
            long totalCost = quantity * movieCost;
            LocalDateTime localTime = LocalDateTime.now();
            
            uploadToDb(title, quantity, totalCost, localTime);
            
        });
        
        ticketQuantityBox.addActionListener(e -> {
            int quantity = (Integer) ticketQuantityBox.getSelectedItem();
            long totalCost = quantity * movieCost;
            ticketCostField.setText(String.valueOf(totalCost));
        });
    }
    
    private void uploadToDb(String title, int quantity, long totalCost, LocalDateTime localTime) {
        try {
            Document doc = new Document()
                    .append("movieTitle", title)
                    .append("ticketQuantity", quantity)
                    .append("totalCost", totalCost)
                    .append("dateTime", localTime);
            
            GridFSUploadOptions options = new GridFSUploadOptions().metadata(doc);
            
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = mongoClient.getDatabase("MovieBookings"); // or another name
            database.getCollection("Orders").insertOne(doc);
            
            JOptionPane.showMessageDialog(this, "Booking successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

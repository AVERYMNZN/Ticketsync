/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExtraComponents;

import Modules.FontLoader;
import Modules.OrderData;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author avery
 */
public class OrderCardComponent extends JPanel {
    
    FontLoader fontLoader = new FontLoader();
    
    private OrderData data;
    
    private MongoClient mongoClient;
    private MongoDatabase database;
    private GridFSBucket gridFSBucket;
    
    JLabel title, ticketQuantity, totalCost, dateTime;
    
    public OrderCardComponent(OrderData data) {
        this.data = data;
        setPreferredSize(new Dimension(400, 185));
        setLayout(null); // Use absolute positioning for precise control
        
        putClientProperty("FlatLaf.style", "arc: 15");
        
        initComponents();
        revalidate();
        repaint();
        
    }
    
    private void initComponents() {
        title = new JLabel("Title: " + data.getMovieTitle());
        ticketQuantity = new JLabel("Ticket Quantity: " + data.getTicketQuantity());
        totalCost = new JLabel("Total: " + data.getTotalCost());
        dateTime = new JLabel("Date: " + data.getLocalDateTime());
        
        title.setFont(fontLoader.loadTitleFont(28f));
        ticketQuantity.setFont(fontLoader.loadTextFont(20f));
        totalCost.setFont(fontLoader.loadTextFont(20f));
        dateTime.setFont(fontLoader.loadTextFont(20f));
        
        title.setBounds(20, 20, 300, 25);
        ticketQuantity.setBounds(20, 60, 200, 20);
        totalCost.setBounds(20, 90, 200, 20);
        dateTime.setBounds(20, 120, 300, 20);
        
        add(title);
        add(ticketQuantity);
        add(totalCost);
        add(dateTime);
    }
    
}

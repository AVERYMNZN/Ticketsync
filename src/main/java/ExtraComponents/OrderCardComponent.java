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
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
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
    
    private final Color borderColor = new Color(201, 40, 89);
    private final int arc = 15;
    
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
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        float strokeWidth = 1f;
        float arcF = arc;
        float offset = strokeWidth / 2f;

        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(strokeWidth));

        // Use a precise floating-point rounded rectangle
        g2.draw(new RoundRectangle2D.Float(
            offset,
            offset,
            getWidth() - strokeWidth,
            getHeight() - strokeWidth,
            arcF,
            arcF
        ));

        g2.dispose();
    }
    
}

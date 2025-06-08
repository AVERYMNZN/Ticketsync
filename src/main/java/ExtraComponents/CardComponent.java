/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExtraComponents;

import Modules.FontLoader;
import Modules.GridFSCardData;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author avery
 */
public class CardComponent extends JPanel{
    
    private GridFSCardData data;
    
    JLabel movieTitleLabel, movieDescriptionLabel, movieCostLabel;
    FontLoader fontLoader = new FontLoader();
    private final Color borderColor = new Color(201, 40, 89);
    private final int arc = 15;
    private JButton bookTicketBtn, editCardBtn, delCardBtn;
    
    public CardComponent(GridFSCardData data) {
        this.data = data;
        setPreferredSize(new Dimension(185,240));
//        setBackground(new Color(204, 196, 188));
        setLayout(null); // Use absolute positioning for precise control
        
        putClientProperty("FlatLaf.style", "arc: 15");
//        setBorder(new LineBorder(Color.GRAY, 1));
        
        initComponents();
        revalidate();
        repaint();
    }
    
    private void initComponents() {
        // Debug: Check if data and image are not null
        System.out.println("Creating CardComponent for: " + data.getTitle());
        System.out.println("Image is null: " + (data.getImage() == null));
        
        if (data.getImage() != null) {
            System.out.println("Image dimensions: " + data.getImage().getWidth() + "x" + data.getImage().getHeight());
        }
        
        // Attempts to create an image for the card component
        try {
            BufferedImage image = data.getImage();
            
            if (image != null) {
                // Create a landscape-oriented image component
                // Using 16:9 aspect ratio for a more rectangular, landscape look
                MovieScalableImage scalableImg = new MovieScalableImage(image, 16.0 / 9.0);
                
                
                // Position the image at the top of the card with some padding
                // Leave space at the bottom for text (about 60-80 pixels)
                scalableImg.setBounds(10, 10, 165, 93); // Width: 165, Height: 93 (16:9 ratio)
                add(scalableImg);
                System.out.println("Successfully added MovieScalableImage with landscape aspect ratio");
                
                // Alternative: If you want a different rectangular ratio, you can use:
                // scalableImg = new MovieScalableImage(image, 3.0 / 2.0); // 3:2 ratio
                // scalableImg.setBounds(10, 10, 165, 110); // Adjust height accordingly
                
            } else {
                System.out.println("Image is null, cannot create MovieScalableImage");
                // Add a placeholder panel to show something
                JPanel placeholder = new JPanel();
                placeholder.setBackground(Color.LIGHT_GRAY);
                placeholder.setBounds(10, 10, 165, 93); // Same dimensions as the image
                add(placeholder);
            }
        } catch (Exception e) {
            System.out.println("Exception creating MovieScalableImage: " + e.getMessage());
            e.printStackTrace();
        }

        String titleToUse = data.getTitle().length() > 20 ? data.getTitle().substring(0, 20) + "..." : data.getTitle();
        movieTitleLabel = new JLabel(titleToUse);
        movieCostLabel = new JLabel("₱" + String.valueOf(data.getMovieCost()));
        movieDescriptionLabel = new JLabel("<html><p style='overflow-wrap: break-word; word-wrap: break-word;'>"
                                       + data.getDescription().trim()
                                       + "</p></html>");
        
        bookTicketBtn = new JButton("Book");
        editCardBtn = new JButton("Edit");
        delCardBtn = new JButton("Del");
        
        
        movieTitleLabel.setFont(fontLoader.loadTextFontBold(14f));
        movieDescriptionLabel.setFont(fontLoader.loadTextFont(12f));
        movieCostLabel.setFont(fontLoader.loadTextFontBold(16f));
        
        for (JButton btn : new JButton[] {bookTicketBtn, editCardBtn, delCardBtn}) {
            btn.setFont(fontLoader.loadButtonFont(14f));
            btn.setBackground(new Color(201, 40, 89));
            btn.setForeground(Color.WHITE);
        }
        
        movieTitleLabel.setBounds(15, 110, 200, 30);
        movieDescriptionLabel.setBounds(15, 130, 150, 70);
        movieCostLabel.setBounds(130, 130, 100, 30);
        
        bookTicketBtn.setBounds(10, 172, 80, 55);
        editCardBtn.setBounds(95, 170, 80, 30);
        delCardBtn.setBounds(95, 200, 80, 30);
        
        add(movieTitleLabel);
        add(movieCostLabel);
        
        add(bookTicketBtn);
        add(editCardBtn);
        add(delCardBtn);
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
    
    public JButton getBookTicketBtn() {
        return bookTicketBtn;
    }
    
    public JButton getEditCardBtn() {
        return editCardBtn;
    }
    
    public JButton getDelCardBtn() {
        return delCardBtn;
    }
}

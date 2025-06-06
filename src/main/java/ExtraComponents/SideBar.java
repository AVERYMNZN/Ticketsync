package ExtraComponents;
import Modules.FontLoader;
import ExtraComponents.ImagePanel;
import ExtraComponents.ScalableImagePanel;
import Modules.StringManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SideBar extends ImagePanel{
    
    JLabel ticketSyncTitle;
    ImagePanel ticketIcon;
    
    public SideBar(String path) {
        super(path);
        setLayout(null);
        setPreferredSize(new Dimension(250, 800));
        setBackground(new Color(201, 40, 89));
        
        initComponents();
    }
    
    private void initComponents() {
        //Handles image icon for ticket while catching exceptions
        try {
            BufferedImage ticketIcon = ImageIO.read(new File("src/main/resources/tickets.png"));
            ScalableImagePanel scalingTicketIcon = new ScalableImagePanel(ticketIcon);
            scalingTicketIcon.setPreferredSize(new Dimension(48, 48));
            scalingTicketIcon.setBounds(30, 40, 48, 48);
            add(scalingTicketIcon);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
        
        FontLoader fontLoader = new FontLoader();
        
        // declare components
        ticketSyncTitle = new JLabel(StringManager.get("app.topHint"));
        ticketSyncTitle.setFont(fontLoader.loadTitleFont(30f));
        ticketSyncTitle.setForeground(Color.WHITE);
            
        // Set the bounds
        ticketSyncTitle.setBounds(85, 50, 150, 30);
             
        //Add components 
        add(ticketSyncTitle);
        
        
        revalidate();
        repaint();
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExtraComponents;

import Modules.FontLoader;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import org.bson.Document;

/**
 *
 * @author avery
 */
public class EditCardComponent extends JFrame{
    FontLoader fontLoader = new FontLoader();
    
    JButton addImageBtn, confirmBtn;
    JLabel fileToUseLabel, movieTitleLabel, movieCostLabel, movieDescriptionLabel;
    JTextField movieTitleField, movieCostField;
    JTextArea descriptionArea;
    JScrollPane descriptionScrollPane;
    JFileChooser chooser;
    FileInputStream inputStream;
    
    String title, description;
    BufferedImage image;
    long movieCost;
    
    public EditCardComponent(String title, String description, BufferedImage image, long movieCost) {
        
        this.title = title;
        this.description = description;
        this.image = image;
        this.movieCost = movieCost;
        
        setSize(350, 400);
        setTitle("Edit Card");
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
        addImageBtn = new JButton("Add Image"); 
        confirmBtn = new JButton("Confirm");
        
//        fileToUseLabel = new JLabel("File Selected: ");
        movieTitleLabel = new JLabel("Movie Title");
        movieCostLabel = new JLabel("Price");
        movieDescriptionLabel = new JLabel("Description");
        
        movieTitleField = new JTextField(title);
        movieCostField = new JTextField(String.valueOf(movieCost));
        
        descriptionArea = new JTextArea(description);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        
        
        descriptionScrollPane = new JScrollPane(descriptionArea);
        descriptionScrollPane.setPreferredSize(new Dimension(200, 200));
        descriptionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        

//        fileToUseLabel.setFont(fontLoader.loadHintFont(14f));
        movieTitleLabel.setFont(fontLoader.loadTitleFont(14f));
        movieCostLabel.setFont(fontLoader.loadTitleFont(14f));
        movieDescriptionLabel.setFont(fontLoader.loadTitleFont(14f));
        
        addImageBtn.setBackground(new Color(201, 40, 89));
        addImageBtn.setForeground(Color.WHITE);
        
        confirmBtn.setBackground(new Color(201, 40, 89));
        confirmBtn.setForeground(Color.WHITE);
        
        addImageBtn.setBounds(230, 20, 100, 30);
        confirmBtn.setBounds(115, 300, 100, 40);
        
//        fileToUseLabel.setBounds(115, 35, 200, 20);
        
        movieTitleLabel.setBounds(20, 5, 100, 20);
        movieCostLabel.setBounds(20, 60, 100, 20);
        movieDescriptionLabel.setBounds(20, 115, 100, 20);
        
        movieTitleField.setBounds(15, 25, 200, 30);
        movieCostField.setBounds(15, 80, 200, 30);
        
        descriptionScrollPane.setBounds(15, 135, 200, 100);
        
        add(addImageBtn); add(confirmBtn);
        add(movieTitleLabel); add(movieCostLabel); add(movieDescriptionLabel);
        add(movieTitleField); add(movieCostField);
        
        add(descriptionScrollPane);
        
        storeImage();
    }
    
    private void storeImage() {

        JLabel previewLabel = new JLabel("Preview");
        previewLabel.setBounds(255, 60, 100, 20);
        previewLabel.setFont(fontLoader.loadTitleFont(14f));
        try {
            
            ScalableImagePanel scalingPreview = new ScalableImagePanel(image);
            scalingPreview.setPreferredSize(new Dimension(50, 50));
            scalingPreview.setBounds(230, 90, 100, 100);
            add(previewLabel);
            add(scalingPreview);
            revalidate();
            repaint();
        } catch (Exception e) {
        }
        
    }
    
    private void updateToDb() {
        try (MongoClient client = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = client.getDatabase("MovieImages");
            GridFSBucket gridFSBucket = GridFSBuckets.create(database);
            
            GridFSFindIterable files = gridFSBucket.find(new Document("metadata.movieTitle", title));
            
            GridFSFile gridFSFile = files.first();
            
            if (gridFSFile == null) {
                System.out.println("File not found!");
                return;
            }
            
            Document metadata = gridFSFile.getMetadata();
            System.out.println("Current metadata" + metadata);
            
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

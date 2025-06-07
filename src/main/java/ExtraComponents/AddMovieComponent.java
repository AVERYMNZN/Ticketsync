/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExtraComponents;

import Modules.FontLoader;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author avery
 */
public class AddMovieComponent extends JFrame {
    
    FontLoader fontLoader = new FontLoader();
    File selectedFile, savedFile;
    JButton addImageBtn, confirmBtn;
    JLabel fileToUseLabel, movieTitleLabel, movieCostLabel, movieDescriptionLabel;
    JTextField movieTitleField, movieCostField;
    JTextArea descriptionArea;
    JScrollPane descriptionScrollPane;
    JFileChooser chooser;
    FileInputStream inputStream;
    
    
    private MongoClient mongoClient;
    private MongoDatabase database;
    private GridFSBucket gridFSBucket;
    
    public static void main(String[] args) {
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
        
        SwingUtilities.invokeLater(() -> {
            new AddMovieComponent();
        });
    }
    
    public AddMovieComponent() {
        initializeMongoConnection();
        
        setSize(350, 400);
        setTitle("Add Image");
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
        
        movieTitleField = new JTextField();
        movieCostField = new JTextField();
        
        descriptionArea = new JTextArea();
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
        handleEvents();
    }
    
    private void handleEvents() {
        addImageBtn.addActionListener(e -> {
           
           chooser = new JFileChooser();
           chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Image files", "jpg", "jpeg", "png", "gif"));
           
           
           int response = chooser.showOpenDialog(this);
           
           if (response == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                storeImage(selectedFile);
            }
        }); 
        
        confirmBtn.addActionListener(e -> {
        try {
            // Validate inputs
            String title = movieTitleField.getText().trim();
            String description = descriptionArea.getText().trim();
            String priceText = movieCostField.getText().trim();
            
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a movie title!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (priceText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a price!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (savedFile == null) {
                JOptionPane.showMessageDialog(this, "Please select an image first!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double price = Double.parseDouble(priceText);
            uploadToDB(savedFile, title, description, price);
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid price!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    });
    }
    
    private void storeImage(File imageFile) {
        try {
            if (!imageFile.exists()) {
                JOptionPane.showMessageDialog(this, "Selected file does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!isImageFile(imageFile.getName())) {
                JOptionPane.showMessageDialog(this, "Please select a valid image file!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            savedFile = imageFile;
//            fileToUseLabel.setText("File Selected: " + imageFile.getName());
            System.out.println("saved " + savedFile.getName());
            
            JLabel previewLabel = new JLabel("Preview");
            previewLabel.setBounds(255, 60, 100, 20);
            previewLabel.setFont(fontLoader.loadTitleFont(14f));
            try {
                BufferedImage preview = ImageIO.read(savedFile);
                ScalableImagePanel scalingPreview = new ScalableImagePanel(preview);
                scalingPreview.setPreferredSize(new Dimension(50, 50));
                scalingPreview.setBounds(230, 90, 100, 100);
                add(previewLabel);
                add(scalingPreview);
                revalidate();
                repaint();
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
    }
    
    private void uploadToDB(File imageFile, String title, String description, double cost) {
        try {   
            inputStream = new FileInputStream(imageFile);

            // Create metadata
            Document metadata = new Document()
                    .append("contentType", getContentType(imageFile.getName()))
                    .append("fileSize", imageFile.length())
                    .append("uploadDate", new java.util.Date())
                    .append("originalName", imageFile.getName())
                    .append("filePath", imageFile.getAbsolutePath())
                    .append("movieTitle", title)
                    .append("movieDescription", description)
                    .append("movieCost", cost);

            // Create upload options
            GridFSUploadOptions options = new GridFSUploadOptions().metadata(metadata);

            // Upload the file
            ObjectId fileId = gridFSBucket.uploadFromStream(
                imageFile.getName(),
                inputStream,
                options
            );

            // Success message
            String successMessage = String.format(
                "Movie uploaded successfully!\n\nTitle: %s\nFile: %s\nSize: %s bytes\nFile ID: %s",
                title,
                imageFile.getName(),
                imageFile.length(),
                fileId.toString()
            );

            JOptionPane.showMessageDialog(this, successMessage, "Success", JOptionPane.INFORMATION_MESSAGE);

            // Optionally clear the form
            movieTitleField.setText("");
            movieCostField.setText("");
            descriptionArea.setText("");

        } catch (Exception e) {
            String errorMessage = "Upload failed: " + e.getMessage();
            JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println(errorMessage);
            e.printStackTrace();
        } finally {
            // Close the input stream
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
     
     private boolean isImageFile(String fileName) {
        String[] imageExtensions = {".jpg", ".jpeg", ".png", ".gif"};
        String lowerFileName = fileName.toLowerCase();
        
        for (String ext : imageExtensions) {
            if (lowerFileName.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }
     
     private String getContentType(String fileName) {
        String lowerFileName = fileName.toLowerCase();
        
        if (lowerFileName.endsWith(".jpg") || lowerFileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (lowerFileName.endsWith(".png")) {
            return "image/png";
        } else if (lowerFileName.endsWith(".gif")) {
            return "image/gif";
        }
        return "application/octet-stream";
    }
     
    private void initializeMongoConnection() {
        try {
            this.mongoClient = MongoClients.create("mongodb://localhost:27017");
            this.database = mongoClient.getDatabase("MovieImages");
            this.gridFSBucket = GridFSBuckets.create(database);
            System.out.println("Connected to MongoDB successfully!");
        } catch (Exception e) {
            System.err.println("Failed to connect to MongoDB: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Close db connection
    public void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("MongoDB connection closed.");
        }
    }
}

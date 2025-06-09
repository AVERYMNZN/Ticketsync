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
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
public class EditCardComponent extends JFrame{
    FontLoader fontLoader = new FontLoader();
    
    JButton addImageBtn, confirmBtn;
    JLabel fileToUseLabel, movieTitleLabel, movieCostLabel, movieDescriptionLabel;
    JTextField movieTitleField, movieCostField;
    JTextArea descriptionArea;
    JScrollPane descriptionScrollPane;
    JFileChooser chooser;
    FileInputStream inputStream;
    
    String originalTitle, title, description;
    BufferedImage image;
    long movieCost;
    File selectedFile, savedFile;
    boolean imageChanged = false;
    
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
        
        
    }
    
    public EditCardComponent(String title, String description, BufferedImage image, long movieCost) {
        
        this.originalTitle = title; // Store original title for database lookup
        this.title = title;
        this.description = description;
        this.image = image;
        this.movieCost = movieCost;
        
        initializeMongoConnection();
        
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
        addImageBtn = new JButton("Change Image"); 
        confirmBtn = new JButton("Update Movie");
        
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
        
        movieTitleLabel.setFont(fontLoader.loadTitleFont(14f));
        movieCostLabel.setFont(fontLoader.loadTitleFont(14f));
        movieDescriptionLabel.setFont(fontLoader.loadTitleFont(14f));
        
        addImageBtn.setBackground(new Color(201, 40, 89));
        addImageBtn.setForeground(Color.WHITE);
        
        confirmBtn.setBackground(new Color(201, 40, 89));
        confirmBtn.setForeground(Color.WHITE);
        
        addImageBtn.setBounds(230, 20, 100, 30);
        confirmBtn.setBounds(100, 300, 150, 40);
        
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
                 updateImagePreview(selectedFile);
                 imageChanged = true;
             }
        });
        
        confirmBtn.addActionListener(e -> {
            try {
                // Validate inputs
                String newTitle = movieTitleField.getText().trim();
                String newDescription = descriptionArea.getText().trim();
                String cleanedDescription = newDescription.replaceAll("\\s+", " ");
                String priceText = movieCostField.getText().trim();
                
                if (newTitle.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a movie title!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (priceText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a price!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                double newPrice = Double.parseDouble(priceText);
                updateToDb(newTitle, cleanedDescription, newPrice);
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid price!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
    }
    
    private void updateImagePreview(File imageFile) {
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
            
            // Remove existing preview components
            getContentPane().removeAll();
            initComponents(); // Re-initialize all components
            
            // Add new preview
            JLabel previewLabel = new JLabel("New Preview");
            previewLabel.setBounds(255, 60, 100, 20);
            previewLabel.setFont(fontLoader.loadTitleFont(14f));
            
            BufferedImage preview = ImageIO.read(savedFile);
            ScalableImagePanel scalingPreview = new ScalableImagePanel(preview);
            scalingPreview.setPreferredSize(new Dimension(50, 50));
            scalingPreview.setBounds(230, 90, 100, 100);
            add(previewLabel);
            add(scalingPreview);
            
            revalidate();
            repaint();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading image preview: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void storeImage() {
        if (image == null) return;
        
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
            e.printStackTrace();
        }
    }
    
    private void updateToDb(String newTitle, String newDescription, double newCost) {
        try {
            // Find the existing file by original title
            GridFSFindIterable files = gridFSBucket.find(new Document("metadata.movieTitle", originalTitle));
            GridFSFile gridFSFile = files.first();
            
            if (gridFSFile == null) {
                JOptionPane.showMessageDialog(this, "Original movie not found in database!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Get the old file's ObjectId and data
            ObjectId oldFileId = gridFSFile.getObjectId();
            
            // Determine which image to use
            BufferedImage imageToUse;
            String fileName;
            String contentType;
            
            if (imageChanged && savedFile != null) {
                // Use new image
                imageToUse = ImageIO.read(savedFile);
                fileName = savedFile.getName();
                contentType = getContentType(savedFile.getName());
            } else {
                // Use existing image
                imageToUse = this.image;
                fileName = gridFSFile.getFilename();
                Document oldMetadata = gridFSFile.getMetadata();
                contentType = oldMetadata != null ? oldMetadata.getString("contentType") : "image/jpeg";
            }
            
            // Create new metadata with updated values
            Document newMetadata = new Document()
                    .append("contentType", contentType)
                    .append("uploadDate", new java.util.Date())
                    .append("originalName", fileName)
                    .append("movieTitle", newTitle)
                    .append("movieDescription", newDescription)
                    .append("movieCost", newCost);
            
            // Convert BufferedImage to InputStream
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String format = contentType.equals("image/png") ? "png" : "jpg";
            ImageIO.write(imageToUse, format, baos);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            
            // Add file size to metadata
            newMetadata.append("fileSize", baos.size());
            
            // Create upload options with new metadata
            GridFSUploadOptions options = new GridFSUploadOptions().metadata(newMetadata);
            
            // Upload the new file
            ObjectId newFileId = gridFSBucket.uploadFromStream(
                fileName,
                bais,
                options
            );
            
            // Delete the old file
            gridFSBucket.delete(oldFileId);
            
            // Success message
            String successMessage = String.format(
                "Movie updated successfully!\n\nTitle: %s\nDescription: %s\nPrice: $%.2f\nFile ID: %s",
                newTitle,
                newDescription,
                newCost,
                newFileId.toString()
            );
            
            JOptionPane.showMessageDialog(this, successMessage, "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Close streams
            baos.close();
            bais.close();
            
            // Close the dialog
            this.dispose();
            
        } catch (Exception e) {
            String errorMessage = "Update failed: " + e.getMessage();
            JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println(errorMessage);
            e.printStackTrace();
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
    
    @Override
    public void dispose() {
        closeConnection();
        super.dispose();
    }
}
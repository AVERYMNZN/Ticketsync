/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modules;

import java.awt.image.BufferedImage;

public class GridFSCardData {
    private String id;
    private String title;
    private String description;
    private BufferedImage image;
    private String contentType;
    private long movieCost;
    
    public GridFSCardData(String id, String title, String description, BufferedImage image, 
                          String contentType, long movieCost) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.contentType = contentType;
        this.movieCost = movieCost;
    }
    
    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public BufferedImage getImage() { return image; }
    public String getContentType() { return contentType; }
    public long getMovieCost() { return movieCost; }
}
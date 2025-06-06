/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modules;

/**
 *
 * @author avery
 */
public class CardData {
    private String path, title, description;
    private int width, height;

    public CardData(String path, int width, int height, String title, String description) {
        this.path = path; this.title = title; this.description = description;
        this.width = width; this.height = height;

    }
    
    public String getPath() {
        return path;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
}

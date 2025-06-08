/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modules;

import java.time.LocalDateTime;

/**
 *
 * @author avery
 */
public class OrderData {
    
    private String movieTitle;
    private int ticketQuantity;
    private long totalCost;
    private String dateTime;
    
    public OrderData(String movieTitle, int ticketQuantity, long totalCost, String dateTime) {
       this.movieTitle = movieTitle;
       this.ticketQuantity = ticketQuantity;
       this.totalCost = totalCost;
       this.dateTime = dateTime;
    }
    
    public String getMovieTitle() {
        return movieTitle;
    }
    
    public int getTicketQuantity() {
        return ticketQuantity;
    }
    
    public long getTotalCost() {
        return totalCost;
    }
    
    public String getLocalDateTime() {
        return dateTime;
    }
}

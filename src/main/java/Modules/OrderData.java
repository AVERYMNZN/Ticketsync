/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modules;

/**
 *
 * @author avery
 */
public class OrderData {
    
    private String movieTitle;
    private int ticketQuantity;
    
    public OrderData(String movieTitle, int ticketQuantity) {
       this.movieTitle = movieTitle;
       this.ticketQuantity = ticketQuantity;
    }
    
    public String getMovieTitle() {
        return movieTitle;
    }
    
    public int getTicketQuantity() {
        return ticketQuantity;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modules;

/**
 *
 * @author avery
 */
public class ValidationDataObject {

    
    private String text;
    private boolean truthy;
    
    
    public ValidationDataObject() {
        
    }
    
    public ValidationDataObject(String text, boolean truthy) {
        this.text=text;
        this.truthy=truthy;
    }
    
    public String getText() {
        return text;
    }

    public boolean isTruthy() {
        return truthy;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTruthy(boolean truthy) {
        this.truthy = truthy;
    }
    
}

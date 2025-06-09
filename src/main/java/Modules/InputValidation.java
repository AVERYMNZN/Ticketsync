package Modules;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import javax.swing.JTextField;

/**
 *
 * @author avery
 */
public class InputValidation {
    
    public static ValidationDataObject validateName(JTextField field) {
        String fieldName = field.getName().trim();
        String fieldText = field.getText().trim();
        
        if (fieldText.isEmpty()) {
            return new ValidationDataObject("Name cannot be empty.", false);
        } 
        else if (checkForNumber(fieldText)) {
            return new ValidationDataObject("Name cannot contain numbers", false);
        } 
        else if (checkForInvalidChars(fieldText)) {
            return new ValidationDataObject("Name cannot contain invalid characters", false);
        } 
        else if (fieldText.length() < 2) {
            return new ValidationDataObject("Name is too short", false);
        }
        else if (fieldText.length() > 50) {
            return new ValidationDataObject("Name is too long", false);
        } else if (checkForRepeatedChars(fieldText)) {
            return new ValidationDataObject("Name contains repeated characters", false);
        }
        
        return new ValidationDataObject("Name is valid", true);
    }
    
    public static ValidationDataObject validateAge(JTextField field) {
        String fieldName = field.getName().trim();
        String fieldText = field.getText().trim();
        int age;
        
        if (fieldText.isEmpty()) {
            return new ValidationDataObject("Age cannot be empty", false);
        }
        
        try {
            age = Integer.parseInt(fieldText);
        } catch (NumberFormatException e) {
            return new ValidationDataObject("Age must be a valid number", false);
        }
        
        if (age < 0) {
            return new ValidationDataObject("Age must not be negative", false);
        } else if (age < 17) {
            return new ValidationDataObject("Age must be above 17", false);
        } else if (age > 120) {
            return new ValidationDataObject("Age is too large", false);
        }
 
        return new ValidationDataObject("Age is valid.", true);
    }
    
    
    
    public static ValidationDataObject validateHoursWorked(JTextField field) {
        String fieldName = field.getName().trim();
        String fieldText = field.getText().trim();
        
        if (fieldText.isEmpty()) {
            return new ValidationDataObject("Hours worked must not be empty", false);
        }
        
        try {
            Double hoursWorked = Double.parseDouble(fieldText);
            
            if (hoursWorked <= 0) {
                return new ValidationDataObject("Hours worked must be a positive number", false);
            }
            
        } catch (NumberFormatException e) {
            return new ValidationDataObject("Invalid input for hours worked", false);
        }
        return new ValidationDataObject("Hours worked is valid", true);
    }
    
    public static ValidationDataObject validateRatePerHour(JTextField field) {
         String fieldText = field.getText().trim();

    if (fieldText.isEmpty()) {
        return new ValidationDataObject("Rate per hour cannot be empty", false);
    }

    try {
        double rate = Double.parseDouble(fieldText);
        if (rate <= 0) {
            return new ValidationDataObject("Rate must be a positive number", false);
        }
        if (rate > 10000) {
            return new ValidationDataObject("Rate is unrealistically high", false);
        }
    } catch (NumberFormatException e) {
        return new ValidationDataObject("Rate must be a valid number", false);
    }

    return new ValidationDataObject("Rate is valid", true);
    }
    
    public static boolean checkForNumber(String text) {
        return text.matches(".*\\d.*");
    }
    
    public static boolean checkForInvalidChars(String text) {
        return text.matches(".*[^a-zA-Z\\s'\\-].*");
//        return text.matches(".*[^a-zA-Z\\s].*");
    }
    
    public static boolean checkForRepeatedChars(String text) {
        return text.matches(".*(['\\-])\\1.*");
    }
}
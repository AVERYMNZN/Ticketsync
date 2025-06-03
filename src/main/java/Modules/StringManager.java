/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modules;

import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author avery
 */
public class StringManager {
    private static final Properties props = new Properties();
    
    static {
        try(InputStream input = StringManager.class.getClassLoader().getResourceAsStream("strings.properties")) {
            if (input != null) {
                props.load(input);
            } else {
                System.err.println("Could not find strings.properties in classpath");
            }
        } catch (Exception e) {
            
        }
    }
    
    public static String get(String key) {
        return props.getProperty(key, "!" + key + "!");
    }
}

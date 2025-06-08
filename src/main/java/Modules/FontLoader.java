/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modules;

import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author avery
 */
public class FontLoader {
    private Font loadFont(String path, float size, int style) {
        try (InputStream fontStream = getClass().getClassLoader().getResourceAsStream(path)) {
            if (fontStream == null) {
                throw new IOException("Font not found: " + path);
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(style, size);
            System.out.println("Loaded font: " + font.getFontName() + " size " + font.getSize2D());
            return font;
            
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.PLAIN, (int) size);
        }
    }
    
    public Font loadHintFont(float size) {
        return loadFont("fonts/Qanelas-Medium.ttf", size, Font.PLAIN);
    }

    public Font loadTitleFont(float size) {
        
        return loadFont("fonts/Qanelas-ExtraBold.otf", size, Font.PLAIN);
    }
    
    public Font loadButtonFont(float size) {
        return loadFont("fonts/Qanelas-Bold.ttf", size, Font.PLAIN);
    }
    
    public Font loadTextFont(float size) {
        return loadFont("fonts/Roboto.ttf", size, Font.PLAIN);
    }

    public Font loadTextFontBold(float size) {
        return loadFont("fonts/Roboto.ttf", size, Font.BOLD);
    }
    
    public Font loadCustomFont(String path, float size) {
        return loadFont(path, size, Font.PLAIN);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modules;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.swing.*;
import org.apache.batik.transcoder.TranscoderOutput;

/**
 *
 * @author avery
 */
public class SVGIconLoader {
    public static BufferedImage loadSVGAsImage(String resourcePath, int width, int height) throws Exception {
        InputStream inputStream = SVGIconLoader.class.getClassLoader().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IllegalArgumentException("SVG resource not found: " + resourcePath);
        }
        TranscoderInput input = new TranscoderInput(inputStream);
        
        final BufferedImage[] image = new BufferedImage[1];
        
        ImageTranscoder t = new ImageTranscoder() {
            @Override
            public BufferedImage createImage(int w, int h) {
                return new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            }
            @Override
            public void writeImage(BufferedImage img, TranscoderOutput out) {
                image[0] = img;
            }
        };
        
        t.addTranscodingHint(ImageTranscoder.KEY_WIDTH, (float) width);
        t.addTranscodingHint(ImageTranscoder.KEY_HEIGHT, (float) height);
        
        t.transcode(input, null);
        return image[0];
    }
    
    public ImageIcon loadSVGIcon(String path, int fallbackSize) {
    try {
        BufferedImage img = SVGIconLoader.loadSVGAsImage(path, 25, 25);
        return new ImageIcon(img);
    } catch (Exception e) {
        e.printStackTrace(); // for debugging
        // fallback: transparent empty icon
        BufferedImage fallback = new BufferedImage(fallbackSize, fallbackSize, BufferedImage.TYPE_INT_ARGB);
        return new ImageIcon(fallback);
    }
}
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExtraComponents;

import Modules.OrderData;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author avery
 */
public class OrderCardComponent extends JPanel {
    
    private OrderData data;
    
    public OrderCardComponent(OrderData data) {
        this.data = data;
        setPreferredSize(new Dimension(185, 500));
        setLayout(null); // Use absolute positioning for precise control
        
        putClientProperty("FlatLaf.style", "arc: 15");
        
        initComponents();
        revalidate();
        repaint();
        
    }
    
    private void initComponents() {
        
    }
    
}

package edu.jsu.mcis;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JPanel implements ShapeObserver {
    private CustomWidget customWidget;
    private JLabel label;

    public Main() {
        customWidget = new CustomWidget();
        customWidget.addShapeObserver(this);
        
        label = new JLabel("Hexagon", JLabel.CENTER);
        label.setName("label");
        
        setLayout(new BorderLayout());
        add(customWidget, BorderLayout.CENTER);
        add(label, BorderLayout.NORTH);
        
    }
    
    public void shapeChanged(ShapeEvent event) {
        if(event.isSelected()) { 
            label.setText("Hexagon");
        } else if (!event.isSelected()) {
            label.setText("Octagon");
        }
    }


	public static void main(String[] args) {
            JFrame window = new JFrame();
            window.setTitle("Main");
            window.add(new Main());
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setSize(500, 500);
            window.setVisible(true);
	}
}
package edu.jsu.mcis;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class CustomWidget extends JPanel implements MouseListener {
    private java.util.List<ShapeObserver> observers;
    
    
    private final Color HEXAGON_SELECTED_COLOR = Color.green;
    private final Color DESELECTED_COLOR = Color.white;
    private final Color OCTAGON_SELECTED_COLOR = Color.red;
    
    private boolean selected = true;
    private Point[] hexagonVertices;
    private Point[] octagonVertices;

    
    public CustomWidget() {
        observers = new ArrayList<>();
        Dimension dim = getPreferredSize();
        
        hexagonVertices = new Point[6];
        for(int i = 0; i < hexagonVertices.length; i++) { 
            hexagonVertices[i] = new Point(); 
        }
        
        octagonVertices = new Point[8];
        for(int i = 0; i < octagonVertices.length; i++) { 
            octagonVertices[i] = new Point(); 
        }
        
        calculateVertices(dim.width, dim.height);
        
        setBorder(BorderFactory.createLineBorder(Color.black));
        addMouseListener(this);
    }

    
    public void addShapeObserver(ShapeObserver observer) {
        if(!observers.contains(observer)) observers.add(observer);
    }
    
    public void removeShapeObserver(ShapeObserver observer) {
        observers.remove(observer);
    }
    
    private void notifyObservers() {
        ShapeEvent event = new ShapeEvent(selected);
        for(ShapeObserver obs : observers) {
            obs.shapeChanged(event);
        }
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    private void calculateVertices(int width, int height) {
        // Square size should be half of the smallest dimension (width or height).

        int side = Math.min(width, height) / 4;
        
        Point[] hexsign = {new Point(-4, 0), new Point(-3, 2), new Point(-2, 2),
            new Point(-1, 0), new Point(-2, -2), new Point(-3, -2)};
        for(int i = 0; i < hexagonVertices.length; i++) {
            hexagonVertices[i].setLocation(width/2 + hexsign[i].x * side/2, height/2 + hexsign[i].y * side/2);
        }
        
        Point[] octsign = {new Point(1, 1), new Point(2, 2), new Point(3, 2),
            new Point(4, 1), new Point(4, -1), new Point(3, -2),
            new Point(2, -2), new Point(1, -1)};
        for(int i = 0; i < octagonVertices.length; i++) {
            octagonVertices[i].setLocation(width/2 + octsign[i].x * side/2, height/2 + octsign[i].y * side/2);
        }
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        calculateVertices(getWidth(), getHeight());
        Shape[] shape = getShapes();
        g2d.setColor(Color.black);
        g2d.draw(shape[0]);
        g2d.draw(shape[1]);
        if(selected == true) {
            g2d.setColor(HEXAGON_SELECTED_COLOR);
            g2d.fill(shape[0]);
            g2d.setColor(DESELECTED_COLOR);
            g2d.fill(shape[1]);
        }
        else if (!selected) {
            g2d.setColor(DESELECTED_COLOR);
            g2d.fill(shape[0]);
            g2d.setColor(OCTAGON_SELECTED_COLOR);
            g2d.fill(shape[1]);
        }
    }

    public void mouseClicked(MouseEvent event) {
        Shape[] shape = getShapes();
        if(shape[0].contains(event.getX(), event.getY())) {
            selected = true;
            notifyObservers();
        }
        if(shape[1].contains(event.getX(), event.getY())) {
            selected = false;
            notifyObservers();
        }
        repaint(shape[0].getBounds());
        repaint(shape[1].getBounds());
    }
    public void mousePressed(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
    public void mouseEntered(MouseEvent event) {}
    public void mouseExited(MouseEvent event) {}
    
    public Shape[] getShapes() {
        Shape shape[] = new Shape[2];
        int[] x = new int[hexagonVertices.length];
        int[] y = new int[hexagonVertices.length];
        for(int i = 0; i < hexagonVertices.length; i++) {
            x[i] = hexagonVertices[i].x;
            y[i] = hexagonVertices[i].y;
        }
        shape[0] = new Polygon(x, y, hexagonVertices.length);
        
        x = new int[octagonVertices.length];
        y = new int[octagonVertices.length];
        for(int i = 0; i < octagonVertices.length; i++) {
            x[i] = octagonVertices[i].x;
            y[i] = octagonVertices[i].y;
        }
        
        shape[1] = new Polygon(x, y, octagonVertices.length);
        return shape;
    }
    
    public boolean isSelected() { 
        return selected; 
    }

	public static void main(String[] args) {
            JFrame window = new JFrame("Custom Widget");
            window.add(new CustomWidget());
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setSize(300, 300);
            window.setVisible(true);
	}
}

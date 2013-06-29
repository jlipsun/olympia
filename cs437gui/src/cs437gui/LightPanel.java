package cs437gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class LightPanel extends JPanel{

	  // Declare an Image object for us to use.
    Image image;
    
    // Create a constructor method
    public LightPanel(){
       super();
       // Load an image to play with.
       image = Toolkit.getDefaultToolkit().getImage("luckycat.gif");
    }
  
    public void paintComponent(Graphics g){
         Graphics2D g2d=(Graphics2D)g; // Create a Java2D version of g.
         //left right && up down
         g2d.translate(0, 320); // Translate the center of our coordinates.
         g2d.rotate(0);  // Rotate the image by 1 radian.
         g2d.drawImage(image, 0, 0, 50, 50, this);
    }


}
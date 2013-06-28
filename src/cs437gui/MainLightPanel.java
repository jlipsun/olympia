package cs437gui;

// Exercise15_15.java: Use scroll bar to choose colors
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainLightPanel extends JPanel implements AdjustmentListener {
	private static final long serialVersionUID = 1L;


//Panels to hold rgb colors, colors, vertical/horizontal, and pass to the main module
  JPanel rgbPanel, colorPanel, WidthAndHeightPanel, effectsPanel;
  
  
  //CenterLightPanel clp = new CenterLightPanel();
  //Call Graphic Light constructor
  GraphicDragLight gdl = new GraphicDragLight();   
  
  
  // Declare scroll bars for RGB values
  private JScrollBar jscbRed, jscbGreen, jscbBlue;
  
  //Panel specifically for the parts of color
  JPanel colorLabels, colorScrollBar;
  
  //Panel specifically for the parts of rgb
  JPanel rgbLabels, rgbScrollBar;
  
  // Declare color component values
  private int redValue, greenValue, blueValue;
  String[] colorValues = {"Yellow", "Orange", "Gray", "Pink", "White", "Black", "Magenta", "Cyan"};
  JComboBox colorBox = new JComboBox(colorValues); 
  
  //Declare effects component values
  String[] effectsValues = {"None", "Snowflakes", "Dot-Star", "Point-Star", "Water-Reflections"};
  JComboBox effectsBox = new JComboBox(effectsValues);
  
  //Panel specifically for the width, height, diagonal
  JPanel widthHeightDiagonalLabels, widthHeightDiagonalSliders;
    
  //Create Slider
  JSlider flood, tilt, pan;
  
  
  public MainLightPanel() {	  
	  
	  /*For vertical and horizontal*/
	  
	  	
	   // Panel widthHeightDiagonalLabels to hold width, height, diagonal
		widthHeightDiagonalLabels = new JPanel(new GridLayout(3, 1));  
		widthHeightDiagonalLabels.add(new JLabel("Flood"));
		widthHeightDiagonalLabels.add(new JLabel("Tilt"));
		widthHeightDiagonalLabels.add(new JLabel("Pan"));
	    
		// Panel colorScrollBar to hold width and height sliders
		widthHeightDiagonalSliders = new JPanel(new GridLayout(3, 1));   
		widthHeightDiagonalSliders.add(flood = new JSlider());
		widthHeightDiagonalSliders.add(tilt = new JSlider());
		widthHeightDiagonalSliders.add(pan = new JSlider());
		flood.setOrientation(JSlider.HORIZONTAL);
		flood.setMaximum(350);
		tilt.setOrientation(JSlider.HORIZONTAL);
		tilt.setMaximum(350);
		pan.setOrientation(JSlider.HORIZONTAL);
		pan.setMaximum(350);	  
	    
	    // Create a panel to hold rgb values
	    WidthAndHeightPanel = new JPanel(new BorderLayout());
	    WidthAndHeightPanel.add(widthHeightDiagonalLabels, BorderLayout.WEST);
	    WidthAndHeightPanel.add(widthHeightDiagonalSliders, BorderLayout.CENTER);
	    
	    // Register listener for the width, height,diagonal
	    flood.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				gdl.height = flood.getValue();
				gdl.width = flood.getValue();
				gdl.invalidate();
				gdl.repaint();
			}
		});
	    
	    tilt.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				gdl.height = tilt.getValue();
				gdl.invalidate();
				gdl.repaint();
			}
		});
	    
	    pan.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				gdl.width = pan.getValue();
				gdl.invalidate();
				gdl.repaint();
			}
		});
	    
	    
	 WidthAndHeightPanel.setBorder(new CompoundBorder(new TitledBorder("Set Flood and Angle"),
	      new EmptyBorder(2, 2, 2, 2)));
	  
	
	/*For rgb option*/
	   
    // Panel rgbLabels to hold rgb labels
    rgbLabels = new JPanel(new GridLayout(3, 1));
    
    rgbLabels.add(new JLabel("Red"));
    rgbLabels.add(new JLabel("Green"));
    rgbLabels.add(new JLabel("Blue"));
    
    // Panel rgbScrollBar to hold rgb scrollbars
    rgbScrollBar = new JPanel(new GridLayout(3, 1));
    
    rgbScrollBar.add(jscbRed = new JScrollBar());
    jscbRed.setOrientation(JScrollBar.HORIZONTAL);
    jscbRed.setMaximum(255);

    rgbScrollBar.add(jscbGreen = new JScrollBar());
    jscbGreen.setOrientation(JScrollBar.HORIZONTAL);
    jscbGreen.setMaximum(255);

    rgbScrollBar.add(jscbBlue = new JScrollBar());
    jscbBlue.setOrientation(JScrollBar.HORIZONTAL);
    jscbBlue.setMaximum(255);

    // Create a panel to hold rgb values
    rgbPanel = new JPanel(new BorderLayout(10, 10));
    rgbPanel.add(rgbLabels, BorderLayout.WEST);
    rgbPanel.add(rgbScrollBar, BorderLayout.CENTER);

    // Register listener for the scroll bars
    jscbRed.addAdjustmentListener(this);
    jscbGreen.addAdjustmentListener(this);
    jscbBlue.addAdjustmentListener(this);

    //Set border for rgb values
    rgbPanel.setBorder(new CompoundBorder(new TitledBorder("Adjust RGB Color Values:"),
      new EmptyBorder(2, 2, 2, 2)));
    
  
    /*For color options*/
    
    // Create a panel to hold color values
    colorPanel = new JPanel();
   
    colorPanel.add(colorBox);
    
    // Register listener
    colorBox.addActionListener(new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
		 if(colorBox.getSelectedIndex() == 0)
			 gdl.setForeground(Color.yellow);
		 else if(colorBox.getSelectedIndex() == 1)
			 gdl.setForeground(Color.orange);
		 else if(colorBox.getSelectedIndex() == 2)
			 gdl.setForeground(Color.gray);
		 else if(colorBox.getSelectedIndex() == 3)
			 gdl.setForeground(Color.pink);
		 else if(colorBox.getSelectedIndex() == 4)
			 gdl.setForeground(Color.white);
		 else if(colorBox.getSelectedIndex() == 5)
			 gdl.setForeground(Color.black);
		 else if(colorBox.getSelectedIndex() == 6)
			 gdl.setForeground(Color.magenta);
		 else if(colorBox.getSelectedIndex() == 7)
			 gdl.setForeground(Color.cyan);
		}
	});
    
    colorPanel.setBorder(new CompoundBorder(new TitledBorder("Select Color For Light:"),
      new EmptyBorder(2, 2, 2, 2)));

    
    /* Effects Options*/
    
    // Create a panel to hold color values
    effectsPanel = new JPanel();
   
    effectsPanel.add(effectsBox);
    
    // Register listener
    effectsBox.addActionListener(new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
		 if(effectsBox.getSelectedIndex() == 0)
			 System.out.print("0");
		 else if(effectsBox.getSelectedIndex() == 1)
			 System.out.print("1");
		 else if(effectsBox.getSelectedIndex() == 2)
			 System.out.print("2");
		 else if(effectsBox.getSelectedIndex() == 3)
			 System.out.print("3");
		 else if(effectsBox.getSelectedIndex() == 4)
			 System.out.print("4");
	}

	});
    
    effectsPanel.setBorder(new CompoundBorder(new TitledBorder("Select Effects For Light:"),
      new EmptyBorder(2, 2, 2, 2)));
    
  }

  public void adjustmentValueChanged(AdjustmentEvent e) {
	 
	if (e.getSource() == jscbRed){
      redValue = jscbRed.getValue();
      System.out.println("red : " + redValue);
	}
    else if (e.getSource() == jscbGreen){
      greenValue = jscbGreen.getValue();
	  System.out.println("green: " +greenValue);
    }  
    else if (e.getSource() == jscbBlue){
      blueValue = jscbBlue.getValue();
      System.out.print("blue: " + blueValue);
    }
    
    Color color = new Color(redValue, greenValue, blueValue);
    
    gdl.setForeground(color);
    
  }

  public int getXposition() {
	  return gdl.getXPosition();
  }
  
  public int getYPosition() {
	  return gdl.getYPosition();
  }
  
  public int getRedComponent() {
	  return jscbRed.getValue();
  }
  
  public int getGreenComponent() {
	  return jscbGreen.getValue();
  }
  
  public int getBlueComponent() {
	  return jscbBlue.getValue();
  }
  
  public String getGoboPattern() {
	  return effectsValues[effectsBox.getSelectedIndex()];
  }
  
  public int getFlood() {
	  return flood.getValue();
  }
  
  public int getTilt() {
	  return flood.getValue();
  }
  
  public int getPan(){
	  return pan.getValue();
  }
}
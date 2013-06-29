package cs437gui;

// Exercise15_15.java: Use scroll bar to choose colors
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainLightPanel extends JPanel implements AdjustmentListener {
	private static final long serialVersionUID = 1L;
	
	private Color Colors[] = {
		Color.red,
		Color.yellow,
		Color.blue,
		Color.orange,
		new Color(127,0,255), // Violet
		Color.green
	};

//Panels to hold RGB colors, colors, vertical/horizontal, and pass to the main module
  JPanel rgbPanel, colorPanel, WidthAndHeightPanel, effectsPanel;
  
  //Call Graphic Light constructor
  GraphicDragLight gdl = new GraphicDragLight();   
  
  
  // Declare scroll bars for RGB values
  private JScrollBar jscbRed, jscbGreen, jscbBlue;
  
  //Panel specifically for the parts of color
  JPanel colorLabels, colorScrollBar;
  
  //Panel specifically for the parts of RGB
  JPanel rgbLabels, rgbScrollBar;
  
  // Declare color component values
  private int redValue, greenValue, blueValue;
  String[] colorValues = {"Red", "Yellow", "Blue", "Orange", "Violet", "Green"};
  JComboBox colorBox = new JComboBox(colorValues); 
  
  //Declare effects component values
  String[] effectsValues = {"None", "Snowflakes", "Dot-Star", "Point-Star", "Water-Reflections", "Branches"};
  JComboBox effectsBox = new JComboBox(effectsValues);
  
  //Panel specifically for the width, height, diagonal
  JPanel widthHeightDiagonalLabels, widthHeightDiagonalSliders;
    
  //Create Slider
  JSlider flood, tilt, pan;
  BufferedImage snowflakes;
  
  
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
	      new EmptyBorder(2, 2, 0, 0)));
	  
	
	/*For rgb option*/
	   
    // Panel rgbLabels to hold rgb labels
    rgbLabels = new JPanel(new GridLayout(3, 1));
    
    rgbLabels.add(new JLabel("Red"));
    rgbLabels.add(new JLabel("Green"));
    rgbLabels.add(new JLabel("Blue"));
    
    // Panel rgbScrollBar to hold RGB scroll bars
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

    // Create a panel to hold RGB values
    rgbPanel = new JPanel(new BorderLayout());
    rgbPanel.add(rgbLabels, BorderLayout.WEST);
    rgbPanel.add(rgbScrollBar, BorderLayout.CENTER);

    // Register listener for the scroll bars
    jscbRed.addAdjustmentListener(this);
    jscbGreen.addAdjustmentListener(this);
    jscbBlue.addAdjustmentListener(this);

    //Set border for RGB values
    rgbPanel.setBorder(new CompoundBorder(new TitledBorder("Adjust RGB Color Values:"),
      new EmptyBorder(2, 2, 0, 0)));
    
  
    /*For color options*/
    
    // Create a panel to hold color values
    colorPanel = new JPanel();
    colorPanel.setLayout(new BorderLayout());
    colorPanel.add(colorBox, BorderLayout.CENTER);
    
    // Register listener
    colorBox.addActionListener(
    	new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			Color color = Colors[colorBox.getSelectedIndex()];

    			gdl.setForeground(color);
    			jscbRed.setValue(color.getRed());
    			jscbGreen.setValue(color.getGreen());
    			jscbBlue.setValue(color.getBlue());
	    	}
    	}
	);
    
    colorPanel.setBorder(new CompoundBorder(new TitledBorder("Select Color For Light:"),
      new EmptyBorder(2, 2, 0, 0)));

    
    /* Effects Options*/
    
    // Create a panel to hold color values
    effectsPanel = new JPanel(new BorderLayout());
    final JPanel goboPicturePanelContainer = new JPanel();
    goboPicturePanelContainer.setPreferredSize(new Dimension(75, 75));
    final ImageIcon snowflakes = new ImageIcon("snowflakes.jpg");
    final ImageIcon dotStar = new ImageIcon("dotStar.jpg");
    final ImageIcon fivePointStar = new ImageIcon("fivePointStar.jpg");
    final ImageIcon waterReflections = new ImageIcon("waterReflections.jpg");
    final ImageIcon branches = new ImageIcon("branches.jpg");
   
    effectsPanel.add(effectsBox, BorderLayout.WEST);
    effectsPanel.add(goboPicturePanelContainer, BorderLayout.CENTER);
    
    // Register listener
    effectsBox.addActionListener(new ActionListener() {
		
	public void actionPerformed(ActionEvent e) {
		 if(effectsBox.getSelectedIndex() == 0) {
			 goboPicturePanelContainer.removeAll();
				 
				 
			 goboPicturePanelContainer.validate();
				 goboPicturePanelContainer.repaint();
		 }
			 
		
			
		 else if(effectsBox.getSelectedIndex() == 1){
			
			 goboPicturePanelContainer.removeAll();
			// goboPicturePanelContainer.add(goboPicturePanel);
			 goboPicturePanelContainer.add(new JLabel(snowflakes)); 
			 
			 goboPicturePanelContainer.validate();
			 goboPicturePanelContainer.repaint();
			
		 }
			 
		else if(effectsBox.getSelectedIndex() == 2){
			goboPicturePanelContainer.removeAll();
			// goboPicturePanelContainer.add(goboPicturePanel);
			goboPicturePanelContainer.add(new JLabel(dotStar));  
			 
			goboPicturePanelContainer.validate();
			goboPicturePanelContainer.repaint();
			
		
		}
		 else if(effectsBox.getSelectedIndex() == 3) {
			 goboPicturePanelContainer.removeAll();
				// goboPicturePanelContainer.add(goboPicturePanel);
			 goboPicturePanelContainer.add(new JLabel(fivePointStar)); 
				 
			 goboPicturePanelContainer.validate();
			 goboPicturePanelContainer.repaint();
			 
		 }
		 else if(effectsBox.getSelectedIndex() == 4) {
			 goboPicturePanelContainer.removeAll();
				// goboPicturePanelContainer.add(goboPicturePanel);
			 goboPicturePanelContainer.add(new JLabel(waterReflections)); 
				 
			 goboPicturePanelContainer.validate();
			 goboPicturePanelContainer.repaint();
			 
	}
		
		else if(effectsBox.getSelectedIndex() == 5) {
			goboPicturePanelContainer.removeAll();
				// goboPicturePanelContainer.add(goboPicturePanel);
			goboPicturePanelContainer.add(new JLabel(branches)); 
				 
			goboPicturePanelContainer.validate();
			goboPicturePanelContainer.repaint();
	
		}
	}
	});

    effectsPanel.setBorder(new CompoundBorder(new TitledBorder("Select Gobo Pattern:"),
      new EmptyBorder(2, 2, 0, 0)));
    
  }

  public void adjustmentValueChanged(AdjustmentEvent e) {
	 
	if (e.getSource() == jscbRed){
      redValue = jscbRed.getValue();
      
	}
    else if (e.getSource() == jscbGreen){
      greenValue = jscbGreen.getValue();
	  
    }  
    else if (e.getSource() == jscbBlue){
      blueValue = jscbBlue.getValue();
      
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

public void setRed(int red) {
	this.jscbRed.setValue(red);
}

public void setGreen(int green) {
	this.jscbGreen.setValue(green);
}

public void setBlue(int blue) {
	this.jscbBlue.setValue(blue);
}

public void setFlood(int flood) {
	this.flood.setValue(flood);
}

public void setTilt(int tilt) {
	this.tilt.setValue(tilt);
}

public void setPan(int pan) {
	this.pan.setValue(pan);
}

public void setGoboPattern (String gobo) {
	this.effectsBox.setSelectedItem(gobo);
}

}
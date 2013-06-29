package cs437gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LightModule extends Module {
	private static final long serialVersionUID = 1L;

	private JPanel mainPanel = new JPanel();

	//Call constructors
	MainLightPanel mlp = new MainLightPanel();
	
	//Crazy stuff
	private JPanel stagePanel = new JPanel(new BorderLayout());
	private JPanel propertyPanel = new JPanel(new GridLayout(2, 2));
	
	
	public LightModule(){
		super(5);
		
		mainPanel.setLayout(new BorderLayout());

		propertyPanel.add(mlp.colorPanel);
		propertyPanel.add(mlp.effectsPanel);
		propertyPanel.add(mlp.rgbPanel);
		propertyPanel.add(mlp.WidthAndHeightPanel);
		
		stagePanel.add(mlp.gdl, BorderLayout.CENTER);
		ImageIcon theatre = new ImageIcon("seatingChart.jpg");
		stagePanel.add(new JLabel(theatre), BorderLayout.SOUTH);
		
		mainPanel.add(stagePanel, BorderLayout.CENTER);
		mainPanel.add(propertyPanel, BorderLayout.SOUTH);

		this.add(mainPanel,BorderLayout.CENTER);
	}

	public void clearInput() {
		super.clearInput();

		mlp.gdl.setXPosition(0);
		mlp.gdl.setYPosition(0);
		mlp.colorBox.setSelectedIndex(0);
		mlp.setGoboPattern("None");
		mlp.setFlood(50);
		mlp.setTilt(50);
		mlp.setPan(50);

		this.revalidate();
		this.repaint();
	}

	public ArrayList<Cue> createCueFromInput(double startTime, double endTime) {
		// Create a new cue using supplied startTime and endTime
		int x = mlp.getXposition();
		int y = mlp.getYPosition();
		int red = mlp.getRedComponent();
		int green = mlp.getGreenComponent();
		int blue = mlp.getBlueComponent();
		String goboPattern = mlp.getGoboPattern();
		int flood = mlp.getFlood();
		int tilt = mlp.getTilt();
		int pan = mlp.getPan();
		
		Cue cue = new LightingCue(startTime, endTime, x, y, red, green, blue, goboPattern, flood, tilt, pan);
		ArrayList<Cue> newCues = new ArrayList<Cue>();
		newCues.add(cue);
		return newCues;
	}
	
	public void populateInputFromCue(Cue cue) {
		// *** Type cast supplied cue to appropriate type ***
		LightingCue lightingCue = (LightingCue)cue;

		// Populate all fields from type casted cue
		mlp.gdl.setXPosition(lightingCue.getXposition());
		mlp.gdl.setYPosition(lightingCue.getYposition());
		mlp.setRed(lightingCue.getRvalue());
		mlp.setGreen(lightingCue.getGvalue());
		mlp.setBlue(lightingCue.getBvalue());
		mlp.setGoboPattern(lightingCue.getGoboPattern());
		mlp.setFlood(lightingCue.getFlood());
		mlp.setTilt(lightingCue.getTilt());
		mlp.setPan(lightingCue.getPan());
	}

	public void updateStatus(ArrayList<Cue> activeCues, JPanel statusPanel) {
		statusPanel.setLayout(new BorderLayout());
		statusPanel.setBackground(Color.WHITE);
		statusPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		ImageIcon icon;

		if (activeCues.size() > 0) {
			icon = new ImageIcon("lightingFXON.jpg");
		}
		else {
			icon = new ImageIcon("lightingFXOFF.jpg");
		}

		Image image = icon.getImage();
		image = image.getScaledInstance(200, 40, Image.SCALE_SMOOTH);
		icon.setImage(image);

		statusPanel.add(new JLabel(icon), BorderLayout.CENTER);
	}
}


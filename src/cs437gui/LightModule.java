package cs437gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class LightModule extends Module {
	private static final long serialVersionUID = 1L;

	private double startTime;
	private double endTime;
	
	//Keep track of time from video
    private int intTime;
    private int seconds;
    private int minutes;
    private int hours;
    private String secondsText;
    private String minutesText;
    private String hoursText;
	
	private JPanel timePanel = new JPanel();
	private JPanel mainPanel = this;
	private JPanel buttonPanel = new JPanel();
	
	private JLabel startLabel = new JLabel("Start Time: ");
	private JLabel endLabel = new JLabel("End Time: ");
	private JLabel startField = new JLabel("0:0:0");
	private JLabel endField = new JLabel("0:0:0");
	
	//Panel to the east
	private JPanel eastPanel = new JPanel();

		
	JButton setStartTime = new JButton("Set Start Time");
	JButton setEndTime = new JButton("Set End Time");
	JButton saveButton = new JButton("Save");
	JButton clearButton = new JButton("Clear");

	//Call constructors
	MainLightPanel mlp = new MainLightPanel();
	
	
	//Crazy stuff
	private JPanel stagePanel = new JPanel(new BorderLayout());
	private JPanel propertyPanel = new JPanel(new BorderLayout());
	
	private JPanel colorPanel = new JPanel(new GridLayout(1, 2, 5, 5));
	
	private JPanel westColorPanel = new JPanel(new BorderLayout());
	private JPanel eastColorPanel = new JPanel(new BorderLayout());
	
	
	
	public LightModule(){
		super(5);
		
		mainPanel.setLayout(new BorderLayout());
		
		timePanel.setLayout(new BorderLayout());
		
		JPanel timeSubPanelWest = new JPanel(new BorderLayout());
		JPanel timeSubPanelEast = new JPanel(new BorderLayout());
		
		timeSubPanelWest.add(startLabel, BorderLayout.WEST);
		timeSubPanelWest.add(startField, BorderLayout.EAST);
		timeSubPanelEast.add(endLabel, BorderLayout.WEST);
		timeSubPanelEast.add(endField, BorderLayout.EAST);
		
		timePanel.add(timeSubPanelWest, BorderLayout.WEST);
		timePanel.add(timeSubPanelEast, BorderLayout.EAST);
				
		createEastTab();
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(startTime <= endTime){
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
				
					OlympiaMainGui.cpm.addCue(cue);
            	}else
            		JOptionPane.showMessageDialog(null, "Error. Start time should be less than end time.");
			}
		});
		
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    startField.setText("00:00:00");
                    endField.setText("00:00:00");
            }
        });
		
        setStartTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
                          	
               	startTime = OlympiaMainGui.olympiaPlayer.mediaPlayer.getMediaTime().getSeconds();
            	intTime = (int)startTime;
        	    seconds = intTime%60;
        		minutes = (intTime/60) % 60;
        		hours = (intTime/3600);
        		
        		secondsText = Integer.toString(seconds);
        		minutesText = Integer.toString(minutes);
        		hoursText = Integer.toString(hours);
        		
        		startField.setText(hoursText + ":" + minutesText + ":" + secondsText);
            }
        });
        
        setEndTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	endTime = OlympiaMainGui.olympiaPlayer.mediaPlayer.getMediaTime().getSeconds();
            	intTime = (int)endTime;
        	    seconds = intTime%60;
        		minutes = (intTime/60) % 60;
        		hours = (intTime/3600);
        		
        		secondsText = Integer.toString(seconds);
        		minutesText = Integer.toString(minutes);
        		hoursText = Integer.toString(hours);
        		
        		endField.setText(hoursText + ":" + minutesText + ":" + secondsText);
            }
        });
		
		buttonPanel.setLayout(new BorderLayout());

		JPanel tempTimePanel = new JPanel(new GridLayout());
		tempTimePanel.add(setStartTime);
		tempTimePanel.add(setEndTime);
		
		JPanel tempActionPanel = new JPanel(new GridLayout());
		
		tempActionPanel.add(saveButton);
		tempActionPanel.add(clearButton);
		
		buttonPanel.add(tempTimePanel, BorderLayout.NORTH);
		buttonPanel.add(tempActionPanel, BorderLayout.SOUTH);	
		
		// JPanel rgbPanel, colorPanel, WidthAndHeightPanel, effectsPanel;
		westColorPanel.add(mlp.colorPanel, BorderLayout.NORTH);
		westColorPanel.add(mlp.rgbPanel, BorderLayout.CENTER);
		
		eastColorPanel.add(mlp.effectsPanel, BorderLayout.NORTH);
		eastColorPanel.add(mlp.WidthAndHeightPanel, BorderLayout.CENTER);
		
		
		colorPanel.add(westColorPanel);
		colorPanel.add(eastColorPanel);
		
		
		propertyPanel.add(timePanel, BorderLayout.NORTH);
		propertyPanel.add(colorPanel, BorderLayout.CENTER);
		propertyPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		
	/*	mainPanel.add(timePanel, BorderLayout.NORTH);
		mainPanel.add(eastPanel, BorderLayout.EAST);
		mainPanel.add(mlp.gdl, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		*/
		
		
		stagePanel.add(mlp.gdl, BorderLayout.CENTER);
		stagePanel.add(new JLabel("Picture"), BorderLayout.SOUTH);
		
		
		
		
		
		
		
		
		mainPanel.add(stagePanel, BorderLayout.CENTER);
		mainPanel.add(propertyPanel, BorderLayout.SOUTH);
		
	}

	void createEastTab() {
		eastPanel = new JPanel(new GridLayout(4,1));
		eastPanel.add(mlp.colorPanel);
		eastPanel.add(mlp.rgbPanel);
		eastPanel.add(mlp.WidthAndHeightPanel);
		eastPanel.add(mlp.effectsPanel);
		
	}
	
}

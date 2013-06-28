package cs437gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

public class SpecialEffectsModule extends Module {
	private static final long serialVersionUID = 1L;

	private double startTime;
	private double endTime;
	
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
	private JLabel startField = new JLabel("00:00:00");
	private JLabel endField = new JLabel("00:00:00");
	
	private JTabbedPane tabs = new JTabbedPane();
	private JPanel windPanel = new JPanel();
	private JPanel mistPanel = new JPanel();
	private JPanel pyroPanel = new JPanel();
	private JPanel fogPanel = new JPanel();
	
	JPanel[] windSubPanel;
	JPanel[] mistSubPanel;
	JPanel[] pyroSubPanel;
	JPanel[] fogSubPanel;
	JSlider[] slider;
	JCheckBox[] checkBox;

	JButton setStartTime = new JButton("Set Start Time");
	JButton setEndTime = new JButton("Set End Time");
	JButton saveButton = new JButton("Save");
	JButton clearButton = new JButton("Clear");

	
	public SpecialEffectsModule(){
		super(6);
		
		mainPanel.setLayout(new BorderLayout(5, 5));
		
		timePanel.setLayout(new BorderLayout());
		
		JPanel timeSubPanelWest = new JPanel(new BorderLayout());
		JPanel timeSubPanelEast = new JPanel(new BorderLayout());
		
		timeSubPanelWest.add(startLabel, BorderLayout.WEST);
		timeSubPanelWest.add(startField, BorderLayout.EAST);
		timeSubPanelEast.add(endLabel, BorderLayout.WEST);
		timeSubPanelEast.add(endField, BorderLayout.EAST);
		
		timePanel.add(timeSubPanelWest, BorderLayout.WEST);
		timePanel.add(timeSubPanelEast, BorderLayout.EAST);
		
		createWindTab();
		createMistTab();
		createPyroTab();
		createFogTab();
		
		tabs.add(windPanel, "Wind (mph)");
		tabs.add(mistPanel, "Mist (oz)");
		tabs.add(pyroPanel, "Pyro (meters)");
		tabs.add(fogPanel, "Fog (cubic ft/min)");
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(tabs.getSelectedIndex() == 0){ // Wind
					
					for(int i = 0; i < windSubPanel.length; i++){
						if( ((JCheckBox)windSubPanel[i].getComponent(0)).isSelected() ){
							int component = i;
							int value = ((JSlider)windSubPanel[i].getComponent(1)).getValue();
							
							if(startTime <= endTime){
            					Cue cue = new SpecialEffectsCue(startTime, endTime, 0, component, value);
            				
            					OlympiaMainGui.cpm.addCue(cue);
            					OlympiaMainGui.g.setupSouthContainer();
							
							}else {
								JOptionPane.showMessageDialog(null, "Error. Start time should be less than end time.");
							}
							
							
						}
					}
					
				}
				else if(tabs.getSelectedIndex() == 1){ // Mist
					
					for(int i = 0; i < mistSubPanel.length; i++){
						if( ((JCheckBox)mistSubPanel[i].getComponent(0)).isSelected() ){
							int component = i;
							int value = ((JSlider)mistSubPanel[i].getComponent(1)).getValue();

							if(startTime <= endTime){
            					Cue cue = new SpecialEffectsCue(startTime, endTime, 1, component, value);
            				
            					OlympiaMainGui.cpm.addCue(cue);
							
							}else {
								JOptionPane.showMessageDialog(null, "Error. Start time should be less than end time.");
							}
						}
					}
					
				}
				else if(tabs.getSelectedIndex() == 2){ // Pyro
					
					for(int i = 0; i < pyroSubPanel.length; i++){
						if( ((JCheckBox)pyroSubPanel[i].getComponent(0)).isSelected() ){
							int component = i;
							int value = ((JSlider)pyroSubPanel[i].getComponent(1)).getValue();
							
							if(startTime <= endTime){
								Cue cue = new SpecialEffectsCue(startTime, endTime, 2, component, value);
            				
            					OlympiaMainGui.cpm.addCue(cue);
							
							}else {
								JOptionPane.showMessageDialog(null, "Error. Start time should be less than end time.");
							}
						}
					}
					
				}
				else if(tabs.getSelectedIndex() == 3){  // Fog
					
					for(int i = 0; i < fogSubPanel.length; i++){
						if( ((JCheckBox)fogSubPanel[i].getComponent(0)).isSelected() ){
							int component = i;
							int value = ((JSlider)fogSubPanel[i].getComponent(1)).getValue();
							
							if(startTime <= endTime){
            					Cue cue = new SpecialEffectsCue(startTime, endTime, 3, component, value);
            				
            					OlympiaMainGui.cpm.addCue(cue);
							
							}else {
								JOptionPane.showMessageDialog(null, "Error. Start time should be less than end time.");
							}
						}
					}
					
				}
				else { // if out of bounds
					System.out.println("Error");
				}
				
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
		
		mainPanel.add(timePanel, BorderLayout.NORTH);
		mainPanel.add(tabs, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
	}

	void createWindTab() {
		final int WIND_SPEED_MIN = 0;
		final int WIND_SPEED_MAX = 20;
		JSlider[] slider = new JSlider[5];
		JCheckBox[] status = new JCheckBox[5];
		windSubPanel = new JPanel[5];
		windPanel.setLayout(new GridLayout(5,1));
		
		for(int i = 0; i < slider.length; i++){
			slider[i] = new JSlider(JSlider.HORIZONTAL, WIND_SPEED_MIN, WIND_SPEED_MAX, 0);
			slider[i].setMajorTickSpacing(5);
			slider[i].setMinorTickSpacing(1);
			slider[i].setPaintTicks(true);
			slider[i].setPaintLabels(true);
		}

		for(int i = 0; i < status.length; i++){
			status[i] = new JCheckBox("On/Off", false);
		}
		
		for(int i = 0; i < windSubPanel.length; i++){
			windSubPanel[i] = new JPanel();
			windSubPanel[i].setLayout(new BorderLayout());
			windSubPanel[i].setBorder(new TitledBorder( (i + 1) + ""));
			windSubPanel[i].add(status[i], BorderLayout.WEST);
			windSubPanel[i].add(slider[i], BorderLayout.CENTER);
			windSubPanel[i].add(new JPanel(), BorderLayout.SOUTH);
			windPanel.add(windSubPanel[i]);
		}
		
	}
	
	void createMistTab() {
		final int MIST_SPEED_MIN = 0;
		final int MIST_SPEED_MAX = 20;
		JSlider[] slider = new JSlider[5];
		JCheckBox[] status = new JCheckBox[5];
		mistSubPanel = new JPanel[5];
		mistPanel.setLayout(new GridLayout(5,1));
		
		for(int i = 0; i < slider.length; i++){
			slider[i] = new JSlider(JSlider.HORIZONTAL, MIST_SPEED_MIN, MIST_SPEED_MAX, 0);
			slider[i].setMajorTickSpacing(5);
			slider[i].setMinorTickSpacing(1);
			slider[i].setPaintTicks(true);
			slider[i].setPaintLabels(true);
		}

		for(int i = 0; i < status.length; i++){
			status[i] = new JCheckBox("On/Off", false);
		}
		
		for(int i = 0; i < mistSubPanel.length; i++){
			mistSubPanel[i] = new JPanel();
			mistSubPanel[i].setLayout(new BorderLayout());
			mistSubPanel[i].setBorder(new TitledBorder( (i + 1) + ""));
			mistSubPanel[i].add(status[i], BorderLayout.WEST);
			mistSubPanel[i].add(slider[i], BorderLayout.CENTER);
			mistSubPanel[i].add(new JPanel(), BorderLayout.SOUTH);
			mistPanel.add(mistSubPanel[i]);
		}
		
	}
	
	void createPyroTab() {
		final int PYRO_SPEED_MIN = 0;
		final int PYRO_SPEED_MAX = 5;
		JSlider[] slider = new JSlider[5];
		JCheckBox[] status = new JCheckBox[5];
		pyroSubPanel = new JPanel[5];
		pyroPanel.setLayout(new GridLayout(5,1));
		
		for(int i = 0; i < slider.length; i++){
			slider[i] = new JSlider(JSlider.HORIZONTAL, PYRO_SPEED_MIN, PYRO_SPEED_MAX, 0);
			slider[i].setMajorTickSpacing(1);
			slider[i].setPaintTicks(true);
			slider[i].setPaintLabels(true);
		}

		for(int i = 0; i < status.length; i++){
			status[i] = new JCheckBox("On/Off", false);
		}
		
		for(int i = 0; i < pyroSubPanel.length; i++){
			pyroSubPanel[i] = new JPanel();
			pyroSubPanel[i].setLayout(new BorderLayout());
			pyroSubPanel[i].setBorder(new TitledBorder( (i + 1) + ""));
			pyroSubPanel[i].add(status[i], BorderLayout.WEST);
			pyroSubPanel[i].add(slider[i], BorderLayout.CENTER);
			pyroSubPanel[i].add(new JPanel(), BorderLayout.SOUTH);
			pyroPanel.add(pyroSubPanel[i]);
		}
		
	}
	
	
	void createFogTab() {
		final int FOG_SPEED_MIN = 0;
		final int FOG_SPEED_MAX = 30;
		JSlider[] slider = new JSlider[5];
		JCheckBox[] status = new JCheckBox[5];
		fogSubPanel = new JPanel[5];
		fogPanel.setLayout(new GridLayout(5,1));
		
		for(int i = 0; i < slider.length; i++){
			slider[i] = new JSlider(JSlider.HORIZONTAL, FOG_SPEED_MIN, FOG_SPEED_MAX, 0);
			slider[i].setMajorTickSpacing(5);
			slider[i].setMinorTickSpacing(1);
			slider[i].setSnapToTicks(true);
			slider[i].setPaintTicks(true);
			slider[i].setPaintLabels(true);
		}

		for(int i = 0; i < status.length; i++){
			status[i] = new JCheckBox("On/Off", false);
		}
		
		for(int i = 0; i < fogSubPanel.length; i++){
			fogSubPanel[i] = new JPanel();
			fogSubPanel[i].setLayout(new BorderLayout());
			fogSubPanel[i].setBorder(new TitledBorder( (i + 1) + ""));
			fogSubPanel[i].add(status[i], BorderLayout.WEST);
			fogSubPanel[i].add(slider[i], BorderLayout.CENTER);
			fogSubPanel[i].add(new JPanel(), BorderLayout.SOUTH);
			fogPanel.add(fogSubPanel[i]);
		}
		
	}
}

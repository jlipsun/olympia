package cs437gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

public class SpecialEffectsModule extends Module {
	private static final long serialVersionUID = 1L;

	private JPanel mainPanel = new JPanel();
	
	
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


	public SpecialEffectsModule(){
		super(6);
		
		mainPanel.setLayout(new BorderLayout(5, 5));
		
		
		
		
		createWindTab();
		createMistTab();
		createPyroTab();
		createFogTab();
		
		tabs.add(windPanel, "Wind (mph)");
		tabs.add(mistPanel, "Mist (oz)");
		tabs.add(pyroPanel, "Pyro (meters)");
		tabs.add(fogPanel, "Fog (cubic ft/min)");
		
		
        
		mainPanel.add(tabs, BorderLayout.CENTER);
		
		add(mainPanel, BorderLayout.CENTER);
		
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
	
	public ArrayList<Cue> createCueFromInput(double startTime, double endTime) {
		ArrayList<Cue> newCues = new ArrayList<Cue>();

		for(int i = 0; i < windSubPanel.length; i++){
			if( ((JCheckBox)windSubPanel[i].getComponent(0)).isSelected() ){
				int component = i;
				int value = ((JSlider)windSubPanel[i].getComponent(1)).getValue();

				Cue cue = new SpecialEffectsCue(startTime, endTime, 0, component, value);
							
    			newCues.add(cue);
			}
		}
	
		for(int i = 0; i < mistSubPanel.length; i++){
			if( ((JCheckBox)mistSubPanel[i].getComponent(0)).isSelected() ){
				int component = i;
				int value = ((JSlider)mistSubPanel[i].getComponent(1)).getValue();
	
    			Cue cue = new SpecialEffectsCue(startTime, endTime, 1, component, value);

    			newCues.add(cue);
			}
		}

		for(int i = 0; i < pyroSubPanel.length; i++){
			if( ((JCheckBox)pyroSubPanel[i].getComponent(0)).isSelected() ){
				int component = i;
				int value = ((JSlider)pyroSubPanel[i].getComponent(1)).getValue();
	
				Cue cue = new SpecialEffectsCue(startTime, endTime, 2, component, value);

				newCues.add(cue);	
			}
			
		}

		for(int i = 0; i < fogSubPanel.length; i++){
			if( ((JCheckBox)fogSubPanel[i].getComponent(0)).isSelected() ){
				int component = i;
				int value = ((JSlider)fogSubPanel[i].getComponent(1)).getValue();
	
    			Cue cue = new SpecialEffectsCue(startTime, endTime, 3, component, value);
    				
    			newCues.add(cue);

			}
		}

		if (newCues.size() == 0) {
			JOptionPane.showMessageDialog(null, "Error. No cues data was inputed.");
		}

		return newCues;		
	}

	public void populateInputFromCue(Cue cue) {
		SpecialEffectsCue sCue = (SpecialEffectsCue)cue;
		clearInput();
		
		int subID = sCue.getSubModuleID();
		tabs.setSelectedIndex(subID);
		
		switch( subID ){
		
			default:
			case 0: ((JSlider)windSubPanel[sCue.getComponent()].getComponent(1)).setValue(sCue.getValue());
				    ((JCheckBox)windSubPanel[sCue.getComponent()].getComponent(0)).setSelected(true);
				    break;
			case 1:
				  ((JSlider)mistSubPanel[sCue.getComponent()].getComponent(1)).setValue(sCue.getValue());
		    	  ((JCheckBox)mistSubPanel[sCue.getComponent()].getComponent(0)).setSelected(true);
		    	   break;
			case 2:
				((JSlider)pyroSubPanel[sCue.getComponent()].getComponent(1)).setValue(sCue.getValue());
			    ((JCheckBox)pyroSubPanel[sCue.getComponent()].getComponent(0)).setSelected(true);
			    break;
			case 3:
				((JSlider)fogSubPanel[sCue.getComponent()].getComponent(1)).setValue(sCue.getValue());
			    ((JCheckBox)fogSubPanel[sCue.getComponent()].getComponent(0)).setSelected(true);
			    break;
		
		}
		
		
		
	}
	
	public void clearInput(){
		super.clearInput();

		for(int i = 0; i < 5; i++){
			((JCheckBox)pyroSubPanel[i].getComponent(0)).setSelected(false);
			((JCheckBox)windSubPanel[i].getComponent(0)).setSelected(false);
			((JCheckBox)fogSubPanel[i].getComponent(0)).setSelected(false);
			((JCheckBox)mistSubPanel[i].getComponent(0)).setSelected(false);
			
			((JSlider)pyroSubPanel[i].getComponent(1)).setValue(0);
			((JSlider)windSubPanel[i].getComponent(1)).setValue(0);
			((JSlider)fogSubPanel[i].getComponent(1)).setValue(0);
			((JSlider)mistSubPanel[i].getComponent(1)).setValue(0);
			
		}
	}

	public void updateStatus(ArrayList<Cue> activeCues, JPanel statusPanel) {
		statusPanel.setLayout(new BorderLayout());
		statusPanel.setBackground(Color.WHITE);
		statusPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		ImageIcon icon;

		if (activeCues.size() > 0) {
			icon = new ImageIcon("spfxON.jpg");
		}
		else {
			icon = new ImageIcon("spfxOFF.jpg");
		}

		Image image = icon.getImage();
		image = image.getScaledInstance(200, 40, Image.SCALE_SMOOTH);
		icon.setImage(image);

		statusPanel.add(new JLabel(icon), BorderLayout.CENTER);
	}
}

package cs437gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class Module extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private int ModuleID;

	private JLabel startTimeText = new JLabel("0:0:0.0");
	private JLabel endTimeText = new JLabel("0:0:0.0");

	private JButton startTimeButton = new JButton("Set Start Time");
	private JButton endTimeButton = new JButton("Set End Time");
	private JButton clearTimesButton = new JButton("Clear Times");

	private JButton clearButton = new JButton("Clear Cue");
	private JButton cancelButton = new JButton("Cancel");
	private JButton saveButton = new JButton("Save Cue");
	private JButton deleteButton = new JButton("Delete Cue");

	private double startTime = 0;
	private double endTime = 0;

	private Cue EditCue;
	private ArrayList<Cue> ActiveCues;

	public Module(int ModuleID) {
		super(new BorderLayout());

		JPanel startTimePanel = new JPanel(new BorderLayout(5, 5));
		startTimePanel.add(new JLabel("Start Time: "), BorderLayout.WEST);
		startTimePanel.add(startTimeText, BorderLayout.CENTER);
		JPanel endTimePanel = new JPanel(new BorderLayout(5, 5));
		endTimePanel.add(new JLabel("End Time: "), BorderLayout.WEST);
		endTimePanel.add(endTimeText, BorderLayout.CENTER);
		JPanel timeOutputPanel = new JPanel(new GridLayout(1, 2));
		timeOutputPanel.add(startTimePanel);
		timeOutputPanel.add(endTimePanel);

		startTimeButton.addActionListener(this);
		endTimeButton.addActionListener(this);
	    clearTimesButton.addActionListener(this);
		JPanel timeButtonPanel = new JPanel(new GridLayout(1, 3));
		timeButtonPanel.add(startTimeButton);
		timeButtonPanel.add(endTimeButton);
		timeButtonPanel.add(clearTimesButton);

		JPanel timePanel = new JPanel(new GridLayout(2,1));
		timePanel.setBorder(new TitledBorder("Time"));
		timePanel.add(timeOutputPanel);
		timePanel.add(timeButtonPanel);

		clearButton.addActionListener(this);
		cancelButton.addActionListener(this);
		saveButton.addActionListener(this);
		deleteButton.addActionListener(this);
		JPanel cueButtonPanel = new JPanel(new GridLayout(2, 2));
		cueButtonPanel.add(clearButton);
		cueButtonPanel.add(cancelButton);
		cueButtonPanel.add(saveButton);
		cueButtonPanel.add(deleteButton);

		JPanel commonPanel = new JPanel(new BorderLayout());//new GridLayout(2, 1));
		commonPanel.add(timePanel, BorderLayout.CENTER);
		commonPanel.add(cueButtonPanel, BorderLayout.SOUTH);

		add(commonPanel, BorderLayout.SOUTH);

		this.ModuleID = ModuleID;
	}

	public int getModuleID()	{
		return ModuleID;
	}

	public void clearInput() {
		startTime = 0;
		endTime = 0;

		startTimeText.setText("0:0:0");
		endTimeText.setText("0:0:0");
	}

	public Cue createCueFromInput(double startTime, double endTime) {return null;}
	public void populateInputFromCue(Cue cue) {}

	static private String formatTime(double time) {
		int intTime = (int)time;
	    int seconds = intTime % 60;
		int minutes = (intTime / 60) % 60;
		int hours = (intTime / 3600);
		
		return hours + ":" + minutes + ":" + seconds;
	}

	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();

		if (source == startTimeButton) {
			startTime = OlympiaMainGui.olympiaPlayer.mediaPlayer.getMediaTime().getSeconds();
			startTimeText.setText(formatTime(startTime));
		}

		else if (source == endTimeButton) {
			endTime = OlympiaMainGui.olympiaPlayer.mediaPlayer.getMediaTime().getSeconds();
			endTimeText.setText(formatTime(endTime));
		}

		else if (source == clearTimesButton) {
			startTime = 0;
			endTime = 0;

			startTimeText.setText("0:0:0");
			endTimeText.setText("0:0:0");
		}

		else if (source == clearButton) {
			clearInput();
		}

		else if (source == cancelButton) {
			OlympiaMainGui.g.useModule(null);
		}
	
		else if (source == saveButton) {
			if(startTime >= endTime) {
				JOptionPane.showMessageDialog(null, "Error. Start time should be less than end time.");
			}

			else {
				Cue newCue = createCueFromInput(startTime, endTime);

				if (newCue != null) {
					OlympiaMainGui.cpm.removeCue(EditCue);
					OlympiaMainGui.cpm.addCue(newCue);
					EditCue = null;
				}

				OlympiaMainGui.g.useModule(null);
				OlympiaMainGui.g.setupSouthContainer();
			}	
		}

		else if (source == deleteButton) {
			OlympiaMainGui.cpm.removeCue(EditCue);
			OlympiaMainGui.g.useModule(null);
		}

		else if (source.getClass() == CueButton.class) {
			OlympiaMainGui.g.useModule(this);

			EditCue = ((CueButton)source).getCue();
			populateInputFromCue(EditCue);

			startTime = EditCue.getStartTime();
			startTimeText.setText(formatTime(startTime));
			endTime = EditCue.getEndTime();
			endTimeText.setText(formatTime(endTime));
		}
	}
}

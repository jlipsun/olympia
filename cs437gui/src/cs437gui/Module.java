package cs437gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
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

	private JButton startTimeButton = new JButton();
	private JButton endTimeButton = new JButton();
	private JButton clearTimesButton = new JButton();

	private JButton clearButton = new JButton();
	private JButton cancelButton = new JButton();
	private JButton saveButton = new JButton();
	private JButton deleteButton = new JButton();

	private double startTime = 0;
	private double endTime = 0;

	private Cue EditCue;
	private ArrayList<Cue> ActiveCues = new ArrayList<Cue>();

	private JPanel ModuleStatus = new JPanel();

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

		ImageIcon startIcon = new ImageIcon("setStartTimeButton.jpg");
		Image startImage = startIcon.getImage();
		startImage = startImage.getScaledInstance(160, 40, Image.SCALE_SMOOTH);
		startIcon.setImage(startImage);
		startTimeButton.setIcon(startIcon);
		startTimeButton.setBorder(null);
		startTimeButton.setMargin(new Insets(0, 0, 0, 0));

		ImageIcon endIcon = new ImageIcon("setEndTimeButton.jpg");
		Image stopImage = endIcon.getImage();
		stopImage = stopImage.getScaledInstance(160, 40, Image.SCALE_SMOOTH);
		endIcon.setImage(stopImage);
		endTimeButton.setIcon(endIcon);
		endTimeButton.setBorder(null);
		endTimeButton.setMargin(new Insets(0, 0, 0, 0));
		
		ImageIcon timesIcon = new ImageIcon("clearTimesButton.jpg");
		Image timesImage = timesIcon.getImage();
		timesImage = timesImage.getScaledInstance(160, 40, Image.SCALE_SMOOTH);
		timesIcon.setImage(timesImage);
		clearTimesButton.setIcon(timesIcon);
		clearTimesButton.setBorder(null);
		clearTimesButton.setMargin(new Insets(0, 0, 0, 0));

		startTimeButton.addActionListener(this);
		endTimeButton.addActionListener(this);
	    clearTimesButton.addActionListener(this);

		JPanel timeButtonPanel = new JPanel(new GridLayout(1, 3));
		timeButtonPanel.add(startTimeButton);
		timeButtonPanel.add(endTimeButton);
		timeButtonPanel.add(clearTimesButton);

		JPanel timePanel = new JPanel(new BorderLayout());
		timePanel.setBorder(new TitledBorder("Time"));
		timePanel.add(timeOutputPanel, BorderLayout.NORTH);
		timePanel.add(timeButtonPanel, BorderLayout.CENTER);

		clearButton.addActionListener(this);
		cancelButton.addActionListener(this);
		saveButton.addActionListener(this);
		deleteButton.addActionListener(this);

		ImageIcon clearIcon = new ImageIcon("clearCueButton.jpg");
		Image clearImage = clearIcon.getImage();
		clearImage = clearImage.getScaledInstance(140, 35, Image.SCALE_SMOOTH);
		clearIcon.setImage(clearImage);
		clearButton.setIcon(clearIcon);
		clearButton.setBorder(null);
		clearButton.setMargin(new Insets(0, 0, 0, 0));

		ImageIcon cancelIcon = new ImageIcon("cancelButton.jpg");
		Image cancelImage = cancelIcon.getImage();
		cancelImage = cancelImage.getScaledInstance(140, 35, Image.SCALE_SMOOTH);
		cancelIcon.setImage(cancelImage);
		cancelButton.setIcon(cancelIcon);
		cancelButton.setBorder(null);
		cancelButton.setMargin(new Insets(0, 0, 0, 0));

		ImageIcon deleteIcon = new ImageIcon("deleteCueButton.jpg");
		Image deleteImage = deleteIcon.getImage();
		deleteImage = deleteImage.getScaledInstance(140, 35, Image.SCALE_SMOOTH);
		deleteIcon.setImage(deleteImage);
		deleteButton.setIcon(deleteIcon);
		deleteButton.setBorder(null);
		deleteButton.setMargin(new Insets(0, 0, 0, 0));

		ImageIcon saveIcon = new ImageIcon("saveCueButton.jpg");
		Image saveImage = saveIcon.getImage();
		saveImage = saveImage.getScaledInstance(140, 35, Image.SCALE_SMOOTH);
		saveIcon.setImage(saveImage);
		saveButton.setIcon(saveIcon);
		saveButton.setBorder(null);
		saveButton.setMargin(new Insets(0, 0, 0, 0));

		JPanel cueButtonPanel = new JPanel(new GridLayout(1, 4));
		cueButtonPanel.add(clearButton);
		cueButtonPanel.add(cancelButton);
		cueButtonPanel.add(saveButton);
		cueButtonPanel.add(deleteButton);
		
		JPanel commonPanel = new JPanel(new BorderLayout());
		commonPanel.add(timePanel, BorderLayout.CENTER);
		commonPanel.add(cueButtonPanel, BorderLayout.SOUTH);

		add(commonPanel, BorderLayout.SOUTH);

		this.ModuleID = ModuleID;

		updateStatus(ActiveCues, ModuleStatus);
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

	public ArrayList<Cue> createCueFromInput(double startTime, double endTime) {return null;}
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

		if (source == startTimeButton && OlympiaMainGui.olympiaPlayer != null) {
			startTime = OlympiaMainGui.olympiaPlayer.mediaPlayer.getMediaTime().getSeconds();
			startTimeText.setText(formatTime(startTime));
		}

		else if (source == endTimeButton && OlympiaMainGui.olympiaPlayer != null) {
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
			EditCue = null;
			OlympiaMainGui.g.useModule(null);
		}
	
		else if (source == saveButton) {
			if(startTime >= endTime) {
				JOptionPane.showMessageDialog(null, "Error. Start time should be less than end time.");
			}

			else {
				ArrayList<Cue> newCues = createCueFromInput(startTime, endTime);

				if (newCues != null && newCues.size() > 0) {
					OlympiaMainGui.cpm.removeCue(EditCue);

					for (int c = 0; c < newCues.size(); ++c) {
						Cue newCue = newCues.get(c);
						OlympiaMainGui.cpm.addCue(newCue);
					}

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
	}

	public void editCue(Cue cue) {
		OlympiaMainGui.g.useModule(this);

		EditCue = cue;
		populateInputFromCue(EditCue);

		startTime = EditCue.getStartTime();
		startTimeText.setText(formatTime(startTime));
		endTime = EditCue.getEndTime();
		endTimeText.setText(formatTime(endTime));
	}

	public void executeCue(Cue newCue) {
		ActiveCues.add(newCue);
		ModuleStatus.removeAll();
		updateStatus(ActiveCues, ModuleStatus);
		ModuleStatus.revalidate();
	}

	public void cancelCue(Cue oldCue) {
		ActiveCues.remove(oldCue);
		ModuleStatus.removeAll();
		updateStatus(ActiveCues, ModuleStatus);
		ModuleStatus.revalidate();
	}

	public void cancelAllCues() {
		ActiveCues.removeAll(ActiveCues);
		ModuleStatus.removeAll();
		updateStatus(ActiveCues, ModuleStatus);
	}

	public void updateStatus(ArrayList<Cue> activeCues, JPanel statusPanel) {
	}

	public JPanel getStatusPanel() {
		return ModuleStatus;
	}
}

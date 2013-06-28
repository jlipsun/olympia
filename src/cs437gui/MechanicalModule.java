package cs437gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class MechanicalModule extends Module {
	private static final long serialVersionUID = 1L;

	private JPanel startTimePanel = new JPanel(new BorderLayout(5, 5));
	private JPanel endTimePanel = new JPanel(new BorderLayout(5, 5));
	private JPanel timePanel = new JPanel(new GridLayout(1, 2, 10, 10));
	private JPanel mechPanel = new JPanel(new GridLayout(1, 1, 10, 10));
	private JPanel buttonsPanel = new JPanel(new GridLayout(2, 2, 5, 5));

	private JLabel startLabel = new JLabel(" Time starts: ");
	private JLabel startTextField = new JLabel("0:0:0");
	private JLabel endLabel = new JLabel("Time ends: ");
	private JLabel endTextField = new JLabel("0:0:0");

	private String[] lists = { "Curtain", "Backdrop", "Set Piece", "Trap Door" };
	private JList mechList = new JList(lists);

	private JButton saveButton = new JButton("Save");
	private JButton clearButton = new JButton("Clear");
	private JButton cancelButton = new JButton("Cancel");

	private int moduleID;
	private boolean status;
	
	private JButton startButton = new JButton("Set Start Time");
    private JButton endButton = new JButton("Set End Time");

    private double startTime;
    private double endTime;
    
    private int intTime;
    private int seconds;
    private int minutes;
    private int hours;
    private String secondsText;
    private String minutesText;
    private String hoursText;

	public MechanicalModule() {
		super(0);

		setLayout(new BorderLayout(10, 10));

		startTimePanel.add(startLabel, BorderLayout.WEST);
		startTimePanel.add(startTextField, BorderLayout.CENTER);
		endTimePanel.add(endLabel, BorderLayout.WEST);
		endTimePanel.add(endTextField, BorderLayout.CENTER);
		timePanel.add(startTimePanel);
		timePanel.add(endTimePanel);

		buttonsPanel.add(startButton);
		buttonsPanel.add(endButton);
		buttonsPanel.add(saveButton);
		buttonsPanel.add(clearButton);
		//buttonsPanel.add(cancelButton);

		mechPanel.setBorder(new TitledBorder("Mechanical Operations"));
		mechPanel.add(mechList);

		add(timePanel, BorderLayout.NORTH);
		add(mechPanel, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(startTime <= endTime){
					status = true;
				
					moduleID = mechList.getSelectedIndex();
				
					Cue cue = new MechanicalCue(startTime, endTime, moduleID);
				
					OlympiaMainGui.cpm.addCue(cue);
				}else
					JOptionPane.showMessageDialog(null, "Error. Start time should be less than end time.");
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    startTextField.setText("0:0:0");
                    endTextField.setText("0:0:0");
            }
		});
		
		 startButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
            	startTime = OlympiaMainGui.olympiaPlayer.mediaPlayer.getMediaTime().getSeconds();
             	intTime = (int)startTime;
         	    seconds = intTime%60;
         		minutes = (intTime/60) % 60;
         		hours = (intTime/3600);
         		
         		secondsText = Integer.toString(seconds);
         		minutesText = Integer.toString(minutes);
         		hoursText = Integer.toString(hours);
         		
         		startTextField.setText(hoursText + ":" + minutesText + ":" + secondsText);
             }
         });
         
         endButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
            	endTime = OlympiaMainGui.olympiaPlayer.mediaPlayer.getMediaTime().getSeconds();
             	intTime = (int)endTime;
         	    seconds = intTime%60;
         		minutes = (intTime/60) % 60;
         		hours = (intTime/3600);
         		
         		secondsText = Integer.toString(seconds);
         		minutesText = Integer.toString(minutes);
         		hoursText = Integer.toString(hours);
         		
         		endTextField.setText(hoursText + ":" + minutesText + ":" + secondsText);
             }
         });
	}

	public int getModuleID() {
		return moduleID;
	}

	public void setModuleID(int moduleID) {
		this.moduleID = moduleID;
	}

	public boolean isOn() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void executeCue(Cue cue) {
		this.status = true;

		if (mechList.getSelectedValue() == "Curtain") {
			this.moduleID = 1;
		} else if (mechList.getSelectedValue() == "Backdrop") {
			this.moduleID = 2;
		} else if (mechList.getSelectedValue() == "Set Piece") {
			this.moduleID = 3;
		} else if (mechList.getSelectedValue() == "Trap Door") {
			this.moduleID = 4;
		} else {
			System.out.println(mechList.getSelectedIndex());
		}

		// System.out.println(status);
		// System.out.println(this.moduleID);

		/*
		 * NewPosition moves = new NewPosition(); if (cue.getDirection() ==
		 * "left") { moves.left(); } else if (cue.getDirection() == "right") {
		 * moves.right(); } else if (cue.getDirection() == "up") { moves.up(); }
		 * else if (cue.getDirection() == "down") { moves.down(); } else if
		 * (cue.getDirection() == "left-right") { moves.leftRight(); } else if
		 * (cue.getDirection() == "up-down") { moves.upDown(); }
		 */
	}

}

class NewPosition extends JPanel {
	private static final long serialVersionUID = 1L;

	private int x;
	private int y;
	private int x2;
	private int y2;
	private int interval = 10;

	protected void paintComponent(Graphics g) {
		super.paint(g);
	}

	public void left() {
		x = getWidth();
		x -= interval;
		repaint();
	}

	public void right() {
		x += interval;
		repaint();
	}

	public void up() {
		y = getHeight();
		y -= interval;
		repaint();
	}

	public void down() {
		y += interval;
		repaint();
	}

	public void leftRight() {
		x = getWidth() / 2;
		x2 = getWidth() / 2;
		x -= interval;
		x2 += interval;
		repaint();
	}

	public void upDown() {
		y = getHeight() / 2;
		y2 = getHeight() / 2;
		y2 -= interval;
		y += interval;
		repaint();
	}

	public Dimension getPreferredSize() {
		return new Dimension();
	}
}
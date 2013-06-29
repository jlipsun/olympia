package cs437gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

public class MechanicalModule extends Module implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private GraphicDragLight setpiecedrag = new GraphicDragLight();

	private JPanel mechPanel = new JPanel(new BorderLayout());

	private JTabbedPane tabbedPanel = new JTabbedPane();
	private JPanel setpiecepanels = new JPanel(new BorderLayout());
	private JPanel stagepanel = new JPanel(new BorderLayout());
	

	
	private JPanel curtainOptionPanel = new JPanel(new BorderLayout());
	private JPanel backdropOptionPanel = new JPanel(new BorderLayout());
	private JPanel setPieceOptionPanel = new JPanel(new BorderLayout());
	private JPanel trapDoorOptionPanel = new JPanel(new BorderLayout());
	private JPanel setPiecePanel = new JPanel(new BorderLayout());
	
	private String curtain = "Curtain";
	private String backdrop = "Backdrop";
	private String setPiece = "Set Piece";
	private String trapDoor = "Trap Door";
	private JCheckBox jrbCurtain = new JCheckBox(curtain);
	private JCheckBox jrbBackdrop = new JCheckBox(backdrop);
	private JCheckBox jrbSetPiece = new JCheckBox(setPiece);
	private JCheckBox jrbTrapDoor = new JCheckBox(trapDoor);
	
	private String[] curtainLists = {"", "Up", "Down"};
	private JComboBox curtainBox = new JComboBox(curtainLists);
	
	private String[] backdropLists = {"", "Cathedral", "Island", "Paris city streets", "Skyline", "Garden", "Others"};
	private JComboBox backdropBox = new JComboBox(backdropLists);
	
	private String[] setPieceLists = {"Select Set Piece", "Arch", "Gate", "Cathedral front door", "Tower", "Column", "Others"};
	private JComboBox setPieceBox = new JComboBox(setPieceLists);
	
	private String[] trapDoorLists = {"", "Left", "Right"};
	private JComboBox trapDoorBox = new JComboBox(trapDoorLists);
	
	public MechanicalModule() {
		super(0);

		mechPanel.setBorder(new TitledBorder("Mechanical Operations"));
		
	    jrbCurtain.setActionCommand(curtain);
	    jrbBackdrop.setActionCommand(backdrop);
	    jrbSetPiece.setActionCommand(setPiece);
	    jrbTrapDoor.setActionCommand(trapDoor);
	    
	    curtainOptionPanel.add(jrbCurtain, BorderLayout.WEST);
	    curtainOptionPanel.add(curtainBox, BorderLayout.CENTER);
	    backdropOptionPanel.add(jrbBackdrop, BorderLayout.WEST);
	    backdropOptionPanel.add(backdropBox, BorderLayout.CENTER);
	    setPieceOptionPanel.add(jrbSetPiece, BorderLayout.WEST);
	    setPieceOptionPanel.add(setPieceBox, BorderLayout.CENTER);
	    trapDoorOptionPanel.add(jrbTrapDoor, BorderLayout.WEST);
		trapDoorOptionPanel.add(trapDoorBox, BorderLayout.CENTER);
		
		tabbedPanel.add(curtainOptionPanel, "Curtains");
		tabbedPanel.add(backdropOptionPanel, "Backdrop");
		tabbedPanel.add(trapDoorOptionPanel, "TrapDoor");
		
		ImageIcon theatre = new ImageIcon("seatingChart.jpg");
		stagepanel.add(setpiecedrag, BorderLayout.CENTER);
		stagepanel.add(new JLabel(theatre), BorderLayout.SOUTH);
		
		setpiecepanels.setBorder(new TitledBorder("Set Piece"));
		setpiecepanels.add(stagepanel, BorderLayout.CENTER);
		setpiecepanels.add(setPiecePanel, BorderLayout.SOUTH);
		
		mechPanel.add(setpiecepanels, BorderLayout.CENTER);
		mechPanel.add(tabbedPanel, BorderLayout.SOUTH);
		add(mechPanel, BorderLayout.CENTER);
		
		setPieceBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index= setPieceBox.getSelectedIndex();
				
				if (index < 0) {
					return;
				}
				setpiecedrag.setLabel(setPieceLists[index]);
			}
		});
	}

	public void clearInput() {
		super.clearInput();
		 jrbCurtain.setSelected(false);
		 jrbBackdrop.setSelected(false);
		 jrbSetPiece.setSelected(false);
		 jrbTrapDoor.setSelected(false);
		 curtainBox.setSelectedIndex(0);
		 backdropBox.setSelectedIndex(0);
		 setPieceBox.setSelectedIndex(0);
		 trapDoorBox.setSelectedIndex(0);
		 setpiecedrag.setXPosition(0);
		 setpiecedrag.setYPosition(0);
		 
		
	}

	public ArrayList<Cue> createCueFromInput(double startTime, double endTime) {
		ArrayList<Cue> newCues = new ArrayList<Cue>();

		if (jrbSetPiece.isSelected()==true) {
			int index= setPieceBox.getSelectedIndex();
			
			if (index > -1) {
				Cue cue = new MechanicalCue(startTime, endTime, 0, setPieceLists[index], setpiecedrag.getXPosition(),setpiecedrag.getYPosition());
				newCues.add(cue);
			}
			
				
		}
		
		if (jrbBackdrop.isSelected()==true) {
			int index= backdropBox.getSelectedIndex();
			
			if (index > -1) {
				Cue cue = new MechanicalCue(startTime, endTime, 1, backdropLists[index], 0,0);
				newCues.add(cue);
			}
		}
		
		if (jrbTrapDoor.isSelected()==true) {
			int index= trapDoorBox.getSelectedIndex();
			
			if (index > -1) {
				Cue cue = new MechanicalCue(startTime, endTime, 2, trapDoorLists[index], 0,0);
				newCues.add(cue);
			}
			
		}
		
		if (jrbCurtain.isSelected()==true) {
			int index= curtainBox.getSelectedIndex();
			
			if (index > -1) {
				Cue cue = new MechanicalCue(startTime, endTime, 3, curtainLists[index], 0,0);
				newCues.add(cue);
			}
			
			else JOptionPane.showMessageDialog(null, "Must select either up or down");
			
			
		}		
			
		if (newCues.size() == 0) {
			JOptionPane.showMessageDialog(null, "Error. No cues data was inputed.");
		}

		return newCues;
		
	}
	
	public void populateInputFromCue(Cue cue) {
		
		MechanicalCue mcue = (MechanicalCue)cue;
		//mechList.setSelectedIndex(mcue.getModuleID());
		
		clearInput();
		
		//populate cues is it set piece?  curtain, backdrop?
		
		if (mcue.getModuleID()==0 ) {
			//set piece
			jrbSetPiece.setSelected(true);
			setpiecedrag.setXPosition((int)mcue.getX());
			setpiecedrag.setYPosition((int)mcue.getY());
			setPieceBox.setSelectedItem(mcue.getDescription());
			
		}
		if (mcue.getModuleID()== 1) {
			//back drop
			jrbBackdrop.setSelected(true);
			backdropBox.setSelectedItem(mcue.getDescription());
		}
		if (mcue.getModuleID()== 2) {
			//trap door
			jrbTrapDoor.setSelected(true);
			trapDoorBox.setSelectedItem(mcue.getDescription());
			
		}
		if (mcue.getModuleID()== 3) {
			jrbCurtain.setSelected(true);
			curtainBox.setSelectedItem(mcue.getDescription());
		}
			
	}
	
	public void updateStatus(ArrayList<Cue> activeCues, JPanel statusPanel) {
		statusPanel.setLayout(new BorderLayout());
		statusPanel.setBackground(Color.WHITE);
		statusPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		ImageIcon icon;

		if (activeCues.size() > 0) {
			icon = new ImageIcon("mechEffectsON.jpg");
		}
		else {
			icon = new ImageIcon("mechEffectsOFF.jpg");
		}

		Image image = icon.getImage();
		image = image.getScaledInstance(200, 40, Image.SCALE_SMOOTH);
		icon.setImage(image);

		statusPanel.add(new JLabel(icon), BorderLayout.CENTER);
	}
}
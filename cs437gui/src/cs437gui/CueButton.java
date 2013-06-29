package cs437gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JToolTip;

public class CueButton extends JLabel implements MouseListener {
	private static final long serialVersionUID = 1L;

	private Cue cue;
	private Module module;

	public CueButton(Cue cue, int Height) {
		super("Cue");

		double start = 10 * cue.getStartTime();
		double end = 10 * cue.getEndTime();

		int intInTime = (int)cue.getStartTime();
    	int inSeconds = intInTime%60;
	    int inMinutes = (intInTime/60) % 60;
		int inHours = (intInTime/3600);
		String inTime = inHours + ":" + inMinutes + ":" + inSeconds;

		int intOutTime = (int)cue.getEndTime();
		int outSeconds = intOutTime%60;
	    int outMinutes = (intOutTime/60) % 60;
		int outHours = (intOutTime/3600);
		String outTime = outHours + ":" + outMinutes + ":" + outSeconds;

		String toolTip = "StartTime: " + inTime + "\nEndTime: " + outTime;

		switch (cue.getModuleID()) {
			default: // not associated with a time line just break;
				break;
			case 0:
			case 1:
			case 2:
			case 3: // put on mechanical cue
				module = OlympiaMainGui.g.moduleMechanical;
				MechanicalCue mechanicalCue = (MechanicalCue)cue;
				switch (mechanicalCue.getModuleID()) {
					case 0:	toolTip += "\nType: Set Piece";	break;
					case 1:	toolTip += "\nType: Backdrop";	break;
					case 2:	toolTip += "\nType: Trap Door";	break;
					case 3:	toolTip += "\nType: Curtain";	break;
				}
				break;
			case 4: // put on supertitle time line
				module = OlympiaMainGui.g.moduleSuperTitle;
				SupertitleCue supertitleCue = (SupertitleCue)cue;
				toolTip += "\nTitle: " + supertitleCue.getTitle();
				break;
			case 5: // put on light time line
				module = OlympiaMainGui.g.moduleLight;
				LightingCue lightingCue = (LightingCue)cue;
				toolTip += "\nPosition: (" + lightingCue.getXposition() + ", " + lightingCue.getYposition() + ")";
				toolTip += "\nColor: (" + lightingCue.getRvalue() + ", " + lightingCue.getGvalue() + ", " + lightingCue.getBvalue() + ")";
				toolTip += "\nPattern: " + lightingCue.getGoboPattern();
				toolTip += "\nFlood: " + lightingCue.getFlood();
				toolTip += "\nTilt: " + lightingCue.getTilt();
				toolTip += "\nPan: " + lightingCue.getPan();
				break;
			case 6: //pun on special effects time line
				module = OlympiaMainGui.g.moduleSpecialEffects;
				SpecialEffectsCue specialEffectsCue = (SpecialEffectsCue)cue;
				switch (specialEffectsCue.getSubModuleID()) {
					case 0:	toolTip += "\nType: Wind";	break;
					case 1:	toolTip += "\nType: Myst";	break;
					case 2:	toolTip += "\nType: Pyro";	break;
					case 3:	toolTip += "\nType: Fog";	break;
				}
				toolTip += "\nComponent: " + specialEffectsCue.getComponent();
				toolTip += "\nValue: " + specialEffectsCue.getValue();
				break;
		}

		setVerticalAlignment(JLabel.CENTER);
		setHorizontalAlignment(JLabel.CENTER);
		setOpaque(true);
		setBackground(new Color(0.6f, 0.6f, 0.9f, 1.0f));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setToolTipText(toolTip);
		setSize(new Dimension((int)(end - start), Height));
		addMouseListener(this);
		this.cue = cue;
	}

	public JToolTip createToolTip() {
		return new JMultiLineToolTip();
	}

	public Cue getCue() {
		return cue;
	}

	public void mouseClicked(MouseEvent e) {
		module.editCue(cue);
	}

	public void mouseEntered(MouseEvent e)	{}
	public void mouseExited(MouseEvent e)	{}
	public void mousePressed(MouseEvent e)	{}
	public void mouseReleased(MouseEvent e)	{}
}
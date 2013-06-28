package cs437gui;

import javax.swing.JButton;
import javax.swing.JToolTip;

public class CueButton extends JButton {
	private static final long serialVersionUID = 1L;

	private Cue cue;

	public CueButton(String Text, Cue cue) {
		super(Text);
		this.cue = cue;
	}

	public JToolTip createToolTip() {
		return new JMultiLineToolTip();
	}

	public Cue getCue() {
		return cue;
	}
}
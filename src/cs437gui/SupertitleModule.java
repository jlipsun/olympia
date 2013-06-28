package cs437gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class SupertitleModule extends Module {
	private static final long serialVersionUID = 1L;

	private JPanel titlePanel = new JPanel(new BorderLayout());
	private JTextArea titleArea = new JTextArea();

	public SupertitleModule() {
		// *** Call module super class constructor with moduleID ***
		super(4);

		// Create the panels for input
		// *** do not create time input stuff, it is created by module super class ***
		titlePanel.setBorder(new TitledBorder("Title"));
		titlePanel.add(titleArea);

		// Add the created panel to any location except south
		// *** south panel is reserved for time input stuff by module super class ***
		add(titlePanel, BorderLayout.CENTER);
	}

	public void clearInput() {
		// *** Call module super class clearInput() method to clear time inputs ***
		super.clearInput();

		// Clear all input fields
		titleArea.setText(null);
	}

	public Cue createCueFromInput(double startTime, double endTime) {
		// Create a new cue using supplied startTime and endTime
		SupertitleCue newCue = new SupertitleCue(startTime, endTime, titleArea.getText());
		return newCue;
	}

	public void populateInputFromCue(Cue cue) {
		// *** Type cast supplied cue to appropriate type ***
		SupertitleCue supertitleCue = (SupertitleCue)cue;

		// Populate all fields from type casted cue
		titleArea.setText(supertitleCue.getTitle());
	}
}

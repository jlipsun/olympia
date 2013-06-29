package cs437gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.util.ArrayList;

public class cueTrackingModule {

	ActionListener tracker = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// get current play time
			if (OlympiaMainGui.olympiaPlayer == null)				return;
			if (OlympiaMainGui.olympiaPlayer.mediaPlayer == null)	return;
			
			double time = OlympiaMainGui.olympiaPlayer.mediaPlayer.getMediaTime().getSeconds();

			// check for new active cues and send to module to execute
			ArrayList<Cue> allCues = OlympiaMainGui.cpm.getAllCues();
			ArrayList<Cue> newCues = new ArrayList<Cue>();
			for (int c = 0; c < allCues.size(); ++c) {
				Cue cue = allCues.get(c);

				if (cue.isActive(time) == false)	continue;

				int index = activeCues.indexOf(cue);
				if (index != -1)	continue;

				newCues.add(cue);

				switch (cue.getModuleID()) {
					default: // not associated with a module, just break;
						break;

					case 0:
					case 1:
					case 2:
					case 3: // mechanical cue
						// send cue to mechanical module to execute the cue
						break;

					case 4: // supertitle cue
						// send cue to supertitle module to execute the cue
						break;

					case 5: // lighting cue
						// send cue to lighting module to execute the cue
						break;

					case 6: // special effects cue
						// send cue to special effects module to execute the cue
						break;
				}
			}

			// check for cues no longer active and send to module to cancel
			ArrayList<Cue> finishedCues = new ArrayList<Cue>(); 
			for (int c = 0; c < activeCues.size(); ++c) {
				Cue cue = activeCues.get(c);

				if (cue.isActive(time) == true)	continue;

				finishedCues.add(cue);

				switch (cue.getModuleID()) {
					default: // not associated with a module, just break;
						break;

					case 0:
					case 1:
					case 2:
					case 3: // mechanical cue
						// send cue to mechanical module to cancel the cue
						break;

					case 4: // supertitle cue
						// send cue to supertitle module to cancel the cue
						break;

					case 5: // lighting cue
						// send cue to lighting module to cancel the cue
						break;

					case 6: // special effects cue
						// send cue to special effects module to cancel the cue
						break;
				}
			}

			activeCues.removeAll(finishedCues);
			activeCues.addAll(newCues);

			if (newCues.size() != 0 || finishedCues.size() != 0) {
				System.out.println("---------------");
				System.out.println("time: " + time);
				System.out.println("started: " + newCues.size());
				System.out.println("continuing: " + (activeCues.size() - newCues.size()));
				System.out.println("ended: " + finishedCues.size());
				System.out.println("---------------");
			}
		}
	};

	private Timer timer = new Timer(100, tracker);
	private ArrayList<Cue> activeCues = new ArrayList<Cue>();

	public cueTrackingModule() {
		timer.start();
	}

	public void clearAllCues() {
		activeCues.removeAll(activeCues);
	}
}

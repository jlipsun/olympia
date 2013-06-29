/*
 * This class will create the Player
 */

package cs437gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import java.net.URL;

import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.JPanel;

//WIKIPEDIA
class OlympiaPlayer extends JPanel{
		private static final long serialVersionUID = 1L;

		Player mediaPlayer;
		Component video, controls;
		
		//Constructor for the Main Gui
		public OlympiaPlayer(URL mediaURL){
					
			setLayout(new BorderLayout());
			Manager.setHint( Manager.LIGHTWEIGHT_RENDERER, true );
	
			try {
	
				mediaPlayer = Manager.createRealizedPlayer(mediaURL);
	
				video = mediaPlayer.getVisualComponent();
				
				controls = mediaPlayer.getControlPanelComponent();
				
				if(video != null)
					add(video, BorderLayout.NORTH);
				if(controls != null)
					add(controls, BorderLayout.SOUTH);
				mediaPlayer.start();
			}
			catch(NoPlayerException nomediaPlayer){
			}
			catch(CannotRealizeException cannotrelaizeexception){
			}
			catch(IOException ioexception){
			}
		}
}

//http://code.google.com/p/olympia-cs437/
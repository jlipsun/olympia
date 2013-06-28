package cs437gui;

import javax.swing.*;
import java.net.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.awt.*;

public class OlympiaMainGui extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	//Frame that will hold all the components
	private JFrame mainInterface;
		
	//The OlympiaPlayer
	static OlympiaPlayer olympiaPlayer;
		
	//Panels as containers to hold components for the main interface
	private JPanel northContainer, centerContainer, southContainer, eastContainer;

	// Create Modules
	private Module moduleCurrent, moduleSuperTitle, moduleMechanical, moduleSpecialEffects, moduleLight; 
	
	//Components for north container
	private JMenuBar menuBar;
	private JMenu file, add, create;
	
	//Menu items for the File option
	private JMenuItem newProjectMenu, loadProjectMenu, saveProjectMenu, aboutMenu, exitFileMenu; 
	
	//Menu items for the Add option
	private JMenuItem addVideoMenu, addLibrettoFileMenu, addScoreFileMenu; 
	
	//Menu items for the Create option
	private JMenuItem mechanicalCue, lightsCue, supertitleCue, specialEffectsCue;
	
    //Components for center container
    private JPanel videoPanel, statusPanel, videoSubContainerWest;
    private JButton addMechanical, addSuperTitles, addLight, addSpecialEffects;
	private JButton mechanicalStatus, superTitleStatus, lightStatus, specialEffectsStatus;
    
	//Components for east container
	JTabbedPane eastTabs;
	JPanel noCueSelected;
	
    //The URL for the player
	static URL videoURL;
	URL cuesURL;
	
	//JPanel to add the add buttons below video
	private JPanel panelToHoldAdd;
	
	//Instantiate cue module
	static cueProgrammingModule cpm = new cueProgrammingModule();
	static cueTrackingModule ctm = new cueTrackingModule();

	static OlympiaMainGui g;
	
	
	//Constructor
	public OlympiaMainGui(){
		
		mainInterface = new JFrame("Olympia");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		mainInterface.setSize(screenSize.width - 50, screenSize.height - 50);
		mainInterface.validate();
		mainInterface.setLocationRelativeTo(null);
		mainInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainInterface.setVisible(true);

		//Call methods to build the main interface and arrange panels
		setupNorthContainer();
		setupCenterContainer();
		setupEastContainer();
		setupSouthContainer();
		
		moduleCurrent = null;
		moduleSuperTitle = new SupertitleModule();
		moduleMechanical = new MechanicalModule();
		moduleLight = new LightModule();
		moduleSpecialEffects = new SpecialEffectsModule();
		
		g = (this);

		mainInterface.validate();
		mainInterface.repaint();
	}
	
	//Method that creates the add buttons and time lines to the south container
	public void setupSouthContainer(){
		// remove old time line from main interface
		if (southContainer != null)	mainInterface.remove(southContainer);
		southContainer = null;

		// set up a panel to hold time lines
		southContainer = new JPanel();
		southContainer.setLayout(new BorderLayout());

		// create panel to hold time line labels with *****setLatyout(NULL)***** and *****setPreferredSize()*****
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(null);
		labelPanel.setPreferredSize(new Dimension(80, 120));
		southContainer.add(labelPanel, BorderLayout.WEST);

		// create time line labels using *****setSize()***** and *****setLocation()*****
		JLabel supertitleLabel = new JLabel("Supertitles");
		supertitleLabel.setSize(100, 20);
		supertitleLabel.setLocation(0, 7);
		labelPanel.add(supertitleLabel);

		JLabel mechanicalLabel = new JLabel("Mechanical");
		mechanicalLabel.setSize(100, 20);
		mechanicalLabel.setLocation(0, 37);
		labelPanel.add(mechanicalLabel);
		
		JLabel lightLabel = new JLabel("Lights");
		lightLabel.setSize(100, 20);
		lightLabel.setLocation(0, 67);
		labelPanel.add(lightLabel);

		JLabel specialEffectsLabel = new JLabel("Special FX");
		specialEffectsLabel.setSize(100, 20);
		specialEffectsLabel.setLocation(0, 97);
		labelPanel.add(specialEffectsLabel);

		// create panel to hold cues with *****setLatyout(NULL)***** and *****setPreferredSize()*****
		int movieLength = 0;
		if (olympiaPlayer != null) {
			if (olympiaPlayer.mediaPlayer != null) {
				movieLength = (int)(10 * olympiaPlayer.mediaPlayer.getDuration().getSeconds());				
			}
		}
		JLayeredPane cuePanel = new JLayeredPane();
		cuePanel.setLayout(null);
		cuePanel.setPreferredSize(new Dimension(movieLength, 120));

		// create time line lines using *****setSize()***** and *****setLocation()*****
		JPanel supertitleTimeline = new JPanel();
		supertitleTimeline.setSize(movieLength, 31);
		supertitleTimeline.setLocation(0, 0);
		supertitleTimeline.setBackground(Color.WHITE);
		supertitleTimeline.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		cuePanel.add(supertitleTimeline);

		JPanel mechanicalTimeline = new JPanel();
		mechanicalTimeline.setSize(movieLength, 31);
		mechanicalTimeline.setLocation(0, 30);
		mechanicalTimeline.setBackground(Color.GRAY);
		mechanicalTimeline.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		cuePanel.add(mechanicalTimeline);

		JPanel lightTimeline = new JPanel();
		lightTimeline.setSize(movieLength, 31);
		lightTimeline.setLocation(0, 60);
		lightTimeline.setBackground(Color.YELLOW);
		lightTimeline.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		cuePanel.add(lightTimeline);

		JPanel specialEffectsTimeline = new JPanel();
		specialEffectsTimeline.setSize(movieLength, 30);
		specialEffectsTimeline.setLocation(0, 90);
		specialEffectsTimeline.setBackground(Color.RED);
		specialEffectsTimeline.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		cuePanel.add(specialEffectsTimeline);

		// set up all the cues in the appropriate time lines
		ArrayList<Cue> cues = cpm.getAllCues();
		for (int c = 0; c < cues.size(); ++c) {
			Cue cue = cues.get(c);

			int start = (int)(10 * cue.getStartTime());
			int end = (int)(10 * cue.getEndTime());

			int timeline = -1;

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

    		// create cue button using *****setSize()***** and *****setLocation()*****
			CueButton cueButton = new CueButton("Cue", cue);

			switch (cue.getModuleID()) {
				default: // not associated with a time line just break;
					break;
				case 0:
				case 1:
				case 2:
				case 3: // put on mechanical cue
					timeline = 1;
					MechanicalCue mechanicalCue = (MechanicalCue)cue;
					switch (mechanicalCue.getModuleID()) {
						case 0:	toolTip += "\nType: Curtain";	break;
						case 1:	toolTip += "\nType: Backdrop";	break;
						case 2:	toolTip += "\nType: Set Piece";	break;
						case 3:	toolTip += "\nType: Trap Door";	break;
					}
					break;
				case 4: // put on supertitle time line
					timeline = 0;
					cueButton.addActionListener(moduleSuperTitle);
					SupertitleCue supertitleCue = (SupertitleCue)cue;
					toolTip += "\nTitle: " + supertitleCue.getTitle();
					break;
				case 5: // put on light time line
					timeline = 2;
					LightingCue lightingCue = (LightingCue)cue;
					toolTip += "\nPosition: (" + lightingCue.getXposition() + ", " + lightingCue.getYposition() + ")";
					toolTip += "\nColor: (" + lightingCue.getRvalue() + ", " + lightingCue.getGvalue() + ", " + lightingCue.getBvalue() + ")";
					toolTip += "\nPattern: " + lightingCue.getGoboPattern();
					toolTip += "\nFlood: " + lightingCue.getFlood();
					toolTip += "\nTilt: " + lightingCue.getTilt();
					toolTip += "\nPan: " + lightingCue.getPan();
					break;
				case 6: //pun on special effects time line
					timeline = 3;
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

			cueButton.setSize(new Dimension(end - start, 20));
			cueButton.setLocation(start, 5 + 30 * timeline);
			cueButton.setToolTipText(toolTip);
			cuePanel.add(cueButton, 1, 0);
		}

		// create a scroll pane to hold the time line panel and always show horizontal scroll bar
		JScrollPane scrollPane = new JScrollPane(cuePanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		southContainer.add(scrollPane, BorderLayout.CENTER);
	
		// Add scroll pane to the main interface
		mainInterface.add(southContainer, BorderLayout.SOUTH);
		southContainer.revalidate();
	}
	
	//Method that creates the Menu Bar and current action to the north container
	public void setupNorthContainer(){
		
		//Create the north container
		northContainer = new JPanel(new BorderLayout());
		
		//The main menu bar that holds File, Player, and About
		menuBar = new JMenuBar();
		
		
		/*
		 * 
		 The following components are about the File menu
		 *
		 */
		
		//Add the option to click on File
		file = new JMenu("File");	
		
		//Under File, user can click on these options
		newProjectMenu = new JMenuItem("New Project");
		loadProjectMenu = new JMenuItem("Load Project");
		saveProjectMenu = new JMenuItem("Save Project");
		aboutMenu = new JMenuItem("About");
		exitFileMenu = new JMenuItem("Exit");
		
		file.add(newProjectMenu);
		file.add(loadProjectMenu);
		file.add(saveProjectMenu);
		file.add(aboutMenu);
		file.add(exitFileMenu);
		
		/*
		 * 
		 The following components are about the Add menu
		 *
		 */
		
		//Add the option to click on File
		add = new JMenu("Add");
		
		//Add the options to File
		addVideoMenu = new JMenuItem("Video");
		addLibrettoFileMenu = new JMenuItem("Libretto");
		addScoreFileMenu = new JMenuItem("Score");
		
		add.add(addVideoMenu);
		add.add(addLibrettoFileMenu);
		add.add(addScoreFileMenu);
		
		
		/*
		 * 
		 The following components are about the Player menu
		 *
		 */
		
		//Add the option to click on Player
		create = new JMenu("Create");
		
		//Under Player, user can click on these to apply effects. Initialization.
		mechanicalCue = new JMenuItem("Mechanical Effects");
		lightsCue = new JMenuItem("Light Effects");
		supertitleCue = new JMenuItem("Super Titles");
		specialEffectsCue = new JMenuItem("Special Effects");
		

		//Add the options under Player
		create.add(mechanicalCue);
		create.add(lightsCue);
		create.add(supertitleCue);
		create.add(specialEffectsCue);
				
		//Add File and Player to the main menu bar
		menuBar.add(file);
		menuBar.add(add);
		menuBar.add(create);
		
		//Add the main menu bar to the north container
		northContainer.add(menuBar, BorderLayout.NORTH);
		
		//Add north container to the main interface
		mainInterface.add(northContainer, BorderLayout.NORTH);
		
		//Add the Action Listeners
		newProjectMenu.addActionListener(this);
		loadProjectMenu.addActionListener(this);
		saveProjectMenu.addActionListener(this);
		aboutMenu.addActionListener(this);
		exitFileMenu.addActionListener(this);
		addVideoMenu.addActionListener(this);
		addLibrettoFileMenu.addActionListener(this);
		addScoreFileMenu.addActionListener(this);
		mechanicalCue.addActionListener(this);
		specialEffectsCue.addActionListener(this);
		lightsCue.addActionListener(this);
		supertitleCue.addActionListener(this);
	}
		
	//Method that creates the Panels for the center. It contains the video.
	public void setupCenterContainer(){
		
		//http://www.java2s.com/Code/Java/Swing-JFC/AsimpledemonstrationoftextalignmentinJLabels.htm
		//page 429 book: rows, column, gap horizontal, gap vertically
		centerContainer = new JPanel(new BorderLayout());

		//The panel that will show which effects are activated
		statusPanel = new JPanel(new GridLayout(1,4,0,0));
		
		//This is a sub container to hold the video
		videoSubContainerWest = new JPanel();
		
		//Initialize panel
		videoPanel = new JPanel();		
		
		//Add the video to the sub container
		videoSubContainerWest.add(videoPanel);
		
		//Add a button to display what current action is done and add it to the panel
		
		mechanicalStatus = new JButton("Mechanical: Off");
		superTitleStatus = new JButton("Super Title: Off");
		lightStatus = new JButton("Lights: Off");
		specialEffectsStatus = new JButton("Special Effect: Off");

		statusPanel.add(mechanicalStatus);
		statusPanel.add(superTitleStatus);
		statusPanel.add(lightStatus);
		statusPanel.add(specialEffectsStatus);

		centerContainer.add(statusPanel, BorderLayout.NORTH);
		
		//Add video sub container
		centerContainer.add(videoSubContainerWest, BorderLayout.CENTER);

		//Add center container to main interface
		mainInterface.add(centerContainer, BorderLayout.CENTER);
	}

	//Method that creates the Panels for the east side. It contains the cue editor, score and libretto.
	public void setupEastContainer() {
		eastContainer = new JPanel(new BorderLayout());
		eastContainer.setPreferredSize(new Dimension(600, 100));

		/*
		 * 
		 The following components are about add cue buttons
		 *
		 */

		panelToHoldAdd = new JPanel(new GridLayout(1,4,0,0));

		//Initialize buttons to add cues
		addMechanical = new JButton("Add Mechanics");
		addSuperTitles = new JButton("Add Supertitles");
		addLight = new JButton("Add Light");
		addSpecialEffects = new JButton("Add Special Effects");
		
		//Add the buttons to the panel that holds them
		panelToHoldAdd.add(addMechanical);
		panelToHoldAdd.add(addSuperTitles);
		panelToHoldAdd.add(addLight);
		panelToHoldAdd.add(addSpecialEffects);

		noCueSelected = new JPanel(new BorderLayout());
		noCueSelected.add(new JLabel("Select a cue or create a new one.", JLabel.CENTER), BorderLayout.CENTER);
		noCueSelected.add(panelToHoldAdd, BorderLayout.SOUTH);

		eastTabs = new JTabbedPane();
		eastTabs.add(noCueSelected, "Cue");
		eastTabs.add(new JPanel(), "Score");
		eastTabs.add(new JPanel(), "Libretto");
		
		eastContainer.add(eastTabs, BorderLayout.CENTER);
		mainInterface.add(eastContainer, BorderLayout.EAST);

		//Add action listeners
		addMechanical.addActionListener(this);
		addSuperTitles.addActionListener(this);
		addLight.addActionListener(this);	
		addSpecialEffects.addActionListener(this);
	}

	public void useModule(Module module) {
		if (module != null)	eastTabs.setComponentAt(0, module);
		else				eastTabs.setComponentAt(0, noCueSelected);

		eastTabs.setSelectedIndex(0);
		eastTabs.revalidate();
		eastTabs.repaint();

		moduleCurrent = module;
		if (module != null)	module.clearInput();
	}

	//Method to add Actions too all buttons and panels
	public void actionPerformed(ActionEvent e) {
		
		//North container
		if((e.getSource()== newProjectMenu)){
			newProject();
		}
		else if((e.getSource()== loadProjectMenu)){
			loadProject();
		}
		else if((e.getSource()== saveProjectMenu)){
			saveProject();
		}
		else if((e.getSource()== aboutMenu)){
			JOptionPane.showMessageDialog(mainInterface, "Olympia (C) Carl Potts, George Navarro, Amy Phengphet, Wing Yu, Jethro Lipsun.  This is for CS437 Winter 2011 for Jose Macias");
		}
		else if((e.getSource()== exitFileMenu)){
			System.exit(0);
		}

		if((e.getSource()== addVideoMenu)){
			addVideo();
		}
		else if((e.getSource()== addLibrettoFileMenu)){
			JOptionPane.showMessageDialog(mainInterface, "Not implemented yet");
		}
		else if((e.getSource()== addScoreFileMenu)){
			JOptionPane.showMessageDialog(mainInterface, "Not implemented yet");
		}

		else if((e.getSource()== mechanicalCue) || (e.getSource() == addMechanical)){
			useModule(moduleMechanical);
		}
		else if((e.getSource()== lightsCue) || (e.getSource()== addLight)){
			useModule(moduleLight);
		}
		else if((e.getSource()== supertitleCue) || (e.getSource() == addSuperTitles)){
			useModule(moduleSuperTitle);
		}
		else if((e.getSource()== specialEffectsCue) || (e.getSource() == addSpecialEffects)){
			useModule(moduleSpecialEffects);
		}

		mainInterface.validate();
		mainInterface.repaint();
	}
	
	//FROM GREEN BOOK
	//Method to open video when open is clicked on the File menu
	public void loadVideo(URL path) {
		if(path != null){
			videoURL = path;

			if (olympiaPlayer != null) {
				if (olympiaPlayer.mediaPlayer != null)	olympiaPlayer.mediaPlayer.close();
				videoPanel.remove(olympiaPlayer);
				olympiaPlayer = null;
			}

			olympiaPlayer = new OlympiaPlayer(path);
			videoPanel.add(olympiaPlayer, BorderLayout.NORTH);
		}
	}

	public void addVideo(){
		JFileChooser filechooser = new JFileChooser();
		int result= filechooser.showOpenDialog(null);
		
		if(result== JFileChooser.APPROVE_OPTION) {
			videoURL = null;
			try{
				videoURL = filechooser.getSelectedFile().toURI().toURL();
			}
			catch(MalformedURLException malformedURLException){
			}

			loadVideo(videoURL);
			setupSouthContainer();
		}
	}

	public void newProject() {
		if (olympiaPlayer != null) {
			olympiaPlayer.mediaPlayer.close();
			videoPanel.remove(olympiaPlayer);
			olympiaPlayer = null;
		}

		if (moduleCurrent != null)	mainInterface.remove(moduleCurrent);
		moduleCurrent = null;

		cpm.removeAllCues();
		ctm.clearAllCues();

		videoURL = null;
		cuesURL = null;

		useModule(null);
		setupSouthContainer();
	}

	public void loadProject() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			cuesURL = null;

			try {cuesURL = fileChooser.getSelectedFile().toURI().toURL();}
			catch (MalformedURLException malformedURLException) {}

			if (cuesURL != null) {
				if (olympiaPlayer != null) {
					olympiaPlayer.mediaPlayer.close();
					videoPanel.remove(olympiaPlayer);
					olympiaPlayer = null;
				}

				ctm.clearAllCues();
				
				if (cpm.loadCuesFromFile(cuesURL.getPath()) == false)
					JOptionPane.showMessageDialog(mainInterface, "Failed to load cues from file.");
			}
		}

		useModule(null);
		setupSouthContainer();
	}

	public void saveProject() {
		if (cuesURL == null) {
			JFileChooser fileChooser = new JFileChooser();
			int result = fileChooser.showSaveDialog(null);

			if (result == JFileChooser.APPROVE_OPTION) {
				try {cuesURL = fileChooser.getSelectedFile().toURI().toURL();}
				catch (MalformedURLException malformedURLException) {}
			}
		}

		if (cuesURL != null) {
			if (cpm.saveCuesToFile(cuesURL.getPath()) == false)
				JOptionPane.showMessageDialog(mainInterface, "Failed to save cues to file.");
		}
	}
	
	//Main method that starts up the GUI
	public static void main(String[]args){
		new OlympiaMainGui();
	}
}

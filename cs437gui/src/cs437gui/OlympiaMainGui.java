package cs437gui;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.media.Time;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class OlympiaMainGui extends JFrame implements ActionListener, AdjustmentListener, MouseListener {
	private static final long serialVersionUID = 1L;

	//Frame that will hold all the components
	private JFrame mainInterface;
		
	//The OlympiaPlayer
	static OlympiaPlayer olympiaPlayer;
		
	//Panels as containers to hold components for the main interface
	private JPanel northContainer, centerContainer, southContainer, eastContainer;

	// Create Modules
	public Module moduleCurrent, moduleSuperTitle, moduleMechanical, moduleSpecialEffects, moduleLight; 
	
	//Components for north container
	private JMenuBar menuBar;
	private JMenu file, add, create;
	
	//Menu items for the File option
	private JMenuItem newProjectMenu, loadProjectMenu, saveProjectMenu, aboutMenu, exitFileMenu; 
	
	//Menu items for the Add option
	private JMenuItem addVideoMenu, addLibrettoFileMenu, addScoreFileMenu, removeScoreFileMenu, removeLibrettoFileMenu;
 
	
	//Menu items for the Create option
	private JMenuItem mechanicalCue, lightsCue, supertitleCue, specialEffectsCue;
	
    //Components for center container
    //private JPanel videoPanel, videoSubContainerWest;
    private JPanel statusPanel;
    private JButton addMechanical, addSuperTitles, addLight, addSpecialEffects;
    
	//Components for east container
	JTabbedPane eastTabs;
	JPanel noCueSelected;

	//Components for time line scroll
	JScrollPane cueScroll, labelScroll, tickScroll;
	
    //The URL for the player
	static URL videoURL;
	URL cuesURL;
	
	//the URL for the libretto
	static URL librettoURL;
	//URL for score
	static URL scoreURL;
	
	//JPanel to add the add buttons below video
	private JPanel panelToHoldAdd;
	
	private JPanel playHead;
	
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

		moduleCurrent = null;
		moduleSuperTitle = new SupertitleModule();
		moduleMechanical = new MechanicalModule();
		moduleLight = new LightModule();
		moduleSpecialEffects = new SpecialEffectsModule();

		//Call methods to build the main interface and arrange panels
		setupNorthContainer();
		setupCenterContainer();
		setupEastContainer();
		setupSouthContainer();

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

		// create panel to hold cues with *****setLatyout(NULL)***** and *****setPreferredSize()*****
		int movieLength = 0;
		if (olympiaPlayer != null) {
			if (olympiaPlayer.mediaPlayer != null) {
				movieLength = (int)(10 * olympiaPlayer.mediaPlayer.getDuration().getSeconds());				
			}
		}
		JLayeredPane cuePanel = new JLayeredPane();
		cuePanel.addMouseListener(this);
		cuePanel.setLayout(null);

		// create time lines using *****setSize()***** and *****setLocation()*****
		ImageIcon supertitleGradient = new ImageIcon("tanGradientPNG.png");
		JLabel supertitleTimeline = new JLabel(supertitleGradient);
		supertitleTimeline.setLayout(null);
		supertitleTimeline.setSize(movieLength, 1);
		supertitleTimeline.setLocation(0, 0);
		supertitleTimeline.setBackground(Color.WHITE);
		supertitleTimeline.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		supertitleTimeline.addMouseListener(this);
		cuePanel.add(supertitleTimeline, 0);
		ArrayList<Integer> supertitleEnds = new ArrayList<Integer>();

		ImageIcon mechanicalGradient = new ImageIcon("brownGradientPNG.png");
		JLabel mechanicalTimeline = new JLabel(mechanicalGradient);
		mechanicalTimeline.setLayout(null);
		mechanicalTimeline.setSize(movieLength, 1);
		mechanicalTimeline.setLocation(0, 30);
		mechanicalTimeline.setBackground(Color.GRAY);
		mechanicalTimeline.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		mechanicalTimeline.addMouseListener(this);
		cuePanel.add(mechanicalTimeline);
		ArrayList<Integer> mechanicalEnds = new ArrayList<Integer>();

		ImageIcon lightGradient = new ImageIcon("goldGradientPNG.png");
		JLabel lightTimeline = new JLabel(lightGradient);
		lightTimeline.setLayout(null);
		lightTimeline.setSize(movieLength, 1);
		lightTimeline.setLocation(0, 60);
		lightTimeline.setBackground(Color.YELLOW);
		lightTimeline.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lightTimeline.addMouseListener(this);
		cuePanel.add(lightTimeline, 0);
		ArrayList<Integer> lightEnds = new ArrayList<Integer>();

		ImageIcon specialEffectsGradient = new ImageIcon("redGradientPNG.png");
		JLabel specialEffectsTimeline = new JLabel(specialEffectsGradient);
		specialEffectsTimeline.setLayout(null);
		specialEffectsTimeline.setSize(movieLength, 0);
		specialEffectsTimeline.setLocation(0, 90);
		specialEffectsTimeline.setBackground(Color.RED);
		specialEffectsTimeline.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		specialEffectsTimeline.addMouseListener(this);
		cuePanel.add(specialEffectsTimeline, 0);
		ArrayList<Integer> specialEffectsEnds = new ArrayList<Integer>();

		// create variable for dynamic time line
		int timelineBaseStart = 0;
		int timelineStackHeight = 30;
		int timelineInnerSpacing = 3;

		// set up all the cues in the appropriate time lines
		ArrayList<Cue> cues = cpm.getAllCues();
		for (int c = 0; c < cues.size(); ++c) {
			Cue cue = cues.get(c);

			double start = 10 * cue.getStartTime();
			double end = 10 * cue.getEndTime();

			ArrayList<Integer> ends = null;
			JLabel timelinePanel = null;

			switch (cue.getModuleID()) {
				default: // not associated with a time line just break;
					break;
				case 0:
				case 1:
				case 2:
				case 3: // put on mechanical cue
					ends = mechanicalEnds;
					timelinePanel = mechanicalTimeline;
					break;
				case 4: // put on supertitle time line
					ends = supertitleEnds;
					timelinePanel = supertitleTimeline;
					break;
				case 5: // put on light time line
					ends = lightEnds;
					timelinePanel = lightTimeline;
					break;
				case 6: //pun on special effects time line
					ends = specialEffectsEnds;
					timelinePanel = specialEffectsTimeline;
					break;
			}

			// Determine where in time line to place
			int timelineStack = 0;

			while (true) {
				if (timelineStack >= ends.size()) {
					Dimension size = timelinePanel.getSize();
					size.height += timelineStackHeight;
					timelinePanel.setSize(size);
					ends.add(0);
					break;
				}
				else if (ends.get(timelineStack) > start) {
					++timelineStack;
					continue;
				}
				else {
					break;
				}
			}

			ends.set(timelineStack, (int)end);

    		// create cue button using *****setLocation()*****
			CueButton cueButton = new CueButton(cue, timelineStackHeight - 2 * timelineInnerSpacing);
			cueButton.setLocation((int)start, timelineInnerSpacing + timelineStackHeight * timelineStack);
			timelinePanel.add(cueButton);
		}

		// make sure all time lines are a minimum height 
		Dimension size = null;

		size = supertitleTimeline.getSize();
		if (size.height < timelineStackHeight) {
			size.height += timelineStackHeight;
			supertitleTimeline.setSize(size);
		}

		size = mechanicalTimeline.getSize();
		if (size.height < timelineStackHeight) {
			size.height += timelineStackHeight;
			mechanicalTimeline.setSize(size);
		}

		size = lightTimeline.getSize();
		if (size.height < timelineStackHeight) {
			size.height += timelineStackHeight;
			lightTimeline.setSize(size);
		}

		size = specialEffectsTimeline.getSize();
		if (size.height < timelineStackHeight) {
			size.height += timelineStackHeight;
			specialEffectsTimeline.setSize(size);
		}

		// stack time lines 
		Point location = new Point(0, timelineBaseStart);

		supertitleTimeline.setLocation(location);
		location.y += supertitleTimeline.getSize().height - 1;

		mechanicalTimeline.setLocation(location);
		location.y += mechanicalTimeline.getSize().height - 1;

		lightTimeline.setLocation(location);
		location.y += lightTimeline.getSize().height - 1;

		specialEffectsTimeline.setLocation(location);
		location.y += specialEffectsTimeline.getSize().height - 1;

		if (movieLength > 0) {
			Image supertitleImage = supertitleGradient.getImage();
			supertitleGradient.setImage(supertitleImage.getScaledInstance(movieLength, supertitleTimeline.getSize().height, Image.SCALE_SMOOTH));
			
			Image mechanicalImage = mechanicalGradient.getImage();
			mechanicalGradient.setImage(mechanicalImage.getScaledInstance(movieLength, supertitleTimeline.getSize().height, Image.SCALE_SMOOTH));
			
			Image lightingImage = lightGradient.getImage();
			lightGradient.setImage(lightingImage.getScaledInstance(movieLength, supertitleTimeline.getSize().height, Image.SCALE_SMOOTH));
			
			Image specialImage = specialEffectsGradient.getImage();
			specialEffectsGradient.setImage(specialImage.getScaledInstance(movieLength, supertitleTimeline.getSize().height, Image.SCALE_SMOOTH));
		}

		JPanel rulerPanel = new JPanel();
		rulerPanel.setLayout(new BorderLayout());
		JPanel topLeftCorner = new JPanel();
		topLeftCorner.setPreferredSize(new Dimension(80, 30));
		JPanel topRightCorner = new JPanel();
		topRightCorner.setPreferredSize(new Dimension(20, 30));
		rulerPanel.add(topLeftCorner, BorderLayout.WEST);
		rulerPanel.add(topRightCorner, BorderLayout.EAST);

		Ticks ticks = new Ticks();
		ticks.setPreferredSize(new Dimension(movieLength, 25));
		ticks.addMouseListener(this);
		tickScroll = new JScrollPane(ticks);
		tickScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tickScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		tickScroll.setPreferredSize(new Dimension(0, 25));
		rulerPanel.add(tickScroll, BorderLayout.CENTER);
		
		southContainer.add(rulerPanel, BorderLayout.NORTH);

		// resize cue panel to adjust to time lines
		cuePanel.setPreferredSize(new Dimension(movieLength, location.y + 1));

		// create panel to hold time line labels with *****setLatyout(NULL)***** and *****setPreferredSize()*****
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(null);
		labelPanel.setPreferredSize(new Dimension(80, location.y + 1 + 40));
		//
		labelScroll = new JScrollPane(labelPanel);
		labelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		labelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		labelScroll.setPreferredSize(new Dimension(80, 140));
		southContainer.add(labelScroll, BorderLayout.WEST);

		// create time line labels using *****setSize()***** and *****setLocation()*****
		JLabel supertitleLabel = new JLabel("Supertitles");
		supertitleLabel.setVerticalTextPosition(JLabel.CENTER);
		supertitleLabel.setSize(80, supertitleTimeline.getSize().height);
		supertitleLabel.setLocation(0, supertitleTimeline.getLocation().y);
		labelPanel.add(supertitleLabel);

		JLabel mechanicalLabel = new JLabel("Mechanical");
		mechanicalLabel.setVerticalTextPosition(JLabel.CENTER);
		mechanicalLabel.setSize(80, mechanicalTimeline.getSize().height);
		mechanicalLabel.setLocation(0, mechanicalTimeline.getLocation().y);
		labelPanel.add(mechanicalLabel);

		JLabel lightLabel = new JLabel("Lights");
		lightLabel.setVerticalTextPosition(JLabel.CENTER);
		lightLabel.setSize(80, lightTimeline.getSize().height);
		lightLabel.setLocation(0, lightTimeline.getLocation().y);
		labelPanel.add(lightLabel);

		JLabel specialEffectsLabel = new JLabel("Special FX");
		specialEffectsLabel.setVerticalTextPosition(JLabel.CENTER);
		specialEffectsLabel.setSize(80, specialEffectsTimeline.getSize().height);
		specialEffectsLabel.setLocation(0, specialEffectsTimeline.getLocation().y);
		labelPanel.add(specialEffectsLabel);

		// create play head
		playHead = new JPanel();
		playHead.setBackground(Color.BLACK);
		playHead.setSize(1, specialEffectsLabel.getLocation().y + specialEffectsLabel.getSize().height + 1);
		cuePanel.add(playHead, 0);
		updatePlayheadPosition();

		// create a scroll pane to hold the time line panel and always show horizontal scroll bar
		cueScroll = new JScrollPane(cuePanel);
		cueScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		cueScroll.getHorizontalScrollBar().addAdjustmentListener(this);
		cueScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		cueScroll.getVerticalScrollBar().addAdjustmentListener(this);
		cueScroll.setPreferredSize(new Dimension(0, 140));
		southContainer.add(cueScroll, BorderLayout.CENTER);
	
		// Add scroll pane to the main interface
		mainInterface.add(southContainer, BorderLayout.SOUTH);
		southContainer.revalidate();
	}
	
	public void updatePlayheadPosition() {
		int newPosition = 0;

		if (OlympiaMainGui.olympiaPlayer != null) {
			if (OlympiaMainGui.olympiaPlayer.mediaPlayer != null) {
				newPosition = (int)(OlympiaMainGui.olympiaPlayer.mediaPlayer.getMediaTime().getSeconds() * 10);
			}
		}

		if (newPosition == playHead.getLocation().x)	return;

		playHead.setLocation(newPosition, 0);

		cueScroll.invalidate();
		cueScroll.revalidate();
		cueScroll.repaint();
	}
	
	//Method that creates the Menu Bar and current action to the north container
	public void setupNorthContainer(){
		
		//This is to let the file menu appear above the video
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		
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
		addScoreFileMenu = new JMenuItem("Score");
		addLibrettoFileMenu = new JMenuItem("Libretto");
		removeScoreFileMenu = new JMenuItem("Remove Score");
		removeLibrettoFileMenu = new JMenuItem("Remove Libretto");
		
		add.add(addVideoMenu);
		add.add(addScoreFileMenu);
		add.add(addLibrettoFileMenu);
		add.add(removeScoreFileMenu);
		add.add(removeLibrettoFileMenu);
		
		
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
		addScoreFileMenu.addActionListener(this);
		addLibrettoFileMenu.addActionListener(this);
		removeScoreFileMenu.addActionListener(this);
		removeLibrettoFileMenu.addActionListener(this);
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
		statusPanel = new JPanel(new GridLayout(1,3,0,0));
		
		//Add a button to display what current action is done and add it to the panel

		statusPanel.add(moduleMechanical.getStatusPanel());
		statusPanel.add(moduleLight.getStatusPanel());
		statusPanel.add(moduleSpecialEffects.getStatusPanel());

		centerContainer.add(statusPanel, BorderLayout.NORTH);
		centerContainer.add(moduleSuperTitle.getStatusPanel(), BorderLayout.SOUTH);

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
		addMechanical = new JButton();
		addSuperTitles = new JButton();
		addLight = new JButton();
		addSpecialEffects = new JButton();

		ImageIcon mechanicalIcon = new ImageIcon("addMechFX.jpg");
		Image mechanicalImage = mechanicalIcon.getImage();
		mechanicalImage = mechanicalImage.getScaledInstance(142, 40, Image.SCALE_SMOOTH);
		mechanicalIcon.setImage(mechanicalImage);
		addMechanical.setIcon(mechanicalIcon);
		addMechanical.setBorder(null);
		addMechanical.setMargin(new Insets(0, 0, 0, 0));

		ImageIcon superIcon = new ImageIcon("addSuperTitles.jpg");
		Image superImage = superIcon.getImage();
		superImage = superImage.getScaledInstance(142, 40, Image.SCALE_SMOOTH);
		superIcon.setImage(superImage);
		addSuperTitles.setIcon(superIcon);
		addSuperTitles.setBorder(null);
		addSuperTitles.setMargin(new Insets(0, 0, 0, 0));

		ImageIcon lightIcon = new ImageIcon("addLightFX.jpg");
		Image lightImage = lightIcon.getImage();
		lightImage = lightImage.getScaledInstance(142, 40, Image.SCALE_SMOOTH);
		lightIcon.setImage(lightImage);
		addLight.setIcon(lightIcon);
		addLight.setBorder(null);
		addLight.setMargin(new Insets(0, 0, 0, 0));

		ImageIcon specialIcon = new ImageIcon("addSpecialFx.jpg");
		Image specialImage = specialIcon.getImage();
		specialImage = specialImage.getScaledInstance(142, 40, Image.SCALE_SMOOTH);
		specialIcon.setImage(specialImage);
		addSpecialEffects.setIcon(specialIcon);
		addSpecialEffects.setBorder(null);
		addSpecialEffects.setMargin(new Insets(0, 0, 0, 0));

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
		if (module != null)	moduleCurrent.clearInput();
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
			addLibretto();
		}
		else if((e.getSource()== addScoreFileMenu)){
			addScore();
		}
		
		else if((e.getSource()== removeScoreFileMenu)){
			removeScore();
		}
		else if((e.getSource()== removeLibrettoFileMenu)){
			removeLibretto();
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
				if (olympiaPlayer.mediaPlayer != null) {
					olympiaPlayer.mediaPlayer.close();
				//videoPanel.remove(olympiaPlayer);
				centerContainer.remove(olympiaPlayer);
				}
				olympiaPlayer = null;
			}

			olympiaPlayer = new OlympiaPlayer(path);
			olympiaPlayer.setPreferredSize(new Dimension(100,100));
			centerContainer.add(olympiaPlayer, BorderLayout.CENTER);
			centerContainer.revalidate();
			centerContainer.repaint();
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

	public void addScore() {
		JFileChooser filechooser = new JFileChooser();
		int result= filechooser.showOpenDialog(null);

		if (result== JFileChooser.APPROVE_OPTION) {
			scoreURL = null;
			try{
				scoreURL = filechooser.getSelectedFile().toURI().toURL();
			}
			catch(MalformedURLException malformedURLException){
			}

			//call function that creates panel for that image.
			displayScore();
		}
	}

	public void displayScore() {
		ImageIcon i = new ImageIcon(scoreURL);

		ScrollablePicture scrollablePicture = new ScrollablePicture(i, 10);
		JScrollPane pictureScrollPane = new JScrollPane(scrollablePicture);	 

		eastTabs.setComponentAt(1, pictureScrollPane);
		eastTabs.setSelectedIndex(1);
	}

	public void removeScore() {		 			 
		eastTabs.setComponentAt(1, null);
		eastTabs.remove(1);
		eastTabs.add(new JPanel(), "Score", 1);
		eastTabs.setSelectedIndex(1);
	}

	public void addLibretto() {
		JFileChooser filechooser = new JFileChooser();
		int result= filechooser.showOpenDialog(null);

		if (result== JFileChooser.APPROVE_OPTION) {
			librettoURL = null;
			try{
				librettoURL = filechooser.getSelectedFile().toURI().toURL();
			}
			catch(MalformedURLException malformedURLException){
			}

			//call function that creates panel for that image.
			displayLibretto();
		}
	}

	public void displayLibretto() {
		ImageIcon i = new ImageIcon(librettoURL);

		ScrollablePicture scrollablePicture = new ScrollablePicture(i, 10);
		JScrollPane pictureScrollPane = new JScrollPane(scrollablePicture);

		eastTabs.setComponentAt(2, pictureScrollPane);
		eastTabs.setSelectedIndex(2);		 
	}

	public void removeLibretto() {		
		eastTabs.setComponentAt(2, null);
		eastTabs.remove(2);
		eastTabs.add(new JPanel(), "Libretto", 2);
		eastTabs.setSelectedIndex(2);
	}

	public void newProject() {
		if (olympiaPlayer != null) {
			olympiaPlayer.mediaPlayer.close();
			//videoPanel.remove(olympiaPlayer);
			olympiaPlayer = null;
		}

		cpm.removeAllCues();
		ctm.clearAllCues();

		videoURL = null;
		cuesURL = null;
		scoreURL = null;
		librettoURL = null;

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
					if (olympiaPlayer.mediaPlayer != null)	olympiaPlayer.mediaPlayer.close();
					//videoPanel.remove(olympiaPlayer);
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

	public void adjustmentValueChanged(AdjustmentEvent event) {
		if (event.getAdjustable().getOrientation() == Adjustable.VERTICAL)
			labelScroll.getVerticalScrollBar().setValue(event.getValue());
		if (event.getAdjustable().getOrientation() == Adjustable.HORIZONTAL) {
			tickScroll.getHorizontalScrollBar().setValue(event.getValue());
		}
	}

	public void mouseClicked(MouseEvent event) {
		if (olympiaPlayer == null)				return;
		if (olympiaPlayer.mediaPlayer == null)	return;

		double newLocation = (double)event.getPoint().x / (double)10;
		olympiaPlayer.mediaPlayer.setMediaTime(new Time(newLocation));
	}

	public void mouseEntered(MouseEvent event)	{}
	public void mouseExited(MouseEvent event)	{}
	public void mousePressed(MouseEvent event)	{}
	public void mouseReleased(MouseEvent event)	{}
}

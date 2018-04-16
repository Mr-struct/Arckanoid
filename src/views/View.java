package views;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import controlles.ControllerViews;
import controlles.SoundPlay;
import model.Modele;

@SuppressWarnings("serial")
/*
 * la vue principale 
 */
public class View extends JFrame {
	
	// les attributs//
	
	/////// les panneau ///////////////////////
	public PanelGame panelGame; //celui du jeu
	
	public JPanel thisPanel; // le panneau principal 
	
	public PanelMenu panelMenu; //le panneau du menu
	
	public PanelSettings panelSettings; // le panneau des parametre 
	
	protected LevelSelectView select; //le panneau de demo
	
	public PanelLevelSelect panelLevelSelect; //le panneau de selecteur de niveau 
	
	////// les boutons//////
	
	public ButtonImage tasksButton; 
		
	public ButtonImage settingsButton;
	
	public ButtonImage exitButton;
	
	public ButtonImage nextLevelButton;
	
	public ButtonImage backLevelSelectButton;
		
	public ButtonImage backButtonMenuFromSelect;
	
	public ButtonImage backButtonMenuFromSettings;
		
	public ButtonImage previousSelectButton;
	
	public ButtonImage restartButton;
	
	public ButtonImage nextSelectButton;
	
	protected ButtonImage backSelectButton;
	
	public ButtonImage pauseButton;
	
	public ButtonImage crossButton;
	
	public ButtonImage soundButton;
	
	public SoundPlay soundBT;

	/// les slider///////////////
	public JSlider sliderFxSound;
	
	protected JSlider sliderMainSound;
	
	public JSlider sliderLevel;
	
	//// les timers/////////////////
	public Timer timerPanelLevelSelect = new Timer();
	
	public Timer timerPanelMenu = new Timer();
	
	public Timer timerPanelSettings = new Timer();
	
	public Timer timerPanelGame = new Timer();
	
	public Timer timerView = new Timer();
	
	@SuppressWarnings("unused")
	private int numberOfLevels = 11;// le nombre de niveau -1 
	
	public int levelIndex = 0; // l index du niveau courant 
	
	protected ControllerViews ctrl; // le controleur de vue 
		
	public Modele modele;
	
	/**
	 * contructeur 
	 * 
	 * @param modele le modele Physique
	 */
	public View(Modele modele) {
		
		super("Arkanoid");
		
		this.modele = modele;
		
		this.setLayout(new BorderLayout());
		
		this.setSize(modele.gameWidth,modele.gameHeight);
		/*
		 * initialise tout les boutons
		 */
		this.soundBT = new SoundPlay();
		try {
			///initialise le button qui mene vers le selecteur de niveaux avec les bonnes images
			Image tasksDefaultImg = ImageIO.read(new File("./Obj/buttonsImages/play.png"));
			Image tasksEntredImg = ImageIO.read(new File("./Obj/buttonsImages/playEntred.png"));
			Image tasksSelectdImg = ImageIO.read(new File("./Obj/buttonsImages/playSelected.png"));
			//Image blockedPlayImg =  ImageIO.read(new File("./Obj/buttonsImages/Blockedplay.png"));
			tasksButton = new ButtonImage(tasksDefaultImg,tasksEntredImg,tasksSelectdImg,soundBT);
			
			//initialise le bouton settings du menu avec les bonnes images
			Image settingsDefaultImg = ImageIO.read(new File("./Obj/buttonsImages/settings.png"));
			Image settingsEntredImg = ImageIO.read(new File("./Obj/buttonsImages/settingsEntred.png"));
			Image settingsSelectdImg = ImageIO.read(new File("./Obj/buttonsImages/settingsSelected.png"));
			settingsButton = new ButtonImage(settingsDefaultImg,settingsEntredImg,settingsSelectdImg,soundBT);
			
			//initialise le bouton exit du menu avec les bonnes images
			Image exitDefaultImg = ImageIO.read(new File("./Obj/buttonsImages/exit.png"));
			Image exitEntredImg = ImageIO.read(new File("./Obj/buttonsImages/exitEntred.png"));
			Image exitSelectdImg = ImageIO.read(new File("./Obj/buttonsImages/exitSelected.png"));
			exitButton = new ButtonImage(exitDefaultImg,exitEntredImg,exitSelectdImg,soundBT);
		
	
			
			nextLevelButton =  new ButtonImage(tasksDefaultImg,tasksEntredImg,tasksSelectdImg,soundBT);
			
			//initialise le bouton back du settings les bonnes images
			Image backDefaultImg = ImageIO.read(new File("./Obj/buttonsImages/back.png"));
			Image backEntredImg = ImageIO.read(new File("./Obj/buttonsImages/backEntred.png"));
			Image backSelectdImg = ImageIO.read(new File("./Obj/buttonsImages/backSelected.png"));
			backButtonMenuFromSelect = new ButtonImage(backDefaultImg,backEntredImg,backSelectdImg,soundBT);
			backButtonMenuFromSettings = new ButtonImage(backDefaultImg,backEntredImg,backSelectdImg,soundBT);
			backLevelSelectButton = new ButtonImage(backDefaultImg,backEntredImg,backSelectdImg,soundBT);
			
			//initialise le bouton back du settings les bonnes images
			Image restartDefaultImg = ImageIO.read(new File("./Obj/buttonsImages/restart.png"));
			Image restartEntredImg = ImageIO.read(new File("./Obj/buttonsImages/restartEntred.png"));
			Image restartSelectdImg = ImageIO.read(new File("./Obj/buttonsImages/restartSelected.png"));
			
			restartButton = new ButtonImage(restartDefaultImg,restartEntredImg,restartSelectdImg,soundBT);
		
			//initialise le bouton pause du jeu
			Image pauseDefaultImg = ImageIO.read(new File("./Obj/buttonsImages/pause.png"));
			Image pauseEntredImg = ImageIO.read(new File("./Obj/buttonsImages/pauseEntred.png"));
			Image pasueSelectdImg = ImageIO.read(new File("./Obj/buttonsImages/pauseSelected.png"));
			
			pauseButton = new ButtonImage(pauseDefaultImg,
											pauseEntredImg,
											pasueSelectdImg
											,soundBT);
			
			//initialise le bouton pour annuler la pause du jeu
			Image crossDefaultImg = ImageIO.read(new File("./Obj/buttonsImages/cross.png"));
			Image crossEntredImg = ImageIO.read(new File("./Obj/buttonsImages/crossEntred.png"));
			Image crossSelectdImg = ImageIO.read(new File("./Obj/buttonsImages/crossSelected.png"));
			
			crossButton = new ButtonImage(crossDefaultImg,
											crossEntredImg,
											crossSelectdImg,soundBT);
			//initialise le bouton controleur du son du jeu
			Image soundDefaultImg = ImageIO.read(new File("./Obj/buttonsImages/soundOn.png"));
			Image soundEntredImg = ImageIO.read(new File("./Obj/buttonsImages/soundOnEntred.png"));
			Image soundSelectdImg = ImageIO.read(new File("./Obj/buttonsImages/soundOnSelected.png"));
			Image soundOffImg = ImageIO.read(new File("./Obj/buttonsImages/soundOff.png"));
			soundButton = new ButtonImage(soundDefaultImg,
											soundEntredImg,
											soundSelectdImg,soundOffImg,soundBT);
			soundButton.setEnabled(true);

			//init les boutons du slecteur de niveau 
			Image nextImg = ImageIO.read(new File("./Obj/buttonsImages/next.png"));
			Image nextEntredImg = ImageIO.read(new File("./Obj/buttonsImages/nextEntred.png"));
			Image nextSelectedImg = ImageIO.read(new File("./Obj/buttonsImages/nextSelected.png"));
			nextSelectButton = new ButtonImage(nextImg,nextEntredImg,nextSelectedImg,soundBT);
			
			Image previousImg = ImageIO.read(new File("./Obj/buttonsImages/previous.png"));
			Image previousEntredImg = ImageIO.read(new File("./Obj/buttonsImages/previousEntred.png"));
			Image previousSelectedImg = ImageIO.read(new File("./Obj/buttonsImages/previousSelected.png"));
			
			previousSelectButton = new ButtonImage(previousImg,previousEntredImg,previousSelectedImg,soundBT);
		
		} catch (IOException e) {
			
			System.out.println("l'image n'existe pas");
			
			e.printStackTrace();
		}
		/*
		 * init le slider du son principale 
		 */
		sliderMainSound = new JSlider(JSlider.HORIZONTAL, 0, 100, 10);
		
		/*
		 * init le slider des bruitage
		 */
		sliderFxSound = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
		/*
		 * init le slider du niveux de dificulte
		 */
		sliderLevel = new JSlider(JSlider.HORIZONTAL, 0, 100, 40);

		/*
		 * init le panneau du menu 
		 */
		panelMenu = new PanelMenu(this.getWidth(),this.getHeight(),this);
		
		/*
		 * init le panneau principal 
		 */
		thisPanel = new JPanel();
		
		thisPanel.setLayout(new BorderLayout());
		
		thisPanel.add(panelMenu, BorderLayout.CENTER);
		
		this.getContentPane().add(thisPanel);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		this.setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH); 
		
		//this.setUndecorated(true);

		setVisible(true);
							
		//init le controleur 
		
		ctrl  = new ControllerViews (this);
		
		//lance le menu 
		timerView.schedule(new TimerTask(){

			public void run(){
				
				panelMenu.repaint();
			}	

		}, 0, 20);
	}
		
}

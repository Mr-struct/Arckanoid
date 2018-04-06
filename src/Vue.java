
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

@SuppressWarnings("serial")
public class Vue extends JFrame implements MouseListener {
	
	protected PanelGame panelGame;
	protected JPanel thisPanel;
	protected PanelMenu panelMenu;
	protected PanelSettings panelSettings;
	protected JPanel blackPanel;
	protected PanelLevelSelect panelLevelSelect;
	protected ButtonImage tasksButton;
	protected ButtonImage playButton;
	protected ButtonImage settingsButton;
	protected ButtonImage exitButton;
	protected ButtonImage nextLevelButton;
	protected ButtonImage backLevelSelectButton;
	protected ButtonImage backButtonSetting;
	protected ButtonImage backButtonTasks;
	protected ButtonImage restartButton;
	protected ButtonImage pauseButton;
	protected ButtonImage crossButton;
	protected ButtonImage soundButton;
	protected SoundPlay soundBT;
	protected DefaultListModel<String> modelOfList = new DefaultListModel<String>();
	protected JList<String> list = new JList<String>(modelOfList);
	protected JScrollPane pane ;
	protected JSlider sliderFxSound;
	protected JSlider sliderMainSound;
	protected JSlider sliderLevel;
	protected Timer timerView = new Timer();
	private int numberOfLevels = 11;
	protected ControleurVue ctrl;
	ControleurJeu ctrlGame ;
	@SuppressWarnings("unused")
	protected Modele modele;
	
	public Vue(Modele modele) {
		
		super("Arkanoid");
		
		this.modele = modele;
		
		this.setLayout(new BorderLayout());
		
		modelOfList = new DefaultListModel<String>();
		
		list = new JList<String>(modelOfList);
		for (int i = 0; i <numberOfLevels; i++) {
			
			modelOfList.addElement("level"+i);
		}

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
			tasksButton = new ButtonImage(new ImageIcon(tasksDefaultImg),new ImageIcon(tasksEntredImg),new ImageIcon(tasksSelectdImg),soundBT);
			
			//initialise le bouton settings du menu avec les bonnes images
			Image settingsDefaultImg = ImageIO.read(new File("./Obj/buttonsImages/settings.png"));
			Image settingsEntredImg = ImageIO.read(new File("./Obj/buttonsImages/settingsEntred.png"));
			Image settingsSelectdImg = ImageIO.read(new File("./Obj/buttonsImages/settingsSelected.png"));
			settingsButton = new ButtonImage(new ImageIcon(settingsDefaultImg),new ImageIcon(settingsEntredImg),new ImageIcon(settingsSelectdImg),soundBT);
			
			//initialise le bouton exit du menu avec les bonnes images
			Image exitDefaultImg = ImageIO.read(new File("./Obj/buttonsImages/exit.png"));
			Image exitEntredImg = ImageIO.read(new File("./Obj/buttonsImages/exitEntred.png"));
			Image exitSelectdImg = ImageIO.read(new File("./Obj/buttonsImages/exitSelected.png"));
			exitButton = new ButtonImage(new ImageIcon(exitDefaultImg),new ImageIcon(exitEntredImg),new ImageIcon(exitSelectdImg),soundBT);
			
			//initialise le bouton play du selectioneur de niveau avec les bonnes images
			playButton = new ButtonImage(new ImageIcon(tasksDefaultImg),new ImageIcon(tasksEntredImg),new ImageIcon(tasksSelectdImg),soundBT);
			//playButton.setEnabled(true);
			//playButton.setDisabledIcon(new ImageIcon(blockedPlayImg));
			
			nextLevelButton =  new ButtonImage(new ImageIcon(tasksDefaultImg),new ImageIcon(tasksEntredImg),new ImageIcon(tasksSelectdImg),soundBT);
			
			//initialise le bouton back du settings les bonnes images
			Image backDefaultImg = ImageIO.read(new File("./Obj/buttonsImages/back.png"));
			Image backEntredImg = ImageIO.read(new File("./Obj/buttonsImages/backEntred.png"));
			Image backSelectdImg = ImageIO.read(new File("./Obj/buttonsImages/backSelected.png"));
			backButtonSetting = new ButtonImage(new ImageIcon(backDefaultImg),new ImageIcon(backEntredImg),new ImageIcon(backSelectdImg),soundBT);
			
			backButtonTasks = new ButtonImage(new ImageIcon(backDefaultImg),new ImageIcon(backEntredImg),new ImageIcon(backSelectdImg),soundBT);
			backLevelSelectButton = new ButtonImage(new ImageIcon(backDefaultImg),new ImageIcon(backEntredImg),new ImageIcon(backSelectdImg),soundBT);
			
			//initialise le bouton back du settings les bonnes images
			Image restartDefaultImg = ImageIO.read(new File("./Obj/buttonsImages/restart.png"));
			Image restartEntredImg = ImageIO.read(new File("./Obj/buttonsImages/restartEntred.png"));
			Image restartSelectdImg = ImageIO.read(new File("./Obj/buttonsImages/restartSelected.png"));
			
			restartButton = new ButtonImage(new ImageIcon(restartDefaultImg),new ImageIcon(restartEntredImg),new ImageIcon(restartSelectdImg),soundBT);
		
			//initialise le bouton pause du jeu
			Image pauseDefaultImg = ImageIO.read(new File("./Obj/buttonsImages/pause.png"));
			Image pauseEntredImg = ImageIO.read(new File("./Obj/buttonsImages/pauseEntred.png"));
			Image pasueSelectdImg = ImageIO.read(new File("./Obj/buttonsImages/pauseSelected.png"));
			
			pauseButton = new ButtonImage(new ImageIcon(pauseDefaultImg),
											new ImageIcon(pauseEntredImg),
											new ImageIcon(pasueSelectdImg)
											,soundBT);
			
			//initialise le bouton  pour annuler la pause du jeu
			Image crossDefaultImg = ImageIO.read(new File("./Obj/buttonsImages/cross.png"));
			Image crossEntredImg = ImageIO.read(new File("./Obj/buttonsImages/crossEntred.png"));
			Image crossSelectdImg = ImageIO.read(new File("./Obj/buttonsImages/crossSelected.png"));
			
			crossButton = new ButtonImage(new ImageIcon(crossDefaultImg),
											new ImageIcon(crossEntredImg),
											new ImageIcon(crossSelectdImg),soundBT);
			//initialise le bouton controle son du jeu
			Image soundDefaultImg = ImageIO.read(new File("./Obj/buttonsImages/soundOn.png"));
			Image soundEntredImg = ImageIO.read(new File("./Obj/buttonsImages/soundOnEntred.png"));
			Image soundSelectdImg = ImageIO.read(new File("./Obj/buttonsImages/soundOnSelected.png"));
			
			soundButton = new ButtonImage(new ImageIcon(soundDefaultImg),
											new ImageIcon(soundEntredImg),
											new ImageIcon(soundSelectdImg),soundBT);
			soundButton.setEnabled(true);
			soundButton.setDisabledIcon(new ImageIcon(ImageIO.read(new File("./Obj/buttonsImages/soundOff.png"))));
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
		sliderLevel = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);

		panelMenu = new PanelMenu(this.getWidth(),this.getHeight(),this);
		
		thisPanel = new JPanel();
		
		thisPanel.setLayout(new BorderLayout());
		
		thisPanel.add(panelMenu, BorderLayout.CENTER);
		
		this.getContentPane().add(thisPanel);
		
		this.setLocationRelativeTo(null);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		this.setUndecorated(true);
		
		this.setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH); 


		setVisible(true);
							
		this.addMouseListener(this);
		
		ctrl  = new ControleurVue (modele,this);
		
		timerView.schedule(new TimerTask(){

			public void run(){
				
				panelMenu.repaint();
			}	

		}, 0, 20);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(e.getClickCount());

		if(e.getClickCount() == 10) {
			System.out.println(e.getClickCount());
			modelOfList.addElement("Akira");
			modelOfList.addElement("GodZila");
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	 
		
}

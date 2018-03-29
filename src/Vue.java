
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

@SuppressWarnings("serial")
public class Vue extends JFrame {
	
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
	protected DefaultListModel<String> modelOfList = new DefaultListModel<String>();
	protected JList<String> list = new JList<String>(modelOfList);
	protected JScrollPane pane ;
	protected JSlider sliderFxSound;
	protected JSlider sliderMainSound;
	protected JSlider sliderLevel;
	protected Timer timerView = new Timer();
	private int numberOfLevels = 11;
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

		try {
			///initialise le button qui mene vers le selecteur de niveaux avec les bonnes images
			Image tasksDefaultImg = ImageIO.read(new File("./Obj/buttonsImages/play.png"));
			Image tasksEntredImg = ImageIO.read(new File("./Obj/buttonsImages/playEntred.png"));
			Image tasksSelectdImg = ImageIO.read(new File("./Obj/buttonsImages/playSelected.png"));
			tasksButton = new ButtonImage(new ImageIcon(tasksDefaultImg),new ImageIcon(tasksEntredImg),new ImageIcon(tasksSelectdImg));
			
			//initialise le bouton settings du menu avec les bonnes images
			Image settingsDefaultImg = ImageIO.read(new File("./Obj/buttonsImages/settings.png"));
			Image settingsEntredImg = ImageIO.read(new File("./Obj/buttonsImages/settingsEntred.png"));
			Image settingsSelectdImg = ImageIO.read(new File("./Obj/buttonsImages/settingsSelected.png"));
			settingsButton = new ButtonImage(new ImageIcon(settingsDefaultImg),new ImageIcon(settingsEntredImg),new ImageIcon(settingsSelectdImg));
			
			//initialise le bouton exit du menu avec les bonnes images
			Image exitDefaultImg = ImageIO.read(new File("./Obj/buttonsImages/exit.png"));
			Image exitEntredImg = ImageIO.read(new File("./Obj/buttonsImages/exitEntred.png"));
			Image exitSelectdImg = ImageIO.read(new File("./Obj/buttonsImages/exitSelected.png"));
			exitButton = new ButtonImage(new ImageIcon(exitDefaultImg),new ImageIcon(exitEntredImg),new ImageIcon(exitSelectdImg));
			
			//initialise le bouton play du selectioneur de niveau avec les bonnes images
			playButton = new ButtonImage(new ImageIcon(tasksDefaultImg),new ImageIcon(tasksEntredImg),new ImageIcon(tasksSelectdImg));
			
			nextLevelButton =  new ButtonImage(new ImageIcon(tasksDefaultImg),new ImageIcon(tasksEntredImg),new ImageIcon(tasksSelectdImg));
			
			//initialise le bouton back du settings les bonnes images
			Image backDefaultImg = ImageIO.read(new File("./Obj/buttonsImages/back.png"));
			Image backEntredImg = ImageIO.read(new File("./Obj/buttonsImages/backEntred.png"));
			Image backSelectdImg = ImageIO.read(new File("./Obj/buttonsImages/backSelected.png"));
			backButtonSetting = new ButtonImage(new ImageIcon(backDefaultImg),new ImageIcon(backEntredImg),new ImageIcon(backSelectdImg));
			
			backButtonTasks = new ButtonImage(new ImageIcon(backDefaultImg),new ImageIcon(backEntredImg),new ImageIcon(backSelectdImg));
			backLevelSelectButton = new ButtonImage(new ImageIcon(backDefaultImg),new ImageIcon(backEntredImg),new ImageIcon(backSelectdImg));
			
			//initialise le bouton back du settings les bonnes images
			Image restartDefaultImg = ImageIO.read(new File("./Obj/buttonsImages/restart.png"));
			Image restartEntredImg = ImageIO.read(new File("./Obj/buttonsImages/restartEntred.png"));
			Image restartSelectdImg = ImageIO.read(new File("./Obj/buttonsImages/restartSelected.png"));
			
			restartButton = new ButtonImage(new ImageIcon(restartDefaultImg),new ImageIcon(restartEntredImg),new ImageIcon(restartSelectdImg));
		
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
		
		setVisible(true);
		
		this.setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH); 
		
		ControleurVue ctrl  = new ControleurVue (modele,this);
		
		timerView.schedule(new TimerTask(){

			public void run(){
				
				panelMenu.repaint();
			}	

		}, 0, 20);

		
	}
		
}

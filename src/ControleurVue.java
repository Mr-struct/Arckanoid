import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/*
 */

public class ControleurVue {

	private Modele modele;
	private Vue vue;
	private Timer timerPanelLevelSelect = new Timer();
	private Timer timerPanelMenu = new Timer();
	private Timer timerPanelSettings = new Timer();
	private Timer timerPanelGame = new Timer();
	// constructeur 
	public ControleurVue(Modele modele, Vue vue) {
		this.modele = modele;
		this.vue = vue;

		//gere le clic sur le boutton nouveau jeux
		modele.newGame.addMouseListener(new MouseListener() {public void mouseClicked(MouseEvent e) {
				vue.thisPanel.removeAll();

				vue.panelLevelSelect = new PanelLevelSelect(vue.getWidth(),vue.getHeight(),modele);

				vue.thisPanel.add(vue.panelLevelSelect,BorderLayout.CENTER);

				timerPanelLevelSelect = new Timer();
				
				timerPanelMenu.cancel();
				
				timerPanelSettings.cancel();
				
				timerPanelGame.cancel();
				
				timerPanelLevelSelect.schedule(new TimerTask(){

					public void run(){
						
						modele.pane.revalidate();
						
						modele.pane.repaint();
						
						vue.panelLevelSelect.repaint();
					}

				}, 0, 20);
			}



			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}	
		});




		//gere le clic sur le boutton exit
		modele.exit.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) {
				
				vue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				vue.dispose();
				
				System.exit(0);

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				vue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				vue.dispose();
				
				System.exit(0);
				 

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		} );

		//gere le clic sur le button settings
		modele.settings.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				vue.thisPanel.removeAll();
				
				vue.panelSettings = new PanelSettings(vue.getWidth(),vue.getHeight(),modele);
				
				vue.thisPanel.add(vue.panelSettings,BorderLayout.CENTER);
				
				vue.thisPanel.repaint();
				
				//mise a jour du panelGame (20 milisecondes = 1/50 secondes
				
				timerPanelLevelSelect.cancel();
				
				timerPanelMenu.cancel();
				
				timerPanelGame.cancel();
				
				timerPanelSettings = new Timer();
				
				timerPanelSettings.schedule(new TimerTask(){
					
					public void run(){
						
						vue.panelSettings.repaint();

					}
					
				}, 0, 20);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}	
		});

		//gere le clic sur le button back du menu settings
		modele.backButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

				vue.thisPanel.removeAll();

				vue.panelMenu = new PanelMenu(vue.getWidth(),vue.getHeight(),modele);

				vue.thisPanel.add(vue.panelMenu,BorderLayout.CENTER);
				
				timerPanelLevelSelect.cancel();
				
				timerPanelGame.cancel();
				
				timerPanelSettings.cancel();
				
				timerPanelMenu = new Timer();
				
				timerPanelMenu.schedule(new TimerTask(){

					public void run(){

						vue.panelMenu.repaint();

					}

				}, 0, 20);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		modele.sliderFxSound.addChangeListener(e->soundFxChange());

		modele.sliderLevel.addChangeListener( e-> levelChange());
		
		modele.list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				JList list = (JList) e.getSource();

				int selections[] = list.getSelectedIndices();

				Object selectionValues[] = list.getSelectedValues();
				
				String selectionString = (String)list.getSelectedValue();
				
				String []levelGeted = selectionString.split("[|]");
				
				System.out.println(levelGeted[0]);
				
				try {
					modele.intitlevel("./levels/"+levelGeted[0]+".txt");
					
				} catch (IOException e2) {
					
					// TODO Auto-generated catch block
					
					e2.printStackTrace();
					
				}
				
				try {
					
					vue.panelLevelSelect.imgBackground = ImageIO.read(new File("./levels/"+levelGeted[0]+".txt.png"));
					
				} catch (IOException e1) {
					
					// TODO Auto-generated catch block
					
					e1.printStackTrace();
				}		
			}
		});
		vue.modele.backBnt2.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				vue.thisPanel.removeAll();

				vue.panelMenu = new PanelMenu(vue.getWidth(),vue.getHeight(),modele);

				vue.thisPanel.add(vue.panelMenu,BorderLayout.CENTER);
				
				timerPanelLevelSelect.cancel();
				
				timerPanelGame.cancel();
				
				timerPanelSettings.cancel();
				
				timerPanelMenu = new Timer();
				
				timerPanelMenu.schedule(new TimerTask(){

					public void run(){

						vue.panelMenu.repaint();

					}

				}, 0, 20);

				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		vue.modele.play.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				vue.thisPanel.removeAll();
				
				vue.panelGame = new PanelGame(vue.getWidth(),vue.getHeight(),modele);
				
				ControleurJeu controleurJeu = new ControleurJeu(modele, vue.panelGame);
				
				vue.panelGame.addMouseMotionListener(controleurJeu);
				
				vue.panelGame.addMouseListener(controleurJeu);
				
				vue.thisPanel.add(vue.panelGame,BorderLayout.CENTER);
				
				modele.lancerJeu();
				
				timerPanelLevelSelect.cancel();
				
				timerPanelSettings.cancel();
				
				timerPanelMenu.cancel();;
				
				timerPanelGame = new Timer();
				
				timerPanelGame.schedule(new TimerTask(){
					
					public void run(){
						
						vue.panelGame.repaint();
					}
				}, 0, 20);
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

		});
	}

	public void levelChange() {
		modele.levelDifficulty = 1+(modele.sliderLevel.getValue()/10); 
		System.out.println(modele.levelDifficulty);
	}
	public void soundFxChange() {
		modele.impactSound.volume = modele.sliderFxSound.getValue();
		//modele.backButton.sound.volume = modele.sliderFxSound.getValue();
		//modele.newGame.sound.volume = modele.sliderFxSound.getValue();
		//modele.settings.sound.volume = modele.sliderFxSound.getValue();
		//modele.exit.sound.volume = modele.sliderFxSound.getValue();
		modele.impactSound.note_on(80);
	}

}
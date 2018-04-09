import java.awt.BorderLayout;
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
	private int currenteVolume;
	// constructeur 
	public ControleurVue(Modele modele, Vue vue) {
		this.modele = modele;
		this.vue = vue;
		this.currenteVolume = this.vue.soundBT.volume;

		//gere le clic sur le boutton nouveau jeux
		this.vue.tasksButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {vue.thisPanel.removeAll();

			vue.panelLevelSelect = new PanelLevelSelect(vue);

			vue.thisPanel.add(vue.panelLevelSelect,BorderLayout.CENTER);

			timerPanelLevelSelect = new Timer();
			
			timerPanelMenu.cancel();
			
			timerPanelSettings.cancel();
			
			timerPanelGame.cancel();
			
			vue.timerView.cancel();
			
			timerPanelLevelSelect.schedule(new TimerTask(){

				public void run(){
					
					vue.pane.revalidate();
					
					vue.pane.repaint();
					
					vue.panelLevelSelect.repaint();
				}

			}, 0, 20);

		}
			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}	
		});




		//gere le clic sur le boutton exit
		this.vue.exitButton.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) {
				
				vue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				vue.dispose();
				
				System.exit(0);

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {	}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}
		} );

		//gere le clic sur le button settings
		this.vue.settingsButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				vue.thisPanel.removeAll();
				
				vue.panelSettings = new PanelSettings(vue.getWidth(),vue.getHeight(),vue);
				
				vue.thisPanel.add(vue.panelSettings,BorderLayout.CENTER);
				
				vue.thisPanel.repaint();
				
				//mise a jour du panelGame (20 milisecondes = 1/50 secondes
				
				timerPanelLevelSelect.cancel();
				
				timerPanelMenu.cancel();
				
				timerPanelGame.cancel();
				
				vue.timerView.cancel();
				
				timerPanelSettings = new Timer();
				
				timerPanelSettings.schedule(new TimerTask(){
					
					public void run(){
						
						vue.panelSettings.repaint();

					}
					
				}, 0, 20);

			}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}	
		});

		//gere le clic sur le button back du menu settings
		this.vue.backButtonSetting.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

				vue.thisPanel.removeAll();

				vue.panelMenu = new PanelMenu(vue.getWidth(),vue.getHeight(),vue);

				vue.thisPanel.add(vue.panelMenu,BorderLayout.CENTER);
				
				timerPanelLevelSelect.cancel();
				
				timerPanelGame.cancel();
				
				timerPanelSettings.cancel();
				
				vue.timerView.cancel();
				
				timerPanelMenu = new Timer();
				
				timerPanelMenu.schedule(new TimerTask(){

					public void run(){

						vue.panelMenu.repaint();

					}

				}, 0, 20);
			}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}
		});

		vue.sliderFxSound.addChangeListener(e->soundFxChange());

		vue.sliderLevel.addChangeListener( e-> levelChange());
		
		vue.list.addListSelectionListener(new ListSelectionListener() {

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
				vue.panelLevelSelect.imgBackground1 = new ImageIcon("./levels/"+levelGeted[0]+".txt.gif");	
				
			}
		});
		vue.backButtonTasks.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				vue.thisPanel.removeAll();

				vue.panelMenu = new PanelMenu(vue.getWidth(),vue.getHeight(),vue);

				vue.thisPanel.add(vue.panelMenu,BorderLayout.CENTER);
				
				timerPanelLevelSelect.cancel();
				
				timerPanelGame.cancel();
				
				timerPanelSettings.cancel();
				
				vue.timerView.cancel();
				
				timerPanelMenu = new Timer();
				
				timerPanelMenu.schedule(new TimerTask(){

					public void run(){

						vue.panelMenu.repaint();

					}

				}, 0, 20);
			}
			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}
			
		});
		
		this.vue.playButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {

				vue.thisPanel.removeAll();
				
				vue.panelGame = new PanelGame(vue.getWidth(),vue.getHeight(),vue);	
							
				vue.thisPanel.add(vue.panelGame,BorderLayout.CENTER);
				
				try {
					modele.intitlevel(modele.level.fileLevel);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				modele.lancerJeu();
				
				timerPanelLevelSelect.cancel();
				
				timerPanelSettings.cancel();
				
				timerPanelMenu.cancel();
				
				vue.timerView.cancel();
				
				timerPanelGame = new Timer();
				
				timerPanelGame.schedule(new TimerTask(){
					
					public void run(){
						
						vue.panelGame.repaint();
					}
				}, 0, 20);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}

		});
		
		vue.backLevelSelectButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				vue.thisPanel.removeAll();
				
				vue.panelLevelSelect = new PanelLevelSelect(vue);

				vue.thisPanel.add(vue.panelLevelSelect,BorderLayout.CENTER);

				timerPanelLevelSelect = new Timer();
				
				timerPanelMenu.cancel();
				
				vue.timerView.cancel();
				
				timerPanelSettings.cancel();
				
				timerPanelGame.cancel();
				
				timerPanelLevelSelect.schedule(new TimerTask(){

					public void run(){
						
						vue.pane.revalidate();
						
						vue.pane.repaint();
						
						vue.panelLevelSelect.repaint();
					}

				}, 0, 20);
			}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}
			
		});
		vue.restartButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				vue.thisPanel.removeAll();
				
				vue.thisPanel.repaint();
				
				vue.panelGame = new PanelGame(vue.getWidth(),vue.getHeight(),vue);
								
				vue.thisPanel.add(vue.panelGame,BorderLayout.CENTER);	
				
				timerPanelLevelSelect.cancel();
				
				timerPanelSettings.cancel();
				
				timerPanelMenu.cancel();
				
				vue.timerView.cancel();
				
				timerPanelGame.cancel();
				
				timerPanelGame = new Timer();
				
				try {
					modele.intitlevel(modele.level.fileLevel);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				vue.modele.lancerJeu();
				
				timerPanelGame.schedule(new TimerTask(){
					
					public void run(){
						
						vue.panelGame.repaint();
					}
				}, 0, 20);
			}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}
			
		});
		vue.nextLevelButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					modele.intitlevel(modele.level.nextLevel);
					System.out.println(modele.level.nextLevel);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				vue.thisPanel.removeAll();
				
				vue.thisPanel.repaint();
				
				vue.panelGame = new PanelGame(vue.getWidth(),vue.getHeight(),vue);

				vue.thisPanel.add(vue.panelGame,BorderLayout.CENTER);
				
				
				timerPanelLevelSelect.cancel();
				
				timerPanelSettings.cancel();
				
				timerPanelMenu.cancel();
				
				timerPanelGame.cancel();
				
				vue.timerView.cancel();
				
				timerPanelGame = new Timer();
				
				vue.modele.lancerJeu();
				
				timerPanelGame.schedule(new TimerTask(){
					
					public void run(){
						
						vue.panelGame.repaint();
					}
				}, 0, 20);
			}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}
			
		});
		vue.pauseButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {
				if(modele.isRunning()) {
					modele.suspendreJeu();
				}
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
		});
		vue.crossButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
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
				if(!modele.isRunning()) {
					modele.lancerJeu();
					
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		vue.soundButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {
				if(vue.soundBT.volume > 0) {
					vue.soundButton.setEnabled(false);;
					modele.impactSound.volume = 0;
					vue.soundBT.volume = 0;
				}else {
					vue.soundButton.setEnabled(true);;
					modele.impactSound.volume = currenteVolume;
					vue.soundBT.volume = currenteVolume;
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
		});
	}

	public void levelChange() {
		modele.levelDifficulty = 1+(vue.sliderLevel.getValue()/10); 
		System.out.println(modele.levelDifficulty);
	}
	public void soundFxChange() {
		modele.impactSound.volume = vue.sliderFxSound.getValue();
		vue.soundBT.volume = vue.sliderFxSound.getValue();
		currenteVolume = vue.sliderFxSound.getValue();
		vue.soundBT.note_on(60);
	}

}
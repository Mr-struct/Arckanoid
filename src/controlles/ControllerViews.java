package controlles;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import model.Modele;
import views.LevelSelectView;
import views.PanelGame;
import views.PanelLevelSelect;
import views.PanelMenu;
import views.PanelSettings;
import views.View;

public class ControllerViews {

	private View view;
	private int currenteVolume;
	/**
	 *  constructeur 
	 *  @param view la Jframe (la vue principale)
	 */
	public ControllerViews(View view) {
		this.view = view;

		this.currenteVolume = this.view.soundBT.volume; // on sauvgarde le son dans une variable pour pouvoir revenir a l_original

		/*
		 * gere le clique sur le boutton play du menu
		 * 
		 * le panneau general est vide 
		 * 
		 * les timer des autres panneaux sont arrete
		 * 
		 * le panneau de selection de niveaux est init
		 * 
		 * on le rajoute au panneau central
		 * 
		 * on lance son timer
		 * 
		 * on le reaffiche tout les 20 miliseconde
		 */
		this.view.tasksButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {view.thisPanel.removeAll();

			view.timerPanelMenu.cancel();

			view.timerPanelSettings.cancel();

			view.timerPanelGame.cancel();

			view.timerView.cancel();
			
			view.panelLevelSelect = new PanelLevelSelect(view);

			view.thisPanel.add(view.panelLevelSelect,BorderLayout.CENTER);

			view.timerPanelLevelSelect = new Timer();

			view.timerPanelLevelSelect.schedule(new TimerTask(){

				public void run(){

					view.panelLevelSelect.repaint();
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




		/*
		 * gere le clique sur le boutton exit du menu 
		 * 
		 * la frame est detruite et le processus s arrete 
		 */
		this.view.exitButton.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) {

				view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				view.dispose();

				System.exit(0);

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}
		} );

		/*
		 * gere le clique sur le boutton parametre du menu
		 * 
		 * le panneau general est vide 
		 * 
		 * les timer des autres panneaux sont arrete
		 * 
		 * le panneau de parametre  est init
		 * 
		 * on le rajoute au panneau central
		 * 
		 * et on lance son timer
		 * 
		 * on le reaffiche tout les 20 miliseconde
		 */
		this.view.settingsButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				view.thisPanel.removeAll();

				view.panelSettings = new PanelSettings(view.getWidth(),view.getHeight(),view);

				view.thisPanel.add(view.panelSettings,BorderLayout.CENTER);

				view.thisPanel.repaint();

				view.timerPanelLevelSelect.cancel();

				view.timerPanelMenu.cancel();

				view.timerPanelGame.cancel();

				view.timerView.cancel();

				view.timerPanelSettings = new Timer();

				view.timerPanelSettings.schedule(new TimerTask(){

					public void run(){

						view.panelSettings.repaint();

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

		/*
		 * gere le clique sur le boutton retour du panneau parametre 
		 * 
		 * le panneau general est vide 
		 * 
		 * les timer des autres panneaux sont arrete
		 * 
		 * le panneau du menu est init
		 * 
		 * on le rajoute au panneau central
		 * 
		 * et on lance son timer
		 * 
		 * on le reaffiche tout les 20 miliseconde
		 */
		this.view.backButtonMenuFromSettings.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

				view.thisPanel.removeAll();



				view.timerPanelLevelSelect.cancel();

				view.timerPanelGame.cancel();

				view.timerPanelSettings.cancel();

				view.timerView.cancel();
				
				view.panelMenu = new PanelMenu(view.getWidth(),view.getHeight(),view);

				view.thisPanel.add(view.panelMenu,BorderLayout.CENTER);
				
				view.timerPanelMenu = new Timer();

				view.timerPanelMenu.schedule(new TimerTask(){

					public void run(){

						view.panelMenu.repaint();

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

		/*
		 * gere le clique sur le boutton retour du panneau selecteur de niveau  
		 * 
		 * le panneau general est vide 
		 * 
		 * les timer des autres panneaux sont arrete
		 * 
		 * le panneau du menu est init
		 * 
		 * on le rajoute au panneau central
		 * 
		 * et on lance son timer
		 * 
		 * on le reaffiche tout les 20 miliseconde
		 */
		this.view.backButtonMenuFromSelect.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

				view.thisPanel.removeAll();

				view.panelMenu = new PanelMenu(view.getWidth(),view.getHeight(),view);

				view.thisPanel.add(view.panelMenu,BorderLayout.CENTER);

				view.timerPanelLevelSelect.cancel();

				view.timerPanelGame.cancel();

				view.timerPanelSettings.cancel();

				view.timerView.cancel();

				view.panelLevelSelect.previewModele.suspendreJeu();

				view.panelLevelSelect.previewModele.timerPreview.cancel();

				view.timerPanelMenu = new Timer();

				view.timerPanelMenu.schedule(new TimerTask(){

					public void run(){

						view.panelMenu.repaint();

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

		view.sliderFxSound.addChangeListener(e->soundFxChange()); // le silader du bruitage ecoute son etat grasse a la methode SoundFxChange ()*

		view.sliderLevel.addChangeListener( e-> levelChange()); 
		
		/*
		 * gere le clique sur le boutton retour du panneau de jeu 
		 * 
		 * le panneau general est vide 
		 * 
		 * les timer des autres panneaux sont arrete
		 * 
		 * le panneau du selecteur de niveau est init
		 * 
		 * on le rajoute au panneau central
		 * 
		 * et on lance son timer
		 * 
		 * on le reaffiche tout les 20 miliseconde
		 */
		view.backLevelSelectButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

				view.thisPanel.removeAll();

				view.panelLevelSelect = new PanelLevelSelect(view);

				view.thisPanel.add(view.panelLevelSelect,BorderLayout.CENTER);

				view.timerPanelLevelSelect = new Timer();

				view.timerPanelMenu.cancel();

				view.timerView.cancel();

				view.timerPanelSettings.cancel();

				view.timerPanelGame.cancel();

				view.timerPanelLevelSelect.schedule(new TimerTask(){

					public void run(){

						view.panelLevelSelect.repaint();
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
		
		/*
		 * gere le clique sur le boutton recommancer du panneau de jeu  
		 * 
		 * le panneau general est vide 
		 * 
		 * les timer des autres panneaux sont arrete
		 * 
		 * le panneau du jeu est init
		 * 
		 * on le rajoute au panneau central
		 * 
		 * et on lance son timer
		 * 
		 * on le reaffiche tout les 20 miliseconde
		 */
		view.restartButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				view.thisPanel.removeAll();

				view.thisPanel.repaint();

				view.panelGame = new PanelGame(view.getWidth(),view.getHeight(),view);

				view.thisPanel.add(view.panelGame,BorderLayout.CENTER);	

				view.timerPanelLevelSelect.cancel();

				view.timerPanelSettings.cancel();

				view.timerPanelMenu.cancel();

				view.timerView.cancel();

				view.timerPanelGame.cancel();

				view.timerPanelGame = new Timer();

				try {
					view.modele.intitlevel(view.modele.level.fileLevel);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				view.modele.lancerJeu();

				view.timerPanelGame.schedule(new TimerTask(){

					public void run(){

						view.panelGame.repaint();
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
		
		/*
		 * gere le clique sur le boutton continuer du panneau de jeu  
		 * 
		 * le panneau general est vide 
		 * 
		 * les timer des autres panneaux sont arrete
		 * 
		 * le niveau suivant est init
		 * 
		 * le panneau de jeu suiviant est init avec le modele du niveau suivant 
		 * 
		 * on le rajoute au panneau central
		 * 
		 * et on lance son timer
		 * 
		 * on le reaffiche tout les 20 miliseconde
		 */
		view.nextLevelButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					view.modele.intitlevel(view.modele.level.nextLevel);// init avec le bon niveau 
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				view.thisPanel.removeAll();

				view.thisPanel.repaint();

				view.panelGame = new PanelGame(view.getWidth(),view.getHeight(),view);

				view.thisPanel.add(view.panelGame,BorderLayout.CENTER);
				
				view.timerPanelLevelSelect.cancel();

				view.timerPanelSettings.cancel();

				view.timerPanelMenu.cancel();

				view.timerPanelGame.cancel();

				view.timerView.cancel();

				view.timerPanelGame = new Timer();

				view.modele.lancerJeu();

				view.timerPanelGame.schedule(new TimerTask(){

					public void run(){

						view.panelGame.repaint();
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
		
		/*
		 * gere le clique sur le boutton pause du panneau de jeu  
		 *
		 *si le jeu tourn on le met en pause 
		 */
		view.pauseButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				if(view.modele.isRunning()) {
					
					view.modele.suspendreJeu();
				}
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}



			@Override
			public void mouseReleased(MouseEvent arg0) {}

		});
		
		/*
		 * gere le clique sur le boutton croix du panneau de jeu 
		 *
		 *	on test si le jeu est bien en pause et on relance le jeu  
		 */
		view.crossButton.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent e) {
				
				if(!view.modele.isRunning()) {
				
					view.modele.lancerJeu();		
				
				}
			}

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
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
		
		/*
		 * gere le clique sur le boutton son du panneau de jeu  
		 * 
		 * avctive desactive le son selon l eta 
		 *
		 */
		view.soundButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				//on test si le son est bien active et le bouton du son aussi 
				if(view.modele.impactSound.volume  > 0  && view.soundButton.isEnabled()) {
					
					view.soundButton.setEnabled(false); // on desactive le bouton du sod

					//le son est mis a 0
					view.modele.impactSound.volume = 0; 

					view.soundBT.volume = 0;
					
				}else {
					
					//sino on recative le son dans son etat d origine
					view.modele.impactSound.volume = currenteVolume;

					view.soundBT.volume = currenteVolume;
					
					/*
					 *  on fait quand meme un test sur le bouton 
					 *  pour bien afficher l image du bouton correspondant a l etat du son 
					 */
					
					if(currenteVolume > 0) {
						
						view.soundButton.setEnabled(true);
					}
					else {

						view.soundButton.setEnabled(false);

					}
				}
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

		
			@Override
			public void mouseReleased(MouseEvent arg0) {}

		});

		/*
		 * gere le clique sur le boutton niveau suivant du panneau de selecteur de niveau  
		 * 
		 * le panneau de demo est mis a jour avec le bon niveau a afficher 
		 */
		view.nextSelectButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(view.levelIndex<10) { // on test si on depasse pas le nombre de niveau jouable 
					
					view.levelIndex++;  // on incremente l'index du niveau en cours 
										
					view.panelLevelSelect.previewModele.suspendreJeu(); // la demo est arreter 
					
					view.panelLevelSelect.previewModele.timerPreview.cancel(); // son timer aussi
					
					view.panelLevelSelect.previewModele = new Modele("./levels/level"+view.levelIndex+".txt"); // le bon niveau est charger 
					
					view.panelLevelSelect.imgBackground1 = new ImageIcon("./levels/level"+view.levelIndex+".txt.gif"); // on met a jour le fon du panneau de selecteur de niveau 
					
					view.panelLevelSelect.remove(view.panelLevelSelect.levelSelect); // on supprime la demo du panneau 
					
					view.panelLevelSelect.levelSelect = new LevelSelectView(view.panelLevelSelect.previewModele,view); //on init la  nouvelle demo 
					
					view.panelLevelSelect.add(view.panelLevelSelect.levelSelect); // on rajoute la bonne demo dans le panneau 
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
		/*
		 * meme fonctionnement que nextSelectButton
		 * sauf que l'index du niveau est decrementer 
		 */
		view.previousSelectButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(view.levelIndex>0) {
					
					view.levelIndex--;
					
					System.out.println("index : " + view.levelIndex);

					view.panelLevelSelect.previewModele.suspendreJeu();
					
					view.panelLevelSelect.previewModele.timerPreview.cancel();
					
					view.panelLevelSelect.previewModele = new Modele("./levels/level"+view.levelIndex+".txt");
					
					view.panelLevelSelect.imgBackground1 = new ImageIcon("./levels/level"+view.levelIndex+".txt.gif");
					
					view.panelLevelSelect.remove(view.panelLevelSelect.levelSelect);
					
					view.panelLevelSelect.levelSelect = new LevelSelectView(view.panelLevelSelect.previewModele,view);
					
					view.panelLevelSelect.add(view.panelLevelSelect.levelSelect);

				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});

	}
	
	/*
	 * change le niveau de la difficulter avec la valeur retournee par le slider
	 */
	public void levelChange() {
		
		view.modele.levelDifficulty = 1+(view.sliderLevel.getValue()/10); 
	}
	
	/*
	 * change le niveau du son avec la valeur retourne pas le slider du son 
	 */
	public void soundFxChange() {

		view.modele.impactSound.volume = view.sliderFxSound.getValue();

		view.soundBT.volume = view.sliderFxSound.getValue();

		currenteVolume = view.sliderFxSound.getValue();

		view.soundBT.noteOn(60);

		/*
		 * petit test pour afficher le bon bouton de son dans le panneau du jeu 
		 */
		if(view.modele.impactSound.volume > 0) {

			view.soundButton.setEnabled(true);

		}else {

			view.soundButton.setEnabled(false);

		}
	}

}
package Controls;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import Model.Modele;
import Views.LevelSelectView;
import Views.PanelGame;
import Views.PanelLevelSelect;
import Views.PanelMenu;
import Views.PanelSettings;
import Views.Vue;

public class ControleurViews {

	private Vue vue;
	private int currenteVolume;
	/*
	 *  constructeur 
	 */
	public ControleurViews(Vue vue) {
		this.vue = vue;
		this.currenteVolume = this.vue.soundBT.volume;

		//gere le clic sur le boutton nouveau jeux
		this.vue.tasksButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {vue.thisPanel.removeAll();

			vue.panelLevelSelect = new PanelLevelSelect(vue);

			vue.thisPanel.add(vue.panelLevelSelect,BorderLayout.CENTER);

			vue.timerPanelLevelSelect = new Timer();
			
			vue.timerPanelMenu.cancel();
			
			vue.timerPanelSettings.cancel();
			
			vue.timerPanelGame.cancel();
			
			vue.timerView.cancel();
			
			vue.timerPanelLevelSelect.schedule(new TimerTask(){

				public void run(){
					
					//vue.pane.revalidate();
					
					//vue.pane.repaint();
					
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
				
				vue.timerPanelLevelSelect.cancel();
				
				vue.timerPanelMenu.cancel();
				
				vue.timerPanelGame.cancel();
				
				vue.timerView.cancel();
				
				vue.timerPanelSettings = new Timer();
				
				vue.timerPanelSettings.schedule(new TimerTask(){
					
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
		this.vue.backButtonMenuFromSettings.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

				vue.thisPanel.removeAll();

				vue.panelMenu = new PanelMenu(vue.getWidth(),vue.getHeight(),vue);

				vue.thisPanel.add(vue.panelMenu,BorderLayout.CENTER);
				
				vue.timerPanelLevelSelect.cancel();
				
				vue.timerPanelGame.cancel();
				
				vue.timerPanelSettings.cancel();
				
				vue.timerView.cancel();
				
				vue.timerPanelMenu = new Timer();
				
				vue.timerPanelMenu.schedule(new TimerTask(){

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
		
		//gere le clic sur le button back du menu select
				this.vue.backButtonMenuFromSelect.addMouseListener(new MouseListener() {

					@Override
					public void mouseClicked(MouseEvent e) {

						vue.thisPanel.removeAll();

						vue.panelMenu = new PanelMenu(vue.getWidth(),vue.getHeight(),vue);

						vue.thisPanel.add(vue.panelMenu,BorderLayout.CENTER);
						
						vue.timerPanelLevelSelect.cancel();
						
						vue.timerPanelGame.cancel();
						
						vue.timerPanelSettings.cancel();
						
						vue.timerView.cancel();
						
						vue.panelLevelSelect.previewModele.suspendreJeu();
						
						vue.panelLevelSelect.previewModele.timerPreview.cancel();
						
						vue.timerPanelMenu = new Timer();
						
						vue.timerPanelMenu.schedule(new TimerTask(){

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
		
	/*	vue.list.addListSelectionListener(new ListSelectionListener() {

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
*/
/*		
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
				
				vue.timerPanelLevelSelect.cancel();
				
				vue.timerPanelSettings.cancel();
				
				vue.timerPanelMenu.cancel();
				
				vue.timerView.cancel();
				
				vue.timerPanelGame = new Timer();
				
				vue.timerPanelGame.schedule(new TimerTask(){
					
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
	*/	
		vue.backLevelSelectButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				vue.thisPanel.removeAll();
				
				vue.panelLevelSelect = new PanelLevelSelect(vue);

				vue.thisPanel.add(vue.panelLevelSelect,BorderLayout.CENTER);

				vue.timerPanelLevelSelect = new Timer();
				
				vue.timerPanelMenu.cancel();
				
				vue.timerView.cancel();
				
				vue.timerPanelSettings.cancel();
				
				vue.timerPanelGame.cancel();
				
				vue.timerPanelLevelSelect.schedule(new TimerTask(){

					public void run(){
						
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
				
				vue.timerPanelLevelSelect.cancel();
				
				vue.timerPanelSettings.cancel();
				
				vue.timerPanelMenu.cancel();
				
				vue.timerView.cancel();
				
				vue.timerPanelGame.cancel();
				
				vue.timerPanelGame = new Timer();
				
				try {
					vue.modele.intitlevel(vue.modele.level.fileLevel);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				vue.modele.lancerJeu();
				
				vue.timerPanelGame.schedule(new TimerTask(){
					
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
					vue.modele.intitlevel(vue.modele.level.nextLevel);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				vue.thisPanel.removeAll();
				
				vue.thisPanel.repaint();
				
				vue.panelGame = new PanelGame(vue.getWidth(),vue.getHeight(),vue);

				vue.thisPanel.add(vue.panelGame,BorderLayout.CENTER);
				
				
				vue.timerPanelLevelSelect.cancel();
				
				vue.timerPanelSettings.cancel();
				
				vue.timerPanelMenu.cancel();
				
				vue.timerPanelGame.cancel();
				
				vue.timerView.cancel();
				
				vue.timerPanelGame = new Timer();
				
				vue.modele.lancerJeu();
				
				vue.timerPanelGame.schedule(new TimerTask(){
					
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
				if(vue.modele.isRunning()) {
					vue.modele.suspendreJeu();
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
				if(!vue.modele.isRunning()) {
					vue.modele.lancerJeu();		
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
					vue.modele.impactSound.volume = 0;
					vue.soundBT.volume = 0;
				}else {
					vue.soundButton.setEnabled(true);;
					vue.modele.impactSound.volume = currenteVolume;
					vue.soundBT.volume = currenteVolume;
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
		});
		
		vue.nextSelectButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(vue.levelIndex<10) {
					vue.levelIndex++;
					System.out.println("index : " + vue.levelIndex);
					vue.panelLevelSelect.previewModele.suspendreJeu();
					vue.panelLevelSelect.previewModele.timerPreview.cancel();
					vue.panelLevelSelect.previewModele = new Modele("./levels/level"+vue.levelIndex+".txt");
					vue.panelLevelSelect.imgBackground1 = new ImageIcon("./levels/level"+vue.levelIndex+".txt.gif");
					vue.panelLevelSelect.remove(vue.panelLevelSelect.levelSelect);
					vue.panelLevelSelect.levelSelect = new LevelSelectView(vue.panelLevelSelect.previewModele,vue);
					vue.panelLevelSelect.add(vue.panelLevelSelect.levelSelect);
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
		
		vue.previousSelectButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(vue.levelIndex>0) {
					vue.levelIndex--;
					System.out.println("index : " + vue.levelIndex);

					vue.panelLevelSelect.previewModele.suspendreJeu();
					vue.panelLevelSelect.previewModele.timerPreview.cancel();
					vue.panelLevelSelect.previewModele = new Modele("./levels/level"+vue.levelIndex+".txt");
					vue.panelLevelSelect.imgBackground1 = new ImageIcon("./levels/level"+vue.levelIndex+".txt.gif");
					vue.panelLevelSelect.remove(vue.panelLevelSelect.levelSelect);
					vue.panelLevelSelect.levelSelect = new LevelSelectView(vue.panelLevelSelect.previewModele,vue);
					vue.panelLevelSelect.add(vue.panelLevelSelect.levelSelect);

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

	public void levelChange() {
		vue.modele.levelDifficulty = 1+(vue.sliderLevel.getValue()/10); 
	}
	public void soundFxChange() {
		vue.modele.impactSound.volume = vue.sliderFxSound.getValue();
		vue.soundBT.volume = vue.sliderFxSound.getValue();
		currenteVolume = vue.sliderFxSound.getValue();
		vue.soundBT.note_on(60);
	}

}
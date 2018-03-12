import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;


public class ControleurVue {
	
	private Modele modele;
	private Vue vue;
	
	// constructeur 
	public ControleurVue(Modele modele, Vue vue) {
		this.modele = modele;
		this.vue = vue;
		
		//gere le clic sur le boutton nouveau jeux
		modele.newGame.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				vue.thisPanel.removeAll();
				vue.panelGame = new PanelGame(vue.getWidth(),vue.getHeight(),modele);
				ControleurJeu controleurJeu = new ControleurJeu(modele, vue.panelGame);
				vue.panelGame.addMouseMotionListener(controleurJeu);
				vue.panelGame.addMouseListener(controleurJeu);
				vue.thisPanel.add(vue.panelGame,BorderLayout.CENTER);
				modele.lancerJeu();
				Timer timerAffichage = new Timer();
				timerAffichage.schedule(new TimerTask(){
					public void run(){
						vue.panelGame.repaint();
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
		    	  //dispose();
		    	 
		        }

			@Override
			public void mouseClicked(MouseEvent arg0) {
				vue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				vue.dispose();
				 System.exit(0);
				 //dispose(); 
				
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
				Timer timerAffichage = new Timer();
				timerAffichage.schedule(new TimerTask(){
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
				
			 System.out.println("hello");
			 
			 vue.thisPanel.removeAll();
			 
			 vue.panelMenu = new PanelMenu(vue.getWidth(),vue.getHeight(),modele);
			 
			 vue.thisPanel.add(vue.panelMenu,BorderLayout.CENTER);
			
			 Timer timerAffichage = new Timer();
			
			 timerAffichage.schedule(new TimerTask(){
			
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
	
	}
	
	public void levelChange() {
		modele.levelDifficulty = 11-((modele.sliderLevel.getValue()/10)); 
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
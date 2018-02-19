
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Vue extends JFrame {
	
	private PanelJeu panelJeu;
	private JPanel thisPanel;
	private PanelMenu panelMenu;
	@SuppressWarnings("unused")
	private Modele modele;
	
	public Vue(Modele modele) {
		
		super("Arkanoid");
		
		this.modele = modele;
		
		this.setLayout(new BorderLayout());
		
		panelMenu = new PanelMenu(modele.gameWidth,modele.gameHeight,modele);
		
		this.setSize(modele.gameWidth,modele.gameHeight);
		
		thisPanel = new JPanel();

		thisPanel.setLayout(new BorderLayout());
		
		thisPanel.add(panelMenu, BorderLayout.CENTER);
		
		this.setContentPane(thisPanel);
		
		this.setLocationRelativeTo(null);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		setVisible(true);
		
		this.setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH); 
		// mon met les listener sur les buttons
		panelMenu.exit.addMouseListener(new MouseListener() {
		      public void mousePressed(MouseEvent e) {
		    	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    	  dispose();
					 System.exit(0);
		    	  //dispose();
		    	 
		        }

			@Override
			public void mouseClicked(MouseEvent arg0) {
				 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				 dispose();
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
		
		
		panelMenu.newGame.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			
				thisPanel.removeAll();
				panelJeu = new PanelJeu(getWidth(),getHeight(),modele);
				ControleurJeu controleurJeu = new ControleurJeu(modele, panelJeu);
				panelJeu.addMouseMotionListener(controleurJeu);
				panelJeu.addMouseListener(controleurJeu);
				thisPanel.add(panelJeu,BorderLayout.CENTER);
				modele.lancerJeu();
				//mise � jour de l'panelJeu (20 milisecondes = 1/50 secondes
				Timer timerAffichage = new Timer();
				timerAffichage.schedule(new TimerTask(){
					public void run(){
						panelJeu.repaint();
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
		
		
				
		
	}
		
}

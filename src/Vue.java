import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Vue extends JFrame {
	
	private Controleur controller;
	
	private Affichage affichage;
	
	private Modele modele;
	
	public Vue(int w , int h, Modele modele) {
		
		super("ma fenetre de jeu");
		
		this.modele = modele;
		
		this.setLayout(new BorderLayout());
		
		affichage = new Affichage(w,h,modele);
		
		affichage.setSize(w,h);
		
		this.setSize(w,h);
		
		this.setResizable(false);
		
		this.getContentPane().add(affichage);
		
		this.setLocationRelativeTo(null);
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		setVisible(true);
		
		this.controller = new Controleur(modele, this);
		
		this.addMouseMotionListener(new RaquetteListener());
		
		//mise � jour de l'affichage (20 milisecondes = 1/50 secondes
		Timer timerAffichage = new Timer();
		timerAffichage.schedule(new TimerTask(){
			public void run(){
				affichage.repaint();
			}
		}, 0, 20);
		
	}
	//ici on implement les listener
		class RaquetteListener implements MouseMotionListener {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				controller.moveRaquette(e);
			}
			
		}

}

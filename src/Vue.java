import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.TimerTask;

import javax.swing.JFrame;

public class Vue extends JFrame {
	
	private Modele modele;
	
	private Affichage affichage;
	
	public Vue(int w , int h ,Modele modele) {
		
		super("ma fenetre de jeu");
		this.modele = modele;
		
		this.setLayout(new BorderLayout());
		
		affichage = new Affichage(modele);
		
		affichage.setBackground(Color.GRAY);
		
		affichage.setSize(w,h);
		
		this.getContentPane().add(affichage, BorderLayout.CENTER);
		
		this.setSize(w,h);
		
		this.setLocationRelativeTo(null);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		setVisible(true);
		
		this.addMouseMotionListener(new MouseAdapter(){
			@Override
			public void mouseDragged(MouseEvent e) {
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				modele.raquette.setX(e.getX() - modele.raquette.getWidth()/2);
			}
		});
		//mise à jour de l'affichage (20 milisecondes = 1/50 secondes
		java.util.Timer timerAffichage = new java.util.Timer();
		timerAffichage.schedule(new TimerTask(){
			public void run(){
				affichage.repaint();
			}
		}, 0, 20);
		
	}

}

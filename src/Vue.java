import java.awt.BorderLayout;
import java.awt.Color;

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
		
	}

}

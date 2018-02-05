import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


import javax.swing.JPanel;

public class Affichage extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Modele modele;

	public Affichage (Modele modele) {
		super();
		this.modele = modele;
		modele.intitBriques();
		modele.initBalle();
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		//dessine le fond 
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		// dessine les briques 
		g2d.setColor(Color.RED);
		for(Brique br : modele.briques) {
			
			g2d.fillRect(br.getX(), br.getY(), br.getWidth(), br.getHeight());
		}
		
		//dessine la raquette
		g2d.setColor(Color.GRAY);
		g2d.fillRect(modele.raquette.getX(), modele.raquette.getY() ,modele.raquette.getWidth() , modele.raquette.getHeight());
		
		g2d.setColor(Color.YELLOW);
		//dessine la balle 
		g2d.fillOval(modele.balle.getX(), modele.balle.getY(),10,10 );
		
		
	}
}

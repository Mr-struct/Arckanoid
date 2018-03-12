import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

public class PanelLevelSelect extends JPanel{

	private Modele modele;
	
	public PanelLevelSelect(int width,int height,Modele modele){
		this.modele = modele;
		this.setSize(width,height);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;

		AffineTransform tx = new AffineTransform();
		tx.scale((float) this.getWidth()/ (float)modele.gameWidth, (float) this.getHeight()/ (float)modele.gameHeight);
		g2d.setTransform(tx);
		//dessiner le font
	}
}

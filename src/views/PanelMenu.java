package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelMenu extends JPanel {
	private View view;
	private ImageIcon picBackground;
	private float scaleX = 0.0f;
	private float scaleY = 0.0f;
	private Font firstWord;
	private Font secondWord;
	private Font therdWord;

	public PanelMenu(int width,int height,View view){

		this.view = view;
		this.setSize(width,height);

		//init les fonts
		try {
			firstWord  = Font.createFont(Font.TRUETYPE_FONT, new File("./Fonts/Streamster.ttf")).deriveFont(50f);
			secondWord = Font.createFont(Font.TRUETYPE_FONT, new File("./Fonts/SFAlienEncountersSolid-Ital.ttf")).deriveFont(150f);
			therdWord  = Font.createFont(Font.TRUETYPE_FONT, new File("./Fonts/SFAlienEncountersSolid.ttf")).deriveFont(80f);
		} catch (FontFormatException e1) {

			e1.printStackTrace();

		} catch (IOException e1) {

			e1.printStackTrace();

		}

		//charge limage de fond
		picBackground = new ImageIcon("./Obj/menu.gif");

		// on rajoute les boutons
		this.add(this.view.tasksButton);
		this.add(this.view.settingsButton);
		this.add(this.view.exitButton);
	}

	public void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D)g;
		AffineTransform old = g2d.getTransform();
		
		AffineTransform tx = new AffineTransform();
		// on calcule le scale
		scaleX = (float)(this.getWidth())/(float)(view.modele.gameWidth);
		scaleY =  (float)(this.getHeight())/(float)(view.modele.gameHeight);
		
		tx.scale(scaleX, scaleY); 

		g2d.setTransform(tx);// on met en place le scale 
		
		//dessine le fond 
		g2d.drawImage(picBackground.getImage(), 0, 0,view.modele.gameWidth, view.modele.gameHeight,picBackground.getImageObserver());
		g2d.setColor(new Color(0,0,0,128));
		g2d.fillRect(0, 0,view.modele.gameWidth, view.modele.gameHeight);

		//mise en place des titres
		//premier titre 
		g2d.setFont(firstWord);
		g2d.setColor(Color.magenta);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString("New",view.modele.gameWidth/2-50, 100);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		//second Titre
		g2d.setFont(secondWord);
		GradientPaint gp2 = new GradientPaint(view.modele.gameWidth/2-380,250,new Color(8, 30, 102),view.modele.gameWidth/2-380,150,new Color(190, 8, 214));
		g2d.setPaint(gp2);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString("ARCANOID",view.modele.gameWidth/2-380, 250);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		//troisiemme titre
		g2d.setFont(therdWord);
		GradientPaint gp3 = new GradientPaint(view.modele.gameWidth/2-140,340,new Color(35, 35, 35),view.modele.gameWidth/2-140 ,280,new Color(244, 245, 247));
		g2d.setPaint(gp3);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString("UPSUD",view.modele.gameWidth/2-140, 340);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);


		//mise en place des buttons
		int x = this.view.modele.gameWidth/2 - 250;
		int y = this.view.modele.gameHeight/2;
		this.view.tasksButton.drawButton(x, y,64,64,scaleX,scaleY,g2d);
		this.view.settingsButton.drawButton(x+210,y,64,64,scaleX,scaleY,g2d);
		this.view.exitButton.drawButton(x+420,y,64,64,scaleX,scaleY,g2d);
		g2d.setTransform(old);
	}
}



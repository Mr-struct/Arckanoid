package Views;

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
	private Vue vue;
	private ImageIcon picBackground;
	private float scaleX = 0.0f;
	private float scaleY = 0.0f;
	private Font firstWord;
	private Font secondWord;
	private Font therdWord;

	public PanelMenu(int width,int height,Vue vue){

		this.vue = vue;
		this.setSize(width,height);

		//init les fonts
		try {
			firstWord  = Font.createFont(Font.TRUETYPE_FONT, new File("./Fonts/Streamster.ttf")).deriveFont(50f);
			secondWord = Font.createFont(Font.TRUETYPE_FONT, new File("./Fonts/SFAlienEncountersSolid-Ital.ttf")).deriveFont(150f);
			therdWord  = Font.createFont(Font.TRUETYPE_FONT, new File("./Fonts/SFAlienEncountersSolid.ttf")).deriveFont(80f);
		} catch (FontFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		picBackground = new ImageIcon("./Obj/menu.gif");
		this.add(this.vue.tasksButton);
		this.add(this.vue.settingsButton);
		this.add(this.vue.exitButton);
	}

	public void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D)g;
		AffineTransform old = g2d.getTransform();
		AffineTransform tx = new AffineTransform();
		
		tx.scale((float) this.getWidth()/ (float)vue.modele.gameWidth, (float) this.getHeight()/ (float)vue.modele.gameHeight);
		g2d.setTransform(tx);
		//dessine le fond 
		g2d.drawImage(picBackground.getImage(), 0, 0,vue.modele.gameWidth, vue.modele.gameHeight,picBackground.getImageObserver());
		g2d.setColor(new Color(0,0,0,128));
		g2d.fillRect(0, 0,vue.modele.gameWidth, vue.modele.gameHeight);

		//mise en place des titres
		//premier titre 
		g2d.setFont(firstWord);
		g2d.setColor(Color.magenta);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString("New",vue.modele.gameWidth/2-50, 100);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		//second Titre
		g2d.setFont(secondWord);
		GradientPaint gp2 = new GradientPaint(vue.modele.gameWidth/2-380,250,new Color(8, 30, 102),vue.modele.gameWidth/2-380,150,new Color(190, 8, 214));
		g2d.setPaint(gp2);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString("ARCANOID",vue.modele.gameWidth/2-380, 250);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		//troisiemme titre
		g2d.setFont(therdWord);
		GradientPaint gp3 = new GradientPaint(vue.modele.gameWidth/2-140,340,new Color(35, 35, 35),vue.modele.gameWidth/2-140 ,280,new Color(244, 245, 247));
		g2d.setPaint(gp3);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString("UPSUD",vue.modele.gameWidth/2-140, 340);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		
		
				//mise en place des buttons
				scaleX = (float)(this.getWidth())/(float)(vue.modele.gameWidth);
				scaleY =  (float)(this.getHeight())/(float)(vue.modele.gameHeight);
				int x = this.vue.modele.gameWidth/2 - 250;
				int y = this.vue.modele.gameHeight/2;
				this.vue.tasksButton.drawButton(x, y,64,64,scaleX,scaleY,g2d);
				this.vue.settingsButton.drawButton(x+210,y,64,64,scaleX,scaleY,g2d);
				this.vue.exitButton.drawButton(x+420,y,64,64,scaleX,scaleY,g2d);
				g2d.setTransform(old);
	}
}
	


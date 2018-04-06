
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelMenu extends JPanel {
	private Vue vue;
	private Image imgBackground ;
	private ImageIcon picBackground;
	private int x = 0;
	private int y = 0;
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
		

		/* juste des tests pour le grid bag layout pour un jour !!!!
		//this.vue.tasksButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
		//this.vue.settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
		//this.vue.exitButton.setAlignmentX(Component.CENTER_ALIGNMENT); 
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.VERTICAL;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		//this.add(Box.createVerticalGlue());
		//this.add(button);
		//
		 * 
		 */
		this.add(this.vue.tasksButton);
		this.add(this.vue.settingsButton);
		this.add(this.vue.exitButton);
		//this.add(Box.createVerticalGlue()); 

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
		
		g2d.setTransform(old);
		//mise en place des buttons
				x = this.getWidth()/2 - 200;
				y = (this.getHeight()/2)+50;
				this.vue.tasksButton.setBounds(x,y,64,64);
				this.vue.settingsButton.setBounds(x+200,y,64, 64);
				this.vue.exitButton.setBounds(x+400,y,64, 64);
		
	}
}
	


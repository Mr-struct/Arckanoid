package views;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

@SuppressWarnings("serial")
public class PanelSettings extends JPanel{
	private ImageIcon picBackGround;
	private Font firstWord;
	private View view;
	private float scaleX = 0.0f;
	private float scaleY = 0.0f;

	
	/*
	 * contructeur 
	 * 
	 * @param width la largeur du panneau 
	 * 
	 * @param height la hauteur du panneau 
	 * 
	 * @param view la vue general ou la frame
	 * 
	 */
	public PanelSettings(int width, int height ,View view) {

		super();

		this.view = view;

		this.setSize(width,height);
		
		// on charge les fonts
		try {
			
			firstWord = Font.createFont(Font.TRUETYPE_FONT, new File("./Fonts/Streamster.ttf")).deriveFont(40f);

		} catch (FontFormatException e1) {
			
			e1.printStackTrace();
			
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		//on charge l image de fon 
		this.picBackGround =  new ImageIcon("./Obj/param.gif");
		/*
		 * init le button de retour
		 */
		this.add(this.view.backButtonMenuFromSettings);
		
		//init le slider du son principale 
		
		view.sliderMainSound.setUI(new Slider(view.sliderMainSound));
		view.sliderMainSound.setMinorTickSpacing(5);
		view.sliderMainSound.setMajorTickSpacing(20);
		view.sliderMainSound.setPaintTicks(true);
		view.sliderMainSound.setPaintLabels(true);
		view.sliderMainSound.setLabelTable(view.sliderMainSound.createStandardLabels(10));
		view.sliderMainSound.setBackground(new Color(255,255,255,0));
		view.sliderMainSound.setForeground(new Color(255,255,255));
		
		//this.add(view.sliderMainSound);
		 
		
		/*
		 * init le slider des bruitage
		 */

		view.sliderFxSound.setUI(new Slider(view.sliderFxSound));
		view.sliderFxSound.setMinorTickSpacing(5);
		view.sliderFxSound.setMajorTickSpacing(20);
		view.sliderFxSound.setPaintTicks(true);
		view.sliderFxSound.setPaintLabels(true);
		view.sliderFxSound.setLabelTable(view.sliderMainSound.createStandardLabels(10));
		view.sliderFxSound.setBackground(new Color(255,255,255,0));
		view.sliderFxSound.setForeground(new Color(255,255,255));
		this.add(view.sliderFxSound);


		//init le slider du niveau de dificulte

		view.sliderLevel.setUI(new Slider(view.sliderLevel));
		view.sliderLevel.setMinorTickSpacing(5);
		view.sliderLevel.setMajorTickSpacing(50);
		view.sliderLevel.setPaintTicks(true);
		view.sliderLevel.setPaintLabels(true);
		view.sliderLevel.setLabelTable(view.sliderLevel.createStandardLabels(50));
		view.sliderLevel.setBackground(new Color(255,255,255,0));
		view.sliderLevel.setForeground(new Color(255,255,255));
		this.add(view.sliderLevel);

	}

	public void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D)g;
		//on declare les affine transform un pour sauvgarder l etat actuel et l autre pour le modifier
		AffineTransform tx = new AffineTransform();
		AffineTransform oldTransform = new AffineTransform();
		
		oldTransform = g2d.getTransform();
		// on calcule le scale
		
		scaleX = (float)(this.getWidth())/(float)(view.modele.gameWidth);
		scaleY =  (float)(this.getHeight())/(float)(view.modele.gameHeight);
		
		tx.scale(scaleX, scaleY);
		
		g2d.setTransform(tx);
		
		//dessine le fond 
		g2d.drawImage(picBackGround.getImage(), 0, 0,view.modele.gameWidth, view.modele.gameHeight,null);
		//de la transparance sur les coter de la fenaitre pour un effet !
		Color transparentColor1 = new Color(0, 0, 0, 128);
		Color transparentColor2 = new Color(0, 0, 0,200);

		//degrade de couleur
		GradientPaint gp0 = new GradientPaint(100, 0, transparentColor1, 100 + (view.modele.gameWidth -200)/2, view.modele.gameHeight, transparentColor2, true);                
		g2d.setPaint(gp0);
		g2d.fillRect(100, 0, view.modele.gameWidth - 200, view.modele.gameHeight);
		
		g2d.setTransform(tx);
		//dessines les labeles
		g2d.setColor(new Color(5, 234, 255));
		g2d.setFont(firstWord);

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString("Fx Sound : ", view.modele.gameWidth / 2 - 400, view.modele.gameHeight / 2 - 230);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString("Difficulty : ", view.modele.gameWidth / 2 - 400, view.modele.gameHeight / 2 - 100);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		
		//dessine le niveau de difficulte choisit
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		if(view.sliderLevel.getValue() >= 0 && view.sliderLevel.getValue()<=20) {

			g2d.drawString("Grany Mode " + (view.modele.levelDifficulty*10-10) +"%", view.modele.gameWidth / 2, view.modele.gameHeight / 2 + 40 );

		}else if(view.sliderLevel.getValue()>20 && view.sliderLevel.getValue()<=40) {

			g2d.drawString("Easy " + (view.modele.levelDifficulty*10-10)+"%", view.modele.gameWidth / 2, view.modele.gameHeight / 2 + 40 );

		}else if(view.sliderLevel.getValue()>40 && view.sliderLevel.getValue()<=60) {

			g2d.drawString("Normal " +(view.modele.levelDifficulty*10-10)+"%", view.modele.gameWidth / 2, view.modele.gameHeight / 2 + 40 );
		}
		else if(view.sliderLevel.getValue()>60 && view.sliderLevel.getValue()<=80) {

			g2d.drawString("Hard " + (view.modele.levelDifficulty*10-10)+"%", view.modele.gameWidth / 2, view.modele.gameHeight / 2 + 40 );
		}
		else if(view.sliderLevel.getValue()>80 && view.sliderLevel.getValue()<=100) {

			g2d.drawString("God Mode " + (view.modele.levelDifficulty*10-10)+"%", view.modele.gameWidth / 2, view.modele.gameHeight / 2 + 40 );
		}
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		
		// dessine le bouton
		//mise en place des buttons
		
		int x = this.view.modele.gameWidth/2 - this.view.modele.gameWidth/4;
		int y = this.view.modele.gameHeight - this.view.modele.gameHeight/3 ;getInsets();

		this.view.backButtonMenuFromSettings.drawButton(x ,y,64,64,scaleX,scaleY,g2d);
		g2d.setTransform(oldTransform);
		// dessine les sliders
		
		//view.sliderMainSound.paintImmediately(this.getWidth()/2-this.getHeight()/4, this.getHeight()/2-220, this.getWidth()/2, 50);
		//view.sliderMainSound.setBounds(this.getWidth()/2-200, this.getHeight()/2- this.getHeight()/4, this.getWidth()/2, 50);
	
		int sliderX = this.getWidth()/2-200;

		if(this.getWidth() < this.getHeight()) {
			
			sliderX = this.getWidth()/2-100;
		}
		//dessine le slider pour le sonfx
		view.sliderFxSound.setBounds(sliderX, this.getHeight()/2-this.getHeight()/3,  this.getWidth()/2, 50);
		//dessine le slider pour le niveau de dificulté 
		view.sliderLevel.setBounds(sliderX, this.getHeight()/2-this.getHeight()/6  ,  this.getWidth()/2, 50);
	}

	// class interne qui redefinie le slider
	public class Slider extends BasicSliderUI {

		public Slider(JSlider b) {
			super(b);
		}

		@Override
		public void paintTrack(Graphics g) {

			Graphics2D g2d = (Graphics2D) g;
			//c.getParent();
			Rectangle r = trackRect;
			GradientPaint gp = new GradientPaint(r.x, r.y,new Color(5, 234, 255) ,r.x + r.width, r.y + r.height,new Color(119, 19, 103));
			g2d.setPaint(gp);
			//dessine le slider avec un degrader au lieu d'une couleur simple
			g.fillRect(r.x, r.y, r.width, r.height);
		}
		@Override
		public void paintThumb(Graphics g) {
			super.paintThumb(g);
		}
	}
}

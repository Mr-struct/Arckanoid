import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

public class PanelSettings extends JPanel{
	private Image imgBackground;
	private Font firstWord;
	Modele modele;

	
	PanelSettings(int width, int height ,Modele modele) {
		
		super();
		
		this.modele = modele;
		
		this.setSize(width,height);
		
		try {
			 	firstWord = Font.createFont(Font.TRUETYPE_FONT, new File("./Fonts/Streamster.ttf")).deriveFont(40f);
			 
			} catch (FontFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		//init les image 
				try {
					this.imgBackground = ImageIO.read(new File("./Obj/Menu.jpg"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*
				 * init le button de retour
				 */
				this.add(modele.backButton);
				/*
				 * init le slider du son principale 
				 */
				modele.sliderMainSound.setUI(new Slider(modele.sliderMainSound));
				modele.sliderMainSound.setMinorTickSpacing(5);
				modele.sliderMainSound.setMajorTickSpacing(20);
				modele.sliderMainSound.setPaintTicks(true);
				modele.sliderMainSound.setPaintLabels(true);
				modele.sliderMainSound.setLabelTable(modele.sliderMainSound.createStandardLabels(10));
				modele.sliderMainSound.setBackground(new Color(255,255,255,0));
				modele.sliderMainSound.setForeground(new Color(255,255,255));
				this.add(modele.sliderMainSound);
				
				/*
				 * init le slider des bruitage
				 */
				modele.sliderFxSound.setUI(new Slider(modele.sliderFxSound));
				modele.sliderFxSound.setMinorTickSpacing(5);
				modele.sliderFxSound.setMajorTickSpacing(20);
				modele.sliderFxSound.setPaintTicks(true);
				modele.sliderFxSound.setPaintLabels(true);
				modele.sliderFxSound.setLabelTable(modele.sliderMainSound.createStandardLabels(10));
				modele.sliderFxSound.setBackground(new Color(255,255,255,0));
				modele.sliderFxSound.setForeground(new Color(255,255,255));
				this.add(modele.sliderFxSound);
				
				/*
				 * init le slider du niveau de dificultée
				*/
				modele.sliderLevel.setUI(new Slider(modele.sliderLevel));
				modele.sliderLevel.setMinorTickSpacing(5);
				modele.sliderLevel.setMajorTickSpacing(50);
				modele.sliderLevel.setPaintTicks(true);
				modele.sliderLevel.setPaintLabels(true);
				modele.sliderLevel.setLabelTable(modele.sliderLevel.createStandardLabels(50));
				modele.sliderLevel.setBackground(new Color(255,255,255,0));
				modele.sliderLevel.setForeground(new Color(255,255,255));
				this.add(modele.sliderLevel);
	}
	
	public void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		//dessine le fond 
		g2d.drawImage(imgBackground, 0, 0,this.getWidth(), this.getHeight(),null);
		g2d.setColor(new Color(180, 45, 230,128));
		g2d.fillRoundRect(200,100,this.getWidth()-400,this.getHeight()-200,50,50);
		
		// dessine les sliders
		modele.sliderMainSound.setBounds(this.getWidth()/2 -100,this.getHeight()/2 -100, 300, 50);
		modele.sliderFxSound.setBounds(this.getWidth()/2 -100, this.getHeight()/2 , 300, 50);
		//dessine le slider pour le niveau de dificulté 
		modele.sliderLevel.setBounds(this.getWidth()/2 -100, this.getHeight()/2 +100  , 300, 50);
		
		//dessines les labeles
		g2d.setColor(new Color(5, 234, 255));
		g2d.setFont(firstWord);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString("Music : ", this.getWidth()/2 -280, this.getHeight()/2-80);
		g2d.drawString("Fx Sound : ", this.getWidth()/2 -280, this.getHeight()/2 +20);
		g2d.drawString("Difficulty : ", this.getWidth()/2-280, this.getHeight()/2 +130);
		
		
		if(modele.sliderLevel.getValue() >= 0 && modele.sliderLevel.getValue()<=20) {
			g2d.drawString("Grany Mode", this.getWidth()/2, this.getHeight()/2 +200);
		}else if(modele.sliderLevel.getValue()>20 && modele.sliderLevel.getValue()<=40) {
			g2d.drawString("Easy", this.getWidth()/2, this.getHeight()/2 +200);
		}else if(modele.sliderLevel.getValue()>40 && modele.sliderLevel.getValue()<=60) {
			g2d.drawString("Normal ", this.getWidth()/2, this.getHeight()/2 +200);
		}
		else if(modele.sliderLevel.getValue()>60 && modele.sliderLevel.getValue()<=80) {
			g2d.drawString("Hard", this.getWidth()/2, this.getHeight()/2 +200);
		}
		else if(modele.sliderLevel.getValue()>80 && modele.sliderLevel.getValue()<=100) {
			g2d.drawString("God Mode", this.getWidth()/2, this.getHeight()/2 +200);
		}
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		
		// dessine le bouton
		this.modele.backButton.setBounds(this.getWidth()/2-400 , this.getHeight()-200,200,50);
		
		
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
			g.fillRect(r.x, r.y, r.width, r.height);
	    }
	    @Override
	    public void paintThumb(Graphics g) {
	       super.paintThumb(g);
	    }
	}
	    

}

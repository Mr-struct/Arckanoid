import java.awt.BasicStroke;
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


import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelGame extends JPanel {

	private static final long serialVersionUID = 1L;

	private Modele modele;

	private Image imgBackground,imgDefaultRaquette,imgDefaultBalle;

	private  ArrayList<Image> imgBrique = new ArrayList<Image>(0);
	
	private ArrayList<Image> imgBonus = new ArrayList<Image>(0);

	private Font myFont;
	
	int widthBalle = 10, heightBalle = 10;

	public PanelGame (int width,int height,Modele modele) {

		super();

		this.modele = modele;

		this.setSize(width,height);

		try {
			myFont = Font.createFont(Font.TRUETYPE_FONT, new File("./Fonts/SFAlienEncountersSolid.ttf")).deriveFont(20f);
		} catch (FontFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// on charge les image 
		try {
			imgBackground = ImageIO.read(new File(modele.level.levelBackground));

			imgDefaultRaquette = ImageIO.read(new File("src/Obj/bar3.png"));

			imgDefaultBalle = ImageIO.read(new File("src/Obj/BALLE2.png"));

			for(int i = 0 ; i< 6;i++) {

				imgBrique.add( ImageIO.read(new File("src/Obj/br"+i+".png")) );

			}
			for(int i = 0 ; i < 3;i++) {

				imgBonus.add( ImageIO.read(new File("src/Obj/bn"+i+".png")) );

			}

		} catch (IOException e) {

			e.printStackTrace();
		}
		
		this.add(modele.backlevelSelectionButton);
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;

		AffineTransform tx = new AffineTransform();
		AffineTransform oldTransform = new AffineTransform();
		tx.scale((float) this.getWidth()/ (float)modele.gameWidth, (float) this.getHeight()/ (float)modele.gameHeight);
		g2d.setTransform(tx);
		oldTransform = g2d.getTransform();
		
		//dessine le fond 
		g2d.drawImage(imgBackground, 0,0,modele.gameWidth,modele.gameHeight, null);

		//couleur mis a jour pour la transparence 
		g2d.setColor(new Color(6,6,6,128));

		
		//dessine les bonus
		synchronized (modele.bonus){
			for(Bonus bn : modele.bonus) {
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

				g2d.fillRoundRect(bn.getX()+15,bn.getY()+15,bn.getWidth(),bn.getHeight(),15,15);
				
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

				switch (bn.getType() ){
				case 0:
					g2d.drawImage(imgBonus.get(0), bn.getX(),bn.getY(),bn.getWidth(),bn.getHeight(), this);
					break;
				case 1:
					g2d.drawImage(imgBonus.get(1), bn.getX(),bn.getY(),bn.getWidth(),bn.getHeight(), this);

					break;
				case 2: 
					g2d.drawImage(imgBonus.get(2), bn.getX(),bn.getY(),bn.getWidth(),bn.getHeight(), this);

					break;
				};
			}
		}
		
		//dessine l'ombre de la balle
		synchronized (modele.balles){
			for(Balle balle: modele.balles) {
				//dessine l'ombre de la balle
				g2d.setColor(new Color(6,6,6,128));
				g2d.fillOval(balle.getIntX()+15,balle.getIntY()+15,balle.getWidth(),balle.getHeight());
				//dessine la balle
				GradientPaint gp1 = new GradientPaint(balle.getIntX() ,balle.getIntY()-5 ,Color.WHITE ,balle.getIntX() + balle.getWidth() ,balle.getIntY()+balle.getHeight() , Color.BLACK);                
				g2d.setPaint(gp1);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				//g2d.drawImage(imgDefaultBalle, balle.getX(),balle.getY(),balle.getWidth(),balle.getHeight(), this);
				g2d.fillOval(balle.getIntX(),balle.getIntY(),balle.getWidth(),balle.getHeight());
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
			}
		}
		
		g2d.setColor(new Color(6,6,6,128));
		// dessine les briques en fonction du type
		synchronized (modele.briques){
			for(Brique br : modele.briques) {
				g2d.fillRect(br.getX()+10,br.getY()+10,br.getWidth(),br.getHeight());
					switch (br.getType() ){
						case 0:
	
						g2d.drawImage(imgBrique.get(0), br.getX(),br.getY(),br.getWidth(),br.getHeight(), this);
						break;
	
					case 1:
						g2d.drawImage(imgBrique.get(1), br.getX(),br.getY(),br.getWidth(),br.getHeight(), this);
	
						break;
	
					case 2 :
	
						g2d.drawImage(imgBrique.get(2), br.getX(),br.getY(),br.getWidth(),br.getHeight(), this);
	
						break;
	
					case 3 :
	
						g2d.drawImage(imgBrique.get(3), br.getX(),br.getY(),br.getWidth(),br.getHeight(), this);
	
						break;
	
					case 4 :
	
						g2d.drawImage(imgBrique.get(4), br.getX(),br.getY(),br.getWidth(),br.getHeight(), this);
	
						break;
	
					case 5 :
	
						g2d.drawImage(imgBrique.get(5), br.getX(),br.getY(),br.getWidth(),br.getHeight(), this);
	
						break;
					default:
						g2d.drawImage(imgBrique.get(0), br.getX(),br.getY(),br.getWidth(),br.getHeight(), this);
	
					};
				}		
			}
			
		
			
			//dessine l'ombre de la raquette 
			g2d.fillRoundRect(modele.raquette.getX()+15, modele.raquette.getY()+15, modele.raquette.getWidth(), modele.raquette.getHeight(),10,10);

			// dessine la raquette
			g2d.drawImage(imgDefaultRaquette, modele.raquette.getX(),modele.raquette.getY(),modele.raquette.getWidth(),modele.raquette.getHeight(), null);
			
			//de la transparance sur les coter de la fenaitre pour un effet !
			Color transparentColor1 = new Color(59, 59, 112, 128);
			Color transparentColor2 = new Color(159, 59, 240,255);

			//dégradé1 de couleur
			GradientPaint gp1 = new GradientPaint(modele.gameWidth-100, 0, transparentColor1, modele.gameWidth-100, modele.gameHeight, transparentColor2, true);                
			g2d.setPaint(gp1);
			g2d.fillRect((modele.gameWidth-200), 0, 200, modele.gameHeight);

			//dégrade2 de couleur
			GradientPaint gp2 = new GradientPaint(100, 0, transparentColor1, 100, modele.gameHeight, transparentColor2, true);                
			g2d.setPaint(gp2);
			g2d.fillRect(0, 0, 200, modele.gameHeight);
			
			/*
			 * affiche le gain lord d'une colision avec une brique 
			 */
			g2d.setFont(myFont);
			
			synchronized(modele.effects) {
				
				for(AnimatedObject effect : modele.effects) {
					g2d.setColor(new Color(255,255,255,255-effect.getAge()));
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
					g2d.drawString(effect.getPopUp(), effect.getX(), effect.getY());
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
					
				}
			}
			synchronized(modele.explosions) {
				for(AnimatedObject explosion : modele.explosions) {
					g2d.setStroke(new BasicStroke(explosion.getAge()));
					
					
					
					g2d.setColor(new Color(0, 203, 255,128 - explosion.getAge()));
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
					g2d.drawOval(explosion.getX()-10, explosion.getY()-10, explosion.getWidth()+20, explosion.getHeight()+20);
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
					
					
					g2d.setColor(new Color(2, 128, 200, 128- explosion.getAge()));
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
					g2d.drawOval(explosion.getX()-5, explosion.getY()-5, explosion.getWidth()+10, explosion.getHeight()+10);
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
					
					g2d.setColor(new Color(4,64, 128, 128 - explosion.getAge()));
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
					g2d.drawOval(explosion.getX(), explosion.getY(), explosion.getWidth(), explosion.getHeight());
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

				}
			}
			g2d.setFont(myFont);
			g2d.setColor(Color.CYAN);
			g2d.drawString("SCORE : ",modele.gameWidth-170 , 40);
			g2d.drawString(modele.level.levelRank,modele.gameWidth-170 , 120);
			g2d.drawString(modele.level.levelName,modele.gameWidth-170 , 160);
			g2d.drawString("Bonus : ",30 , 40);
			g2d.setColor(new Color(255,120,128));
			g2d.drawString(modele.stringScore,modele.gameWidth-170 , 80);
			

			// dessine le cadre à droite 
			g2d.setStroke(new BasicStroke(1));
			g2d.setColor(Color.BLACK);
			g2d.drawRect(modele.gameWidth-180, 10, 160, 200);//gauche	
			g2d.drawRect(20, 10, 160, 200);//droite

			//dessine les ombres des paneau du bonus et de du score 
			g2d.setColor(new Color(5,5,5,128));
			g2d.fillRect(modele.gameWidth-20, 15, 10, 205);//gaucheGauche
			g2d.fillRect(modele.gameWidth-175,210, 155, 10);//gaucheBAS

			g2d.setColor(new Color(5,5,5,128));
			g2d.fillRect(180, 15, 10, 205);//droiteGauche
			g2d.fillRect(25,210, 155, 10);//droiteBas
			
			if(modele.win) {
				g2d.setColor(new Color(3,3,3,200));
				g2d.fillRect(0,0,modele.gameWidth,modele.gameHeight);

				//dégradé1 de couleur
				GradientPaint gp0 = new GradientPaint(modele.endScreen.getX()-100, 0, transparentColor1, modele.gameWidth-100, modele.gameHeight, transparentColor2, true);                
				g2d.setPaint(gp0);
				g2d.fillRect(modele.endScreen.getX(), modele.endScreen.getY(), modele.endScreen.getWidth(), modele.endScreen.getHeight());
				
			}
			
			if(modele.lose) {
				// dessin d'un fond  noir avec transparance pour un certain effet
				g2d.setColor(new Color(3,3,3,200));
				g2d.fillRect(0,0,modele.gameWidth,modele.gameHeight);

				//dégradé1 de couleur
				GradientPaint gp0 = new GradientPaint(modele.endScreen.getX()-100, 0, transparentColor1, modele.gameWidth-100, modele.gameHeight, transparentColor2, true);                
				g2d.setPaint(gp0);
				g2d.fillRect(modele.endScreen.getX(), modele.endScreen.getY(), modele.endScreen.getWidth(), modele.endScreen.getHeight());
				g2d.setTransform(oldTransform);
				modele.backlevelSelectionButton.setBounds(modele.endScreen.getX()+200, modele.endScreen.getY()+500, 200, 60);
			}
		}

	}
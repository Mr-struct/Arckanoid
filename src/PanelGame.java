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
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelGame extends JPanel {

	private static final long serialVersionUID = 1L;

	private Vue vue;

	private Image imgBackground,imgDefaultRaquette,imgDefaultBalle;

	private  ArrayList<Image> imgBrique = new ArrayList<Image>(0);
	
	private ArrayList<Image> imgBonus = new ArrayList<Image>(0);
	
	private ImageIcon fier1,fier2,fier3 ,imgConfettis;

	private Font myFont,firstWord,therdWord;
	
	int widthBalle = 10, heightBalle = 10;

	public PanelGame (int width,int height,Vue vue) {
	
		super();
		
		this.vue = vue;

		this.setSize(width,height);

		try {
			myFont = Font.createFont(Font.TRUETYPE_FONT, new File("./Fonts/SFAlienEncountersSolid.ttf")).deriveFont(20f);
			
			firstWord  = Font.createFont(Font.TRUETYPE_FONT, new File("./Fonts/SFAlienEncountersSolid.ttf")).deriveFont(80f);

			therdWord  = Font.createFont(Font.TRUETYPE_FONT, new File("./Fonts/SFAlienEncountersSolid.ttf")).deriveFont(30f);

		} catch (FontFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// on charge les image 
		fier1 = new ImageIcon("./Obj/fierWorks1.gif");
		fier2 = new ImageIcon("./Obj/fierWorks2.gif");
		fier3 = new ImageIcon("./Obj/fierWorks3.gif");
		imgConfettis = new ImageIcon("./Obj/test1.gif");
		try {
			imgBackground = ImageIO.read(new File(vue.modele.level.levelBackground));

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
		this.add(vue.backLevelSelectButton);
		this.add(vue.nextLevelButton);
		this.add(vue.restartButton);
	}

	public void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		vue.backLevelSelectButton.setBounds(-this.getWidth(), -this.getHeight(), 0, 0);
		vue.restartButton.setBounds(-this.getWidth(),-this.getHeight(), 0, 0);
		vue.backLevelSelectButton.setBounds(-this.getWidth(), -this.getHeight(), 0, 0);
		vue.nextLevelButton.setBounds(-this.getWidth(),-this.getHeight(), 0, 0);
		
		AffineTransform tx = new AffineTransform();
		AffineTransform oldTransform = new AffineTransform();
		oldTransform = g2d.getTransform();
		tx.scale((float) this.getWidth()/ (float)vue.modele.gameWidth, (float) this.getHeight()/ (float)vue.modele.gameHeight);
		g2d.setTransform(tx);
		
		//dessine le fond 
		g2d.drawImage(imgBackground, 0,0,vue.modele.gameWidth,vue.modele.gameHeight, null);

		//couleur mis a jour pour la transparence 
		g2d.setColor(new Color(6,6,6,128));

		
		//dessine les bonus
		synchronized (vue.modele.bonus){
			for(Bonus bn : vue.modele.bonus) {
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
		synchronized (vue.modele.balles){
			for(Balle balle: vue.modele.balles) {
				//dessine l'ombre de la balle
				g2d.setColor(new Color(6, 6, 6,128));
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.fillOval(balle.getIntX()+10,balle.getIntY()+10,balle.getWidth(),balle.getHeight());
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

			}
		}
		
		g2d.setColor(new Color(6,6,6,128));
		// dessine les briques en fonction du type
		synchronized (vue.modele.briques){
			for(Brique br : vue.modele.briques) {
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
		//dessine la balle
				synchronized (vue.modele.balles){
					for(Balle balle: vue.modele.balles) {
						
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
			//dessine l'ombre de la raquette 
			g2d.fillRoundRect(vue.modele.raquette.getX()+15, vue.modele.raquette.getY()+15, vue.modele.raquette.getWidth(), vue.modele.raquette.getHeight(),10,10);

			// dessine la raquette
			g2d.drawImage(imgDefaultRaquette, vue.modele.raquette.getX(),vue.modele.raquette.getY(),vue.modele.raquette.getWidth(),vue.modele.raquette.getHeight(), null);
			
			//de la transparance sur les coter de la fenaitre pour un effet !
			Color transparentColor1 = new Color(59, 59, 112, 128);
			Color transparentColor2 = new Color(159, 59, 240,255);

			//dégradé1 de couleur
			GradientPaint gp1 = new GradientPaint(vue.modele.gameWidth-100, 0, transparentColor1, vue.modele.gameWidth-100, vue.modele.gameHeight, transparentColor2, true);                
			g2d.setPaint(gp1);
			g2d.fillRect((vue.modele.gameWidth-200), 0, 200, vue.modele.gameHeight);

			//dégrade2 de couleur
			GradientPaint gp2 = new GradientPaint(100, 0, transparentColor1, 100, vue.modele.gameHeight, transparentColor2, true);                
			g2d.setPaint(gp2);
			g2d.fillRect(0, 0, 200, vue.modele.gameHeight);
			
			/*
			 * affiche le gain lord d'une colision avec une brique 
			 */
			g2d.setFont(myFont);
			
			synchronized(vue.modele.effects) {
				
				for(AnimatedObject effect : vue.modele.effects) {
					g2d.setColor(new Color(255,255,255,255-effect.getAge()));
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
					g2d.drawString(effect.getPopUp(), effect.getX(), effect.getY());
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
					
				}
			}
			synchronized(vue.modele.explosions) {
				for(AnimatedObject explosion : vue.modele.explosions) {
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
			g2d.drawString("SCORE : ",vue.modele.gameWidth-170 , 40);
			g2d.drawString(vue.modele.level.levelRank,vue.modele.gameWidth-170 , 120);
			g2d.drawString(vue.modele.level.levelName,vue.modele.gameWidth-170 , 160);
			g2d.drawString("Bonus : ",30 , 40);
			g2d.setColor(new Color(255,120,128));
			g2d.drawString(vue.modele.stringScore,vue.modele.gameWidth-170 , 80);
			

			//dessine les ombres des paneau du bonus et de du score 
			g2d.setColor(new Color(5,5,5,128));
			g2d.setStroke(new BasicStroke(1));
			g2d.fillRect(vue.modele.gameWidth-20, 15, 10, 205);//gaucheGauche
			g2d.fillRect(vue.modele.gameWidth-175,210, 155, 10);//gaucheBAS

			g2d.fillRect(180, 15, 10, 205);//droiteGauche
			g2d.fillRect(25,210, 155, 10);//droiteBas
			
			// dessine le cadre à droite 
			g2d.setColor(Color.BLACK);
			g2d.drawRect(vue.modele.gameWidth-180, 10, 160, 200);//gauche	
			g2d.drawRect(20, 10, 160, 200);//droite

		
			
			if(vue.modele.win) {
				
				//dessine le fond transparant noir 
				g2d.setColor(new Color(3,3,3,200));
				g2d.fillRect(0,0,vue.modele.gameWidth,vue.modele.gameHeight);
				
				//dessine le degrade de couleur
				GradientPaint gp0 = new GradientPaint(vue.modele.endScreen.getX()-100,
									0, transparentColor1, 
									vue.modele.gameWidth-100,
									vue.modele.gameHeight,
									transparentColor2, true);                
				g2d.setPaint(gp0);
				g2d.fillRect(vue.modele.endScreen.getX(),
							vue.modele.endScreen.getY(), 
							vue.modele.endScreen.getWidth(),
							vue.modele.endScreen.getHeight());
				
				//dessine le carer centrale
				GradientPaint gp5 = new GradientPaint( vue.modele.endScreen.getX() + 100 + (vue.modele.endScreen.getWidth() - 200) / 2  ,
														vue.modele.endScreen.getY()+100,
														new Color(255,10,128),
														vue.modele.endScreen.getX() + 100 + (vue.modele.endScreen.getWidth() - 200) / 2, 
														vue.modele.endScreen.getY() + 100 + vue.modele.endScreen.getHeight() - 200,
														new Color(10,100,255), true);                
				g2d.setPaint(gp5);
				g2d.setStroke(new BasicStroke(8));
				g2d.drawRoundRect(
								vue.modele.endScreen.getX()+100,
								vue.modele.endScreen.getY()+100,
								vue.modele.endScreen.getWidth() - 200, vue.modele.endScreen.getHeight() - 200,10,10);
				//dessine le you win
				g2d.setFont(firstWord);
				GradientPaint gp3 = new GradientPaint(vue.modele.endScreen.getX()+350,
													vue.modele.endScreen.getY()+200,
													new Color(107, 0, 196), 
													vue.modele.endScreen.getX() +350,
													vue.modele.endScreen.getY()+400,
													new Color(0, 255, 233), true);
				g2d.setPaint(gp3);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.drawString("YOU WIN",vue.modele.endScreen.getX()+300, vue.modele.endScreen.getY()+300);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
				
				//dessine le score final
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.drawString(vue.modele.stringScore, vue.modele.endScreen.getX()+400 -(firstWord.getSize()/2), vue.modele.endScreen.getY()+440);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
				
				//dessine le score
				g2d.setFont(therdWord);
				
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.drawString("Your Score :",vue.modele.endScreen.getX()+350, vue.modele.endScreen.getY()+350);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
	
				//dessine les conffetis
				g2d.drawImage(imgConfettis.getImage(), vue.modele.endScreen.getX(), vue.modele.endScreen.getY(),vue.modele.endScreen.getWidth(), vue.modele.endScreen.getHeight(),imgConfettis.getImageObserver());

				// dessine les feu d'artifis
				g2d.drawImage(fier1.getImage(), vue.modele.endScreen.getX()-40, vue.modele.endScreen.getY(),300, 300,fier1.getImageObserver());
				
				g2d.drawImage(fier2.getImage(), vue.modele.endScreen.getX()-40, vue.modele.endScreen.getY(),350, 350,fier2.getImageObserver());
				
				g2d.drawImage(fier1.getImage(), vue.modele.endScreen.getX()+680, vue.modele.endScreen.getY(),300, 300,fier1.getImageObserver());
				
				g2d.drawImage(fier2.getImage(), vue.modele.endScreen.getX()+650, vue.modele.endScreen.getY(),350, 350,fier2.getImageObserver());
			
				//g2d.drawImage(fier3.getImage(), vue.modele.endScreen.getX()+300, vue.modele.endScreen.getY()+200,300, 300,fier3.getImageObserver());
				/*if( vue.backLevelSelectButton.getParent() == null && vue.nextLevelButton.getParent() == null ) {
					this.add(vue.nextLevelButton);
					this.add(vue.backLevelSelectButton);
				}*/
				//dessine les button
				g2d.setTransform(oldTransform);
				int x = this.getWidth()/2;
				int y = vue.modele.endScreen.getY()+this.getHeight()/2 + this.getHeight()/8;;
				vue.backLevelSelectButton.setBounds(x-this.getWidth()/8 - 64, y, 64, 64);
				vue.nextLevelButton.setBounds(x+this.getWidth()/8,y, 64, 64);
	
			}			
			else
			if(vue.modele.lose) {
				
				// dessin d'un fond  noir avec transparance pour un certain effet
				g2d.setColor(new Color(3,3,3,200));
				g2d.fillRect(0,0,vue.modele.gameWidth,vue.modele.gameHeight);
				//dégradé1 de couleur
				GradientPaint gp5 = new GradientPaint(vue.modele.endScreen.getX()-100, 0, transparentColor1, vue.modele.gameWidth-100, vue.modele.gameHeight, transparentColor2, true);                
				g2d.setPaint(gp5);
				g2d.fillRect(vue.modele.endScreen.getX(), vue.modele.endScreen.getY(), vue.modele.endScreen.getWidth(), vue.modele.endScreen.getHeight());
				
				//dessine le degrade de couleur
				GradientPaint gp0 = new GradientPaint(vue.modele.endScreen.getX()-100,
									0, transparentColor1, 
									vue.modele.gameWidth-100,
									vue.modele.gameHeight,
									transparentColor2, true);                
				g2d.setPaint(gp0);
				g2d.fillRect(vue.modele.endScreen.getX(),
							vue.modele.endScreen.getY(), 
							vue.modele.endScreen.getWidth(),
							vue.modele.endScreen.getHeight());
				
				//dessine le carer centrale
				GradientPaint gp7 = new GradientPaint( vue.modele.endScreen.getX() + 100 + (vue.modele.endScreen.getWidth() - 200) / 2  ,
														vue.modele.endScreen.getY()+100,
														new Color(107, 0, 196),
														vue.modele.endScreen.getX() + 100 + (vue.modele.endScreen.getWidth() - 200) / 2, 
														vue.modele.endScreen.getY() + 100 + vue.modele.endScreen.getHeight() - 200,
														new Color(0, 255, 233), true);                
				g2d.setPaint(gp7);
				g2d.setStroke(new BasicStroke(8));
				g2d.drawRoundRect(
								vue.modele.endScreen.getX()+100,
								vue.modele.endScreen.getY()+100,
								vue.modele.endScreen.getWidth() - 200, vue.modele.endScreen.getHeight() - 200,10,10);
				//dessine le you win
				g2d.setFont(firstWord);
				GradientPaint gp3 = new GradientPaint(vue.modele.endScreen.getX()+350,
						vue.modele.endScreen.getY()+200,
						new Color(107, 0, 196), 
						vue.modele.endScreen.getX() +350,
						vue.modele.endScreen.getY()+400,
						new Color(0, 255, 233), true);
				g2d.setPaint(gp3);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.drawString("YOU LOOSE",vue.modele.endScreen.getX()+250, vue.modele.endScreen.getY()+300);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
				
				//dessine le score final
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.drawString(vue.modele.stringScore, vue.modele.endScreen.getX()+400 -(firstWord.getSize()/2), vue.modele.endScreen.getY()+440);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
				
				//dessine your  score
				g2d.setFont(therdWord);
				
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.drawString("Your Score :",vue.modele.endScreen.getX()+350, vue.modele.endScreen.getY()+350);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
				
				// on dessine les button
				/*if( vue.backLevelSelectButton.getParent() == null && vue.restartButton.getParent() == null ) {
					this.add(vue.restartButton);
					this.add(vue.backLevelSelectButton);
				}*/
				g2d.setTransform(oldTransform);
				int x = this.getWidth()/2;
				int y = vue.modele.endScreen.getY()+this.getHeight()/2 + this.getHeight()/8;
				vue.backLevelSelectButton.setBounds(x-this.getWidth()/8 - 64, y, 64, 64);
				vue.restartButton.setBounds(x+this.getWidth()/8,y, 64, 64);
			}

		}

	}
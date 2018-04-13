package Views;

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
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import Controls.ControleurGame;
import Objects.AnimatedObject;
import Objects.Ball;
import Objects.Bonus;
import Objects.Brick;

public class PanelGame extends JPanel{

	private static final long serialVersionUID = 1L;

	private Vue vue;

	private Image imgDefaultRaquette;

	private  ArrayList<Image> imgBrique = new ArrayList<Image>(0);

	private ArrayList<Image> imgBonus = new ArrayList<Image>(0);

	private ImageIcon fier1,fier2,thunder ,imgConfettis, imgBackground1;

	private Font myFont,firstWord,therdWord;

	private ArrayList <AnimatedObject> ballsTrac = new ArrayList<AnimatedObject>();

	private Color transparentColor1 = new Color(255, 0, 203, 255);
	private Color transparentColor2 = new Color(0, 200, 255, 255);
	private float scaleX = 0.0f;
	private float scaleY = 0.0f;
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
		imgConfettis = new ImageIcon("./Obj/test1.gif");
		thunder = new ImageIcon("./Obj/thunder.gif");
		try {
		
			
			imgBackground1 = new ImageIcon(vue.modele.level.levelBackground);

			imgDefaultRaquette = ImageIO.read(new File("./Obj/paddelS.png"));

			for(int i = 0 ; i< 6;i++) {

				imgBrique.add( ImageIO.read(new File("./Obj/br"+i+".png")) );

			}
			for(int i = 0 ; i < 4;i++) {

				imgBonus.add( ImageIO.read(new File("./Obj/bn"+i+".png")) );

			}

		} catch (IOException e) {

			e.printStackTrace();
		}
		this.add(vue.backLevelSelectButton);
		this.add(vue.nextLevelButton);
		this.add(vue.restartButton);
		this.add(vue.pauseButton);
		this.add(vue.soundButton);
		this.add(vue.crossButton);
		ControleurGame ctrlGame = new ControleurGame(this.vue.modele,this.vue);
		this.addMouseListener(ctrlGame);
		this.addMouseMotionListener(ctrlGame);
		this.addKeyListener(ctrlGame);
	}

	public void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D)g;
		//mise en place des buttons
		scaleX = (float)(this.getWidth())/(float)(vue.modele.gameWidth);
		scaleY =  (float)(this.getHeight())/(float)(vue.modele.gameHeight);
		
		AffineTransform tx = new AffineTransform();
		tx.scale((float) this.getWidth()/ (float)vue.modele.gameWidth, (float) this.getHeight()/ (float)vue.modele.gameHeight);

		g2d.setTransform(tx);
		// on dessine les boutons qu'on utilise pas en dehor de la zonne du jeu
		vue.backLevelSelectButton.drawButton(-vue.modele.gameWidth, -vue.modele.gameHeight, 64, 64,scaleX,scaleY,g2d);
		vue.restartButton.drawButton(-vue.modele.gameWidth, -vue.modele.gameHeight, 64, 64,scaleX,scaleY,g2d);
		vue.backLevelSelectButton.drawButton(-vue.modele.gameWidth, -vue.modele.gameHeight, 64, 64,scaleX,scaleY,g2d);
		vue.nextLevelButton.drawButton(-vue.modele.gameWidth, -vue.modele.gameHeight, 64, 64,scaleX,scaleY,g2d);
		vue.crossButton.drawButton(-vue.modele.gameWidth, -vue.modele.gameHeight, 64, 64,scaleX,scaleY,g2d);
		

		//dessine le fond 
		//g2d.drawImage(imgBackground, 0,0,vue.modele.gameWidth,vue.modele.gameHeight, null);
		g2d.drawImage(imgBackground1.getImage(),0,0,vue.modele.gameWidth,vue.modele.gameHeight, null);


		//couleur mis a jour pour la transparence 
		g2d.setColor(new Color(6,6,6,128));

		//dessine l'ombre des bonus
		synchronized (vue.modele.bonus){
			for(Bonus bn : vue.modele.bonus) {
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

				g2d.fillRoundRect(bn.getX()+15,bn.getY()+15,bn.getWidth(),bn.getHeight(),15,15);

				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
			}
		}

		//dessine l'ombre de la raquette 
		g2d.fillRoundRect(vue.modele.raquette.getX()+15, vue.modele.raquette.getY()+15, vue.modele.raquette.getWidth(), vue.modele.raquette.getHeight(),10,10);

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
				case 3: 
					g2d.drawImage(imgBonus.get(3), bn.getX(),bn.getY(),bn.getWidth(),bn.getHeight(), this);

					break;
				};
			}
		}

		//rajoute des traces pour la balle
		synchronized (vue.modele.balls){
			for(Ball ball: vue.modele.balls) {
				synchronized(ballsTrac) {
					ballsTrac.add (new AnimatedObject(ball.getIntX(),ball.getIntY(),ball.getWidth(),ball.getHeight()));
				}
			}
		}
		
		//dessine la trac de la balle
		synchronized(ballsTrac) { for(AnimatedObject trac : ballsTrac) {
			g2d.setColor(new Color(255- (10*trac.getAge()), 50 + trac.getAge(),255,180 - trac.getAge()));
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.fillOval( trac.getX() + trac.getAge()/2,
					trac.getY() + trac.getAge()/2,
					trac.getWidth(),
					trac.getHeight());
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

			trac.timer.schedule(new TimerTask(){

				public  void run() {
					trac.setWidth(trac.getWidth()-1);
					trac.setHeight(trac.getHeight()-1);
					trac.setAge(trac.getAge()+1);
					if(trac.getWidth() <=1){
						synchronized(ballsTrac) {
							ballsTrac.remove(trac);
						}
						this.cancel();
					}
				}
			}, 0, 500);
		}
		}
		g2d.setColor(new Color(6,6,6,128));

		// dessine les briques en fonction du type
		synchronized (vue.modele.bricks){
			for(Brick br : vue.modele.bricks) {
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

		// dessine la raquette
		g2d.drawImage(imgDefaultRaquette, vue.modele.raquette.getX(),vue.modele.raquette.getY(),vue.modele.raquette.getWidth(),vue.modele.raquette.getHeight(), null);

		//dessine l'animation des balles perdu
		synchronized(vue.modele.lostBallsAnimation){
			for(AnimatedObject o : vue.modele.lostBallsAnimation) {	
				g2d.setStroke(new BasicStroke(o.getAge()));
				g2d.setColor(new Color(255,0,0,130-o.getAge()));
				g2d.drawRect(0, o.getY(), vue.modele.gameWidth, o.getHeight());
			}
		}
		//dessine l'eclaire si la raquette est magnetique
		if(vue.modele.curBonus==3)
			g2d.drawImage(thunder.getImage(), vue.modele.raquette.getX(),vue.modele.raquette.getY()-10,vue.modele.raquette.getWidth(),50, null);

		//dessine la balle
		synchronized (vue.modele.balls){
			for(Ball ball: vue.modele.balls) {


				
				//dessine le halo sur la balle
			/*	
			 * a decomneter pour avoir l'effet de lumer sur la balle
			 * 
			 * g2d.setColor(new Color(255,255,255,80));
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.fillOval(balle.getIntX()-10,balle.getIntY()-10,balle.getWidth()+20,balle.getHeight()+20);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

				g2d.setColor(new Color(255,255,255,60));
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.fillOval(balle.getIntX()-20,balle.getIntY()-20,balle.getWidth()+40,balle.getHeight()+40);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

				g2d.setColor(new Color(255,255,255,40));
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.fillOval(balle.getIntX()-35,balle.getIntY()-35,balle.getWidth()+70,balle.getHeight()+70);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
				
				g2d.setColor(new Color(255,255,255,20));
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.fillOval(balle.getIntX()-55,balle.getIntY()-55,balle.getWidth()+110,balle.getHeight()+110);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
				*/
				//dessine la balle
				GradientPaint gp1 = new GradientPaint(ball.getIntX() ,ball.getIntY()-5 ,Color.WHITE ,ball.getIntX() + ball.getWidth() ,ball.getIntY()+ball.getHeight() , Color.BLACK);                
				g2d.setPaint(gp1);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				//g2d.drawImage(imgDefaultBalle, balle.getX(),balle.getY(),balle.getWidth(),balle.getHeight(), this);
				g2d.fillOval(ball.getIntX(),ball.getIntY(),ball.getWidth(),ball.getHeight());
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

			}
		}

		



		//dessine les bords transparants noir 
		g2d.setColor(new Color(0,0,0,128));
		g2d.fillRect((vue.modele.gameWidth-200), 0, 200, vue.modele.gameHeight); // celui de droite
		g2d.fillRect(0, 0, 200, vue.modele.gameHeight); // celeui de gauche
		
		//dessine les ombres des paneau du bonus et de du score 
		g2d.setColor(Color.BLACK);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillRoundRect(vue.modele.gameWidth-170, 20, 155, vue.modele.gameHeight/2-vue.modele.gameHeight/20  + 69, 10, 10);//droite	
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillRoundRect(30, 20, 155, vue.modele.gameHeight/2-vue.modele.gameHeight/20  + 69,10,10);//gauche
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		
		/*
		g2d.fillRoundRect(vue.modele.gameWidth-20, 15, 10, vue.modele.gameHeight/2-vue.modele.gameHeight/20  + 79,5,5);//droite Gauche
		g2d.fillRoundRect(vue.modele.gameWidth-175,vue.modele.gameHeight/2-vue.modele.gameHeight/20  + 84, 155, 10,5,5);//droite BAS

		g2d.fillRoundRect(180, 15, 10, vue.modele.gameHeight/2-vue.modele.gameHeight/20  + 79,5,5);//Gauche Gauche
		g2d.fillRoundRect(25,vue.modele.gameHeight/2-vue.modele.gameHeight/20  + 84, 155, 10,5,5);//gauche Bas
		*/
		// dessine le cadre a droite 
		GradientPaint gpCardre1 = new GradientPaint(vue.modele.gameWidth-180 + 160/2, 10, transparentColor1,vue.modele.gameWidth-180 + 160/2, vue.modele.gameHeight/2-vue.modele.gameHeight/20  + 74, transparentColor2, true);                
		g2d.setPaint(gpCardre1);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillRoundRect(vue.modele.gameWidth-180, 10, 160, vue.modele.gameHeight/2-vue.modele.gameHeight/20  + 74, 10, 10);//droite	
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		GradientPaint gpCardre2 = new GradientPaint(90, 10, transparentColor1, 90, vue.modele.gameHeight/2-vue.modele.gameHeight/20  + 74, transparentColor2, true);                
		g2d.setPaint(gpCardre2);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillRoundRect(20, 10, 160, vue.modele.gameHeight/2-vue.modele.gameHeight/20  + 74,10,10);//gauche
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		
		
		g2d.setFont(myFont);
		g2d.setColor(Color.CYAN);

		//dessine le  string score a droite
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString("SCORE : ",vue.modele.gameWidth-170 , 40);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		//dessine le niveau du jeux a droite
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString(vue.modele.level.levelRank,vue.modele.gameWidth-170 , 120);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		//dessine le nom du niveau a droite
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString(vue.modele.level.levelName,vue.modele.gameWidth-170 , 160);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		//dessine le string bonus a gauche
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString("Bonus : ",30 , 40);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		//dessin le string lives a gauche
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString("Lives : ",30 , vue.modele.gameHeight/2 -vue.modele.gameHeight/5);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		//dessine les vies a droite
		for(int i = 0 ; i < vue.modele.vies; i ++) {
			g2d.drawImage(imgDefaultRaquette, 30, vue.modele.gameHeight/2 -vue.modele.gameHeight/7 + (i * 30), 140, 15,null);
		}
		g2d.setColor(new Color(0,255,128));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString(vue.modele.stringScore,vue.modele.gameWidth-170 , 80);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		
		//dessine le carde qui va contenir les bonus
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.black);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawRect(30,90,80,80);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		//g2d.drawString("ajouter ici timer pour le bonus",135,130);

		//dessine le bonus courrant
		if(vue.modele.curBonus>=0) {
			g2d.drawImage(imgBonus.get(vue.modele.curBonus),40,100,60,60,this);
		}

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
		
		//dessine les boutons de pause et de sond
		
		vue.pauseButton.drawButton(this.vue.modele.gameWidth - 164,
									this.vue.modele.gameHeight/2 - this.vue.modele.gameHeight/6,64,64,scaleX,scaleY,g2d);
		
		vue.soundButton.drawButton(this.vue.modele.gameWidth - 164,
									this.vue.modele.gameHeight/2 - this.vue.modele.gameHeight/20,64,64,scaleX,scaleY,g2d);

		if(vue.modele.win) {
			if(vue.pauseButton.getParent()!= null && vue.crossButton.getParent()!=null) {
				this.remove(vue.pauseButton);
				this.remove(vue.soundButton);
				System.out.println("removed");
			}

			//dessine le fond transparant noir 
			g2d.setColor(new Color(3,3,3,200));
			g2d.fillRect(0,0,vue.modele.gameWidth,vue.modele.gameHeight);

			//dessine le degrade de couleur
			GradientPaint gp0 = new GradientPaint(vue.modele.endScreen.getX()+vue.modele.endScreen.getWidth()/2,
					0, transparentColor1, 
					vue.modele.endScreen.getX()+vue.modele.endScreen.getWidth()/2,
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
			g2d.setStroke(new BasicStroke(10));
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.drawRoundRect(
					vue.modele.endScreen.getX()+100,
					vue.modele.endScreen.getY()+100,
					vue.modele.endScreen.getWidth() - 200, vue.modele.endScreen.getHeight() - 200,10,10);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

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
			g2d.drawString("Your Score :",vue.modele.endScreen.getX()+365, vue.modele.endScreen.getY()+350);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

			//dessine les conffetis
			g2d.drawImage(imgConfettis.getImage(), vue.modele.endScreen.getX(), vue.modele.endScreen.getY(),vue.modele.endScreen.getWidth(), vue.modele.endScreen.getHeight(),imgConfettis.getImageObserver());

			// dessine les feu d'artifis
			g2d.drawImage(fier1.getImage(), vue.modele.endScreen.getX()-40, vue.modele.endScreen.getY(),300, 300,fier1.getImageObserver());

			g2d.drawImage(fier2.getImage(), vue.modele.endScreen.getX()-40, vue.modele.endScreen.getY(),350, 350,fier2.getImageObserver());

			g2d.drawImage(fier1.getImage(), vue.modele.endScreen.getX()+680, vue.modele.endScreen.getY(),300, 300,fier1.getImageObserver());

			g2d.drawImage(fier2.getImage(), vue.modele.endScreen.getX()+650, vue.modele.endScreen.getY(),350, 350,fier2.getImageObserver());

			int x = this.vue.modele.gameWidth/2;
			int y = this.vue.modele.endScreen.getY() + this.vue.modele.gameHeight/2 + this.vue.modele.gameHeight / 8;
			
			
			//on d�place les boutons qu'on veut pas afficher hors de la zone  
			vue.nextLevelButton.drawButton(x+this.vue.modele.gameWidth/8,y, 64, 64,scaleX,scaleY,g2d);
			vue.backLevelSelectButton.drawButton(x-this.vue.modele.gameWidth/8 - 64, y, 64, 64,scaleX,scaleY,g2d);
			vue.pauseButton.drawButton(-vue.modele.gameWidth, -vue.modele.gameHeight, 0, 0,scaleX,scaleY,g2d);
			vue.soundButton.drawButton(-vue.modele.gameWidth, -vue.modele.gameHeight, 0, 0,scaleX,scaleY,g2d);

		}			
		else
			if(vue.modele.lose) {
				if(vue.pauseButton.getParent()!= null && vue.crossButton.getParent()!=null) {
					this.remove(vue.pauseButton);
					this.remove(vue.soundButton);
				}
				// dessin d'un fond  noir avec transparance pour un certain effet
				g2d.setColor(new Color(3,3,3,200));
				g2d.fillRect(0,0,vue.modele.gameWidth,vue.modele.gameHeight);
			
			/*	//degrade de couleur
				GradientPaint gp5 = new GradientPaint(vue.modele.gameWidth/2,
						0, transparentColor1,
						vue.modele.gameWidth/2,
						vue.modele.gameHeight, transparentColor2, true);                
				g2d.setPaint(gp5);
				g2d.fillRect(vue.modele.endScreen.getX(), vue.modele.endScreen.getY(), vue.modele.endScreen.getWidth(), vue.modele.endScreen.getHeight());
				 */
				//dessine le carer centrale
				GradientPaint gp7 = new GradientPaint( vue.modele.endScreen.getX() + 100 + (vue.modele.endScreen.getWidth() - 200) / 2  ,
						vue.modele.endScreen.getY()+100,
						new Color(107, 0, 196),
						vue.modele.endScreen.getX() + 100 + (vue.modele.endScreen.getWidth() - 200) / 2, 
						vue.modele.endScreen.getY() + 100 + vue.modele.endScreen.getHeight() - 200,
						new Color(0, 255, 233), true);                
				g2d.setPaint(gp7);
				g2d.setStroke(new BasicStroke(10));
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.drawRoundRect(
						vue.modele.endScreen.getX()+100,
						vue.modele.endScreen.getY()+100,
						vue.modele.endScreen.getWidth() - 200, vue.modele.endScreen.getHeight() - 200,10,10);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

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
				g2d.drawString("Your Score :",vue.modele.endScreen.getX()+365, vue.modele.endScreen.getY()+350);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

				int x = this.vue.modele.gameWidth/2;
				int y = this.vue.modele.endScreen.getY() + this.vue.modele.gameHeight/2 + this.vue.modele.gameHeight / 8;
				vue.backLevelSelectButton.drawButton(x-this.vue.modele.gameWidth/8 - 64, y, 64, 64,scaleX,scaleY,g2d);
				vue.restartButton.drawButton(x+this.vue.modele.gameWidth/8,y, 64, 64,scaleX,scaleY,g2d);
				
				vue.pauseButton.drawButton(-vue.modele.gameWidth, -vue.modele.gameHeight, 0, 0,scaleX,scaleY,g2d);
				vue.soundButton.drawButton(-vue.modele.gameWidth, -vue.modele.gameHeight, 0, 0,scaleX,scaleY,g2d);

			}else
				if(!vue.modele.isRunning()) {
					// dessin d'un fond  noir avec transparance pour un certain effet
					g2d.setColor(new Color(3,3,3,200));
					g2d.fillRect(0,0,vue.modele.gameWidth,vue.modele.gameHeight);
					//dessine le carer centrale
					GradientPaint gp7 = new GradientPaint( 300 +  (vue.modele.gameWidth-200) / 2  ,
							100,
							new Color(107, 0, 196),
							300 +  (vue.modele.gameWidth-200) / 2, 
							vue.modele.gameHeight -600,
							new Color(0, 255, 233), true);                
					g2d.setPaint(gp7);
					g2d.setStroke(new BasicStroke(10));
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
					g2d.drawRoundRect(300  ,100,vue.modele.gameWidth-600, vue.modele.gameHeight - 200,10,10);
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

					g2d.setFont(firstWord);
					GradientPaint gp3 = new GradientPaint(450,
							300,
							new Color(107, 0, 196), 
							450,
							350,
							new Color(0, 255, 233), true);
					g2d.setPaint(gp3);
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
					g2d.drawString("PAUSE",vue.modele.gameWidth/2-150, 300);
					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
					
					int x = this.vue.modele.gameWidth/2;
					int y = this.vue.modele.gameHeight/2 + this.vue.modele.gameHeight / 8;
					vue.backLevelSelectButton.drawButton(x-this.vue.modele.gameWidth/8 - 64, y, 64, 64,scaleX,scaleY,g2d);
					vue.crossButton.drawButton(x+this.vue.modele.gameWidth/8,y, 64, 64,scaleX,scaleY,g2d);
					
					vue.pauseButton.drawButton(-vue.modele.gameWidth, -vue.modele.gameHeight, 0, 0,scaleX,scaleY,g2d);
					vue.soundButton.drawButton(-vue.modele.gameWidth, -vue.modele.gameHeight, 0, 0,scaleX,scaleY,g2d);

				}
		addNotify();
	}
	public void addNotify() {
		super.addNotify();
		requestFocus();
	}


}
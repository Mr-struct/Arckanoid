package views;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.Modele;
import objects.AnimatedObject;
import objects.Ball;
import objects.Bonus;
import objects.Brick;
@SuppressWarnings("serial")
/*
 * cette classe est utiliser pour afficher la demo dnas le panneau selecteur de niveau 
 * 
 * elle fonctione comme le panneau de jeu et a presque les meme attributs
 */
public class  LevelSelectView extends JPanel implements MouseListener{

	//les attributs

	private  Modele modele; //le modele c'est le modele demo

	private  View view; 

	private Image imgDefaultRaquette;

	private  ArrayList<Image> imgBrique = new ArrayList<Image>(0);

	private ArrayList<Image> imgBonus = new ArrayList<Image>(0);

	private ImageIcon thunder, imgBackground1;

	private Font myFont;

	private ArrayList <AnimatedObject> ballsTrac = new ArrayList<AnimatedObject>();

	private Color transparentColor1 = new Color(255, 0, 203, 255);

	private Color transparentColor2 = new Color(0, 200, 255, 255);

	private Color MousHoverClic = Color.WHITE;

	private Image imgChain;

	private boolean entred; // boolean utiliser pour savoir si la souris survole ou non le panneau demo

	@SuppressWarnings("unused")

	private float scaleX = 0.0f;

	@SuppressWarnings("unused")

	private float scaleY = 0.0f;

	/*
	 * constructeur 
	 * 
	 * @param view la vue generale
	 * 
	 * @param modele le modele demo contructeur avec un string
	 */
	public LevelSelectView (Modele modele,View view) {

		this.modele = modele;

		this.view = view;

		//on cahre les fonts
		try {

			myFont = Font.createFont(Font.TRUETYPE_FONT, new File("./Fonts/SFAlienEncountersSolid.ttf")).deriveFont(20f);

		} catch (FontFormatException e1) {

			e1.printStackTrace();

		} catch (IOException e1) {

			e1.printStackTrace();

		}

		// on charge les images 
		thunder = new ImageIcon("./Obj/thunder.gif");
		try {
			imgChain = ImageIO.read(new File("./Obj/chain.png"));

			imgBackground1 = new ImageIcon(modele.level.levelBackground);

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

		this.entred = false;

		//on test si on peut jouer le niveau et on rajoute le listener (on peut cliquer dessus

		if(this.modele.level.canBePlayed) {

			this.addMouseListener(this);

		}
	}

	// on dessine rien dans cette methode on va plutot utiliser notre propre methode de dessin pour le scale
	public void paintComponent(Graphics g) {
	}


	/*
	 * dessine le panneau avec des coordonnee compatible avec le scale du parent 
	 * 
	 * @param x la position x 
	 * 
	 * @param y la position y
	 * 
	 * @param width la largeur 
	 * 
	 * @param height la hauteur 
	 * 
	 * @param scaleX le scale x selon le scale x du parent
	 * 
	 * @param scaleY le scale y selon le scale y du parent
	 * 
	 * @param g2d le graphique associeer
	 */
	public void drawPreview(int x, int y, int width, int height, float scaleX, float scaleY, Graphics2D g2d) {

		//on set la position et la taille du panneau avec le scale 
		this.setBounds( (int)(scaleX*x), (int) (scaleY*y), (int) (scaleX*width), (int)(scaleY*height));

		AffineTransform tx = new AffineTransform();

		// on translate et scale le contenus du panneau cette fois 
		tx.translate((int)(scaleX*x), (int) (scaleY*y));

		tx.scale((float) (scaleX*width)/ (float)modele.gameWidth, (float) (scaleY*height)/ (float)modele.gameHeight);

		g2d.setTransform(tx);

		// on test si la souris survole le panneau on affiche un carre blac autoure
		if(entred) {

			g2d.setColor(MousHoverClic);

			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

			g2d.fillRoundRect(-20, -20,modele.gameWidth+40,modele.gameHeight+40,15,15);

			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		}

		//dessine le fond de la demo
		g2d.drawImage(imgBackground1.getImage(),0,0,modele.gameWidth,modele.gameHeight, null);

		//couleur mis a jour pour la transparence 
		g2d.setColor(new Color(6,6,6,128));

		//dessine l'ombre des bonus
		synchronized (modele.bonus){
			for(Bonus bn : modele.bonus) {
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

				g2d.fillRoundRect(bn.getX()+15,bn.getY()+15,bn.getWidth(),bn.getHeight(),15,15);

				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
			}
		}

		//dessine l'ombre de la raquette 
		g2d.fillRoundRect(modele.raquette.getX()+15, modele.raquette.getY()+15, modele.raquette.getWidth(), modele.raquette.getHeight(),10,10);

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
				case 3: 
					g2d.drawImage(imgBonus.get(3), bn.getX(),bn.getY(),bn.getWidth(),bn.getHeight(), this);

					break;
				};
			}
		}

		//rajoute des traces pour la balle
		synchronized (modele.balls){
			for(Ball ball: modele.balls) {
				synchronized(ballsTrac) {
					ballsTrac.add (new AnimatedObject(ball.getIntX(),ball.getIntY(),ball.getWidth(),ball.getHeight()));
				}
			}
		}

		//dessine la trace de la balle
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
		synchronized (modele.bricks){
			for(Brick br : modele.bricks) {
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
		g2d.drawImage(imgDefaultRaquette, modele.raquette.getX(),modele.raquette.getY(),modele.raquette.getWidth(),modele.raquette.getHeight(), null);


		//dessine l'eclaire si la raquette est magnetique
		if(modele.curBonus==3)
			g2d.drawImage(thunder.getImage(), modele.raquette.getX(),modele.raquette.getY()-10,modele.raquette.getWidth(),50, null);

		//dessine la balle
		synchronized (modele.balls){
			for(Ball ball: modele.balls) {
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
		g2d.fillRect((view.modele.gameWidth-200), 0, 200, view.modele.gameHeight); // celui de droite
		g2d.fillRect(0, 0, 200, view.modele.gameHeight); // celeui de gauche

		//dessine les ombres des panneau du bonus et de du score 
		g2d.setColor(Color.BLACK);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillRoundRect(view.modele.gameWidth-170, 20, 155, view.modele.gameHeight/2-view.modele.gameHeight/20  + 69, 10, 10);//droite	
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillRoundRect(30, 20, 155, view.modele.gameHeight/2-view.modele.gameHeight/20  + 69,10,10);//gauche
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		// dessine le cadre a droite 
		GradientPaint gpCardre1 = new GradientPaint(modele.gameWidth-180 + 160/2, 10, transparentColor1,modele.gameWidth-180 + 160/2, modele.gameHeight/2-modele.gameHeight/20  + 74, transparentColor2, true);                
		g2d.setPaint(gpCardre1);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillRoundRect(modele.gameWidth-180, 10, 160, modele.gameHeight/2-modele.gameHeight/20  + 74, 10, 10);//droite	
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		GradientPaint gpCardre2 = new GradientPaint(90, 10, transparentColor1, 90, modele.gameHeight/2-modele.gameHeight/20  + 74, transparentColor2, true);                
		g2d.setPaint(gpCardre2);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillRoundRect(20, 10, 160, modele.gameHeight/2-modele.gameHeight/20  + 74,10,10);//gauche
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		//set la bonne fonte
		g2d.setFont(myFont);

		g2d.setColor(Color.CYAN);

		//dessine le  string score a droite
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString("SCORE : ",modele.gameWidth-170 , 40);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		//dessine le niveau du jeux a droite
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString(modele.level.levelRank,modele.gameWidth-170 , 120);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		//dessine le nom du niveau a droite
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString(modele.level.levelName,modele.gameWidth-170 , 160);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		//dessine le string bonus a gauche
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString("Bonus : ",30 , 40);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		//dessin le string lives a gauche
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString("Lives : ",30 , modele.gameHeight/2 -modele.gameHeight/5);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		//dessine les vies a droite
		for(int i = 0 ; i < modele.vies; i ++) {
			g2d.drawImage(imgDefaultRaquette, 30, modele.gameHeight/2 -modele.gameHeight/7 + (i * 30), 140, 15,null);
		}
		g2d.setColor(new Color(255,120,128));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString(modele.stringScore,modele.gameWidth-170 , 80);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		//dessine le carde qui va contenir les bonus
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.black);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawRect(30,90,80,80);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		//dessine le bonus courrant
		if(modele.curBonus >= 0) {

			g2d.drawImage(imgBonus.get(modele.curBonus),40,100,60,60,this);

		}

		/*
		 * affiche le gain lors d'une colision avec une brique 
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

		//dessine les explosion lors d une colision
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

		// on test si le niveau peut etre jouer si c'est non on dessine une image de chaine 
		if(!this.modele.level.canBePlayed) {

			g2d.drawImage(imgChain,0,0,modele.gameWidth,modele.gameHeight,null);

		}

		this.getParent().repaint();

	}

	@Override
	public void mouseClicked(MouseEvent e) {


	}
	@Override
	/*
	 * lance le jeu lors du clique si le niveau est jouable 
	 * 
	 * le panneau general est vide 
	 * 
	 * les timer des autres panneaux sont arrete
	 * 
	 * le panneau du jeu selectione est init
	 * 
	 * on le rajoute au panneau central
	 * 
	 * et on lance son timer
	 * 
	 * on le reaffiche tout les 20 miliseconde
	 */
	public void mousePressed(MouseEvent e) {
		this.getParent().repaint();

		MousHoverClic = Color.YELLOW;

		System.out.println("index level : " + view.levelIndex);

		try {

			view.modele.intitlevel("./levels/level"+view.levelIndex+".txt");

		} catch (IOException e1) {

			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		view.thisPanel.removeAll();

		view.thisPanel.repaint();

		view.panelGame = new PanelGame(view.getWidth(),view.getHeight(),view);

		view.thisPanel.add(view.panelGame,BorderLayout.CENTER);

		view.timerPanelLevelSelect.cancel();

		view.timerPanelSettings.cancel();

		view.timerPanelMenu.cancel();

		view.timerPanelGame.cancel();

		view.timerView.cancel();

		modele.suspendreJeu();

		modele.timerPreview.cancel();

		view.timerPanelGame = new Timer();

		view.modele.lancerJeu();

		view.timerPanelGame.schedule(new TimerTask(){

			public void run(){

				view.panelGame.repaint();
			}
		}, 0, 20);
	}
	@Override
	/*
	 * la couleur est du carre qui entoure la demo est change et le parent est repeint
	 */
	public void mouseReleased(MouseEvent e) {
		
		this.getParent().repaint();
		MousHoverClic = Color.WHITE;


	}
	@Override
	/*
	 * la couleur est du carre qui entoure la demo est change et le parent est repeint
	 */
	public void mouseEntered(MouseEvent e) {
		this.getParent().repaint();
		MousHoverClic = Color.WHITE;
		entred = true;

	}
	@Override
	/*
	 *  la couleur est du carre qui entoure la demo est change et le parent est repeint
	 */
	public void mouseExited(MouseEvent e) {
		this.getParent().repaint();
		entred = false;

	}
}
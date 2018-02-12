import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Affichage extends JPanel {

	private static final long serialVersionUID = 1L;

	private Modele modele;
	
	private Image imgBackground,imgDefaultRaquette,imgDefaultBalle;
	
	private  ArrayList<Image> imgBrique = new ArrayList<Image>(0);
	
	private Font myFont;
	
	public Affichage (int width,int height,Modele modele) {
		
		super();
		
		this.modele = modele;
		
		this.setSize(width,height);
		System.out.println(this.getWidth()+" X "+this.getHeight());
		
		  //create the font to use. Specify the size!
	    try {
			myFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Hacked CRT.ttf")).deriveFont(14f);
		} catch (FontFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
		// on charge les image 
		try {
			imgBackground = ImageIO.read(new File(modele.levelBackground));
			
			imgDefaultRaquette = ImageIO.read(new File("src/Obj/bar3.png"));
			
			imgDefaultBalle = ImageIO.read(new File("src/Obj/BALLE2.png"));
			
			for(int i = 0 ; i< 5;i++) {
				
				imgBrique.add( ImageIO.read(new File("src/Obj/br"+i+".png")) );
				
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		//dessine le fond 
		g2d.drawImage(imgBackground, 0,0,this.getWidth(),this.getHeight(), null);
	
		
		//de la transparance sur les coter de la fenaitre pour un effet !
		Color transparentbalck0 = new Color(59, 59, 112, 128);
		
		Color transparentblack1 = new Color(159, 59, 240,255);
		
		//dégradé1 de couleur
		GradientPaint gp1 = new GradientPaint(this.getWidth()-100, 0, transparentbalck0, this.getWidth()-100, this.getHeight(), transparentblack1, true);                
	    
		g2d.setPaint(gp1);
		
	    g2d.fillRect((this.getWidth()-200), 0, 200, this.getHeight());
	    
	    //dégrade2 de couleur
		
	    GradientPaint gp2 = new GradientPaint(100, 0, transparentbalck0, 100, this.getHeight(), transparentblack1, true);                
	    
	    g2d.setPaint(gp2);
		
	    g2d.fillRect(0, 0, 200, this.getHeight());
	    
	    //couleur mis a jour pour la transparence 
	    g2d.setColor(new Color(6,6,6,128));
	    // dessine les briques en fonction du type
		for(Brique br : modele.briques) {
			g2d.fillRect(br.getX()+10,br.getY()+10,60,30);
			
			switch (br.type ){
			case 0:
			
				g2d.drawImage(imgBrique.get(0), br.getX(),br.getY(),60,30, this);
				break;
			
			case 1:
				g2d.drawImage(imgBrique.get(1), br.getX(),br.getY(),60,30, this);
				
				break;
				
			case 2 :
				
				g2d.drawImage(imgBrique.get(2), br.getX(),br.getY(),60,30, this);
			
				break;
				
			case 3 :
				
				g2d.drawImage(imgBrique.get(3), br.getX(),br.getY(),60,30, this);
				
				break;
				
			case 4 :
				
				g2d.drawImage(imgBrique.get(4), br.getX(),br.getY(),60,30, this);
				
				break;
				
				default:
					g2d.drawImage(imgBrique.get(0), br.getX(),br.getY(),60,30, this);
				
			};
		
		}
		

		//dessine l'ombre de la balle
		g2d.fillOval(modele.balle.getX()+5,modele.balle.getY()+5,modele.balle.getWidth(),modele.balle.getHeight());
		//dessine la balle
		g2d.drawImage(imgDefaultBalle, modele.balle.getX(),modele.balle.getY(),modele.balle.getWidth(),modele.balle.getHeight(), this);
		
		//dessine l'ombre de la raquette 
		g2d.fillRoundRect(modele.raquette.getX()+10, modele.raquette.getY()+10, modele.raquette.getWidth(), modele.raquette.getHeight(),10,10);
		// dessine la raquette
		g2d.drawImage(imgDefaultRaquette, modele.raquette.getX(),modele.raquette.getY(),modele.raquette.getWidth(),modele.raquette.getHeight(), null);
		
		
		/*
		 * reste à faire dessiner les bonnus
		 * et les effet de victoir d'explosion ect...
		 */
		
		//affiche le score dans un carrer
		
		g2d.setFont(myFont);
		g2d.setColor(Color.magenta);
		g2d.drawString("SCORE : ",this.getWidth()-170 , 40);
		g2d.drawString("00000000",this.getWidth()-170 , 80);
		g2d.drawString(modele.levelRank,this.getWidth()-170 , 120);
		g2d.drawString(modele.levelName,this.getWidth()-170 , 160);
		g2d.drawString("Bonus : ",this.getWidth()-1170 , 40);
		
		// dessine le cadre à droite 
		g2d.setStroke(new BasicStroke(5));
		g2d.setColor(Color.BLACK);
		g2d.drawRect(this.getWidth()-180, 10, 170, 200);//gauche	
		g2d.drawRect(this.getWidth()-1180, 10, 170, 200);//droite
		
		
		// dessine les poto gauche et droite 
		g2d.setColor(Color.MAGENTA);
		g2d.fillRect(this.getWidth()-995, 0, 5, this.getHeight());//gauche
		g2d.fillRect(this.getWidth()-200, 0, 5, this.getHeight());//droite
		//getWidth
		
	}
	
	// doit afficher une annimation quand un objet est détruit 
	public void animatedExplosion() {
		
	}
	
}

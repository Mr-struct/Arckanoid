
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelMenu extends JPanel {
	
	private Modele modele;
	protected  int move;
	private Image imgBackground;
	int x;
	int y;
	private Font firstWord;
	private Font secondWord;
	private Font therdWord;
	protected Button newGame = new Button("New Game");
	protected Button settings = new Button("Settings");
	protected Button exit = new Button("Exit");
	
	public PanelMenu(int width,int height,Modele modele){
		
		this.modele = modele;
		this.setSize(width,height);
		this.move = 0;
		
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
		 
		//init les image 
		try {
			this.imgBackground = ImageIO.read(new File("./Obj/Menu.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
	}
	
	public void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		//dessine le fond 
		g2d.drawImage(imgBackground, 0, 0,this.getWidth(), this.getHeight(),null);
		
		//mise en place des buttons
		x = this.getWidth()/2-100;
		y = this.getHeight()/2+50;
		this.newGame.setBounds(x,y,200, 50);
		this.settings.setBounds(x,y+60,200, 50);
		this.exit.setBounds(x,y+120,200, 50);
		this.add(newGame);
		this.add(settings);
		this.add(exit);
		
		//mise en place des titres
		g2d.setFont(firstWord);
		g2d.setColor(Color.magenta);
		g2d.drawString("New",this.getWidth()/2-50, 100);
		
		g2d.setFont(secondWord);
		GradientPaint gp2 = new GradientPaint(this.getWidth()/2-380,250,new Color(8, 30, 102),this.getWidth()/2-380,150,new Color(190, 8, 214));
	    g2d.setPaint(gp2);
		g2d.drawString("ARCANOID",this.getWidth()/2-380, 250);
		
		g2d.setFont(therdWord);
		GradientPaint gp3 = new GradientPaint(this.getWidth()/2-140,340,new Color(35, 35, 35),this.getWidth()/2-140 ,280,new Color(244, 245, 247));
	    g2d.setPaint(gp3);
		g2d.drawString("UPSUD",this.getWidth()/2-140, 340);
	}
}

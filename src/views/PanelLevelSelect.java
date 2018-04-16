package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import model.Modele;

@SuppressWarnings("serial")
public class PanelLevelSelect extends JPanel{
	
	private View view;
	
	private Modele modele; 
	
	private float scaleX;
	
	private float scaleY;
	
	private Font firstWord;
	
	private Font secondWord;
	
	public ImageIcon imgBackground1;
	
	public Modele previewModele; // le modele de demo
	
	public LevelSelectView levelSelect; // le panneau de la demo

	/**
	 * Contructeur 
	 * 
	 * @param view la vue generale
	 * 
	 */
	public PanelLevelSelect(View view){

		this.view = view;
		
		this.modele = view.modele;
		
		this.setSize(view.getWidth(),view.getHeight());
		
		this.scaleX = 0.0f;
		
		this.scaleY = 0.0f;
		
		//chage l'image de fond
		imgBackground1 = new ImageIcon("./levels/level"+view.levelIndex+".txt.gif");
		
		//charge les fonts
		try {
		
			firstWord  = Font.createFont(Font.TRUETYPE_FONT, new File("./Fonts/Streamster.ttf")).deriveFont(50f);
		
			secondWord = Font.createFont(Font.TRUETYPE_FONT, new File("./Fonts/SFAlienEncountersSolid.ttf")).deriveFont(20f);
		
		} catch (FontFormatException e1) {
			
			e1.printStackTrace();
		
		} catch (IOException e1) {
		
			e1.printStackTrace();
		
		}
		//init le modele demo
		previewModele = new Modele("./levels/level"+view.levelIndex+".txt");
		
		//init le panneau de demo
		levelSelect = new LevelSelectView(previewModele,view);
		
		// on rajoute les boutons
		this.add(view.nextSelectButton);
		
		this.add(view.previousSelectButton);
		
		this.add(view.backButtonMenuFromSelect);
		
		// on rajoute le panneau de demo
		this.add(levelSelect);
			
	}

	public void paintComponent(Graphics g) {
				
		Graphics2D g2d = (Graphics2D)g;
		
		scaleX = (float)(this.getWidth())/(float)(modele.gameWidth);
		
		scaleY =  (float)(this.getHeight())/(float)(modele.gameHeight);
		
		AffineTransform tx = new AffineTransform();
		
		tx.scale(scaleX, scaleY);
		
		g2d.setTransform(tx); // on applique le scale 
		
		// on dessine le fond 
		g2d.drawImage(imgBackground1.getImage(),0,0,modele.gameWidth,modele.gameHeight, null);              
		
		g2d.setColor(new Color(0, 0, 0,128));
		
		g2d.fillRect(250, 0, modele.gameWidth-500, modele.gameHeight);

	
		g2d.setColor(Color.PINK);
		g2d.setFont(firstWord);
		
		//dessine le nom du niveau 
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString(previewModele.level.levelName, view.modele.gameWidth/2 -2*firstWord.getSize() - 20, 100);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		
		g2d.setColor(Color.CYAN);
		g2d.setFont(secondWord);
		//dessine le score max 
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString("HIGH SCORE " + previewModele.level.levelHightScore, 800 -  2*secondWord.getSize(), view.modele.gameHeight-100);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		
		//dessine le niveau de difficulte 
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString("DIFFICULTY : " + previewModele.level.levelDifficulty, 400, view.modele.gameHeight-100);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		
		//dessine les boutons
		view.backButtonMenuFromSelect.drawButton(80,50,64,64,scaleX,scaleY,g2d);
		view.nextSelectButton.drawButton(436 + modele.gameWidth-800,view.modele.gameHeight/2,64,64,scaleX,scaleY,g2d);
		view.previousSelectButton.drawButton(300,view.modele.gameHeight/2,64,64,scaleX,scaleY,g2d);
		
		//dessine le panneau de demo
		levelSelect.drawPreview(400, 150,modele.gameWidth-800, modele.gameHeight-300, scaleX, scaleY, g2d);
	}
}

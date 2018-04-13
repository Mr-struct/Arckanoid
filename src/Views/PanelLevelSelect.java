package Views;

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

import Model.Modele;

@SuppressWarnings("serial")

public class PanelLevelSelect extends JPanel{
	
	private Vue vue;
	
	private Modele modele; 
	
	private float scaleX;
	
	private float scaleY;
	
	private Font firstWord;
	
	private Font secondWord;
	
	public ImageIcon imgBackground1;
	
	public Modele previewModele;
	
	public LevelSelectView levelSelect;

	public PanelLevelSelect(Vue vue){

		this.vue = vue;
		
		this.modele = vue.modele;
		
		this.setSize(vue.getWidth(),vue.getHeight());
		
		this.scaleX = 0.0f;
		
		this.scaleY = 0.0f;
		
		imgBackground1 = new ImageIcon("./levels/level"+vue.levelIndex+".txt.gif");
		
		try {
		
			firstWord  = Font.createFont(Font.TRUETYPE_FONT, new File("./Fonts/Streamster.ttf")).deriveFont(50f);
		
			secondWord = Font.createFont(Font.TRUETYPE_FONT, new File("./Fonts/SFAlienEncountersSolid.ttf")).deriveFont(20f);
		
		} catch (FontFormatException e1) {
			
			e1.printStackTrace();
		
		} catch (IOException e1) {
		
			e1.printStackTrace();
		
		}
		
		previewModele = new Modele("./levels/level"+vue.levelIndex+".txt");
		
		levelSelect = new LevelSelectView(previewModele,vue);
		
		this.add(vue.nextSelectButton);
		
		this.add(vue.previousSelectButton);
		
		this.add(vue.backButtonMenuFromSelect);
		
		this.add(levelSelect);
			
	}

	public void paintComponent(Graphics g) {
		
		this.vue.playButton.setBounds(-this.getWidth() , -this.getHeight(), 0, 0);
		
		Graphics2D g2d = (Graphics2D)g;
		
		scaleX = (float)(this.getWidth())/(float)(modele.gameWidth);
		
		scaleY =  (float)(this.getHeight())/(float)(modele.gameHeight);
		
		AffineTransform tx = new AffineTransform();
		
		tx.scale((float) this.getWidth()/ (float)modele.gameWidth, (float) this.getHeight()/ (float)modele.gameHeight);
		
		g2d.setTransform(tx);
		
		g2d.drawImage(imgBackground1.getImage(),0,0,modele.gameWidth,modele.gameHeight, null);              
		
		g2d.setColor(new Color(0, 0, 0,128));
		
		g2d.fillRect(250, 0, modele.gameWidth-500, modele.gameHeight);

		scaleX = (float)(this.getWidth())/(float)(modele.gameWidth);
		
		scaleY =  (float)(this.getHeight())/(float)(modele.gameHeight);
		
		g2d.setColor(Color.PINK);
		
		g2d.setFont(firstWord);
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.drawString(previewModele.level.levelName, vue.modele.gameWidth/2  - 2*firstWord.getSize(), 100);
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		
		g2d.setColor(Color.CYAN);
	
		g2d.setFont(secondWord);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.drawString("HEIGHT SCORE " + previewModele.level.levelHightScore, 770 -  2*secondWord.getSize(), vue.modele.gameHeight-100);
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.drawString("DIFFICULTY : " + previewModele.level.levelDifficulty, 400, vue.modele.gameHeight-100);
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		
		vue.backButtonMenuFromSelect.drawButton(80,50,64,64,scaleX,scaleY,g2d);
		
		vue.nextSelectButton.drawButton(436 + modele.gameWidth-800,vue.modele.gameHeight/2,64,64,scaleX,scaleY,g2d);
		
		vue.previousSelectButton.drawButton(300,vue.modele.gameHeight/2,64,64,scaleX,scaleY,g2d);
		
		levelSelect.updateBounds(400, 150,modele.gameWidth-800, modele.gameHeight-300, scaleX, scaleY, g2d);
	}


}

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.basic.BasicListUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

@SuppressWarnings("serial")
public class PanelLevelSelect extends JPanel{
	private Vue vue;
	private Modele modele; 
	//protected Image imgBackground;
	private Font firstWord;
	private Font secondWord;
	protected ImageIcon imgBackground1;
	public PanelLevelSelect(Vue vue){

		this.vue = vue;
		
		this.modele = vue.modele;
		
		this.setSize(vue.getWidth(),vue.getHeight());

			//imgBackground = ImageIO.read(new File("./levels/level0.txt.png"));
			imgBackground1 = new ImageIcon("./levels/level0.txt.gif");
		
		
		try {
			firstWord  = Font.createFont(Font.TRUETYPE_FONT, new File("./Fonts/Streamster.ttf")).deriveFont(50f);
			secondWord = Font.createFont(Font.TRUETYPE_FONT, new File("./Fonts/SFAlienEncountersSolid.ttf")).deriveFont(30f);
		} catch (FontFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		vue.list.setUI(new myListUI());
		
		vue.pane = new JScrollPane(vue.list);
		
		vue.pane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		
		vue.list.setForeground(Color.BLACK);
		
		vue.list.setFont(secondWord);
		
		vue.pane.setBorder(null);
		
		this.add(vue.pane,BorderLayout.CENTER);
		
		this.add(vue.backButtonTasks);
		
		this.add(vue.playButton);
		
		this.vue.playButton.setBounds(-this.getWidth() , -this.getHeight(), 0, 0);

		this.vue.playButton.setBounds(-this.getWidth() , -this.getHeight(), 0, 0);

		
	}

	public void paintComponent(Graphics g) {
		
		this.vue.playButton.setBounds(-this.getWidth() , -this.getHeight(), 0, 0);
		
		Graphics2D g2d = (Graphics2D)g;
		/*
		AffineTransform tx = new AffineTransform();
		AffineTransform oldTransform = new AffineTransform();
		oldTransform = g2d.getTransform();
		tx.scale((float) this.getWidth()/ (float)this.getWidth(), (float) this.getHeight()/ (float)this.getHeight());
		 * je desactive le scale car sous windows il y'a des saut d'image que je n'arive pas à resoudre 
		 * du coup on calcule tout en focntion de la taille de l'ecran
		 * g2d.setTransform(tx);
		 */
		
		//dessine le fond en fonction du niveau selectione
		//g2d.drawImage(imgBackground, 0,0,this.getWidth(),this.getWidth(), null);
		g2d.drawImage(imgBackground1.getImage(),0,0,this.getWidth(),this.getWidth(), null);
		
		// dessin d'un fond  noir avec transparance pour un certain effet
		g2d.setColor(new Color(3,3,3,200));
		g2d.fillRect(0,0,this.getWidth(),this.getWidth());
		
		

		//de la transparance sur les coter de la fenaitre pour un effet !
		Color transparentColor1 = new Color(59, 59, 112, 128);
		Color transparentColor2 = new Color(159, 59, 240,255);

		//dégradé1 de couleur
		GradientPaint gp0 = new GradientPaint(this.getWidth()-100, 0, transparentColor1,this.getWidth()-100, this.getHeight(), transparentColor2, true);                
		g2d.setPaint(gp0);
		//g2d.fillRect(100, 0, this.getWidth()-200, this.getWidth());
		
		// dessine le nom du niveau selectioner
		g2d.setFont(firstWord);
		
		GradientPaint gp1 = new GradientPaint(this.getWidth()/2-200,60,new Color(8, 30, 102),this.getWidth()/2-100,0,new Color(190, 8, 214));
	    g2d.setPaint(gp1);
	   
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString(modele.level.levelName,this.getWidth()/2-100, 60);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		
		g2d.setFont(secondWord);
		g2d.setColor(Color.CYAN);
		 
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString("Hight Score " + modele.level.levelHightScore,200, this.getHeight()-250);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString("Difficulty : "+modele.level.levelDifficulty,this.getWidth() - 500 - secondWord.getSize(), this.getHeight()-250);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		
		//dessine les buttons
		int x = this.getWidth() / 2 ;
		
		int y = (this.getHeight() / 2 + this.getWidth() / 7);
		
		//condition si Width < height pour bien placer les boutons en fonction de taille de la fenaitre
		if(this.getWidth()<this.getHeight()) {
			y = (this.getHeight() / 2 + this.getWidth() / 3);
		}
		
		//condition pour activé le boutoun play 
		if (modele.level.canBePlayed){
			this.vue.playButton.setBounds(x+this.getWidth()/4 ,  y, 64, 64);
		//	this.vue.playButton.setEnabled(true);
		
		}
		
		this.vue.backButtonTasks.setBounds(x-this.getWidth()/4 - 64, y, 64, 64);
		
		// affichage de la liste 
		vue.pane.setBounds(200,100,this.getWidth()-400,330);

	}

	public class myListUI extends BasicListUI {
		public myListUI () {
			super();
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		protected void paintCell(Graphics g, int row, Rectangle rowBounds,
				ListCellRenderer cellRenderer, ListModel dataModel,
				ListSelectionModel selModel, int leadIndex) {

			Graphics2D g2d = (Graphics2D)g;
			Object value = dataModel.getElementAt(row);
			boolean cellHasFocus = list.hasFocus() && (row == leadIndex);
			boolean isSelected = selModel.isSelectedIndex(row);

			Component rendererComponent = cellRenderer
					.getListCellRendererComponent(list, value, row, isSelected,
							cellHasFocus);

			int cx = rowBounds.x;
			int cy = rowBounds.y;
			int cw = rowBounds.width;
			int ch = rowBounds.height;

			GradientPaint gp = new GradientPaint(cx+cw/2, cy,new Color(0, 216, 255) ,cx + cw/2, cy + ch,new Color(72, 1, 142));
			g2d.setPaint(gp);
			g2d.fillRect(cx, cy, cw, ch);

			rendererPane.paintComponent(g2d, rendererComponent, list, cx, cy, cw, ch,true);
		}
	}


	public class MyScrollBarUI extends BasicScrollBarUI {
		private Image imageThumb, imageTrack;
		public MyScrollBarUI() {
			super();
			try {
				imageThumb = ImageIO.read(new File("./Obj/ScrolBar.png"));

			} catch (IOException e){}
			
		}
		@Override
		protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
			Graphics2D g2d = (Graphics2D) g;
			c.getParent();
			Rectangle r = trackRect;
			//GradientPaint gp = new GradientPaint(r.x, r.y,new Color(137, 2, 255) ,r.x + r.width, r.y + r.height,new Color(255, 2, 175));
			//g2d.setPaint(gp);
			g2d.setColor(new Color(181, 224, 202));
			g2d.fillRect(r.x, r.y, r.width, r.height);
		}

		@Override
		protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
			g.setColor(new Color(32, 28, 48));
			g.translate(thumbBounds.x, thumbBounds.y);
			g.drawRect( 0, 0, thumbBounds.width - 2, thumbBounds.height - 1 );
			AffineTransform transform = AffineTransform.getScaleInstance((double)thumbBounds.width/imageThumb.getWidth(null),(double)thumbBounds.height/imageThumb.getHeight(null));
			
			((Graphics2D)g).drawImage(imageThumb, transform, null);
			g.translate( -thumbBounds.x, -thumbBounds.y );


		}
	}

}

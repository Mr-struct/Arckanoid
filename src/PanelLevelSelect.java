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
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicListUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

@SuppressWarnings("serial")
public class PanelLevelSelect extends JPanel{
	private Modele modele;
	protected Image imgBackground;
	private Font firstWord;
	private Font secondWord;

	public PanelLevelSelect(int width,int height,Modele modele){

		this.modele = modele;
		
		this.setSize(width,height);

		try {
			imgBackground = ImageIO.read(new File("./levels/level0.txt.png"));

		} catch (IOException e) {

			e.printStackTrace();
		}
		
		try {
			firstWord  = Font.createFont(Font.TRUETYPE_FONT, new File("./Fonts/Streamster.ttf")).deriveFont(50f);
			secondWord = Font.createFont(Font.TRUETYPE_FONT, new File("./Fonts/SFAlienEncountersSolid.ttf")).deriveFont(30f);
		} catch (FontFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
					
		
		add(modele.play);
		modele.play.getIgnoreRepaint();
		
		this.add(modele.backBnt2);
		
		modele.list.setUI(new myListUI());
		
		modele.pane = new JScrollPane(modele.list);
		
		modele.pane.getVerticalScrollBar().setUI(new MyScrollBarUI());
		
		modele.list.setForeground(Color.BLACK);
		
		modele.list.setFont(secondWord);

		for (int i = 0; i <20; i++) {
			
			modele.modelOfList.addElement("level"+i);
		}
		modele.pane.setBorder(new LineBorder(Color.BLACK));
		add(modele.pane,BorderLayout.CENTER);
		
	}

	public void paintComponent(Graphics g) {
		
		if (modele.level.canBePlayed){
			add(modele.play);
			modele.play.getIgnoreRepaint();
		 }		
		else {
			remove(modele.play);
			
		}
		
		Graphics2D g2d = (Graphics2D)g;
		//dessine le fond en fonction du niveau selectione sino par defaut image du menu
		g2d.drawImage(imgBackground, 0,0,this.getWidth(),this.getHeight(), null);
		
		// affichage de la liste 
		modele.pane.setBounds(200,100,this.getWidth()-400,330);
		
		// dessin d'un fond  noir avec transparance pour un certain effet
		g2d.setColor(new Color(3,3,3,200));
		g2d.fillRect(0,0,this.getWidth(),this.getHeight());
		
		

		//de la transparance sur les coter de la fenaitre pour un effet !
		Color transparentColor1 = new Color(59, 59, 112, 128);
		Color transparentColor2 = new Color(159, 59, 240,255);

		//dégradé1 de couleur
		GradientPaint gp0 = new GradientPaint(modele.gameWidth-100, 0, transparentColor1, modele.gameWidth-100, modele.gameHeight, transparentColor2, true);                
		g2d.setPaint(gp0);
		g2d.fillRect(100, 0, this.getWidth()-200, this.getHeight());
		
		// dessine le nom du niveau selectioner
		g2d.setFont(firstWord);
		
		GradientPaint gp1 = new GradientPaint(this.getWidth()/2-200,60,new Color(8, 30, 102),this.getWidth()/2-100,0,new Color(190, 8, 214));
	    g2d.setPaint(gp1);
	   
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString(modele.level.levelName,this.getWidth()/2-100, 60);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		
		g2d.setFont(secondWord);
		g2d.setColor(Color.BLACK);
		 
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString("Hight Score " + modele.level.levelHightScore,200, this.getHeight()-250);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString("Difficulty : "+modele.level.levelDifficulty,800, this.getHeight()-250);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		
		modele.play.setBounds(this.getWidth()/2+50 , this.getHeight()-150,200,60);
		
		modele.backBnt2.setBounds(this.getWidth()/2-300 , this.getHeight()-150,200,60);


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

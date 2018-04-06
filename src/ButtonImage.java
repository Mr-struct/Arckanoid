import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonImage extends JButton  implements MouseListener {
	private ImageIcon defaultImg,enteredImg,clickedImg;
	protected SoundPlay sound;
	private ButtonImage thisClass;
	private SoundPlay soundBoutton;
	
	public ButtonImage(ImageIcon defaultImg, ImageIcon enteredImg, ImageIcon clickedImg,SoundPlay sound) {
		
		super(defaultImg);
		
		this.defaultImg = defaultImg;
		
		this.enteredImg = enteredImg;
		
		this.clickedImg = clickedImg;
		
		setBorderPainted(false);
		
		setFocusPainted(false);
		
		setContentAreaFilled(false);
		
		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
		this.setBorderPainted(false);
		this.sound = sound;
		
		this.sound.setPnogrameChange(4);
		
		
		this.addMouseListener(this);
		
		this.thisClass = this;
	}
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		/*
		 * condition pour anuller l'effet du repainte forcer sous windows 
		 * ondessine un carrer pour cacher l'effet non desiree :( 
		 */
		if(System.getProperty("os.name").equals("Windows 10")){
			//de la transparance sur les coter de la fenaitre pour un effet !
			Color transparentColor1 = new Color(255, 0, 203, 128);
			Color transparentColor2 = new Color(0, 242, 255,200);

			//dégradé1 de couleur
			GradientPaint gp1 = new GradientPaint(getWidth()/2, 0, transparentColor1,getWidth()/2, getHeight(), transparentColor2, true);                
			g2d.setPaint(gp1);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);

		}
		super.paintComponent(g2d);
	}
	public void mouseExited(MouseEvent me)
    {
		thisClass.setIcon(defaultImg);
		sound.note_on(67);

    }

    public  void mouseEntered(MouseEvent me)
    {		
    	thisClass.setIcon(enteredImg);
    	sound.note_on(67);
          
           
    }
    
    @Override
    public void mousePressed(MouseEvent me)
    {
    	thisClass.setIcon(clickedImg);
    	sound.note_on(67);
    }
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		thisClass.setIcon(defaultImg);
		sound.note_on(67);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}

}


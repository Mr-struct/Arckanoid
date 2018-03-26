import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonImage extends JButton  implements MouseListener{
	private ImageIcon defaultImg,enteredImg,clickedImg;
	protected SoundPlay sound;
	private ButtonImage thisClass;
	
	public ButtonImage(ImageIcon defaultImg, ImageIcon enteredImg, ImageIcon clickedImg) {
		
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
		this.sound = new SoundPlay();
		
		this.sound.setPnogrameChange(34);
		
		
		this.addMouseListener(this);
		
		this.thisClass = this;
	}
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		super.paintComponent(g2d);
	}
	
	public void mouseExited(MouseEvent me)
    {
		thisClass.setIcon(defaultImg);
		thisClass.repaint();
		sound.note_on(67);

    }

    public  void mouseEntered(MouseEvent me)
    {		
    	thisClass.setIcon(enteredImg);
    	thisClass.repaint();

    		sound.note_on(67);
          
           
    }
    
    @Override
    public void mousePressed(MouseEvent me)
    {
    	thisClass.setIcon(clickedImg);
		thisClass.repaint();
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


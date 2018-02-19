import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;

public class Button extends JButton implements MouseListener{
	private String name;
	private Font buttonFont;
	private Button meClass;
	private float alpha = .3f;
	public Button (String str) {
		this.setName(str);  

		try {
			buttonFont = Font.createFont(Font.TRUETYPE_FONT, new File("./Fonts/Streamster.ttf")).deriveFont(25f);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.name = str;
		this.setFont(buttonFont);
		// This allows us to paint a round background.
		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
		this.setBorderPainted(false);
		this.addMouseListener(this);
	}
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		
		// dessine les button 
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));// active une transparense alpha calculer 
		GradientPaint gp1 = new GradientPaint(this.getWidth()/2,0,new Color(255, 0, 195),this.getWidth()/2,this.getHeight()-10,new Color(8, 30, 102));
		g2d.setPaint(gp1);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillRoundRect(2, 2, this.getWidth()-5, this.getHeight()-5,20,25);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		
		// dessine le text sur les buttons
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.f)); //annule la transparence 
		GradientPaint gp2 = new GradientPaint(this.getWidth()/2,0,new Color(119, 19, 103),this.getWidth()/2,this.getHeight(),new Color(5, 234, 255));
		g2d.setPaint(gp2);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawString(this.name, this.getWidth() / 2 - (this.getWidth()/ 2 /4)-10, (this.getHeight() / 2) +5);
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
	}
	
	public synchronized void playSound(String audioFilePath ,int delay ) {
		
		  new Thread(new Runnable() {
		    public void run() {
		    		
		    		Boolean playCompleted = true;
		    		File audioFile = new File(audioFilePath);
		    	   
		           try {
		               AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
		    
		               AudioFormat format = audioStream.getFormat();
		    
		               DataLine.Info info = new DataLine.Info(Clip.class, format);
		    
		               Clip audioClip = (Clip) AudioSystem.getLine(info);
		            
		               audioClip.open(audioStream);
		              /* ((FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(+10.f);
		               /*
		                * 
		               FloatControl volume = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
		               volume.setValue(1110);
		               */
		               audioClip.start();
		                
		               while (playCompleted) {
		                   // wait for the playback completes
		                   try {
		                       Thread.sleep(delay);
		                       
		                   } catch (InterruptedException ex) {
		                       ex.printStackTrace();
		                   }
		                   audioClip.close();
		               }
		                
		               
		                
		           } catch (UnsupportedAudioFileException ex) {
		               System.out.println("The specified audio file is not supported.");
		               ex.printStackTrace();
		           } catch (LineUnavailableException ex) {
		               System.out.println("Audio line for playing back is unavailable.");
		               ex.printStackTrace();
		           } catch (IOException ex) {
		               System.out.println("Error playing the audio file.");
		               ex.printStackTrace();
		           }
		    }
		   }).start();
		}

		public synchronized void mouseExited(MouseEvent me)
	    {
	          alpha = 0.3f;
	          
	    }

	    public synchronized void mouseEntered(MouseEvent me)
	    {
	    		this.playSound("./Sounds/ButtonSelected.wav",100); // joue le sond pendant 100 miliseconde
	            alpha = 1.f;
	           
	    }
	    
	    @Override
	    public void mousePressed(MouseEvent me)
	    {
	     
	    }
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
}

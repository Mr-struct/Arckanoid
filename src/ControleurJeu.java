
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Timer;


public class ControleurJeu implements MouseMotionListener,MouseListener, KeyListener {
	
	private Modele modele;
	private Vue vue;
	private int currenteVolume;

	// constructeur 
	public ControleurJeu(Modele modele, Vue vue) {
		this.modele = modele;
		this.vue = vue;
		this.currenteVolume = this.vue.soundBT.volume;

	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		modele.deplacerRaquette(e.getX() * modele.gameWidth /vue.getWidth());
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		modele.lancerBalles();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("hello");
		if(e.getKeyChar() == 'p' || e.getKeyChar() == 'P') {
			if(modele.isRunning()) {
				modele.suspendreJeu();
			}
			else if(!modele.isRunning()) {
				for(Balle b : modele.balles) b.timer = new Timer();
				for(Bonus bn : modele.bonus) bn.timer = new Timer();// ne fonctione pas vraiment faut que stephane fasse attendre au lieu de l'arreter
				modele.lancerBalles();
				modele.lancerJeu();
			}
		}else if(e.getKeyChar() == 'm' || e.getKeyChar() == 'M') {
			if(vue.soundBT.volume > 0) {
				vue.soundButton.setEnabled(false);;
				modele.impactSound.volume = 0;
				vue.soundBT.volume = 0;
			}else {
				vue.soundButton.setEnabled(true);;
				modele.impactSound.volume = currenteVolume;
				vue.soundBT.volume = currenteVolume;
			}
			
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
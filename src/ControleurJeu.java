
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class ControleurJeu implements MouseMotionListener,MouseListener {
	
	private Modele modele;
	private PanelGame vueJeu;
	// constructeur 
	public ControleurJeu(Modele modele, PanelGame panelGame) {
		this.modele = modele;
		this.vueJeu = panelGame;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		modele.deplacerRaquette(e.getX() * modele.gameWidth /vueJeu.getWidth());
		
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
}
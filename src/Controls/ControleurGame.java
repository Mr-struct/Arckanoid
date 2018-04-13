package Controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Model.Modele;
import Views.Vue;

public class ControleurGame implements MouseMotionListener,MouseListener, KeyListener {

	private Modele modele;
	private Vue vue;
	private int currenteVolume;

	/*
	 * constructeur 
	 * @param modele le modele physique du jeu 
	 * @param vue la vue générale du jeu
	 */
	public ControleurGame(Modele modele, Vue vue) {
		this.modele = modele;
		this.vue = vue;
		this.currenteVolume = this.vue.soundBT.volume; // récupere le niveau du volume pour pouvoire le diminuer

	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	/*
	 *controle la raquette avec la souris la souris est mise au centre de la raquette
	 */
	public void mouseMoved(MouseEvent e) {

		modele.deplacerRaquette(e.getX() * modele.gameWidth /vue.getWidth());

	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	/*
	 * lance la balle au clique 
	 */
	public void mousePressed(MouseEvent e) {

		modele.lancerBalles();

	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	/*
	 * récuper les évenements au clavier et met en pause le jeu si l'event est p
	 * met en sourdine si l'event est m
	 */
	public void keyTyped(KeyEvent e) {

		if(e.getKeyChar() == 'p' || e.getKeyChar() == 'P') { // si l'event est p ou P

			if(modele.isRunning()) { // on test si le jeu tourne

				modele.suspendreJeu(); // on le met en pause

			}
			else {

				modele.lancerJeu();//sinon on le lance

			}
		}else 
			if(e.getKeyChar() == 'm' || e.getKeyChar() == 'M') { // si l'event est m ou M
				
				if(vue.soundBT.volume > 0) { // si le son est activer
					
					vue.soundButton.setEnabled(false); // on désactive le bouton qui le controle
					
					vue.soundButton.getParent().repaint(); // on repaint le parent
					
					modele.impactSound.volume = 0; // on met le son à 0
					
					vue.soundBT.volume = 0;
					
				}else { // on fais le contraire dans ce bloc
					
					vue.soundButton.setEnabled(true);
					
					vue.soundButton.updateImg(); // on met à jour l'image
					
					vue.soundButton.getParent().repaint();
					
					modele.impactSound.volume = currenteVolume;
					
					vue.soundBT.volume = currenteVolume;
				}

			}
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
}
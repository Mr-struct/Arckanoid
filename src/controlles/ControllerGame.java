package controlles;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import model.Modele;
import views.View;

public class ControllerGame implements MouseMotionListener,MouseListener, KeyListener {

	private Modele modele;
	private View view;
	private int currenteVolume;

	/**
	 * constructeur 
	 * @param modele le modele physique du jeu 
	 * @param view la vue generale du jeu
	 */
	public ControllerGame(Modele modele, View view) {
		this.modele = modele;
		this.view = view;
		this.currenteVolume = this.view.soundBT.volume; // recupere le niveau du volume pour pouvoire le diminuer

	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	/*
	 *controle la raquette avec la souris + le curseur de la souris est mit au centre de la raquette
	 */
	public void mouseMoved(MouseEvent e) {

		modele.deplacerRaquette(e.getX() * modele.gameWidth /view.getWidth());

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
	 * recupere les evenements au clavier et met en pause le jeu si l event est p
	 * met en sourdine si l event est m
	 */
	public void keyTyped(KeyEvent e) {

		if(e.getKeyChar() == 'p' || e.getKeyChar() == 'P') { // si l event est p ou P

			if(modele.isRunning()) { // on test si le jeu tourne

				modele.suspendreJeu(); // on le met en pause

			}
			else {

				modele.lancerJeu();//sinon on le lance

			}
		}else 
			if(e.getKeyChar() == 'm' || e.getKeyChar() == 'M') { // si l event est m ou M

				//on test si le son est bien active et le bouton du son aussi 
				if(view.modele.impactSound.volume  > 0  && view.soundButton.isEnabled()) {

					view.soundButton.setEnabled(false); // on desactive le bouton du sod

					//le son est mis a 0
					view.modele.impactSound.volume = 0; 

					view.soundBT.volume = 0;

				}else {

					//sino on recative le son dans son etat d origine
					view.modele.impactSound.volume = currenteVolume;

					view.soundBT.volume = currenteVolume;

					/*
					 *  on fait quand meme un test sur le bouton 
					 *  pour bien afficher l image du bouton correspondant a l etat du son 
					 */

					if(currenteVolume > 0) {

						view.soundButton.setEnabled(true);
					}
					else {

						view.soundButton.setEnabled(false);

					}
				}
			}
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
}

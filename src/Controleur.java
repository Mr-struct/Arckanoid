import java.awt.event.MouseEvent;
import java.util.TimerTask;


public class Controleur {
	
	private Modele modele;
	private Vue vue;
	
	// constructeur 
	public Controleur(Modele modele, Vue vue) {
		this.modele = modele;
		this.vue = vue;
		
		/* pas besoin de init ici vue que l'init se fait dans le constructeur du modele
		//initialisation des briques
		modele.intitBriques(0, 0);
		
		//initialisation de la première balle
		modele.initBalle();
		*/
		//TODO attendre que l'interface ait fini de s'afficher avant de lancer le jeu
		modele.lancerJeu();
		
	}

	public void moveRaquette(MouseEvent e) {
		
		modele.raquette.setX(e.getX() - modele.raquette.getWidth()/2);
		
	}
	
	public void lancerBalles() {
		
		modele.lancerBalles();
		
	}
}
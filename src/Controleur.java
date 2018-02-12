import java.awt.event.MouseEvent;
import java.util.TimerTask;


public class Controleur {
	
	private Modele modele;
	private java.util.Timer tLogique = new java.util.Timer();
	
	// constructeur 
	public Controleur(Modele modele) {
		this.modele = modele;
		
		/* pas besoin de init ici vue que l'init se fait dans le constructeur du modele
		//initialisation des briques
		modele.intitBriques(0, 0);
		
		//initialisation de la première balle
		modele.initBalle();
		*/
		//TODO attendre que l'interface ait fini de s'afficher avant de lancer le jeu
		lancerJeu();
		
		//TODO test du lancement de balle après 5 secondes, à remplacer par un lancement manuel
		java.util.Timer tLancement = new java.util.Timer();
		
		tLancement.schedule(new TimerTask(){
			
			public void run() {
				lancerBalles();
			}
		}, 5000);
		
	}
	
	//lance le timer pour la logique du jeu
	public void lancerJeu(){
		
		tLogique.schedule(new TimerTask(){
			
			public void run() {
				
				for(Balle b : modele.balles){
					
				synchronized(b){
					
						if(b.getvX() == 0 && b.getvY() == 0){ //les balles attachées à la raquette (celles dont la vélocité est nulle) suivent la raquette
							
							b.setX((modele.raquette.getX()+65) + b.getrX()); //faut les centrer sur la raquette du coup  c'est modele.raquette.getX() + b.getrX() + la moitier du width de la raquette càd 65
							
							b.setY((modele.raquette.getY()-20) + b.getrY()); //meme chose ici - la taille de la balle càd 20
						}else
						{ 
							//les balles ayant une vélocité se déplacent selon celle-ci
							b.setX(b.getX() + b.getvX());
							b.setY(b.getY() + b.getvY());
						}
					}
				}
			}
		}, 0, 10);
	}
	
	//Action permettant de lancer les balles attachées à la raquette (celles dont la vélocité est nulle) en leur donnant une velocité initiale
	public void lancerBalles(){
		
		//faire en sorte que le premier lancement de la balle suit le pointeur de la souris et attend qu'on clic dessus 
		
		for(Balle b : modele.balles){
			
			synchronized(b){
				
				if(b.getvX() == 0 && b.getvY() == 0){
					
					b.setvX(2);
					b.setvY(-2);
					
				}
			}
		}
	}
	
	//suspend le jeu, le jeu peut reprendre en appelant lancerJeu
	public void suspendreJeu(){
		
		tLogique.cancel();
	}

	

	public void moveRaquette(MouseEvent e) {
		
		modele.raquette.setX(e.getX() - modele.raquette.getWidth()/2);
		
	}
	
	
	public Modele getModele() {
		
		return this.modele;
	}
}
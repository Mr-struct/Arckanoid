import java.awt.Color;
import java.util.ArrayList;

public class Modele {
	protected ArrayList<Brique> briques = new ArrayList<Brique>(); 
	protected Raquette raquette = new Raquette(400,680,100,20,Color.CYAN);
	protected Balle balle; //TODO retirer ce chanp et utiliser uniquement l'array "balles", nécessaire pour le multiballe
	protected ArrayList<Balle> balles = new ArrayList<Balle>();
	
	//TODO déplaccer initBriques et initBalle dans le controleur une fois le terrain terminé
	public void intitBriques() {
		for(int i = 0; i < 10; i++) {
			for(int j= 0; j < 3;j++) {
				briques.add(new Brique( (i*50)+150, (j*100)+30 ,40 ,20 ) );
			}
		}
	}
	
	public void initBalle(){
		balle = new Balle ( raquette.getX()+50 ,raquette.getY()-10,0,0);
		balles.add(balle);
	}
	 
}

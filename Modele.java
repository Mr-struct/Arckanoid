import java.util.ArrayList;
import java.util.Random;

public class Modele {
	protected ArrayList<Brique> briques = new ArrayList<Brique>(0); 
	protected Raquette raquette;
	protected Balle balle;
	private Random rand = new Random();
	public Modele() {
		
		raquette = new Raquette(500,680,150,30);
		balle = new Balle (raquette.getX()+55 ,raquette.getY()-20,0,0);
		intitBriques(9,5);
	}
	
	public void intitBriques(int w ,int h) {
		
		for(int i = 0; i < w; i++) {
			for(int j= 0; j < h;j++) {
				// initialise les brique avec juste un random faut changer ça avec une proba 
				int type = rand.nextInt(5)+1;
				//briques.add(new Brique(x            ,  y           , width,height,type, valuer = (type*10) pour le score) 
				briques.add(new Brique( (i*80)+250, (j*(h*10))+50 ,80   ,30    ,type, type) );
				}
		}
	}
	 
}

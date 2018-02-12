
public class Main  {

	public static void main(String[] args) {
		Modele modele = new Modele("src/levels/level0.txt");
		Controleur controller = new Controleur(modele);
		Vue vue = new Vue (1200,800, controller);
	}

}

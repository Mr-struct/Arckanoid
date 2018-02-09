
public class Main  {

	public static void main(String[] args) {
		Modele modele = new Modele();
		Controleur controleur = new Controleur(modele);
		Vue vue = new Vue (800,800, modele);
	}

}

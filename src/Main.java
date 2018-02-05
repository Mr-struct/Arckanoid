
public class Main  {

	public static void main(String[] args) {
		Modele modele = new Modele();
		Controller controller = new Controller(modele);
		Vue vue = new Vue (800,800, modele);
	}

}

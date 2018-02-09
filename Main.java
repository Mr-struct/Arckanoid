
public class Main  {

	public static void main(String[] args) {
		Modele modele = new Modele();
		Controller controller = new Controller(modele);
		Vue vue = new Vue (1200,800, controller);
	}

}

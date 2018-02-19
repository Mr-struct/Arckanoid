public class Main  {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		Modele modele = new Modele("src/levels/level1.txt");
		Vue vue = new Vue(modele);
	}

}

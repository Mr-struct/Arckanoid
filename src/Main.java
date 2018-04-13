import Model.Modele;
import Views.Vue;

public class Main  {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		Modele modele = new Modele();
		
		Vue vue = new Vue(modele);
	}
}

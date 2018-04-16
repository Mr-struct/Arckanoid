import model.Modele;
import views.View;

public class Main  {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		Modele modele = new Modele();
		
		View view = new View(modele);
	}
}

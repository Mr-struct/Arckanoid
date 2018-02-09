import java.awt.event.MouseEvent;

public class Controller {
	private Modele modele;
	
	// constructeur 
	public Controller(Modele modele) {
		this.modele =  modele;
	}

	public Modele getModele() {
		return modele;
	}

	public void setModele(Modele modele) {
		this.modele = modele;
	}

	public void moveRaquette(MouseEvent e) {
		
		modele.raquette.setX(e.getX() - modele.raquette.getWidth()/2);
	}
	

}

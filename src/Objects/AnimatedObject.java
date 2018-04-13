package Objects;

import java.util.Timer;

public class AnimatedObject {
	// les attirbuts
	private int x , y, width, height, age ; //les coordonnées + la largeur + la hauteur + age utilisé pour differant effets (durée de l'explosion ...)
	
	private String popUp; // utiliser pour afficher le gain du score lors d'une collision brique balle
	
	public  Timer timer = new Timer(); // timer spécifique à l'objet 
	
	/*
	 * contructeur pour le score à afficher
	 * 
	 * @param x le coordonnée x initial de l'objet
	 * @pram y la coordonnée y initial de l'objet
	 * @param popUp le message à afficher 
	 */
	public AnimatedObject(int x, int y,String popUp) {
		this.x = x;
		this.y = y;
		this.age = 0;
		this.popUp = popUp;
	}
	
	/*
	 * contructeur pour differanttes animation 
	 * @param x le coordonnée x initial de l'objet
	 * @pram y la coordonnée y initial de l'objet
	 * @param width la largeur de l'objet
	 * @param height la hauteur de l'objet
	 */
	public AnimatedObject(int x, int y,int width, int height) {
		this.x = x;
		this.y = y;
		this.age = 0;
		this.width = width;
		this.height = height;
		
	}

	/*
	 * @return x la coordonnée x de l'objet
	 */
	public int getX() {
		return x;
	}

	/*
	 * @param x met à jour la coordonnée x de l'objet
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/*
	 * @return y la coordonnée y de l'objet 
	 */
	public int getY() {
		return y;
	}
	
	/*
	 * @param y met à jour la coordonnée y de l'objet
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/*
	 * @return popUp retourne le message 
	 */
	public String getPopUp() {
		return popUp;
	}
	
	/*
	 * @param popUp met à jour le message à afficher
	 */
	public void setPopUp(String popUp) {
		this.popUp = popUp;
	}
	
	/*
	 * @return age retourn la variable age
	 */
	public int getAge() {
		return age;
	}
	
	/*
	 * @param age met à jour la variable age
	 */
	public void setAge(int age) {
		this.age = age;
	}
	
	/*
	 * @return width retourne la largeur de l'objet
	 */
	public int getWidth() {
		return width;
	}
	
	/*
	 * @param width met à jour la largeur de l'objet 
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/*
	 * @return height retourne la hauteur de l'objet
	 */
	public int getHeight() {
		return height;
	}
	
	/*
	 * @param height met à jour la hauteur de l'objet 
	 */
	public void setHeight(int height) {
		this.height = height;
	}

}

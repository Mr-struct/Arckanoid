package objects;

import java.util.Timer;

public class AnimatedObject {
	// les attirbuts
	private int x , y, width, height, age ; //les coordonnees + la largeur + la hauteur + age utilise pour differant effets (duree de l'explosion ...)
	
	private String popUp; // utilise pour afficher le gain du score lors d'une collision brique balle
	
	public  Timer timer = new Timer(); // timer specifique a l'objet 
	
	/*
	 * contructeur pour le score a afficher
	 * 
	 * @param x le coordonnee x initial de l'objet
	 * 
	 * @pram y la coordonnee y initial de l'objet
	 * 
	 * @param popUp le message a afficher 
	 */
	public AnimatedObject(int x, int y,String popUp) {
		this.x = x;
		this.y = y;
		this.age = 0;
		this.popUp = popUp;
	}
	
	/*
	 * contructeur pour differanttes animation 
	 * 
	 * @param x le coordonnee x initial de l'objet
	 * 
	 * @pram y la coordonnee y initial de l'objet
	 * 
	 * @param width la largeur de l'objet
	 * 
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
	 * @return x la coordonnee x de l'objet
	 */
	public int getX() {
		return x;
	}

	/*
	 * @param x met a jour la coordonnee x de l'objet
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/*
	 * @return y la coordonnee y de l'objet 
	 */
	public int getY() {
		return y;
	}
	
	/*
	 * @param y met a jour la coordonnee y de l'objet
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
	 * @param popUp met a jour le message a afficher
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
	 * @param age met a jour la variable age
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
	 * @param width met a jour la largeur de l'objet 
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
	 * @param height met a jour la hauteur de l'objet 
	 */
	public void setHeight(int height) {
		this.height = height;
	}

}

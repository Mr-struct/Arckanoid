package Objects;

import java.util.Timer;

public class Ball {
	
	// les attributs
	private int width, height; // la largeur + la hauteur de la balle
	
	private double x, y; // les coordonnée x et  y  de la balle 
	
	private double vX, vY; // les coordonées du vecteur vitesse x et y 
	
	private int paddleX, paddleY; //coordonnées  x et y relative a la raquette quand la balle est accrochée
	
	protected boolean hadCollision;  // boolean pour détécter si la balle à eu une collision
	
	public Timer timer = new Timer(); // timer de balle pour controller son mouvment
	
	/*
	 * constructeur init la balle avec ces coordonnées x, y ca vitesse sa taille 
	 * @param x coordonée x de la balle
	 * @param y coordonée y de la balle
	 * @param vX coordonée x du vecteur vitesse de la balle
	 * @paramv vY coordonée y du vecteur vitesse de la balle
	 * @param pX  coordonée x de la balle sur à la raquette 
	 * @param pY  coordonée y de la balle sur à la raquette 
	 * @param width largeur de la balle
	 * @param height hauteur de la balle
	 */
	
	public Ball(double x, double y , double vX, double vY, int pX, int pY, int width, int height) {
		
		this.x = x;
		
		this.y = y;
		
		this.vX = vX;
		
		this.vY = vY;
		
		this.paddleX = pX;
		
		this.paddleY = pY; 
		
		this.width = width;
		
		this.height = height;
		
		this.hadCollision = false;
	}
	
	/*
	 * @return x coordonée x de la balle
	 */
	public double getdX() {
		
		return x;
		
	}
	
	/*
	 * @return x coordonée x de la balle en entier pour l'afficher
	 */
	public int getIntX() {
		
		return (int) Math.round(x);
		
	}
	
	/*
	 * @param x met à jour la coordonnée x de la balle
	 */
	public void setX(double x) {
		
		this.x = x;
		
	}
	
	/*
	 * @return y la coordonnée y de la balle
	 */
	public double getdY() {
		
		return y;
		
	}
	
	/*
	 * @return y la coordonnée y de la balle en entier pour l'afficher
	 */
	public int getIntY() {
		
		return (int) Math.round(y);
		
	}

	/*
	 * @param y met à jour la coordonnée y de la balle
	 */
	public void setY(double y) {
		
		this.y = y;
		
	}
	/*
	 * @return width la largeur de la balle
	 */
	public int getWidth() {
		
		return width;
		
	}
	
	/*
	 * @param width  met à jour la largeur de la balle
	 */
	public void setWidth(int width) {
		
		this.width = width;
		
	}
	
	/*
	 * @return height la hauteur de la balle
	 */
	public int getHeight() {
		return height;
	}

	/*
	 * @param height met à la hauteur de la balle
	 */
	public void setHeight(int height) {
		
		this.height = height;
		
	}
	
	/*
	 * @return vX la coordonnée vX du vecteur vitesse
	 */
	public double getvX() {
		
		return vX;
		
	}

	/*
	 * @param vX met à jour la coordonée vX du vecteur vitesse de la balle
	 */
	public void setvX(double vX) {
		
		this.vX = vX;
		
	}

	/*
	 * @return vY la coordonée vY du vecteur vitesse de la balle
	 */
	public double getvY() {
		
		return vY;
		
	}
	
	/*
	 * @param vY met à jour la coordonnée y  du vecteur vitesse 
	 */
	public void setvY(double vY) {
		
		this.vY = vY;
		
	}
	
	/*
	 * @return paddleX la coordonée x de la balle sur la raquette
	 */
	public int getPaddleX() {
		
		return paddleX;
		
	}
	
	/*
	 * @param rX met à jour la coordonée x de la balle sur la raquette
	 */
	public void setPaddleX(int rX) {
		
		this.paddleX = rX;
		
	}
	/*
	 * @return paddleY la coordonée Y de la balle sur la raquette
	 */
	public int getPaddleY() {
		
		return paddleY;
		
	}
	
	/*
	 *@param  met à jour  paddleY la coordonée Y de la balle sur la raquette
	 */
	public void setPaddleY(int rY) {
		
		this.paddleY = rY;
		
	}
}

package objects;

import java.util.Timer;

public class Ball {
	
	// les attributs
	private int width, height; // la largeur + la hauteur de la balle
	
	private double x, y; // les coordonnee x et  y  de la balle 
	
	private double vX, vY; // les coordonees du vecteur vitesse x et y 
	
	private int paddleX, paddleY; //coordonnees  x et y relative a la raquette quand la balle est accrochee
	
	protected boolean hadCollision;  // boolean pour detecter si la balle a eu une collision
	
	public Timer timer = new Timer(); // timer de balle pour controller son mouvment
	
	/**
	 * constructeur init la balle avec ces coordonnees x, y ca vitesse sa taille
	 *  
	 * @param x coordonee x de la balle
	 * 
	 * @param y coordonee y de la balle
	 * 
	 * @param vX coordonee x du vecteur vitesse de la balle
	 * 
	 * @paramv vY coordonee y du vecteur vitesse de la balle
	 * 
	 * @param pX  coordonee x de la balle sur a la raquette 
	 * 
	 * @param pY  coordonee y de la balle sur a la raquette 
	 * 
	 * @param width largeur de la balle
	 * 
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
	
	/**
	 * @return x coordonee x de la balle
	 */
	public double getdX() {
		
		return x;
		
	}
	
	/**
	 * @return x coordonee x de la balle en entier pour l'afficher
	 */
	public int getIntX() {
		
		return (int) Math.round(x);
		
	}
	
	/**
	 * @param x met a jour la coordonnee x de la balle
	 */
	public void setX(double x) {
		
		this.x = x;
		
	}
	
	/**
	 * @return y la coordonnee y de la balle
	 */
	public double getdY() {
		
		return y;
		
	}
	
	/**
	 * @return y la coordonnee y de la balle en entier pour l'afficher
	 */
	public int getIntY() {
		
		return (int) Math.round(y);
		
	}

	/**
	 * @param y met a jour la coordonnee y de la balle
	 */
	public void setY(double y) {
		
		this.y = y;
		
	}
	/**
	 * @return width la largeur de la balle
	 */
	public int getWidth() {
		
		return width;
		
	}
	
	/**
	 * @param width  met a jour la largeur de la balle
	 */
	public void setWidth(int width) {
		
		this.width = width;
		
	}
	
	/**
	 * @return height la hauteur de la balle
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height met a la hauteur de la balle
	 */
	public void setHeight(int height) {
		
		this.height = height;
		
	}
	
	/**
	 * @return vX la coordonnee vX du vecteur vitesse
	 */
	public double getvX() {
		
		return vX;
		
	}

	/**
	 * @param vX met a jour la coordonee vX du vecteur vitesse de la balle
	 */
	public void setvX(double vX) {
		
		this.vX = vX;
		
	}

	/**
	 * @return vY la coordonee vY du vecteur vitesse de la balle
	 */
	public double getvY() {
		
		return vY;
		
	}
	
	/**
	 * @param vY met a jour la coordonnee y  du vecteur vitesse 
	 */
	public void setvY(double vY) {
		
		this.vY = vY;
		
	}
	
	/**
	 * @return paddleX la coordonee x de la balle sur la raquette
	 */
	public int getPaddleX() {
		
		return paddleX;
		
	}
	
	/**
	 * @param rX met a jour la coordonee x de la balle sur la raquette
	 */
	public void setPaddleX(int rX) {
		
		this.paddleX = rX;
		
	}
	
	/**
	 * @return paddleY la coordonee Y de la balle sur la raquette
	 */
	public int getPaddleY() {
		
		return paddleY;
		
	}
	
	/**
	 *@param  met a jour  paddleY la coordonee Y de la balle sur la raquette
	 */
	public void setPaddleY(int rY) {
		
		this.paddleY = rY;
		
	}
}

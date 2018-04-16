package objects;


public class Raquette {

	private int  x, y, width, height, initWidth;  //les coordonnees x, y, la largeur, la hauteur , la largeur inital de la raquette 
	
	/**
	 *  constructeur init une raquette
	 * @param x la coordonnee x de la raquette
	 * @param y la coordonnee  y de la raquette
	 * @param width la largeur de la raquette
	 * @param height la hauteur de la raquette
	 */
	public Raquette( int x , int y ,int width , int height){
		
		this.x = x;
		
		this.y = y;
		
		this.initWidth = width;
		
		this.width = width;
		
		this.height = height;
		
	}

	/**
	 * @return x la coordonnee x de la raquette
	 */
	public int getX() {
		
		return x;
	}

	/**
	 * @param x met a jour la coordonnee x de la raquette
	 */
	public void setX(int x) {
		
		this.x = x;
	}
	
	/**
	 * @return width la largeur initial de la raquette
	 */
	public int getInitWidth() {
		
		return initWidth;
	}
	
	/**
	 * @return width la largeur de la raquette
	 */
	public int getWidth() {
		
		return width;
	}
	
	/**
	 * @param width met a jour la largeur de la raquette
	 */
	public void setWidth(int width) {
		
		this.width = width;
		
	}
	
	/**
	 * @return height la hauteur de la raquette
	 */
	public int getHeight() {
		
		return height;
	}
	
	/**
	 * @param width met a jour la hauteur de la raquette
	 */
	public void setHeight(int height) {
		
		this.height = height;
		
	}
	
	/**
	 * @return y la coordonnee y de la raquette
	 */
	public int getY() {
		
		return y;
		
	}
	
	/**
	 * @param y met a jour la coordonne y 
	 */
	public void setY(int y) {
		
		this.y = y;
		
	}
}

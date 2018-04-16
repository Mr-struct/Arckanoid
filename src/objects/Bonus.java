package objects;

import java.util.Timer;

public class Bonus {
	
	//attributs
	
	private  int x, y, width, height ,type; // les coordonnee x, y, la largeur, la hauteur, le type du bonus
	
	public Timer timer = new Timer(); // timer du bonus pour son animation
	
	/**
	 * contructeur init le bonus
	 * @param x la coordonnee x du bonus
	 * @param y la coordonnee  y du bonus
	 * @param width la largeur du bonus
	 * @param height la hauteur du bonus
	 * @param type le type du bonus
	 */
	public Bonus(int x, int y, int width, int height, int type) {
		
		this.x = x;
		
		this.y = y;
		
		this.width = width;
		
		this.height = height;
		
		this.type = type;
	}
	
	/**
	 * @return x la coordonnee x du bonus
	 */
	public int getX() {
		
		return x;
	}
	
	/**
	 * @param x met a jour la coordonnee x du bonus
	 */
	public void setX(int x) {
		
		this.x = x;
	}
	
	/**
	 * @return y la coordonnee y du bonus
	 */
	public int getY() {
		
		return y;
	}
	
	/**
	 * @param y met a jour la coordonnee y du bonus
	 */
	public void setY(int y) {
		
		this.y = y;
	}

	/**
	 * @return width la coordonnee width du bonus
	 */
	public int getWidth() {
		
		return width;
	}
	
	/**
	 * @return height la coordonnee height du bonus
	 */
	public int getHeight() {
		
		return height;
	}
	
	/**
	 * @return  type la coordonnee type du bonus
	 */
	public int getType() {
		
		return type;
	}

 }

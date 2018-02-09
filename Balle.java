
public class Balle {
	
	//attributs 
	
	private int  x, y, vX, vY; 
		
	//constructeur 
	public Balle(int x, int y , int vX, int vY) {
		this.x = x ;
		this.y = y;
		this.vX = vX;
		this.vY = vY;
	}
	
	
	
	// geter and seter
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}



	public int getvX() {
		return vX;
	}



	public void setvX(int vX) {
		this.vX = vX;
	}



	public int getvY() {
		return vY;
	}



	public void setvY(int vY) {
		this.vY = vY;
	}

	
}

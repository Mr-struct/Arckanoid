public class Balle {
	
	//attributs 
	
	private int  x, y, vX, vY ,width, height;
	private int rX, rY; //coordonnées relative à la raquette quand la balle est accrochée
	//constructeur
	public Balle(int x, int y , int vX, int vY, int rX, int rY, int w, int h) {
		
		this.x = x ;
		this.y = y;
		this.vX = vX;
		this.vY = vY;
		this.rX = rX;
		this.rY = rY; 
		this.width = w;
		this.height = h;
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

	public int getrX() {
		return rX;
	}

	public void setrX(int rX) {
		this.rX = rX;
	}

	public int getrY() {
		return rY;
	}

	public void setrY(int rY) {
		this.rY = rY;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
}
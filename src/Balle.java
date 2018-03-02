import java.util.Timer;

public class Balle {
	
	//attributs 
	
	private int dx, dy, width, height;
	private double x, y, vX, vY;
	private int rX, rY; //coordonnées relative à la raquette quand la balle est accrochée
	
	public Timer timer = new Timer();
	
	//constructeur
	public Balle(int x, int y , int vX, int vY, int rX, int rY, int w, int h) {
		
		this.x = x;
		this.y = y;
		this.vX = vX;
		this.vY = vY;
		this.rX = rX;
		this.rY = rY; 
		this.width = w;
		this.height = h;
	}
	
	// geter and seter
	public double getX() {
		return x;
	}

	public int getiX() {
		return (int) Math.round(x);
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public int getiY() {
		return (int) Math.round(y);
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getvX() {
		return vX;
	}

	public void setvX(double vX) {
		this.vX = vX;
	}

	public double getvY() {
		return vY;
	}

	public void setvY(double vY) {
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

	
}
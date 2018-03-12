import java.util.Timer;

public class Balle {
	
	//attributs 
	
	private int dx, dy, width, height;
	private double x, y, vX, vY;
	private int raquetteX, raquetteY; //coordonnées relative à la raquette quand la balle est accrochée
	protected Boolean hadCollision;
	public Timer timer = new Timer();
	
	//constructeur
	public Balle(int x, int y , int vX, int vY, int rX, int rY, int w, int h) {
		
		this.x = x;
		this.y = y;
		this.vX = vX;
		this.vY = vY;
		this.raquetteX = rX;
		this.raquetteY = rY; 
		this.width = w;
		this.height = h;
		this.hadCollision = false;
	}
	
	// geter and seter
	public double getDoubleX() {
		return x;
	}

	public int getX() {
		return (int) Math.round(x);
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getDoubleY() {
		return y;
	}

	public int getY() {
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

	public int getRaquetteX() {
		return raquetteX;
	}

	public void setRaquetteX(int rX) {
		this.raquetteX = rX;
	}

	public int getRaquetteY() {
		return raquetteY;
	}

	public void setRaquetteY(int rY) {
		this.raquetteY = rY;
	}

	
}

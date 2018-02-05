import java.awt.Color;

public class Raquette {

	private int  x, y, width, height;
	private Color color; 
	public Raquette( int x , int y ,int width , int height , Color color){
		this.x = x;
		this.setY(y);
		this.width = width;
		this.height = height;
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
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

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}

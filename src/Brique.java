
public class Brique {
	private int x , y, width,height;
	
	public Brique(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.setWidth(width);
		this.height = height;
	}

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
	
	public int getHeight(){
		return this.height;
	}
	public int  getWidth() {
		
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	

}

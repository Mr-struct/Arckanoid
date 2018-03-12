import java.util.Timer;

public class CollisionEffects {

	private int x , y, width, height;
	
	private String popUp;
	
	protected  Timer timer = new Timer();
	
	public CollisionEffects(int x, int y,String popUp) {
		this.x = x;
		this.y = y;
		this.width = 0;
		this.height = 0;
		this.popUp = popUp;
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

	public String getPopUp() {
		return popUp;
	}

	public void setPopUp(String popUp) {
		this.popUp = popUp;
	}

}

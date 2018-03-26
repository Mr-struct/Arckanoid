import java.util.Timer;

public class AnimatedObject {

	private int x , y, width, height, age ;
	protected int r,g,b;
	private String popUp;
	
	protected  Timer timer = new Timer();
	
	public AnimatedObject(int x, int y,String popUp) {
		this.x = x;
		this.y = y;
		this.age = 0;
		this.popUp = popUp;
	}
	public AnimatedObject(int x, int y,int width, int height) {
		this.x = x;
		this.y = y;
		this.age = 0;
		this.width = width;
		this.height = height;
		this.r = (int)Math.random()*255;
		
		this.g = (int)Math.random()*255;
		
		this.b = (int)Math.random()*255;
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

	public String getPopUp() {
		return popUp;
	}

	public void setPopUp(String popUp) {
		this.popUp = popUp;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
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

}

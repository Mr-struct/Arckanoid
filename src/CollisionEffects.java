import java.util.Timer;

public class CollisionEffects {

	private int x , y, age;
	
	private String popUp;
	
	protected  Timer timer = new Timer();
	
	public CollisionEffects(int x, int y,String popUp) {
		this.x = x;
		this.y = y;
		this.setAge(0);
		this.popUp = popUp;
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

}

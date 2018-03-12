
public class Bonus {
  //attributs
	private  int x, y, width, height ,type;
	
	public Bonus(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.type =  (int )(Math.random() * 3 );
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

	public int getWidth() {
		return width;
	}

	public int getType() {
		return type;
	}

	public int getHeight() {
		return height;
	}

 }

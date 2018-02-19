public class Brique {

	private int x , y, width,height;;
	protected int type;
	protected int value;
	public Brique(int x, int y, int width, int height, int type,int value) {
		
		this.x = x;
		this.y = y;
		this.setWidth(width);
		this.height = height;
		this.type = type;
		this.value = value*10;
		
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

	public enum BriqueType {

		//Objets directement construits

		Normal(1, "RIEN"),

		Rare (2, "Bonus1"),

		Epique (3, "Bonus2"),

		Legendaire (4, "Bonus3"),

		Doree(5, "Bonus4");


		private int win = 0;

		private String bns = "";



		//Constructeur

		BriqueType(int win, String bns){

			this.win = win;

			this.bns = bns;

		}



		public void getBonus(){

			System.out.println("Editeur : " + bns);

		}



		public int getScore(){

			return win;

		}
	}

}

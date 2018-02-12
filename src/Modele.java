import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;


public class Modele {

	protected ArrayList<Brique> briques = new ArrayList<Brique>(); 

	protected Raquette raquette;

	protected Balle balle; //TODO retirer ce chanp et utiliser uniquement l'array "balles", nécessaire pour le multiballe

	protected ArrayList<Balle> balles = new ArrayList<Balle>();

	protected  String fileLevel;

	protected String levelName;

	protected String levelRank;
	
	protected String levelBackground;
	
	private java.util.Timer tLogique = new java.util.Timer();
	
	protected int maxX = 1000;
	
	protected int minX = 200;
	protected Vue  vue;

	public Modele(String fileLevel) {

		raquette = new Raquette(500,680,150,30);

		this.fileLevel = fileLevel;
		this.levelBackground = this.fileLevel+".png";
		System.out.println(levelBackground);

		try {
			intitlevel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.vue = new Vue(1200, 800, this);


	}

	public void initBalle(){

		// la position de la balle est set dans le controleur dans le lancerJeu()
		balle = new Balle (0,0,0,0,0,0,20,20); 
		balles.add(balle);

	}
	public void intitlevel() throws IOException{

		initBalle();
		String test = readFile(fileLevel);
		String [] out = null;

		// on recupere les lignes du fichier dans out 
		for(int i = 0; i < test.length();i++) {
			out = test.split("\n");
		}
		System.out.println("taille de out est de :" +out.length);

		this.levelRank = out[out.length-4];
		this.levelName = out[out.length-3];
		int colums = Integer.parseInt(out[out.length-2]);
		int lines =  Integer.parseInt(out[out.length-1]);;


		char [] [] char2D = new char [colums][lines];
		char2D = arrayStringTo2DArrayChar(char2D, out, colums, lines);

		for(int i = 0; i < colums; i++) {

			for(int j = 0; j < lines ;j++) {

				// initialise les brique avec juste un random faut changer ça avec une proba 
		

				// matrice inversser
				switch(char2D[j][i]) {

				case 'B': // pour les briques bleu
					
					briques.add(new Brique( (i*(colums-1)*10)+250, (j*lines*10)+50 ,80   ,30    ,0, 1) );
					break;
					
				case 'V': // pour les briques vertes 

					briques.add(new Brique( (i*(colums-1)*10)+250, (j*lines*10)+50 ,80   ,30    ,1, 2) );
					break;

				case 'S': //pour les briques en metal

					briques.add(new Brique( (i*(colums-1)*10)+250, (j*lines*10)+50 ,80   ,30    ,2, 3) );
					break;

				case 'R': // pour les briques rouge

					briques.add(new Brique( (i*(colums-1)*10)+250, (j*lines*10)+50 ,80   ,30    ,3, 4) );
					break;
					
				case 'G' : // pour les brique or

					briques.add(new Brique( (i*(colums-1)*10)+250, (j*lines*10)+50 ,80   ,30    ,4, 5) );

					break;


				};
			}
		}
	}

	public String readFile(String filename) throws IOException
	{
		String content = null;

		File file = new File(filename); //for ex foo.txt

		FileReader reader = null;

		reader = new FileReader(file);

		char[] chars = new char[(int) file.length()];

		reader.read(chars);

		content = new String(chars);

		reader.close();

		if(reader !=null){

			reader.close();
		}

		return content;
	}

	public char [][] arrayStringTo2DArrayChar(char[][]char2D,String [] str,int cols,int lines){
		//Assumes all lines are the same length
		char2D = new char[cols][lines];

		for(int i = 0; i < str.length-2; i++)  {
			String line = str[i];
			char2D[i] = line.toCharArray();
		}
		return char2D;

	}
	
	

	
	//lance le timer pour la logique du jeu
	public void lancerJeu(){
		
		tLogique.schedule(new TimerTask(){
			
			public void run() {
				
				for(Balle b : balles){
					
				synchronized(b){
					
						if(b.getvX() == 0 && b.getvY() == 0){ //les balles attachées à la raquette (celles dont la vélocité est nulle) suivent la raquette
							
							b.setX((raquette.getX()+65) + b.getrX()); //faut les centrer sur la raquette du coup  c'est modele.raquette.getX() + b.getrX() + la moitier du width de la raquette càd 65
							
							b.setY((raquette.getY()-20) + b.getrY()); //meme chose ici - la taille de la balle càd 20
						}else
						{ 
							//les balles ayant une vélocité se déplacent selon celle-ci
							b.setX(b.getX() + b.getvX());
							b.setY(b.getY() + b.getvY());
							//collision avec les murs
							if((b.getvX() < 0 && b.getX() <= minX) || (b.getvX() > 0 && b.getX() + b.getWidth() >= maxX )){
								b.setvX(-b.getvX());
							}
							//collision avec le plafond
							if(b.getvY() < 0 && b.getY() <= 0){
								b.setvY(-b.getvY());
							}
							//collision avec la raquette
							if(b.getvY() > 0 && b.getY() + b.getHeight() >= raquette.getY() && b.getY() + b.getHeight() <= raquette.getY() + raquette.getHeight() && b.getX() > raquette.getX() && b.getX() < raquette.getX() + raquette.getWidth()){
								b.setvY(-b.getvY());
							}
						}
					}
				}
			}
		}, 0, 10);
	}
	
	//Action permettant de lancer les balles attachées à la raquette (celles dont la vélocité est nulle) en leur donnant une velocité initiale
	public void lancerBalles(){
		
		//faire en sorte que le premier lancement de la balle suit le pointeur de la souris et attend qu'on clic dessus 
		
		for(Balle b : balles){
			
			synchronized(b){
				
				if(b.getvX() == 0 && b.getvY() == 0){
					
					b.setvX(2);
					b.setvY(-2);
					
				}
			}
		}
	}
	
	//suspend le jeu, le jeu peut reprendre en appelant lancerJeu
	public void suspendreJeu(){
		
		tLogique.cancel();
	}


}

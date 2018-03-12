
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;
import javax.swing.JSlider;

public class Modele {

	protected ArrayList<Brique> briques = new ArrayList<Brique>(); 
	
	protected ArrayList<Balle> balles = new ArrayList<Balle>();
	
	protected ArrayList<Bonus> bonus = new ArrayList<Bonus>();
	
	protected ArrayList <CollisionEffects> effects = new ArrayList<CollisionEffects>();
	
	protected Raquette raquette;
	
	protected  String fileLevel;

	protected String levelName;

	protected String levelRank;

	protected String levelBackground;
	
	protected Button newGame ;
	
	protected Button settings ;
	
	protected JSlider sliderFxSound;
	
	protected JSlider sliderMainSound;
	
	protected JSlider sliderLevel;
	
	protected Button backButton;
	
	protected Button exit ;
	
	protected int intScore = 0;
	
	protected int levelDifficulty = 7;
	
	protected String stringScore = "00";
	
	private boolean running;
	
	private Object runningLock = new Object();

	private java.util.Timer tLogique = new java.util.Timer();
	
	protected int gameWidth = 1366;
	
	protected int gameHeight = 768;

	protected int minX = 200;

	protected int maxX = gameWidth-200;

	protected Vue  vueJeu;
	
	protected SoundPlay impactSound;
	
	private int musicImpacte = 60;

	public Modele(String fileLevel) {
		/*
		 * init la raquette
		 */
		this.raquette = new Raquette(500,680,150,30);
		
		/*
		 * init le son
		 */
		this.impactSound = new SoundPlay();
		this.impactSound.setPnogrameChange(39);
		
		/*
		 * init les buttons
		 */
		newGame = new Button("New Game");
		
		settings = new Button("Settings");
		
		exit = new Button("Exit");
		
		/*
		 * init le button de retour
		 */
		
		backButton = new Button("Back");
		
		/*
		 * init le slider du son principale 
		 */
		sliderMainSound = new JSlider(JSlider.HORIZONTAL, 0, 100, 10);
		
		/*
		 * init le slider des bruitage
		 */
		sliderFxSound = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
		/*
		 * init le slider du niveux de dificulte
		 */
		sliderLevel = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);

		/*
		 * init le niveau 
		 * rem : ceci va changer ! 
		 */
		this.fileLevel = fileLevel;
		this.levelBackground = this.fileLevel+".png";
		
		try {
			intitlevel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void initBalle(){

		// la position de la balle est set dans le controleur dans le lancerJeu()
		Balle balle = new Balle (0, 0, 0, 0, (raquette.getWidth()/2)-5, -20, 20, 20); 
		Balle balle2 = new Balle (0, 0, 0, 0, (raquette.getWidth()/2)-40, -20, 20, 20); 
		Balle balle3 = new Balle (0, 0, 0, 0, (raquette.getWidth()/2)+40, -20, 20, 20); 
		balles.add(balle);
		balles.add(balle2);
		balles.add(balle3);

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
		System.out.println("taille de la fenaitre est de  :" +gameWidth +"X" +gameHeight);

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

					briques.add(new Brique( (i*(colums-1)*10)+240, (j*lines*7)+50 ,80   ,30    ,0, 1,1,61) );
					break;

				case 'V': // pour les briques vertes 

					briques.add(new Brique( (i*(colums-1)*10)+240, (j*lines*7)+50 ,80   ,30    ,1, 2,1,63) );
					break;

				case 'S': //pour les briques en metal

					briques.add(new Brique( (i*(colums-1)*10)+240, (j*lines*7)+50 ,80   ,30    ,2, 3,2,65) );
					break;

				case 'R': // pour les briques rouge

					briques.add(new Brique( (i*(colums-1)*10)+240, (j*lines*7)+50 ,80   ,30    ,3, 4,1,68) );
					break;

				case 'G' : // pour les brique or

					briques.add(new Brique( (i*(colums-1)*10)+240, (j*lines*7)+50 ,80   ,30    ,4, 5,1,73) );
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




	//appelée quand balle entre en collision avec brique, calcule les effets de la collision sur la balle et la brique
		private boolean collisionBrique(Balle balle, Brique brique){
			
			boolean diagonale = true;
			//dessus et dessous
			if(balle.getDoubleX() >= brique.getX() && balle.getDoubleX() + balle.getWidth() <= brique.getX() + brique.getWidth()){
				diagonale = false;
				//dessus
				if(balle.getvY() > 0 && balle.getDoubleY() + balle.getHeight() < brique.getY() + brique.getHeight())
					balle.setvY(-balle.getvY());
				//dessous
				if(balle.getvY() < 0 && balle.getDoubleY() > brique.getY())
					balle.setvY(-balle.getvY());
			}
			//gauche et droite
			if(balle.getDoubleY() >= brique.getY() && balle.getDoubleY() + balle.getHeight() <= brique.getY() + brique.getHeight()){
				diagonale = false;
				//gauche
				if(balle.getvX() > 0 && balle.getDoubleY() + balle.getWidth() < brique.getX() + brique.getWidth())
					balle.setvX(-balle.getvX());
				//droite
				if(balle.getvX() < 0 && balle.getDoubleX() > brique.getX())
					balle.setvX(-balle.getvX());
			}
			//diagonales
			if(diagonale){
				
				if((balle.getvX() > 0 && balle.getDoubleX() < brique.getX()) || (balle.getvX() < 0 && balle.getDoubleX() + balle.getWidth() > brique.getX() + brique.getWidth()))
					balle.setvX(-balle.getvX());
				
				if((balle.getvY() > 0 && balle.getDoubleY() < brique.getY()) || (balle.getvY() < 0 && balle.getDoubleY() + balle.getHeight() > brique.getY() + brique.getHeight()))
					balle.setvY(-balle.getvY());
	}
		synchronized (briques){
			if(brique.getNumberOfColision() == 0) {
				

				intScore = intScore + brique.getValue();
				//rajout d'un bonus attetion ici metre une proba !!!!!
				synchronized(bonus) {bonus.add(new Bonus(brique.getX()+10,brique.getY(),50,50));}
				
				synchronized(effects) {effects.add(new CollisionEffects(brique.getX()+30,brique.getY()));}
				
				briques.remove(brique);
				
				
			}else if(brique.getNumberOfColision() == 1){
				
				Brique temps = brique;
				briques.remove(brique);
				temps = new Brique(temps.getX(),temps.getY(),temps.getWidth(),temps.getHeight(),5,2,1,65);
				briques.add(temps);
			}
		};
		
		return true;
	}
	
		//met à jour la position de la balle b et teste ses collisions, appelé periodiquement
		private void physiqueBalle(Balle b){
			synchronized(b){
				if(b.getvX() == 0 && b.getvY() == 0){ //les balles attachÃƒÂ©es Ãƒ  la raquette (celles dont la vÃƒÂ©locitÃƒÂ© est nulle) suivent la raquette
					b.setX((raquette.getX()) + b.getRaquetteX());
					b.setY((raquette.getY()) + b.getRaquetteY());
				}else{ 
					//les balles ayant une vÃƒÂ©locitÃƒÂ© se dÃƒÂ©placent selon celle-ci
					b.setX(b.getDoubleX() + b.getvX());
					b.setY(b.getDoubleY() + b.getvY());
					//balle perdue (sortie de l'écran par le bas)
					if(b.getDoubleY() > gameHeight){
						synchronized(balles){
							balles.remove(b);
							if(balles.isEmpty()) perdu();
						}
						b.timer.cancel();
					}
					//collision avec les murs
					if((b.getvX() < 0 && b.getDoubleX() <= minX) || (b.getvX() > 0 && b.getDoubleX() + b.getWidth() >= maxX )){
						impactSound.note_on(musicImpacte+5);
						b.setvX(-b.getvX());
					}
					//collision avec le plafond
					if(b.getvY() < 0 && b.getDoubleY() <= 0){
						impactSound.note_on(musicImpacte+3);
						b.setvY(-b.getvY());
					}
					//collision avec la raquette
					if(b.getvY() > 0 && b.getDoubleY() + b.getHeight() >= raquette.getY() && b.getDoubleY() + b.getHeight() <= raquette.getY() + raquette.getHeight() && b.getDoubleX() + b.getWidth() > raquette.getX() && b.getDoubleX() < raquette.getX() + raquette.getWidth()){

						impactSound.note_on(musicImpacte-3);
						b.setvX(((b.getDoubleX() + b.getWidth() - raquette.getX() - (raquette.getWidth() / 2)) / (raquette.getWidth() / 2)) * b.getvY());
						b.setvY(-b.getvY());
					}
					//collision avec les briques
					Iterator<Brique> i = briques.iterator();
					boolean c = false;
					while(i.hasNext() && !c){
						Brique brique = i.next();
						if(b.getDoubleX() + b.getWidth() > brique.getX() && b.getDoubleX() < brique.getX() + brique.getWidth() && b.getDoubleY() + b.getHeight() > brique.getY() && b.getDoubleY() < brique.getY() + brique.getHeight()){
							
							musicImpacte = (brique.note);
							
							impactSound.note_on(musicImpacte);	
							
							brique.setNumberOfColision(brique.getNumberOfColision() - 1);
							
							c = collisionBrique(b, brique);
						}
					}
				}
			}
	}	
		
		//active la logique du jeu
		public void lancerJeu(){
			synchronized(runningLock){
				if(!running){
					running = true;
					for(Balle b : balles){
						b.timer.schedule(new TimerTask(){
							public void run() {
								physiqueBalle(b);
							}
						}, 0, levelDifficulty);
					}
				}
			}
	}
	//renvoie true si la partie est en cours et false si elle est suspendue
		public boolean isRunning(){
			return running;
		}
		
		//appelé lorsque le jeu est gagné
		public void gagne(){
			System.out.println("Gagne!");
			suspendreJeu();
		}
		
		//appelé lorsque le jeu est perdu 
		public void perdu(){
			System.out.println("Perdu!");
			suspendreJeu();
	}
		
	//deplace la raquette vers la position x
	public void deplacerRaquette(int x){
		int m = raquette.getWidth()/2;
		if(x - m <= minX){
			raquette.setX(minX);
		}else if(x + m >= maxX){
			raquette.setX(maxX - 2 * m);
		}else{
			raquette.setX(x - m);
		}
	}

	//Action permettant de lancer les balles attachÃ©es Ã  la raquette (celles dont la vÃ©locitÃ© est nulle) en leur donnant une velocitÃ© initiale
		public void lancerBalles(){

			//faire en sorte que le premier lancement de la balle suit le pointeur de la souris et attend qu'on clic dessus 
			
			if(running){
				for(Balle b : balles){
		
					synchronized(b){
		
						if(b.getvX() == 0 && b.getvY() == 0){
		
							b.setvY(-4);
							b.setvX(((b.getDoubleX() + b.getWidth() - raquette.getX() - (raquette.getWidth() / 2)) / (raquette.getWidth() / 2)) * -b.getvY());
		
						}
					}
				}
			}
	}
	public void bonusEffect(Bonus bn) {
		
		if(bn.getType() == 0) {// on rajoute 2 balle
			Balle balleRf = balles.get(0);
			if(balles.size()<=2) {
				balles.add(new Balle(balleRf.getX(),balleRf.getY(), 0, 0, balleRf.getRaquetteX(), balleRf.getRaquetteY(),balleRf.getWidth(),balleRf.getHeight()));
				balles.add(new Balle(balleRf.getX()-20,balleRf.getY(), 0, 0, balleRf.getRaquetteX(), balleRf.getRaquetteY(),balleRf.getWidth(),balleRf.getHeight()));

			}
		}
		if(bn.getType() == 1) {
			if(raquette.getWidth()<=150)
			raquette.setWidth(raquette.getWidth()+50);
		}
		if(bn.getType() == 2) {
			if(raquette.getWidth()>150)
			raquette.setWidth(raquette.getWidth()-50);
		}
	}
	//suspend le jeu, le jeu peut reprendre en appelant lancerJeu
	public void suspendreJeu(){

		tLogique.cancel();
	}

}

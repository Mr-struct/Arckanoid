
import java.io.*;
import java.util.*;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

public class Modele {

	protected ArrayList<Brique> briques = new ArrayList<Brique>(); 
	
	protected ArrayList<Balle> balles = new ArrayList<Balle>();
	
	protected ArrayList<Bonus> bonus = new ArrayList<Bonus>();
	
	protected ArrayList <AnimatedObject> effects = new ArrayList<AnimatedObject>();
	
	protected ArrayList <AnimatedObject> explosions = new ArrayList<AnimatedObject>();
	
	protected ArrayList <Level> levels = new ArrayList<Level>();
	
	protected  AnimatedObject endScreen;
	
	protected Raquette raquette;
	
	protected int intScore = 0;
	
	protected int levelDifficulty = 7;

	protected int currentDifficulty;
	
	protected String stringScore = "00";
	
	private boolean running;
	
	private Object runningLock = new Object();
	
	private Random random = new Random();

	protected int gameWidth = 1366;
	
	protected int gameHeight = 768;

	protected int minX = 200;

	protected int maxX = gameWidth-200;

	protected Vue  vueJeu;
	
	protected SoundPlay impactSound;
	
	private int musicImpacte = 60;
	
	protected Level level;
	
	protected int curBonus;
	
	protected boolean win;
	
	protected boolean lose;

	//initialise le niveau selon les param�tres du fichier fileLevel
	public Modele() {
		/*
		 * init le son
		 */
		this.impactSound = new SoundPlay();
		this.impactSound.setPnogrameChange(39);
		
		
		
		/*
		 * init le niveau 
		 */
		try {
			intitlevel("./levels/level0.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//initialise la premi�re balle. sa position sera calcul�e automatiquement en fonction de la raquette
	public void initBalle(){

		// la position de la balle est set dans le controleur dans le lancerJeu()
		Balle balle = new Balle (0, 0, 0, 0, (raquette.getWidth()/2), -20, 20, 20);
		balles.add(balle);
	}
	
	//initialise le terrain
	public void intitlevel(String filLevel) throws IOException{
this.win = false;
		
		this.lose = false;
		briques = new ArrayList<Brique>(); 
		
		balles = new ArrayList<Balle>();
		
		bonus = new ArrayList<Bonus>();
		
		effects = new ArrayList<AnimatedObject>();
		
		stringScore = "00";
		
		intScore = 0;
		
		level = new Level(filLevel);
		/*
		 * init la raquette
		 */
		this.raquette = new Raquette(500,680,150,30);
		/*
		 * init la balle
		 */
		initBalle();
		
		char [] [] char2D = new char [level.briquesCols][level.briquesRows];
		
		char2D = level.arrayStringTo2DArrayChar(level.fileLevelParssed, level.briquesCols, level.briquesRows);
		System.out.println("Cols : "+ level.briquesCols);
		System.out.println("ROW : "+ level.briquesRows);
		for(int i = 0; i < level.briquesCols; i++) {
		
			for(int j = 0; j < level.briquesRows ;j++) {
			
				// matrice inversser
				switch(char2D[j][i]) {

				case 'B': // pour les briques bleu

					briques.add(new Brique( (i*(level.briquesCols+1)*10)+240, (j*level.briquesRows*7)+50 ,80   ,30    ,0, 1,1,61) );
					break;

				case 'V': // pour les briques vertes 

					briques.add(new Brique( (i*(level.briquesCols+1)*10)+240, (j*level.briquesRows*7)+50 ,80   ,30    ,1, 2,1,63) );
					break;

				case 'S': //pour les briques en metal

					briques.add(new Brique( (i*(level.briquesCols+1)*10)+240, (j*level.briquesRows*7)+50 ,80   ,30    ,2, 3,2,65) );
					break;

				case 'R': // pour les briques rouge

					briques.add(new Brique( (i*(level.briquesCols+1)*10)+240, (j*level.briquesRows*7)+50 ,80   ,30    ,3, 4,1,68) );
					break;

				case 'G' : // pour les brique or

					briques.add(new Brique( (i*(level.briquesCols+1)*10)+240, (j*level.briquesRows*7)+50 ,80   ,30    ,4, 5,1,73) );
					break;
				};
			}
		}
	}
	
	public void useBonus(Bonus b) {
		synchronized(bonus){
			b.timer.cancel();
			lancerBalles();
			raquette.setWidth(raquette.getInitWidth());
			
			//Multiballes
			if(b.getType() == 0) {
				synchronized(balles){
					if(balles.size() == 1){
						Balle bi = balles.get(0);
						Balle b1 = new Balle(bi.getdX(), bi.getdY(), bi.getvX() + 1, bi.getvY(), 0, 0, bi.getWidth(), bi.getHeight());
						Balle b2 = new Balle(bi.getdX(), bi.getdY(), bi.getvX() - 1, bi.getvY(), 0, 0, bi.getWidth(), bi.getHeight());
						balles.add(b1);
						balles.add(b2);
	
						b1.timer.schedule(new TimerTask(){
							public void run() {
								physiqueBalle(b1);
							}
						}, 0, 10);
	
						b2.timer.schedule(new TimerTask(){
							public void run() {
								physiqueBalle(b2);
							}
						}, 0, 10);
					}
				}
			}
			
			//raquette agrandie
			if(b.getType() == 1) {
				raquette.setWidth(raquette.getInitWidth() + 50);
			}

			//raquette r�tr�cie
			if(b.getType() == 2) {
				raquette.setWidth(raquette.getInitWidth() - 50);
			}
			
			if(b.getType() == 3) {
			}
			
			curBonus = b.getType();
			bonus.remove(b);
		}
	}
	
	//appel�e quand balle entre en collision avec brique, calcule les effets de la collision sur la balle et la brique
	private boolean collisionBrique(Balle balle, Brique brique){
		boolean diagonale = true;
		//dessus et dessous
		if(balle.getdX() >= brique.getX() && balle.getdX() + balle.getWidth() <= brique.getX() + brique.getWidth()){
			diagonale = false;
			//dessus
			if(balle.getvY() > 0 && balle.getdY() + balle.getHeight() < brique.getY() + brique.getHeight())
				balle.setvY(-balle.getvY());
			//dessous
			if(balle.getvY() < 0 && balle.getdY() > brique.getY())
				balle.setvY(-balle.getvY());
		}
		//gauche et droite
		if(balle.getdY() >= brique.getY() && balle.getdY() + balle.getHeight() <= brique.getY() + brique.getHeight()){
			diagonale = false;
			//gauche
			if(balle.getvX() > 0 && balle.getdY() + balle.getWidth() < brique.getX() + brique.getWidth())
				balle.setvX(-balle.getvX());
			//droite
			if(balle.getvX() < 0 && balle.getdX() > brique.getX())
				balle.setvX(-balle.getvX());
		}
		//diagonales
		if(diagonale){
			
			if((balle.getvX() > 0 && balle.getdX() < brique.getX()) || (balle.getvX() < 0 && balle.getdX() + balle.getWidth() > brique.getX() + brique.getWidth()))
				balle.setvX(-balle.getvX());
			
			if((balle.getvY() > 0 && balle.getdY() < brique.getY()) || (balle.getvY() < 0 && balle.getdY() + balle.getHeight() > brique.getY() + brique.getHeight()))
				balle.setvY(-balle.getvY());
		}
		
		musicImpacte = (brique.note);
		impactSound.note_on(musicImpacte);
		if(brique.getNumberOfColision() > 1){
			brique.setNumberOfColision(brique.getNumberOfColision() - 1);
			if(brique.getType() == 2) brique.setType(5);
		}else if(briques.contains(brique)){
			intScore = intScore + brique.getValue();
			stringScore = String.valueOf(intScore);
			
			//affiche les points gagn�s
			AnimatedObject effect = new AnimatedObject(brique.getX()+30,brique.getY(), "+" + brique.getValue());
			synchronized(effects) {effects.add(effect);}
			effect.timer.schedule(new TimerTask(){
				public void run() {
					effect.setY(effect.getY()-1);
					effect.setAge(effect.getAge() + 2);
					if(effect.getAge() >= 100){
						synchronized(effects){effects.remove(effect);}
						this.cancel();
					}
				}
			}, 0, 20);
			
			//ajouter un effet d'explosion
			AnimatedObject explosion = new AnimatedObject(brique.getX()+30,brique.getY()+30,10,10);
			synchronized(explosions) {explosions.add(explosion);}
			explosion.timer.schedule(new TimerTask(){
				public void run() {
					explosion.setAge(explosion.getAge() + 2);
					if(explosion.getAge() >= 50){
						synchronized(explosions){explosions.remove(explosion);}
						this.cancel();
					}
				}
			}, 0, 20);
			
			//ajoute un bonus
			int btype = random.nextInt(4);
			if((btype == 0 && balles.size() == 1) || (btype != 0 && btype != curBonus)){
				Bonus b = new Bonus(brique.getX() + 10, brique.getY(), 50, 50, btype);
				synchronized(bonus){bonus.add(b);}
				b.timer.schedule(new TimerTask(){
					public void run() {
						//d�placement
						b.setY(b.getY()+1);
						//collision avec la raquette
						if(b.getY() + b.getHeight() >= raquette.getY() && b.getY() + b.getHeight() <= raquette.getY() + raquette.getHeight() && b.getX() + b.getWidth() > raquette.getX() && b.getX() < raquette.getX() + raquette.getWidth())
							useBonus(b);
					}
				}, 0, 10);
			}
			briques.remove(brique);
			if(briques.isEmpty()) gagne();
			
		};
		
		return true;
	}

	//met � jour la position de la balle b et teste ses collisions, appel� periodiquement/**
	
	
	private void physiqueBalle(Balle b){
		synchronized(b){
			if(b.getvX() == 0 && b.getvY() == 0){ //les balles attachÃ©es Ã  la raquette (celles dont la vÃ©locitÃ© est nulle) suivent la raquette
				b.setX((raquette.getX()) + b.getRaquetteX());
				b.setY((raquette.getY()) + b.getRaquetteY());
			}else{ 
				//les balles ayant une vÃ©locitÃ© se dÃ©placent selon celle-ci
				b.setX(b.getdX() + b.getvX());
				b.setY(b.getdY() + b.getvY());
				//balle perdue (sortie de l'�cran par le bas)
				if(b.getdY() > gameHeight){
					b.timer.cancel();
					synchronized(balles){
						balles.remove(b);
						if(balles.isEmpty()) perdu();
					}
				}
				//collision avec les murs
				if((b.getvX() < 0 && b.getdX() <= minX) || (b.getvX() > 0 && b.getdX() + b.getWidth() >= maxX )){
					
					// son lors de la collision avec le mur
					impactSound.note_on(musicImpacte);
					impactSound.note_on(musicImpacte-3);
					////////////////////////////////////////////
					
					b.setvX(-b.getvX());
				}
				//collision avec le plafond
				if(b.getvY() < 0 && b.getdY() <= 0){
					
					// son lors de la collision avec le plafond
					impactSound.note_on(musicImpacte);
					impactSound.note_on(musicImpacte-2);
					////////////////////////////////////////////
					
					b.setvY(-b.getvY());
				}
				//collision avec la raquette
				if(b.getvY() > 0 && b.getdY() + b.getHeight() >= raquette.getY() && b.getdY() + b.getHeight() <= raquette.getY() + raquette.getHeight() && b.getdX() + b.getWidth() > raquette.getX() && b.getdX() < raquette.getX() + raquette.getWidth()){
					
					// son
					impactSound.note_on(musicImpacte+2);
					impactSound.note_on(musicImpacte+5);
					
					if(curBonus == 3){
						b.setvX(0);
						b.setvY(0);
						b.setRaquetteX(b.getIntX() - raquette.getX());
						b.setRaquetteY(b.getIntY() - raquette.getY());
					}else{
						b.setvX(((b.getdX() + b.getWidth() - raquette.getX() - (raquette.getWidth() / 2)) / (raquette.getWidth() / 2)) * b.getvY());
						b.setvY(-b.getvY());
					}
				}
				//collision avec les briques
				synchronized (briques){
					Iterator<Brique> i = briques.iterator();
					boolean c = false;
					while(i.hasNext() && !c){
						Brique brique = i.next();
						if(b.getdX() + b.getWidth() > brique.getX() && b.getdX() < brique.getX() + brique.getWidth() && b.getdY() + b.getHeight() > brique.getY() && b.getdY() < brique.getY() + brique.getHeight()){
							c = collisionBrique(b, brique);
						}
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
					}, 0, 10);
				}
			}
		}
	}

	//suspend le jeu, le jeu peut reprendre en appelant lancerJeu
	public void suspendreJeu(){
		synchronized(runningLock){
			if(running){
				running = false;
				for(Balle b : balles) b.timer.cancel();
				for(Bonus b : bonus) b.timer.cancel();
			}
		}
	}
	
	//renvoie true si la partie est en cours et false si elle est suspendue
	public boolean isRunning(){
		return running;
	}
	
	//appel� lorsque le jeu est gagn�
	public void gagne(){
		
		//je met à jour le height score dans le fichier du niveau
		level.updateLevelHightScore(this.intScore);
		
		//je débloc le niveau suivant
		level.unlockNextLevel();
		
		//je met le boolean de gagner à vrai
		this.win = true;
		
		// le timer pour l'animation de fin
		endScreen = new AnimatedObject(200, this.gameHeight,this.gameWidth-400, this.gameHeight);
		
		endScreen.timer.schedule(new TimerTask(){
			
			public  void run() {
				
				endScreen.setY(endScreen.getY() - 5);
				
				if(endScreen.getY() < 0){
					
					this.cancel();
				}
			}
		}, 0, 1);
		
		suspendreJeu();
	}
	
	//appel� lorsque le jeu est perdu 
	public void perdu(){
		
		//je met a jour le height score dans le fichier du niveau
		level.updateLevelHightScore(this.intScore);
		
		//je met le boolean lose a vrai pour déclancher l'animation dans la vue
		this.lose = true;
		
		// le timer pour l'animation de fin
		endScreen = new AnimatedObject(200, this.gameHeight,this.gameWidth-400, this.gameHeight);
		endScreen.timer.schedule(new TimerTask(){
			public void run() {
				endScreen.setY(endScreen.getY() - 5);
				if(endScreen.getY() < 0){
					this.cancel();
				}
			}
		}, 0, 1);
		
		System.out.println("Perdu!");
		suspendreJeu();
	}

	//d�place la raquette vers la position x
	public void deplacerRaquette(int x){
		if(running){
			int m = raquette.getWidth()/2;
			if(x - m <= minX){
				raquette.setX(minX);
			}else if(x + m >= maxX){
				raquette.setX(maxX - 2 * m);
			}else{
				raquette.setX(x - m);
			}
		}
	}

	//Action permettant de lancer les balles attachées à la raquette (celles dont la vélocité est nulle) en leur donnant une velocité initiale
	public void lancerBalles(){

		//faire en sorte que le premier lancement de la balle suit le pointeur de la souris et attend qu'on clic dessus 
		
		if(running){
			for(Balle b : balles){
	
				synchronized(b){
	
					if(b.getvX() == 0 && b.getvY() == 0){
	
						b.setvY(-levelDifficulty);
						b.setvX(((b.getdX() + b.getWidth() - raquette.getX() - (raquette.getWidth() / 2)) / (raquette.getWidth() / 2)) * -b.getvY());
	
					}
				}
			}
		}
	}

}

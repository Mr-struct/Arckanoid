package model;

import java.io.IOException;
import java.util.*;
import controlles.Level;
import controlles.SoundPlay;
import objects.*;
import views.View;


/**
 * @author Stephane
 *
 */
public class Modele {
	
	public ArrayList<Brick> bricks = new ArrayList<Brick>(); //Liste des briques
	
	public ArrayList<Ball> balls = new ArrayList<Ball>(); //liste des balles
	
	public ArrayList<Bonus> bonus = new ArrayList<Bonus>(); //liste des bonus
	
	public ArrayList <AnimatedObject> effects = new ArrayList<AnimatedObject>(); //nombre de points gagnes
	
	public ArrayList <AnimatedObject> explosions = new ArrayList<AnimatedObject>(); //ecplosions
	
	public ArrayList <AnimatedObject> lostBallsAnimation = new ArrayList<AnimatedObject>(); //effet des balles perdues
		
	public  AnimatedObject endScreen; //ecran de fin de jeu (animation de d�filement)
	
	public Raquette raquette;
	
	protected int intScore = 0; //score entier
	
	public int levelDifficulty = 5; //difficulte de base (choisie par le joueur)

	protected int currentDifficulty; //difficulte actuelle (vitesse de base de la balle)
	
	public String stringScore = "00"; //score sous forme de chaine de caract�re (affichage)
	
	public int vies = 3; //nombre de vies
	
	private boolean running; //True = jeu en cours; False = jeu suspendu ou fini
	
	private Object runningLock = new Object(); //permet de synchroniser la pause
	
	private Random random = new Random(); //generateur d'aleatoire

	//resolution interne du jeu
	public int gameWidth = 1366;
	public int gameHeight = 768;
	
	//coordonnees limites du terrain de jeu
	protected int minX = 200;
	protected int maxX = gameWidth-200;
	
	protected View  vueJeu; //la vue
	
	public SoundPlay impactSound; //effets sonores
	
	private int musicImpacte = 60; //instrument utilise
	
	public Level level; //les donnees du niveau
	
	public int curBonus; //type de bonus actif (-1 = pas de bonus)
	
	public boolean win; //vrai si jeu gagne
	
	public boolean lose; //vrai si jeu perdu
	
	public Timer timerPreview = new Timer();
	
	/**
	 * initialise le niveau selon les parametres du fichier fileLevel
	 */
	public Modele() {
		/*
		 * init le son
		 */
		this.impactSound  =new SoundPlay();
		this.impactSound.setPnogrameChange(11);
		
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
	
	/**
	 * Cree une demo
	 * @param level niveau dans lequel la demo a lieu
	 */
	public Modele(String level) {
		this.preview(level);
	}
	
	/**
	 * Lance la demo
	 * @param level niveau dans lequel la demo a lieu
	 */
	public void preview(String level) {
		try {
			intitlevel(level);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.impactSound  = new SoundPlay();
		this.impactSound.setPnogrameChange(11);
		this.impactSound.volume = 0;		
		lancerJeu();
		timerPreview.schedule( new TimerTask() {
				public void run(){
					if(!isRunning()) {
						preview(level);
					}	
					synchronized(balls){
						lancerBalles();
						Ball bi = balls.get(0);
						if(bi.getdX() <= gameWidth/2)
							deplacerRaquette(bi.getIntX() - bi.getWidth());
						else
							deplacerRaquette(bi.getIntX() + 3 * bi.getWidth());
					}
				}
			}, 1000, 20);
		}
	/*
	 * initialise la premiere balle. sa position sera calculee automatiquement en fonction de la raquette
	 */
	public void initBalle(){

		// la position de la balle est set dans le controleur dans le lancerJeu()
		Ball ball = new Ball (0, 0, 0, 0, (raquette.getWidth()/2), -20, 20, 20);
		balls.add(ball);
	}
	
	/**
	 * initialise le terrain
	 * @param level niveau a charger
	 */
	public void intitlevel(String filLevel) throws IOException{
		
		this.win = false;
		
		this.lose = false;
		
		bricks = new ArrayList<Brick>(); 
		
		balls = new ArrayList<Ball>();
		
		bonus = new ArrayList<Bonus>();
		
		effects = new ArrayList<AnimatedObject>();
		
		explosions = new ArrayList<AnimatedObject>();
		
		lostBallsAnimation = new ArrayList<AnimatedObject>();
		
		stringScore = "00";
		
		intScore = 0;
		
		vies = 3;
		
		currentDifficulty = levelDifficulty;
		
		curBonus = -1;
		
		level = new Level(filLevel);
		
		//init la raquette
		this.raquette = new Raquette(500,680,150,30);
		//init la balle
		initBalle();
		
		char [] [] char2D = new char [level.bricksCols][level.bricksRows];
		
		char2D = level.arrayStringTo2DArrayChar(level.fileLevelParssed, level.bricksCols, level.bricksRows);
		System.out.println("Cols : "+ level.bricksCols);
		System.out.println("ROW : "+ level.bricksRows);
		for(int i = 0; i < level.bricksCols; i++) {
		
			for(int j = 0; j < level.bricksRows ;j++) {
			
				// matrice inversser
				switch(char2D[j][i]) {

				case 'B': // pour les briques bleu

					bricks.add(new Brick( (i*(level.bricksCols+1)*10)+240, (j*level.bricksRows*7)+50 ,80   ,30    ,0, 1,1,61) );
					break;

				case 'V': // pour les briques vertes 

					bricks.add(new Brick( (i*(level.bricksCols+1)*10)+240, (j*level.bricksRows*7)+50 ,80   ,30    ,1, 2,1,63) );
					break;

				case 'S': //pour les briques en metal

					bricks.add(new Brick( (i*(level.bricksCols+1)*10)+240, (j*level.bricksRows*7)+50 ,80   ,30    ,2, 3,2,65) );
					break;

				case 'R': // pour les briques rouge

					bricks.add(new Brick( (i*(level.bricksCols+1)*10)+240, (j*level.bricksRows*7)+50 ,80   ,30    ,3, 4,1,68) );
					break;

				case 'G' : // pour les brique or

					bricks.add(new Brick( (i*(level.bricksCols+1)*10)+240, (j*level.bricksRows*7)+50 ,80   ,30    ,4, 5,1,73) );
					break;
				};
			}
		}
	}
	
	
	/**
	 * le joueur utilise un bonus
	 * @param b le type de bonus
	 */
	public void useBonus(Bonus b) {
		synchronized(bonus){
			b.timer.cancel();
			raquette.setWidth(raquette.getInitWidth());
			
			//Multiballes
			if(b.getType() == 0) {
				lancerBalles();
				synchronized(balls){
					if(balls.size() == 1){
						Ball bi = balls.get(0);
						Ball b1 = new Ball(bi.getdX(), bi.getdY(), bi.getvX() + 1, bi.getvY(), 0, 0, bi.getWidth(), bi.getHeight());
						Ball b2 = new Ball(bi.getdX(), bi.getdY(), bi.getvX() - 1, bi.getvY(), 0, 0, bi.getWidth(), bi.getHeight());
						balls.add(b1);
						balls.add(b2);
	
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
				lancerBalles();
				raquette.setWidth(raquette.getInitWidth() + 50);
			}

			//raquette retrecie
			if(b.getType() == 2) {
				lancerBalles();
				raquette.setWidth(raquette.getInitWidth() - 50);
			}
			
			//raquette collante
			if(b.getType() == 3) {
			}
			
			//empecher la raquette de depasser
			synchronized(raquette){
				if(raquette.getX() + raquette.getWidth() > maxX)
					raquette.setX(maxX - raquette.getWidth());
			}
			curBonus = b.getType();
			bonus.remove(b);
		}
	}
	
	
	/**
	 * appeler quand balle entre en collision avec brique, calcule les effets de la collision sur la balle et la brique
	 * @param ball la balle qui entre en collision
	 * @param brick la brique affectee
	 * @return True si collision (toujours vrai)
	 */
	private boolean collisionBrique(Ball ball, Brick brick){
		boolean diagonale = true;
		//dessus et dessous
		if(ball.getdX() >= brick.getX() && ball.getdX() + ball.getWidth() <= brick.getX() + brick.getWidth()){
			diagonale = false;
			//dessus
			if(ball.getvY() > 0 && ball.getdY() + ball.getHeight() < brick.getY() + brick.getHeight())
				ball.setvY(-ball.getvY());
			//dessous
			if(ball.getvY() < 0 && ball.getdY() > brick.getY())
				ball.setvY(-ball.getvY());
		}
		//gauche et droite
		if(ball.getdY() >= brick.getY() && ball.getdY() + ball.getHeight() <= brick.getY() + brick.getHeight()){
			diagonale = false;
			//gauche
			if(ball.getvX() > 0 && ball.getdY() + ball.getWidth() < brick.getX() + brick.getWidth())
				ball.setvX(-ball.getvX());
			//droite
			if(ball.getvX() < 0 && ball.getdX() > brick.getX())
				ball.setvX(-ball.getvX());
		}
		//diagonales
		if(diagonale){
			
			if((ball.getvX() > 0 && ball.getdX() < brick.getX()) || (ball.getvX() < 0 && ball.getdX() + ball.getWidth() > brick.getX() + brick.getWidth()))
				ball.setvX(-ball.getvX());
			
			if((ball.getvY() > 0 && ball.getdY() < brick.getY()) || (ball.getvY() < 0 && ball.getdY() + ball.getHeight() > brick.getY() + brick.getHeight()))
				ball.setvY(-ball.getvY());
		}
		
		//joue un son
		musicImpacte = (brick.note);
		impactSound.noteOn(musicImpacte);
		if(brick.getNumberOfColision() > 1){
			brick.setNumberOfColision(brick.getNumberOfColision() - 1);
			if(brick.getType() == 2) brick.setType(5);
		}else if(bricks.contains(brick)){
			intScore = intScore + brick.getValue();
			stringScore = String.valueOf(intScore);
			
			//affiche les points gagn�s
			AnimatedObject effect = new AnimatedObject(brick.getX()+30,brick.getY(), "+" + brick.getValue());
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
			AnimatedObject explosion = new AnimatedObject(brick.getX()+30,brick.getY()+30,10,10);
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
			if(random.nextInt(5) == 0){
				int btype = random.nextInt(4);
				if((btype == 0 && balls.size() == 1) || (btype != 0 && btype != curBonus)){
					Bonus b = new Bonus(brick.getX() + 10, brick.getY(), 50, 50, btype);
					synchronized(bonus){bonus.add(b);}
					b.timer.schedule(new TimerTask(){
						public void run() {
							//deplacement
							b.setY(b.getY()+1);
							//collision avec la raquette
							if(b.getY() + b.getHeight() >= raquette.getY() && b.getY() + b.getHeight() <= raquette.getY() + raquette.getHeight() && b.getX() + b.getWidth() > raquette.getX() && b.getX() < raquette.getX() + raquette.getWidth())
								useBonus(b);
							//bonus perdu
							if(b.getY() + b.getHeight() >= gameHeight){
								b.timer.cancel();
								synchronized(bonus){bonus.remove(b);}
							}
						}
					}, 0, 10);
				}
			}
			bricks.remove(brick);
			if(bricks.isEmpty()) gagne();
			
		};
		
		return true;
	}
	
	
	/**
	 * physique de la balle, a appeler dans ses timers
	 * @param b balle mise a jour
	 */
	private void physiqueBalle(Ball b){
		synchronized(b){
			if(b.getvX() == 0 && b.getvY() == 0){ //les balles attachees a la raquette (celles dont la velocite est nulle) suivent la raquette
				b.setX((raquette.getX()) + b.getPaddleX());
				b.setY((raquette.getY()) + b.getPaddleY());
			}else{
				//les balles ayant une velocite non nulle se deplacent selon celle-ci
				b.setX(b.getdX() + b.getvX());
				b.setY(b.getdY() + b.getvY());
				//balle perdue (sortie de l'ecran par le bas)
				if(b.getdY() > gameHeight){
					b.timer.cancel();
					
					//animation de la balle perdues
					AnimatedObject lostBall = new AnimatedObject(minX , gameHeight-5,gameWidth-minX,50);
					synchronized(lostBallsAnimation) {lostBallsAnimation.add(lostBall);}
					lostBall.timer.schedule(new TimerTask(){
						public void run() {
							lostBall.setAge(lostBall.getAge() + 1);
							if(lostBall.getAge() >= 100){
								synchronized(lostBallsAnimation){lostBallsAnimation.remove(lostBall);}
								this.cancel();
							}
						}
					}, 0, 5);
					
					//supprime la balle
					synchronized(balls){
						balls.remove(b);
						if(balls.isEmpty()){
							vies --;
							if(vies <= 0)
								perdu();
							else{
								Ball nb = new Ball (0, 0, 0, 0, (raquette.getWidth()/2), -20, 20, 20);
								balls.add(nb);
								nb.timer.schedule(new TimerTask(){
									public void run() {
										physiqueBalle(nb);
									}
								}, 0, 10);
							}
						}
					}
				}
				//collision avec les murs
				if((b.getvX() < 0 && b.getdX() <= minX) || (b.getvX() > 0 && b.getdX() + b.getWidth() >= maxX )){
					
					// son lors de la collision avec le mur
					impactSound.noteOn(musicImpacte);
					impactSound.noteOn(musicImpacte-3);
					
					b.setvX(-b.getvX());
				}
				//collision avec le plafond
				if(b.getvY() < 0 && b.getdY() <= 0){
					
					// son lors de la collision avec le plafond
					impactSound.noteOn(musicImpacte);
					impactSound.noteOn(musicImpacte-2);
					
					b.setvY(-b.getvY());
				}
				//collision avec la raquette
				if(b.getvY() > 0 && b.getdY() + b.getHeight() >= raquette.getY() && b.getdY() + b.getHeight() <= raquette.getY() + raquette.getHeight() && b.getdX() + b.getWidth() > raquette.getX() && b.getdX() < raquette.getX() + raquette.getWidth()){
					
					// son lors de la collision
					impactSound.noteOn(musicImpacte+2);
					impactSound.noteOn(musicImpacte+5);
					
					//attache a la raquette si 
					if(curBonus == 3){
						b.setvX(0);
						b.setvY(0);
						b.setPaddleX(b.getIntX() - raquette.getX());
						b.setPaddleY(b.getIntY() - raquette.getY());
					}else{
						double angle = ((b.getdX() + b.getWidth() - raquette.getX()) * Math.toRadians(140) / raquette.getWidth()) + Math.toRadians(20);
						if(angle > Math.toRadians(160)) angle = Math.toRadians(160);
						b.setvX(-Math.cos(angle) * currentDifficulty);
						b.setvY(-Math.sin(angle) * currentDifficulty);
					}
				}
				//collision avec les briques
				synchronized (bricks){
					Iterator<Brick> i = bricks.iterator();
					boolean c = false;
					while(i.hasNext() && !c){
						Brick brick = i.next();
						if(b.getdX() + b.getWidth() > brick.getX() && b.getdX() < brick.getX() + brick.getWidth() && b.getdY() + b.getHeight() > brick.getY() && b.getdY() < brick.getY() + brick.getHeight()){
							c = collisionBrique(b, brick);
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
				for(Ball b : balls){
					b.timer = new Timer();
					b.timer.schedule(new TimerTask(){
						public void run() {
							physiqueBalle(b);
						}
					}, 0, 10);
				}
				for(Bonus b : bonus){
					b.timer = new Timer();
					b.timer.schedule(new TimerTask(){
						public void run() {
							//d�placement
							b.setY(b.getY()+1);
							//collision avec la raquette
							if(b.getY() + b.getHeight() >= raquette.getY() && b.getY() + b.getHeight() <= raquette.getY() + raquette.getHeight() && b.getX() + b.getWidth() > raquette.getX() && b.getX() < raquette.getX() + raquette.getWidth())
								useBonus(b);
							//bonus perdu
							if(b.getY() + b.getHeight() > gameHeight){
								b.timer.cancel();
								synchronized(bonus){bonus.remove(b);}
							}
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
				for(Ball b : balls) b.timer.cancel();
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
		suspendreJeu();
	}

	//d�place la raquette vers la position x
	public void deplacerRaquette(int x){
		if(running){
			synchronized(raquette){
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
	}

	//Action permettant de lancer les balles attachées à la raquette (celles dont la vélocité est nulle) en leur donnant une velocité initiale
	public void lancerBalles(){

		//faire en sorte que le premier lancement de la balle suit le pointeur de la souris et attend qu'on clic dessus 
		
		if(running){
			for(Ball b : balls){
	
				synchronized(b){
	
					if(b.getvX() == 0 && b.getvY() == 0){
						double angle = ((b.getdX() + b.getWidth() - raquette.getX()) * Math.toRadians(140) / raquette.getWidth()) + Math.toRadians(20);
						if(angle > Math.toRadians(160)) angle = Math.toRadians(160);
						b.setvX(-Math.cos(angle) * currentDifficulty);
						b.setvY(-Math.sin(angle) * currentDifficulty);
					}
				}
			}
		}
	}

}

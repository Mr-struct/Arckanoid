
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;


public class Modele {

	protected ArrayList<Brique> briques = new ArrayList<Brique>(); 

	protected Raquette raquette;
	
	protected ArrayList<Balle> balles = new ArrayList<Balle>();

	protected  String fileLevel;

	protected String levelName;

	protected String levelRank;

	protected String levelBackground;
	
	private boolean running;
	
	private Object runningLock = new Object();

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	protected int gameWidth = 1366;
	
	protected int gameHeight = 768;

	protected int minX = 200;

	protected int maxX = gameWidth-200;

	protected Vue  vueJeu;
	
	private SoundPlay impactSound;
	
	//initialise le niveau selon les paramËtres du fichier fileLevel
	public Modele(String fileLevel) {
		
		running = false;
		
		this.raquette = new Raquette(500,680,150,30);
		
		this.impactSound = new SoundPlay();
		this.impactSound.setPnogrameChange(14);
		
		this.fileLevel = fileLevel;
		
		this.levelBackground = this.fileLevel+".png";
		
		System.out.println(levelBackground);

		try {
			intitlevel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//initialise la premiËre balle. sa position sera calculÈe automatiquement en fonction de la raquette
	public void initBalle(){

		// la balle se positionne automatiquement au niveau de la raquette
		Balle balle = new Balle (0, 0, 0, 0, (raquette.getWidth()/2)-5, -20, 20, 20); 
		Balle balle2 = new Balle (0, 0, 0, 0, (raquette.getWidth()/2)-40, -20, 20, 20); 
		Balle balle3 = new Balle (0, 0, 0, 0, (raquette.getWidth()/2)+40, -20, 20, 20); 
		balles.add(balle);
		balles.add(balle2);
		balles.add(balle3);

	}
	
	//initialise le terrain
	public void intitlevel() throws IOException{

		initBalle();
		String test = readFile(fileLevel);
		String [] out = null;

		// on recupere les lignes du fichier dans out 
		for(int i = 0; i < test.length();i++) {
			out = test.split("\n");
		}
		System.out.println("taille de out est de : " +out.length);
		System.out.println("taille de la vue est de : " +gameWidth +"X" +gameHeight);

		this.levelRank = out[out.length-4];
		this.levelName = out[out.length-3];
		int colums = Integer.parseInt(out[out.length-2]);
		int lines =  Integer.parseInt(out[out.length-1]);;


		char [] [] char2D = new char [colums][lines];
		char2D = arrayStringTo2DArrayChar(char2D, out, colums, lines);

		for(int i = 0; i < colums; i++) {

			for(int j = 0; j < lines ;j++) {

				// initialise les brique avec juste un random faut changer √ßa avec une proba 


				// matrice inversser
				switch(char2D[j][i]) {

				case 'B': // pour les briques bleu

					briques.add(new Brique( (i*(colums-1)*10)+240, (j*lines*10)+50 ,80   ,30    ,0, 1) );
					break;

				case 'V': // pour les briques vertes 

					briques.add(new Brique( (i*(colums-1)*10)+240, (j*lines*10)+50 ,80   ,30    ,1, 2) );
					break;

				case 'S': //pour les briques en metal

					briques.add(new Brique( (i*(colums-1)*10)+240, (j*lines*10)+50 ,80   ,30    ,2, 3) );
					break;

				case 'R': // pour les briques rouge

					briques.add(new Brique( (i*(colums-1)*10)+240, (j*lines*10)+50 ,80   ,30    ,3, 4) );
					break;

				case 'G' : // pour les brique or

					briques.add(new Brique( (i*(colums-1)*10)+240, (j*lines*10)+50 ,80   ,30    ,4, 5) );
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
	
	//appelÈe quand balle entre en collision avec brique, calcule les effets de la collision sur la balle et la brique
	private boolean collisionBrique(Balle balle, Brique brique){
		
		boolean diagonale = true;
		//dessus et dessous
		if(balle.getX() >= brique.getX() && balle.getX() + balle.getWidth() <= brique.getX() + brique.getWidth()){
			diagonale = false;
			//dessus
			if(balle.getvY() > 0 && balle.getY() + balle.getHeight() < brique.getY() + brique.getHeight())
				balle.setvY(-balle.getvY());
			//dessous
			if(balle.getvY() < 0 && balle.getY() > brique.getY())
				balle.setvY(-balle.getvY());
		}
		//gauche et droite
		if(balle.getY() >= brique.getY() && balle.getY() + balle.getHeight() <= brique.getY() + brique.getHeight()){
			diagonale = false;
			//gauche
			if(balle.getvX() > 0 && balle.getY() + balle.getWidth() < brique.getX() + brique.getWidth())
				balle.setvX(-balle.getvX());
			//droite
			if(balle.getvX() < 0 && balle.getX() > brique.getX())
				balle.setvX(-balle.getvX());
		}
		//diagonales
		if(diagonale){
			if((balle.getvX() > 0 && balle.getX() < brique.getX()) || (balle.getvX() < 0 && balle.getX() + balle.getWidth() > brique.getX() + brique.getWidth()))
				balle.setvX(-balle.getvX());
			if((balle.getvY() > 0 && balle.getY() < brique.getY()) || (balle.getvY() < 0 && balle.getY() + balle.getHeight() > brique.getY() + brique.getHeight()))
				balle.setvY(-balle.getvY());
		}
		synchronized (briques){
			if(briques.contains(brique)){
				briques.remove(brique);
				if(briques.isEmpty()) gagne();
			}
		}
		return true;
	}
	
	//met ‡ jour la position de la balle b et teste ses collisions, appelÈ periodiquement
	private void physiqueBalle(Balle b){
		synchronized(b){
			if(b.getvX() == 0 && b.getvY() == 0){ //les balles attach√É¬©es √É  la raquette (celles dont la v√É¬©locit√É¬© est nulle) suivent la raquette
				b.setX((raquette.getX()) + b.getrX());
				b.setY((raquette.getY()) + b.getrY());
			}else{ 
				//les balles ayant une v√É¬©locit√É¬© se d√É¬©placent selon celle-ci
				b.setX(b.getX() + b.getvX());
				b.setY(b.getY() + b.getvY());
				//balle perdue (sortie de l'Ècran par le bas)
				if(b.getY() > gameHeight){
					synchronized(balles){
						balles.remove(b);
						if(balles.isEmpty()) perdu();
					}
					b.timer.cancel();
				}
				//collision avec les murs
				if((b.getvX() < 0 && b.getX() <= minX) || (b.getvX() > 0 && b.getX() + b.getWidth() >= maxX )){
					b.setvX(-b.getvX());
				}
				//collision avec le plafond
				if(b.getvY() < 0 && b.getY() <= 0){
					b.setvY(-b.getvY());
				}
				//collision avec la raquette
				if(b.getvY() > 0 && b.getY() + b.getHeight() >= raquette.getY() && b.getY() + b.getHeight() <= raquette.getY() + raquette.getHeight() && b.getX() + b.getWidth() > raquette.getX() && b.getX() < raquette.getX() + raquette.getWidth()){
					impactSound.note_on(60);
					b.setvX(((b.getX() + b.getWidth() - raquette.getX() - (raquette.getWidth() / 2)) / (raquette.getWidth() / 2)) * b.getvY());
					b.setvY(-b.getvY());
				}
				//collision avec les briques
				Iterator<Brique> i = briques.iterator();
				boolean c = false;
				while(i.hasNext() && !c){
					Brique brique = i.next();
					if(b.getX() + b.getWidth() > brique.getX() && b.getX() < brique.getX() + brique.getWidth() && b.getY() + b.getHeight() > brique.getY() && b.getY() < brique.getY() + brique.getHeight()){
						impactSound.note_on(65);
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
			}
		}
	}
	
	//renvoie true si la partie est en cours et false si elle est suspendue
	public boolean isRunning(){
		return running;
	}
	
	//appelÈ lorsque le jeu est gagnÈ
	public void gagne(){
		System.out.println("Gagne!");
		suspendreJeu();
	}
	
	//appelÈ lorsque le jeu est perdu 
	public void perdu(){
		System.out.println("Perdu!");
		suspendreJeu();
	}

	//dÔøΩplace la raquette vers la position x
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

	//Action permettant de lancer les balles attach√©es √† la raquette (celles dont la v√©locit√© est nulle) en leur donnant une velocit√© initiale
	public void lancerBalles(){

		//faire en sorte que le premier lancement de la balle suit le pointeur de la souris et attend qu'on clic dessus 
		
		if(running){
			for(Balle b : balles){
	
				synchronized(b){
	
					if(b.getvX() == 0 && b.getvY() == 0){
	
						b.setvY(-4);
						b.setvX(((b.getX() + b.getWidth() - raquette.getX() - (raquette.getWidth() / 2)) / (raquette.getWidth() / 2)) * -b.getvY());
	
					}
				}
			}
		}
	}
	
	public synchronized void playSound(String audioFilePath ,int delay ) {

		new Thread(new Runnable() {
			public void run() {
				Boolean playCompleted = true;

				File audioFile = new File(audioFilePath);

				try {
					AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

					AudioFormat format = audioStream.getFormat();

					DataLine.Info info = new DataLine.Info(Clip.class, format);

					Clip audioClip = (Clip) AudioSystem.getLine(info);
					
					
				    audioClip.open(audioStream);
				    
					audioClip.start();

					while (playCompleted) {
						// wait for the playback completes
						try {
							Thread.sleep(delay);

						} catch (InterruptedException ex) {
							System.out.println("execpt");
						}
						audioClip.close();
					}
				} catch (UnsupportedAudioFileException ex) {
					System.out.println("The specified audio file is not supported.");
					ex.printStackTrace();
				} catch (LineUnavailableException ex) {
					System.out.println("Audio line for playing back is unavailable.");
					ex.printStackTrace();
				} catch (IOException ex) {
					System.out.println("Error playing the audio file.");
					ex.printStackTrace();
				}
			}
		}).start();
	}

}

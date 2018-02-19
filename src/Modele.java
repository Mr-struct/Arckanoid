
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Modele {

	protected ArrayList<Brique> briques = new ArrayList<Brique>(); 

	protected Raquette raquette;
	
	protected ArrayList<Balle> balles = new ArrayList<Balle>();

	protected  String fileLevel;

	protected String levelName;

	protected String levelRank;

	protected String levelBackground;

	private java.util.Timer tLogique = new java.util.Timer();

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	protected int gameWidth = (int) screenSize.getWidth();

	protected int gameHeight =(int) screenSize.getHeight();

	protected int minX = 200;

	protected int maxX = gameWidth-200;

	protected Vue  vueJeu;

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




	private boolean collisionBrique(Balle balle, Brique brique){
		//balle.setvX(-balle.getvX());
		//balle.setvY(-balle.getvY());
		
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
			if((balle.getvX() > 0 && balle.getX() <= brique.getX()) || (balle.getvX() < 0 && balle.getX() + balle.getWidth() >= brique.getX() + brique.getWidth()))
				balle.setvX(-balle.getvX());
			if((balle.getvY() > 0 && balle.getY() <= brique.getY()) || (balle.getvY() < 0 && balle.getY() + balle.getHeight() >= brique.getY() + brique.getHeight()))
				balle.setvY(-balle.getvY());
		}
		briques.remove(brique);
		return true;
	}
	
	//lance le timer pour la logique du jeu
	public void lancerJeu(){
		
		tLogique.schedule(new TimerTask(){
			
			public void run() {
				
				for(Balle b : balles){
					
				synchronized(b){
						if(b.getvX() == 0 && b.getvY() == 0){ //les balles attachÃ©es Ã  la raquette (celles dont la vÃ©locitÃ© est nulle) suivent la raquette
							b.setX((raquette.getX()) + b.getrX());
							b.setY((raquette.getY()) + b.getrY());
						}else{ 
							//les balles ayant une vÃ©locitÃ© se dÃ©placent selon celle-ci
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
							if(b.getvY() > 0 && b.getY() + b.getHeight() >= raquette.getY() && b.getY() + b.getHeight() <= raquette.getY() + raquette.getHeight() && b.getX() + b.getWidth() > raquette.getX() && b.getX() < raquette.getX() + raquette.getWidth()){
								b.setvY(-b.getvY());
							}
							//collision avec les briques
							Iterator<Brique> i = briques.iterator();
							boolean c = false;
							while(i.hasNext() && !c){
								Brique brique = i.next();
								if(b.getX() + b.getWidth() > brique.getX() && b.getX() < brique.getX() + brique.getWidth() && b.getY() + b.getHeight() > brique.getY() && b.getY() < brique.getY() + brique.getHeight()){
									c = collisionBrique(b, brique);
								}
							}
						}
					}
				}
			}
		}, 0, 10);
}

	//d�place la raquette vers la position x
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

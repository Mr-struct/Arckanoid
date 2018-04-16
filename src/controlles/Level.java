package controlles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/*
 * cette classe gere le chargement de niveau 
 * les niveaux sont des fichiers .txt represente comme suite :
 * OOOO
 * RRRR
 * BBBB
 * VVVV
 * GGGG
 * SSSS
 * niveauSuivant.txt (level(0->n).txt)
 * la difficulte (easy ou hard ...)
 * : score max (entier)
 * si il est jouable (yes/non)
 * l index du niveau  (level(0->n))
 * le nom 
 * les colonnes (entier)
 * les lignes(entier)
 * 
 * O c est un vide
 * R pour la birque rouge
 * B pour la brique bleu
 * V pour la brique verte
 * G pour la brique en or
 * S pour la brique argente
 * 
 */
public class Level {
	
	public String levelBackground; // le fond d ecran du niveau 
	
	protected String fileLevel; // le chemin ver le fichier

	public int bricksCols; // le nombre de colonnes
 
	public int bricksRows; // pour le nombre de colonnes
	
	public String levelRank; // l index du niveau  
	
	public String levelName; // le nom du niveau 
	
	public String [] fileLevelParssed = null; // tableau de string pour parsser le fichier
	
	public String levelHightScore; // le soc max obtenu
	
	protected String getStatu; // le statu du niveau jouable ou non
	
	public String levelDifficulty; // le niveau de difficulte
	
	protected String nextLevel; // le niveau suivant au niveau actuel
	
	public boolean canBePlayed; // on indique le niveau est jouable ou non
	
	
	/*
	 * constructeur
	 * 
	 * le niveau est init
	 * 
	 * @param fileLevel le chemin vere le fichier 
	 */
	public Level(String fileLevel) {
		
		this.fileLevel = fileLevel;
		
		this.levelBackground = this.fileLevel+".gif";
		
		//this.readyForPlay = false;

		try {
			String test = readFile(this.fileLevel);

			// on recupere les lignes du fichier dans fileLevelParssed 
			for(int i = 0; i < test.length();i++) {
				fileLevelParssed = test.split("\n");
			}
			
			String path = "./levels/";
			
			// si le system si windos on parrse autrement 
			
			if(System.getProperty("os.name").startsWith("Win")) {
				path = ".\\levels\\";
			}
			/*
			 * on recupere les champ correspondant dans la variable associer
			 */
			this.nextLevel = path+fileLevelParssed[fileLevelParssed.length-8];
			
			this.levelDifficulty = fileLevelParssed[fileLevelParssed.length-7];
			
			this.levelHightScore = fileLevelParssed[fileLevelParssed.length-6];
			
			this.getStatu = fileLevelParssed[fileLevelParssed.length-5];
			
			this.levelRank = fileLevelParssed[fileLevelParssed.length-4];
			
			this.levelName = fileLevelParssed[fileLevelParssed.length-3];
			
			this.bricksCols  = Integer.parseInt(fileLevelParssed[fileLevelParssed.length-2]);
			
			this.bricksRows =  Integer.parseInt(fileLevelParssed[fileLevelParssed.length-1]);
			
			// on test si l etat du niveau est jouable
			if (this.getStatu.equals("yes")) {
				
				this.canBePlayed = true;
			}
			
			else if(this.getStatu.equals("no")){
				
				this.canBePlayed = false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	/*
	 * @param newHightScore 
	 * 
	 * met a jour le scor max dans le fichier en le comparant a celui obtenu
	 */
	public void updateLevelHightScore(int newHightScore) {
		
		String [] str =null;
		
		for(int i = 0; i <this.levelHightScore.length();i++) {
			
			str = this.levelHightScore.split(" ");
		}
		
		int oldHieghScore = Integer.parseInt(str[1]);
		
		if(oldHieghScore < newHightScore) {
			
			try {
				
				updateLine(this.levelHightScore, ": " + newHightScore ,this.fileLevel);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * deverouille le niveau suivant en metant le champ jouable a yes 
	 */
	public void unlockNextLevel() {
	
		try {
			updateLine("no", "yes",this.nextLevel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*
	 * @param filename chemin ver le fichier
	 * 
	 * @return String 
	 * 
	 * lit le fichier et  transforme le tout en un long string !
	 */
	private  String readFile(String filename) throws IOException
	{
		String content = null;

		File file = new File(filename); 

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
	
	/*
	 *	@param  str tableau de string 
	 *
	 *	@param cols le nombre de colonnes
	 *
	 *	@param lines le nombre de lignes
	 *
	 *	@return char[][] 
	 *
	 *	transforme un tableau de string en tableau de char 2d
	 *
	 *
	 */
	public char [][] arrayStringTo2DArrayChar(String [] str,int cols,int lines){
		
		char[][] char2D = new char[cols][lines];

		for(int i = 0; i < lines+1; i++)  {
			
			String line = str[i];
			
			char2D[i] = line.toCharArray();
			
		}
		
		return char2D;
	}
	
	/*
	 * @param toUpdate le nouveau string 
	 * 
	 * @param updated  le string a metre a jour 
	 * 
	 * @param data	   la ligne du fichier correspondante 
	 * 
	 */
	public static void updateLine(String toUpdate, String updated,String data) throws IOException {
		
		BufferedReader file = new BufferedReader(new FileReader(data));
		
	    String line;
	    
	    String input = "";	     
	    
	    while ((line = file.readLine()) != null)
	    	
	    	   input += line + "\n";
	    

	    input = input.replace(toUpdate, updated);

	    FileOutputStream os = new FileOutputStream(data);
	    
	    os.write(input.getBytes());

	    file.close();
	    
	    os.close();
	}
}

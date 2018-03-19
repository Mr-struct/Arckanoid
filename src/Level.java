import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class Level {
	
	protected String levelBackground;
	
	protected String fileLevel;

	protected int briquesCols,briquesRows;
	
	protected Boolean readyForPlay;
	
	protected String levelRank;
	
	protected String levelName;
	
	protected String [] fileLevelParssed = null;
	
	protected String levelHightScore;
	
	protected String getStatu;
	
	protected String levelDifficulty;
	
	private String nextLevel;
	
	protected boolean canBePlayed;
	
	
	
	public Level(String fileLevel) {
		
		this.fileLevel = fileLevel;
		
		this.levelBackground = this.fileLevel+".png";
		
		this.readyForPlay = false;

		try {
			String test = readFile(this.fileLevel);
			

			// on recupere les lignes du fichier dans out 
			for(int i = 0; i < test.length();i++) {
				fileLevelParssed = test.split("\n");
			}
			this.nextLevel = fileLevelParssed[fileLevelParssed.length-8];
			
			this.levelDifficulty = fileLevelParssed[fileLevelParssed.length-7];
			
			this.levelHightScore = fileLevelParssed[fileLevelParssed.length-6];
			
			this.getStatu = fileLevelParssed[fileLevelParssed.length-5];
			
			this.levelRank = fileLevelParssed[fileLevelParssed.length-4];
			
			this.levelName = fileLevelParssed[fileLevelParssed.length-3];
			
			this.briquesCols  = Integer.parseInt(fileLevelParssed[fileLevelParssed.length-2]);
			
			this.briquesRows =  Integer.parseInt(fileLevelParssed[fileLevelParssed.length-1]);
			
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
	
	public void unlockNextLevel() {
	
		try {
			updateLine("no", "yes",this.nextLevel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unused")
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
	
	@SuppressWarnings("unused")
	public char [][] arrayStringTo2DArrayChar(String [] str,int cols,int lines){
		//Assumes all lines are the same length
		char[][] char2D = new char[cols][lines];

		for(int i = 0; i < lines+1; i++)  {
			String line = str[i];
			char2D[i] = line.toCharArray();
		}
		return char2D;

	}
	public static void updateLine(String toUpdate, String updated,String data) throws IOException {
		BufferedReader file = new BufferedReader(new FileReader(data));
	    String line;
	    String input = "";	     
	    while ((line = file.readLine()) != null)
	    	   input += line + System.lineSeparator();

	    input = input.replace(toUpdate, updated);

	    FileOutputStream os = new FileOutputStream(data);
	    os.write(input.getBytes());

	    file.close();
	    os.close();
	}
}

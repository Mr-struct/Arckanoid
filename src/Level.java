import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Level {
	
	protected String levelBackground;
	protected String fileLevel;
	protected int isClear;
	protected int briquesCols,briquesRows;
	protected Boolean readyForPlay;
	protected String levelRank;
	protected char [] [] char2D;
	public Level(String fileLevel) {
		this.fileLevel = fileLevel;
		this.levelBackground = this.fileLevel+".png";
		this.readyForPlay = false;

		try {
			String test = readFile(this.fileLevel);
			String [] out = null;

			// on recupere les lignes du fichier dans out 
			for(int i = 0; i < test.length();i++) {
				out = test.split("\n");
				this.levelRank = out[out.length-4];
				//this.levelName = out[out.length-3];
				this.briquesCols  = Integer.parseInt(out[out.length-2]);
				this.briquesRows =  Integer.parseInt(out[out.length-1]);
				char [] [] char2D = new char [this.briquesCols][this.briquesRows];
				char2D = arrayStringTo2DArrayChar(char2D, out, this.briquesCols, this.briquesRows);
			}
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
	private char [][] arrayStringTo2DArrayChar(char[][]char2D,String [] str,int cols,int lines){
		//Assumes all lines are the same length
		char2D = new char[cols][lines];

		for(int i = 0; i < str.length-2; i++)  {
			String line = str[i];
			char2D[i] = line.toCharArray();
		}
		return char2D;

	}
}

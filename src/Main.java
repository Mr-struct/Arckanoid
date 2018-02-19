import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
public class Main  {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		Modele modele = new Modele("src/levels/level0.txt");
		Vue vue = new Vue(modele);
	}

}

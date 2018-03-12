import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

public class Main  {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		Modele modele = new Modele("src/levels/level0.txt");
		
		Vue vue = new Vue(modele);
	}
}

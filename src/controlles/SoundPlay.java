package controlles;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

/*
 * cette calsse gere le son midi 
 * le son est directement lie au system 
 *  source : https://openclassrooms.com/courses/les-applications-web-avec-javafx/tp-creation-d-un-melordi-debut?q=&hPP=8&idx=prod_v2_COURSES_en&p=0&fR[certificate][0]=true&fR[isWeb][0]=true
 */
public class SoundPlay {

	public int volume = 100; // le niveau du son 

	private Synthesizer synthetiseur; // on cree un synthetiseur

	private MidiChannel canal; // on cree un canal


	public SoundPlay(){

		try {
			//On recupere le synthétiseur, on l'ouvre et on obtient un canal
			synthetiseur = MidiSystem.getSynthesizer();

			synthetiseur.open();

		} catch (MidiUnavailableException ex) {

			Logger.getLogger(SoundPlay.class.getName()).log(Level.SEVERE, null, ex);
		}

		canal = synthetiseur.getChannels()[0];      

		//On initialise l'instrument 0 (le piano pour windows) pour le canal

		canal.programChange(0);

	}



	/*
	 *  Joue la note dont le numéro est en paramètre
	 *  @param note la note a jouer 
	 */

	public synchronized void noteOn(int note){
		new Thread(new Runnable() {
			
			public void run() {
				
				canal.noteOn(note, volume);
				
				Boolean b = true;
				
				while(b) {
					
					try {
						
						Thread.sleep(1);
						
						b = false;
						
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
				}
				canal.noteOff(note);
			}
		}).start();
	}

	/*
	 * @pram note Arrête de jouer la note dont le numéro est en paramètre
	 */

	public void noteOff(int note){

		canal.noteOff(note);

	}

	/*
	 *  
	 * @param instru  Set le type d'instrument dont le numéro MIDI est précisé en paramètre
	 */

	public void setPnogrameChange(int instru){

		canal.programChange(instru);

	}

}


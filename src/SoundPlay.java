import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlay {
	private float volume;
	private int delay;
	private String audioFilePath;
	private FloatControl gainControl;
	private Clip audioClip;
	public SoundPlay(String file ,int delay,float volume) {
		this.delay = delay;
		this.audioFilePath = file;
		this.volume = volume;
		
	}
	
	public float getVolume() {
		return volume;
	}
	public void setVolume(float valume) {
		this.volume = valume;
	}
	
	public synchronized void playSound() {

		new Thread(new Runnable() {
			public void run() {
				Boolean playCompleted = true;

				File audioFile = new File(audioFilePath);

				try {
					AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
					AudioFormat format = audioStream.getFormat();
					DataLine.Info info = new DataLine.Info(Clip.class, format);
					audioClip = (Clip) AudioSystem.getLine(info);
				    audioClip.open(audioStream);
					audioClip.start();

					while (playCompleted) {
						// wait for the playback completes
						try {
							Thread.sleep(delay);

						} catch (InterruptedException ex) {
							ex.printStackTrace();
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

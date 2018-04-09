import java.util.Timer;
import java.util.TimerTask;

public class ModeleDemo extends Modele {
	
	Timer t = new Timer();
	
	public void lancerJeu(){
		impactSound.volume = 0;
		super.lancerJeu();
		t.schedule(new TimerTask(){
			public void run(){
				lancerBalles();
				synchronized(balles){
					Balle bi = balles.get(0);
					if(bi.getdX() <= gameWidth/2)
						deplacerRaquette(bi.getIntX() - bi.getWidth());
					else
						deplacerRaquette(bi.getIntX() + 3 * bi.getWidth());
				}
			}
		}, 1000, 20);
	}
	
	public void suspendreJeu(){
		super.suspendreJeu();
		t.cancel();
	}
}

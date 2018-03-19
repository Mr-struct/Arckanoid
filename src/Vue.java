
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Vue extends JFrame {
	
	protected PanelGame panelGame;
	protected JPanel thisPanel;
	protected PanelMenu panelMenu;
	protected PanelSettings panelSettings;
	protected JPanel blackPanel;
	protected PanelLevelSelect panelLevelSelect;
	@SuppressWarnings("unused")
	protected Modele modele;
	
	public Vue(Modele modele) {
		
		super("Arkanoid");
		
		this.modele = modele;
		
		this.setLayout(new BorderLayout());
		
		this.setSize(modele.gameWidth,modele.gameHeight);
		
		panelMenu = new PanelMenu(this.getWidth(),this.getHeight(),modele);
		
		thisPanel = new JPanel();
		
		thisPanel.setLayout(new BorderLayout());
		
		thisPanel.add(panelMenu, BorderLayout.CENTER);
		
		this.getContentPane().add(thisPanel);
		
		this.setLocationRelativeTo(null);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		setVisible(true);
		
		this.setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH); 
		
		ControleurVue ctrl  = new ControleurVue (modele,this);
		
	}
		
}

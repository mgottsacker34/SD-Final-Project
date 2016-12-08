import java.awt.Color;

import javax.swing.JPanel;


//the game panel on which we will draw the true panels of the game
//it serves just as an interlayer between the frame and the mosaic 
//of panels the player will see, it comunicates with the statsPanel
//and the playPanel throwing them informations coming from the logic
//side of the game
public class GamePanel extends JPanel{
	
	private KeyboardController keyboardController;
	private StatsPanel statsPanel=new StatsPanel();
	private PlayPanel playPanel=new PlayPanel();
	@SuppressWarnings("unused")
	private Protagonist bb8;

	public GamePanel(){
		this.setRequestFocusEnabled(true);
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(null);
		this.setBackground(Color.BLACK);

		this.add(statsPanel);
		statsPanel.setLocation(0, 0);

		this.add(playPanel);
		playPanel.setLocation(0, StatsPanel.STATS_HEIGHT);
		
		
		keyboardController=new KeyboardController();
		this.addKeyListener(keyboardController);
	}
	
	public void addProtagonist(Protagonist bb8) {
		this.bb8=bb8;
		playPanel.addProtagonist(bb8);
//		statsPanel.addProtagonist(bb8);
	}
	
	public void repaintGame(){
		playPanel.repaint();
//		statsPanel.repaint();
	}
	
	
}
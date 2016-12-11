import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class StatsPanel extends JPanel{

	private BufferedImage life;
	private BufferedImage statsPanel;
	public static final int STATS_HEIGHT=50;
	private int lifeCount = 3;
	private Protagonist bb8;
	
	public StatsPanel(){
		this.setSize(GameFrame.WIDTH, STATS_HEIGHT);
		this.setBackground(Color.BLACK);
		this.setLayout(null);
		loadInformations();
	}
	
	public void decreaseLifeCount() {
		lifeCount--;
	}
	
	public void resetLives() {
		lifeCount = 3;
	}
	
	public int getLifeCount() {
		return lifeCount;
	}
	
	public boolean gameOver() {
		return (lifeCount == 0);
	}
	
	private void loadInformations() {
		try {
//			statsPanel=ImageIO.read(getClass().getResource("statsBar.png"));
			life=ImageIO.read(new File("bb8_0.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		
		g2.drawImage(statsPanel,0,0,GameFrame.WIDTH-5,STATS_HEIGHT,null);
		
		for(int i=0; i<lifeCount; i++) {
			g2.drawImage(life,144 + 60*i,4 , 32, 42,null);
		}
		
		g.setColor(Color.WHITE);
		g.drawString("Level: " + bb8.getLevel().getLevel(), 84, 24);
	}
	

	public void addProtagonist(Protagonist bb8) {
		this.bb8=bb8;
	}
	
	
}
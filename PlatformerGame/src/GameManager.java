import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameManager extends Thread{
	
	private static final int DELAY = 10;
	private boolean gameIsRunning = false;
	private Protagonist protagonist;
	private GamePanel gamePanel;
	private Tiles tiles;
	private int bulletDelay = 40;
	private int time = 0;
	
	JFrame youDied = new JFrame("You died :(");
	
	public GameManager(GamePanel gamePanel){
		
		this.tiles = new Tiles();
//		this.world.initializeStage(1);
		
		//initialize protagonist
		this.protagonist = new Protagonist();
		
		this.gamePanel = gamePanel;
		this.gamePanel.addProtagonist(protagonist);
		
		this.gameIsRunning = true;
		
	}
	
	public void run(){
		while(gameIsRunning){
			manageKeys();
			
			//updates protagonist movement if jumping
			
			protagonist.checkAscending();
			protagonist.checkDescending();
			protagonist.collisionChecker();
			
			manageKeys();
			
			gamePanel.repaintGame();
			
			try{
				Thread.sleep(DELAY);
			}catch (InterruptedException ie){
				ie.printStackTrace();
			}
			
			if (protagonist.isDead()) {
				gameIsRunning = false;
				JPanel panel = new JPanel();
				youDied.add(panel);
				JLabel gameOver = new JLabel("You have lost the game");
				panel.add(gameOver);
				youDied.setSize(350, 150);
				youDied.setVisible(true);
			}
		}
	}
	
	public void manageKeys(){
		//access keys being pressed
		HashSet<Integer> currentKeys = KeyboardController.getActiveKeys();
		
		//manage left/right direction
		if(currentKeys.contains(KeyEvent.VK_RIGHT)){
//			if (protagonist.canMove()) {	
				//move right
				protagonist.move(KeyEvent.VK_RIGHT);
//			}
		}else if(currentKeys.contains(KeyEvent.VK_LEFT)){
			//move left
			protagonist.move(KeyEvent.VK_LEFT);
		}else if(currentKeys.isEmpty() && !(protagonist.isJumping())){
			protagonist.stop();
		}
		
		if(currentKeys.contains(KeyEvent.VK_SPACE)){
			if(!protagonist.isJumping()){
				protagonist.jump();
			}
		}
		
		if(currentKeys.contains(KeyEvent.VK_S)) //will added for firing
		{
			if (time <= 0) {
		        protagonist.shoot();
		        time = bulletDelay;  // Reset the timer
		    }else{
		    	time--;
		    }
			
		}
		
//		if(currentKeys.contains(KeyEvent.VK_A)) //will added for firing
//		{
//			protagonist.genEnemy();
//		}
		
	}
}
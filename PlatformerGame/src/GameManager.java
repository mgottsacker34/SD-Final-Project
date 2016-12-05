import java.awt.event.KeyEvent;
import java.util.HashSet;

public class GameManager extends Thread{
	
	private static final int DELAY = 20;
	
	private boolean gameIsRunning = false;
	
	private Protagonist protagonist;
	
	private GamePanel gamePanel;
	
	private World world;
	
	public GameManager(GamePanel gamePanel){
		
		this.world = new World();
		this.world.initializeStage(1);
		
		//initialize protagonist
		this.protagonist = new Protagonist();
		
		this.gamePanel = gamePanel;
		this.gamePanel.addProtagonist(protagonist);
		
		this.gameIsRunning = true;
	}
	
	public void run(){
		while(gameIsRunning){
			//updates protagonist movement if jumping
			protagonist.checkJumpState();
			
			manageKeys();
			
			gamePanel.repaintGame();
			
			try{
				Thread.sleep(DELAY);
			}catch (InterruptedException ie){
				ie.printStackTrace();
			}
		}
	}
	
	public void manageKeys(){
		//access keys being pressed
		HashSet<Integer> currentKeys = KeyboardController.getActiveKeys();
		
		//manage left/right direction
		if(currentKeys.contains(KeyEvent.VK_RIGHT)){
			//move right
			protagonist.move(KeyEvent.VK_RIGHT);
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
	}
	
	
}

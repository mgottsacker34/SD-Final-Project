import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Protagonist {
	
	//instance variables
	private int last_direction = KeyEvent.VK_RIGHT;
	
	private static final int MOVE_COUNTER_THRESH = 6;	//max number of frames of animation
	private int moveCounter = 0;
	private Rectangle collisionBox;	//space occupied by protagonist
	private static final int DISPLACEMENT = 4;	//space covered by single step of protagonist
	private BufferedImage currentImage;
	
	private int PROTAGONIST_HEIGHT = 64;
	private int PROTAGONIST_WIDTH = 40;

	private int currentFrameNumber = 0;	
	
	private int currentX = 128;		//current horizontal start dist
	
	private int currentY = GameFrame.HEIGHT - PlayPanel.TERRAIN_HEIGHT - PROTAGONIST_HEIGHT - 300;
	
	private boolean jumping;
	
	private int jump_count = 0;
	
	private boolean moving;
	private boolean ascending;
	private boolean descending;
	
	private BufferedImage[] run_R;
	private BufferedImage[] run_L;

	public Protagonist(){
		//images for movement
		run_L = new BufferedImage[35];
		run_R = new BufferedImage[35];
		
		loadImages();
		
		currentImage = run_R[0];
		
		collisionBox = new Rectangle(currentX, currentY, PROTAGONIST_WIDTH, PROTAGONIST_HEIGHT);
		
	}

	private void loadImages(){
		for(int i = 0; i < 35; i++){
			try {
				run_R[i] = ImageIO.read(new File("bb8_" + (i) + ".png"));
				run_L[i] = ImageIO.read(new File("bb8_" + (i) + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void move(int direction){
		
		if(direction == KeyEvent.VK_LEFT){
			
			//update position
			currentX = currentX - DISPLACEMENT;
			
			//update bounding box position
			collisionBox.setLocation(currentX, currentY);
			
			//change the current frame in animation
			decrementFrameNumber();
			currentImage = run_L[currentFrameNumber];
			
			//set last direction
			last_direction = KeyEvent.VK_LEFT;
			
		}else if(direction == KeyEvent.VK_RIGHT){
			
			//update position
			currentX = currentX + DISPLACEMENT;
			
			collisionBox.setLocation(currentX, currentY);
			
			incrementFrameNumber();
			currentImage = run_R[currentFrameNumber];
			
			//set last direction
			last_direction = KeyEvent.VK_RIGHT;
			
		}else{
			return;
		}
		moveCounter++;
	}
	
	
	//set current frame when protagonist is moving. we have a total of 5 frames for 
    //each run direction. The variable moveCounter is incremented each time the gameManager
    //calls the move function on the Boy. So according to moveCounter we can choose the current
    //frame. The frame changes every MOVE_COUNTER_THRESH increments of the moveCounter variable.
    //In this case MOVE_COUNTER_THRESH is set to 5. The use of "6" instead of a variable is temporary
    //because I still don't know how many frames will be used in the final animation
	private void incrementFrameNumber(){
		currentFrameNumber++;
		currentFrameNumber %= 35;
		
	}
	
	private void decrementFrameNumber(){
		if(currentFrameNumber == 0){
			currentFrameNumber = 34;
		}else{
			currentFrameNumber--;
			currentFrameNumber %= 35;
		}
		
	}
	
	public void jump(){
		this.jumping = true;
		
		this.jump_count = 0;
		
		if(last_direction == KeyEvent.VK_RIGHT){
			currentImage = run_R[currentFrameNumber];
		}else{
			currentImage = run_L[currentFrameNumber];
		}
	}
	
	public void checkJumpState(){
		if(jumping){
			if(jump_count < 15){
				currentY -= 6;
				collisionBox.setLocation(currentX, currentY);
			}else{
				currentY += 6;
				collisionBox.setLocation(currentX, currentY);
			}
		}
		
		jump_count++;
		
		if(jump_count >= 30){
			jumping = false;
			jump_count = 0;
		}
	}
	
	public boolean isJumping(){
		return jumping;
	}
	
	public void stop(){
		currentImage = run_R[0];
	}
	
	public int getCurrentX(){
		return currentX;
	}
	
	public int getCurrentY(){
		return currentY;
	}
	
	public Rectangle getCollisionBox(){
		return collisionBox;
	}
	
	public BufferedImage getCurrentImage(){
		return currentImage;
	}
	
	public void collisionChecker () {
		int footPos = (int)collisionBox.getMaxY();
		
		int rowCurrent = (int)(currentY/Tile.TILE_SIZE);
		int rowUp = (int)((collisionBox.getMinY()-1)/Tile.TILE_SIZE);
		int rowDown = (int)((collisionBox.getMaxY()+1)/Tile.TILE_SIZE);
		int colCurrent = (int)(currentX/Tile.TILE_SIZE);
		int colRight = (int)((collisionBox.getMaxX()-1)/Tile.TILE_SIZE);
		
		// Check moving first
		if (moving) {
			
			if (World.tiledMap[rowCurrent][colRight].getCollisionBox().intersects(collisionBox)) {
				moving = false;
				return;
			} else {
				return;
			}
		}
			
		// If jumping, check for blocks above character's head
		// If touch, start descending
		if (ascending) {
			if (World.tiledMap[rowUp][colRight].getCollisionBox().intersects(collisionBox)) {
				ascending = false;
				descending = true;
			} else if (World.tiledMap[rowUp][colCurrent].getCollisionBox().intersects(collisionBox)) {
				ascending = false;
				descending = true;
			}
		} else if (descending) {
			if (World.tiledMap[rowDown][colRight].getCollisionBox().intersects(collisionBox)) {
				descending = false;
			} else if (World.tiledMap[rowDown][colCurrent].getCollisionBox().intersects(collisionBox)) {
				descending = false;
			}
		} 
	}
	
	public void checkDescending () {
		if (!descending) {
			if(collisionBox.getMaxY()/Tile.TILE_SIZE>=World.ROWS){
				die();
			}
			
			// Move character in downward direction
			currentY += DISPLACEMENT;
			collisionBox.setLocation(currentX, currentY);
			
			// Update different motions
			collisionChecker();
		}
	}
		
	public void die () {
		//Re-start game? Take away life? Create new map?
	}

}

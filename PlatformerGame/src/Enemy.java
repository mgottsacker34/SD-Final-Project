import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Enemy {

	Image img;
	public int x;
	public int y;
	public boolean see;
	private Rectangle collisionBox;

	
	public Enemy()
	{
		x = 1300;
		y = (int)(Math.random()*590+150); 
		ImageIcon l = new ImageIcon("TieFighter.png");
		img = l.getImage();
		see = true;
		collisionBox = new Rectangle(x, y, 60, 60);
	}
	

	
	public void move()
	{
		x = x - 3;
		collisionBox.setLocation(x, y);
		if (x < 0)
			see = false;
		
	}
	
	public Rectangle getCollisionBox(){
		return collisionBox;
	}
}

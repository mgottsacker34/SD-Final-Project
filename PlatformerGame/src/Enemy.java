import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Enemy {

	Image img;
	public int x;
	public int y;
	public boolean see;
	private Rectangle collisionBox;
	private Protagonist bb8;

	
	public Enemy(Protagonist target)
	{
		bb8 = target;
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
		if (x-bb8.getCurrentX() < 500)
		{
			if (bb8.getLevel().getLevel() > 1)
			{
				if (bb8.getCurrentY()>y)
				{
					y = y + 1;
				} else {
					y = y - 1;
				}
			}
			if (bb8.getLevel().getLevel() > 2)
			{
				if (bb8.getCurrentY()>y)
				{
					y = y + 2;
				} else {
					y = y - 2;
				}
			}
		}
		collisionBox.setLocation(x, y);
		if (x < 0)
			see = false;
		
	}
	
	public Rectangle getCollisionBox(){
		return collisionBox;
	}
}

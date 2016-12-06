import java.awt.*;

import javax.swing.ImageIcon;
public class Bullet {

	int x,y;
	Image img;
	boolean see = true;
	
	public Bullet(int beginX, int beginY)
	{
		x = beginX;
		y = beginY;
		ImageIcon aBullet = new ImageIcon("laserShot.png");
		img = aBullet.getImage();
		see = true;
	}
	
	public void move()
	{
		x = x + 13;
		if (x > GameFrame.WIDTH)
			see = false;
	}
}

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JPanel;

//PlayPanel - Is the panel where you see the actual game in motion,
//all the big part under the stats panel 
public class PlayPanel extends JPanel{
	
	//height of the terrain in pixels - this is basically the distance of the boy's feet 
	//from the bottom border of the window you play the game in
	public static final int TERRAIN_HEIGHT=192;
	
	//height of the PlayPanel 
	public static final int PLAY_PANEL_HEIGHT=1280;
	
	GridLayout grid = new GridLayout(20, 9);
	static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	static ArrayList<Platform> platforms = new ArrayList<Platform>();
	
	
	
	//reference to the protagonist of the game
	private Protagonist bb8;
	
	private Tiles tiles;

	public PlayPanel(){
		
//		super();
		
		tiles = new Tiles();
		
		//set the size of the play panel
		this.setSize(GameFrame.WIDTH, PLAY_PANEL_HEIGHT);
		
		//set a random background color to distinguish the play panel from the rest
		this.setBackground(Color.DARK_GRAY);
		
		//set layout
		this.setLayout(grid);
		
//		Scanner scanner = null;
//		
//		try {
//			scanner = new Scanner(new File("level1.txt"));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		
//		int row = 0;
//		
//		while(scanner.hasNextLine()){
//			String line = scanner.nextLine();
//			String[] words = line.split(" ");
//			
//			for(int col = 0; col < words.length; col++){
//				if(!(words[col].equals("empt"))){
//					tiles.setTile(new Block(words[col], row, col));
//					
//					System.out.print(words[col]);
//				}else{
////					tiles.setTile(new EmptyTile(row, col));
//					System.out.print(words[col]);
//					
//				}
//				
//				add(tiles.getTile(row, col));
////				System.out.println(" Added a tile ");
//			}
//			System.out.println();
//			row++;
//		}
		
//		for(int i = 0; i < grid.getRows(); i++){
//			for(int j = 0; j < grid.getColumns(); j++){
////				if()
//				//add(tiles.getTile(i, j));
//			}
//		}
		
		//double buffering should supposedly improve animations
		this.setDoubleBuffered(true);
		
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2=(Graphics2D)g;
//		if (bb8.getCurrentX()-590%2400 == 0) //590 is the x position when screen vanishes
//			bb8.nx = 0;
//		if (bb8.getCurrentX()-1790%2400 == 0) //1790 is the x where the new screen must be drawn
//			bb8.nx2 = 0;
		g2.drawImage(Tiles.BACKGROUND, bb8.getCurrentX()-1880, Tile.TILE_SIZE, GameFrame.WIDTH, PLAY_PANEL_HEIGHT, null);
		g2.drawImage(Tiles.BACKGROUND, bb8.getCurrentX()+680, Tile.TILE_SIZE, GameFrame.WIDTH, PLAY_PANEL_HEIGHT, null);
		//if (bb8.getCurrentX() >= 590) //bb8 is at point where emptiness shows on the next screen
		g2.drawImage(Tiles.BACKGROUND, bb8.getCurrentX()-600, Tile.TILE_SIZE, GameFrame.WIDTH, PLAY_PANEL_HEIGHT, null);
		
		for(int i = 0; i < Tiles.ROWS; i++){
			for(int j = 0; j < Tiles.COLS; j++){
				if(Tiles.tiles[i][j] != null){
					g2.drawImage(Tiles.tiles[i][j].getImage(), j*Tile.TILE_SIZE, i*Tile.TILE_SIZE, null);
				}
			}
		}
		
		//draw the protagonist of the game
//		if(!bb8.getRestoring()){
			g2.drawImage(bb8.getCurrentImage(), bb8.getCurrentX(), bb8.getCurrentY(), null);
			g2.draw(bb8.getCollisionBox());
//		}
			ArrayList<Bullet> bullets = bb8.getBullets(); //will added to add bullets
			for (int i = 0; i < bullets.size(); i++)
			{
				Bullet bull = (Bullet) bullets.get(i);
				if (bull.see == true){
					bull.move();
					g2.drawImage(bull.img, bull.x, bull.y, null);
					g2.draw(bull.getCollisionBox());
				}

				else
					bullets.remove(i);	
			}
			
			//Generates enemies randomly
			int random = (int)(Math.random()*50+1);
			if (random%50 == 0)	{
				Enemy enemy = new Enemy();
				enemies.add(enemy);
			}
			
			if (enemies.size() != 0)
			{	
				for (int j = 0; j < enemies.size(); j++)
				{
					boolean removal = false;
					Enemy enemy = (Enemy) enemies.get(j);
					if (enemy.see == true){
						enemy.move();
						g2.drawImage(enemy.img, enemy.x, enemy.y, null);
						g2.draw(enemy.getCollisionBox());
						if (enemies.get(j).getCollisionBox().intersects(bb8.getCollisionBox()))
						{
							bb8.died();
							return;
						}
						for(int r = 0; r < bullets.size(); r++)
						{
							if (!bb8.isDead()) 
							{
								if (enemies.get(j).getCollisionBox().intersects(bullets.get(r).getCollisionBox()))
								{
//									enemies.remove(j);
									removal = true;
									bullets.remove(r);
								}
							}
						}
						if (removal)
						{
							enemies.remove(j);
						}
					}
					else
						enemies.remove(j);	
				}
			}
			
			
			
//			int randomBlock = (int)(Math.random()*50+1);
//			if (randomBlock%50 == 0)	{
//				Platform platform = new Platform();
//				platforms.add(platform);
//			}
//			
//			for(int i = 0; i < platforms.size(); i++){
//				Platform platform = (Platform) platforms.get(i);
//				platform.move();
//				if (platform.see == true) {
//					g2.drawImage(platform.img, platform.x, platform.y, null);
//					g2.draw(platform.getCollisionBox());
//				}
//			}
			
			
			
	}
	
	//function called by the GameManager to add the boy (protagonist) to the play panel at runtime
	//the PlayPanel needs a reference to the boy since he's drawn a LOT of times 
	public void addProtagonist(Protagonist bb8) {
		this.bb8=bb8;
	}
}
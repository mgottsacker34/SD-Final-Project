
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;

import javax.swing.JPanel;

//PlayPanel - Is the panel where you see the actual game in motion,
//all the big part under the stats panel 
public class PlayPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	public PlayPanel(){
		
		//set the size of the play panel
		this.setSize(GameFrame.WIDTH, PLAY_PANEL_HEIGHT);
		
		//set a random background color to distinguish the play panel from the rest
		this.setBackground(Color.DARK_GRAY);
		
		//set no layouts
		this.setLayout(null);
		
		//double buffering should supposedly improve animations
		//read more about how double buffer works at http://www.anandtech.com/show/2794/2
		this.setDoubleBuffered(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2=(Graphics2D)g;
		
		g2.drawImage(World.CURRENT_BACKGROUND, 0, Tile.TILE_SIZE, GameFrame.WIDTH, PLAY_PANEL_HEIGHT, null);
		
//		for(int i=0; i<World.ROWS; i++){
//			for(int j=0; j<World.COLS; j++){
//				if(World.tiledMap[i][j] != null){
//					g2.drawImage(World.tiledMap[i][j].getImage(), j*Tile.TILE_SIZE, i*Tile.TILE_SIZE, null);
//				}
//			}
//		}
		
		//draw the protagonist of the game
//		if(!bb8.getRestoring()){
			g2.drawImage(bb8.getCurrentImage(), bb8.getCurrentX(), bb8.getCurrentY(), null);
			g2.draw(bb8.getCollisionBox());
//		}
	}
	
	//function called by the GameManager to add the boy (protagonist) to the play panel at runtime
	//the PlayPanel needs a reference to the boy since he's drawn a LOT of times 
	public void addProtagonist(Protagonist bb8) {
		this.bb8=bb8;
	}
	
	//height of the terrain in pixels - this is basically the distance of the boy's feet 
	//from the bottom border of the window you play the game in
	public static final int TERRAIN_HEIGHT=192;
	
	//height of the PlayPanel 
	public static final int PLAY_PANEL_HEIGHT=640;
	
	//reference to the protagonist of the game
	private Protagonist bb8;
}

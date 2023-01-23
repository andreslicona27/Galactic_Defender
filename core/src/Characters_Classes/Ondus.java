package Characters_Classes;

import com.badlogic.gdx.graphics.Texture;

public class Ondus extends Character{
	// PROPERTIES
	private static int lives = 3;
	private int pointsThatGive;
	private int apeaedAt;
	
	// BUILDER
	public Ondus(Texture image, String name, int positionX, int positionY, int height, int width,
			boolean alive, int numLives) {
		super(image, name, positionX, positionY, height, width, alive, lives);
		
		this.pointsThatGive = 15;
		this.apeaedAt = 200;
	}
	
	
	// FUNCITONS 
		@Override
		public void attack() {
			
		}
}

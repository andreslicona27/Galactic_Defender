package Characters_Classes;

import com.badlogic.gdx.graphics.Texture;

public class Ylgux extends Character{
	//	PROPERTIES
	private static int lives;
	private int pointsThatGive;
	private int apeaedAt;
	
	// BUILDER
	public Ylgux(Texture image, String name, int positionX, int positionY, int height, int width, String attack,
			boolean alive, int numLives) {
		super(image, name, positionX, positionY, height, width, alive, lives);
		
		this.lives = 2;
		this.pointsThatGive = 25;
		this.apeaedAt = 600;
	}
	
	// FUNCITONS 
		@Override
		public void attack() {
			
		}
}

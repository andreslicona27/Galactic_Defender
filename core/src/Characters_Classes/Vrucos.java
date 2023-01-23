package Characters_Classes;

import com.badlogic.gdx.graphics.Texture;

public class Vrucos extends Character{
	// PROPERTIES
	private static int lives = 2;
	private int pointsThatGive;
	private int apeaedAt;

	// BUILDER
	public Vrucos(Texture image, String name, int positionX, int positionY, int height, int width, String attack,
			boolean alive,int numLives) {
		super(image, name, positionX, positionY, height, width, alive, lives);
		
		this.pointsThatGive = 20;
		this.apeaedAt = 400;
	}

	// FUNCITONS 
		@Override
		public void attack() {
			
		}
}

package Characters_Classes;

import com.badlogic.gdx.graphics.Texture;

public class Qels extends Character{
	// PROPERTIES
	private static int lives = 1;
	private int pointsThatGive;
	private int apeaedAt;
	
	// BUILDER
	public Qels(Texture image, String name, int positionX, int positionY, int height, int width,
			boolean alive, int numLives) {
		super(image, name, positionX, positionY, height, width, alive, lives);
		
		this.pointsThatGive = 30;
		this.apeaedAt = 1000;
	}
	
	// FUNCITONS 
	@Override
	public void attack() {
		
	}

}

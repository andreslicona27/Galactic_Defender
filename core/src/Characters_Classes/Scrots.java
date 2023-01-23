package Characters_Classes;

import com.badlogic.gdx.graphics.Texture;

public class Scrots extends Character{
	// PROPERTIES
	private static int lives = 1;
	private int pointsThatGive;
	private int apeaedAt;
		
	// BUILDER
	public Scrots(Texture image, String name, int positionX, int positionY, int height, int width,
			boolean alive, int numLives) {
		super(image, name, positionX, positionY, height, width, alive, lives);
		
		this.pointsThatGive = 10;
		this.apeaedAt = 0;
	}

}

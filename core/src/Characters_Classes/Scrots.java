package Characters_Classes;

import com.badlogic.gdx.graphics.Texture;

public class Scrots extends Character{
	private int lives;
	// PROPERTIES
	private int pointsThatGive;
	private int apeaedAt;
		
	// BUILDER
	public Scrots(Texture image, String name, int positionX, int positionY, int height, int width, String attack,
			boolean alive) {
		super(image, name, positionX, positionY, height, width, attack, alive);
		
		this.lives = 1;
		this.pointsThatGive = 10;
		this.apeaedAt = 0;
	}

}

package Characters_Classes;

import com.badlogic.gdx.graphics.Texture;

public class Qels extends Character{
	// PROPERTIES
	private int lives;
	private int pointsThatGive;
	private int apeaedAt;
	
	// BUILDER
	public Qels(Texture image, String name, int positionX, int positionY, int height, int width, String attack,
			boolean alive) {
		super(image, name, positionX, positionY, height, width, attack, alive);
		
		this.lives = 1;
		this.pointsThatGive = 30;
		this.apeaedAt = 1000;
	}

}

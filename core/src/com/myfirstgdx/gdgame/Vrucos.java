package com.myfirstgdx.gdgame;

import com.badlogic.gdx.graphics.Texture;

public class Vrucos extends Character{
	// PROPERTIES
	private int lives;
	private int pointsThatGive;
	private int apeaedAt;

	// BUILDER
	public Vrucos(Texture image, String name, int positionX, int positionY, int height, int width, String attack,
			boolean alive) {
		super(image, name, positionX, positionY, height, width, attack, alive);
		
		this.lives = 2;
		this.pointsThatGive = 20;
		this.apeaedAt = 400;
	}

}

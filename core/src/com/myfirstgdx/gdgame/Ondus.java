package com.myfirstgdx.gdgame;

import com.badlogic.gdx.graphics.Texture;

public class Ondus extends Character{
	// PROPERTIES
	private int lives;
	private int pointsThatGive;
	private int apeaedAt;
	
	// BUILDER
	public Ondus(Texture image, String name, int positionX, int positionY, int height, int width, String attack,
			boolean alive) {
		super(image, name, positionX, positionY, height, width, attack, alive);
		
		this.lives = 3;
		this.pointsThatGive = 15;
		this.apeaedAt = 200;
	}

}

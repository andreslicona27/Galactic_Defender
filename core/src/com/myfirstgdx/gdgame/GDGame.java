package com.myfirstgdx.gdgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GDGame extends ApplicationAdapter{
	SpriteBatch batch;
	Texture rockete;
	Texture gameBG;
	Sprite spriteGameBG;
	int cont;

	public void StartGame() {
		this.cont = 0;
	}

	// It runs ones, when it create the project
	@Override
	public void create() {		
		// ITs draws the background of the game
		this.gameBG = new Texture("Backgrounds/gameBG.jpg");
		this.spriteGameBG = new Sprite(gameBG);
		spriteGameBG.setSize(Gdx.graphics.getHeight(),Gdx.graphics.getWidth());
		spriteGameBG.setPosition(0,0);

		this.batch = new SpriteBatch();
		this.rockete = new Texture("Characters/rocket.png");
	}

	// It runs a lot of times
	@Override
	public void render() {
		this.batch.begin();
		this.spriteGameBG.draw(batch);
		this.batch.draw(this.rockete, 100, 100);
		this.batch.end();

	}

	// Runs only when the screen is closed
	@Override
	public void dispose() {
		super.dispose();
	}

	private void print(String text) {
		System.out.println(text);
	}
	
	public void resize(int width, int height) {
	    spriteGameBG.setSize(width, height);
	    spriteGameBG.setPosition(0, 0);
	}

}

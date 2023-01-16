package com.myfirstgdx.gdgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GDGame extends Game {
	SpriteBatch Batch;
	SpriteBatch spriteGameBG;
	Texture rockete;
	Texture gameBG;
	int cont;
	
	public void StartGame() {
		this.cont = 0;
	}
	
	// Se ejecuta cada ves que se crea la pantalla
	@Override
	public void create() {
		this.Batch = new SpriteBatch();
		this.rockete = new Texture("Characters/rocket.png");
		this.spriteGameBG = new SpriteBatch();
		this.gameBG = new Texture("Backgrounds/gameBG.jpg");
		
	}
	
	// Se ejecuta varias veces
	@Override
	public void render() {
		 this.Batch.begin();
		 this.Batch.draw(this.rockete, 100,100);
		 // this.Batch.draw(spriteGameBG, 0. 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		 this.Batch.end();
		
	}
	
	// se ejecuta cada vez que se cierra la pantalla
	@Override 
	public void dispose() {
		super.dispose();
	}
	
	private void print(String text) {
		System.out.println(text);
	}
	
}

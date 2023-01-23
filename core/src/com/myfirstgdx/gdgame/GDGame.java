package com.myfirstgdx.gdgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import Utilities.JoyStick;

public class GDGame extends Game{
	// ROCKET PROPERTIES
	SpriteBatch batch;
	Texture rocket;
	
	// BACKGROUND PROPERTIES
	Texture gameBG;
	Sprite spriteGameBG;

	// JOY STICK PROPERTIES
	OrthographicCamera camera;
	JoyStick joystick;
	ShapeRenderer sr;
	Vector3 mouse;
	float positionX;
	float positionY;
	
	// MUSIC PROPERTIES
	private Music bgMusic;


	public void StartGame() {
		
	}

	// It runs ones, when it create the project
	@Override
	public void create() {
		Gdx.graphics.setForegroundFPS(100);
		// Addition of the music 
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("Audio/BackGround.mp3"));
		
		// ITs draws the background of the game
		this.gameBG = new Texture("Backgrounds/gameBG.jpg");
		this.spriteGameBG = new Sprite(gameBG);
		spriteGameBG.setSize(Gdx.graphics.getHeight(), Gdx.graphics.getWidth());
		spriteGameBG.setPosition(0, 0);

		// creation of the joy stick
		camera = new OrthographicCamera(1000, 500);
		camera.setToOrtho(false, 500, 250);

		joystick = new JoyStick(40, 40, 25);
		sr = new ShapeRenderer();
		sr.setProjectionMatrix(camera.combined);

		mouse = new Vector3();

		// Creation of the rocket
		this.batch = new SpriteBatch();
		this.rocket = new Texture("Characters/rocket.png");
		this.positionX = Gdx.graphics.getHeight() / 2;
		this.positionY = Gdx.graphics.getWidth() / 2;
	}

	// It runs a lot of times
	@Override
	public void render() {
		// Joy Stick movement code
		update();
		ScreenUtils.clear(0,0,0,1);
		
		this.batch.begin();
		
		// Music and background code
		bgMusic.play();
		bgMusic.setLooping(true);
		this.spriteGameBG.draw(batch);
		
		// rocket code 
		this.batch.draw(this.rocket, positionX, positionY);
		
		this.batch.end();

		// Joy stick code
		joystick.render(sr);

	}

	// Runs only when the screen is closed
	@Override
	public void dispose() {
		super.dispose();
		sr.dispose();
		bgMusic.dispose();
	}

	public void update() {
		// Joy stick code, for it to update
		camera.unproject(mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0));
		joystick.update(mouse.x, mouse.y);

		// Code for the rocket to move around the screen
		switch (joystick.getState()) {
		case 1:
			positionX += 1;
			break;
		case 2:
			positionY += 1;
			break;
		case 3:
			positionX -= 1;
			break;
		case 4:
			positionY -= 1;
			break;
		}
	}
	

	public void resize(int width, int height) {
		spriteGameBG.setSize(width, height);
		spriteGameBG.setPosition(0, 0);
	}

}

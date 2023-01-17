package com.myfirstgdx.gdgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import Utilities.JoyStick;

public class GDGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture rocket;
	Texture gameBG;
	Sprite spriteGameBG;

	OrthographicCamera camera;
	JoyStick joystick;
	ShapeRenderer sr;
	Vector3 mouse;
	float positionX;
	float positionY;

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

	// It runs a lot of times
	@Override
	public void render() {
		update();
		ScreenUtils.clear(0,0,0,1);
		this.batch.begin();
		// this.spriteGameBG.draw(batch);
		this.batch.draw(this.rocket, positionX, positionY);
		this.batch.end();

		// Joystick code
		joystick.render(sr);

	}

	// Runs only when the screen is closed
	@Override
	public void dispose() {
		super.dispose();
		sr.dispose();
	}

	private void print(String text) {
		System.out.println(text);
	}

	public void resize(int width, int height) {
		spriteGameBG.setSize(width, height);
		spriteGameBG.setPosition(0, 0);
	}

}

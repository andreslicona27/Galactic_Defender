package com.myfirstgdx.gdgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

import Characters_Classes.Scrots;
import Utilities.JoyStick;
import Utilities.Laser;

public class GDGame extends Game{
	// ROCKET PROPERTIES
	SpriteBatch rocketBatch;
	Texture rocket;
	float positionX;
	float positionY;
	
	// ALIENS PROPERTIES
	SpriteBatch alienBatch;
	Texture scrotImage;
	Scrots scrot;
	
	// BACKGROUND PROPERTIES
	Stage stage;

	// JOY STICK PROPERTIES
	OrthographicCamera cameraJoyStick;
	ShapeRenderer srJoyStick;
	JoyStick joystick;
	Vector3 mouse;
	
	// BUTTON PROPERTIES
	OrthographicCamera cameraButton;
	ShapeRenderer srButton;
	Laser laserButton;
	
	// MUSIC PROPERTIES
	private Music bgMusic;
	
	// UTILITIES PROPERTIRD
	float randomPositionX;
	float randomPositionY;

	public void StartGame() {
		
	}

	// It runs ones, when it create the project
	@Override
	public void create() {
		// Utilities code
		//Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		Gdx.graphics.setForegroundFPS(100);
		randomPositionX =  (float)(Math.random()*Gdx.graphics.getWidth()+1);
		randomPositionY =  (float)(Math.random()*Gdx.graphics.getHeight()+1);
		
		// Addition of the music 
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("Audio/BackGround.mp3"));
		
		// ITs draws the background of the game
		JoyStickCreation();
		ButtonCreation();

		// Creation of the rocket
		this.rocketBatch = new SpriteBatch();
		this.rocket = new Texture("Characters/Rocket/rocketU.png");
		this.positionX = Gdx.graphics.getWidth() / 2 - this.rocket.getWidth() / 2;
		this.positionY = Gdx.graphics.getHeight() / 2 - this.rocket.getHeight() / 2;
		
		// Creation of alien 
		this.alienBatch = new SpriteBatch(); 
		scrotImage = new Texture("Characters/aliens/scrot1.png");
		this.scrot = new Scrots(scrotImage, "scrot1", randomPositionX, randomPositionY, 50, 50, true, 1);
	
	}

	// It runs a lot of times
	@Override
	public void render() {
		// Utilities code 
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // For it to clean the screen while drawing the new position 
		
		// Joy Stick movement code
		update();
		joystick.render(srJoyStick);
		laserButton.render(srButton);
		
		// Music and background code
		bgMusic.play();
		bgMusic.setLooping(true);
//		this.stage.draw();
		
		// Characters code 
		this.rocketBatch.begin();
		this.rocketBatch.draw(this.rocket, positionX, positionY);
		this.rocketBatch.end();
		
		this.alienBatch.begin();
		this.alienBatch.draw(this.scrot.getImage(),this.scrot.getPositionX(),this.scrot.getPositionY());
		this.alienBatch.end();
		

	}

	// Runs only when the screen is closed
	@Override
	public void dispose() {
		super.dispose();
		srJoyStick.dispose();
		srButton.dispose();
		bgMusic.dispose();
	}

	/*
	 * Function that updates the movement or the joy stick by getting its state and implementing that movement to the rocket
	 * */
	public void update() {
		// Joy stick code, for it to update
		cameraJoyStick.unproject(mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0));
		joystick.update(mouse.x, mouse.y);

		// Code for the rocket to move around the screen
		switch (joystick.getState()) {
		case 1:
			if(positionX < Gdx.graphics.getWidth()) {
				positionX += 1;
			}
			this.rocket = new Texture("Characters/Rocket/rocketR.png");
			break;
		case 2:
			if(positionY < Gdx.graphics.getHeight() ) {
				positionY += 1;
			}
			this.rocket = new Texture("Characters/Rocket/rocketU.png");
			break;
		case 3:
			if(positionX < Gdx.graphics.getHeight()) {
				positionX -= 1;
			}
			this.rocket = new Texture("Characters/Rocket/rocketL.png");
			break;
		case 4:
			if(positionY < Gdx.graphics.getHeight()) {
				positionY -= 1;
			}
			this.rocket = new Texture("Characters/Rocket/rocketD.png");
			break;
		}
	}
	

	/* 
	 * Function that instance all the needed code for the joy stick
	 * */
	public void JoyStickCreation() {
		cameraJoyStick = new OrthographicCamera(1000, 1000);
		cameraJoyStick.setToOrtho(false, 500, 250);

		joystick = new JoyStick(30, 30, 20);
		srJoyStick = new ShapeRenderer();
		srJoyStick.setProjectionMatrix(cameraJoyStick.combined);

		mouse = new Vector3();
	}
	
	public void ButtonCreation() {
		cameraButton = new OrthographicCamera(1000, 1000);
		cameraButton.setToOrtho(false, 500, 250);
		
		laserButton = new Laser(450, 30,20);
		srButton = new ShapeRenderer();
		srButton.setProjectionMatrix(cameraButton.combined);
		System.out.println(Gdx.graphics.getWidth());
	}


}

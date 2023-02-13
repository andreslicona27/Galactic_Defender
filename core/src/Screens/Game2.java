package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.myfirstgdx.gdgame.GDGame;

import Utilities.Assets;
import Utilities.JoyStick;
import Utilities.Laser;

public class Game2 extends Screens {
	// WORLD PROPERTIES
	World world;
	Box2DDebugRenderer renderer;

	// BACKGROUND PROPERTIES
	Stage stage;

	// ROCKET PROPERTIES
	SpriteBatch rocketBatch;
	Texture rocket;
	float positionX;
	float positionY;

	// ALIENS PROPERTIES
	SpriteBatch alienBatch;
	Texture scrotImage;

	// JOY STICK PROPERTIES
	OrthographicCamera cameraJoyStick;
	ShapeRenderer srJoyStick;
	JoyStick joystick;
	Vector3 mouse;

	// BUTTON PROPERTIES
	OrthographicCamera cameraButton;
	ShapeRenderer srButton;
	Laser laserButton;

	// UTILITIES PROPERTIES
	float randomPositionX;
	float randomPositionY;

	public Game2(final GDGame game) {
		super(game);
		Vector2 gravity = new Vector2(0, 0);
		world = new World(gravity, true);
		renderer = new Box2DDebugRenderer();

		// Joy Stick and fire button creation
		JoyStickCreation();
		joystick.drawShapeRenderer(srJoyStick);
		ButtonCreation();
		laserButton.drawShapeRenderer(srButton);

		randomPositionX = (float) (Math.random() * Gdx.graphics.getWidth() + 1);
		randomPositionY = (float) (Math.random() * Gdx.graphics.getHeight() + 1);

		// Rocket and alien creation
		this.rocket = new Texture("Characters/Rocket/rocketU.png");
		scrotImage = new Texture("Characters/aliens/scrot1.png");
	}

	@Override
	public void draw(float delta) {
		// World code
		oCamUI.update();
		spriteBatch.setProjectionMatrix(oCamUI.combined);
		
		spriteBatch.begin();
		Assets.font.draw(spriteBatch, "Fps" + Gdx.graphics.getFramesPerSecond(), 0, 20);
		spriteBatch.end();
		
		oCamBox2D.update();
		renderer.render(world, oCamBox2D.combined);
		
		// Rocket creation
		rocketBatch = new SpriteBatch();
		this.positionX = Gdx.graphics.getWidth() / 2 - this.rocket.getWidth() / 2;
		this.positionY = Gdx.graphics.getHeight() / 2 - this.rocket.getHeight() / 2;

		rocketBatch.begin();
		rocketBatch.draw(this.rocket, positionX, positionY);
		rocketBatch.end();

		// Alien creation
		alienBatch = new SpriteBatch();

		alienBatch.begin();
		alienBatch.draw(this.scrotImage, randomPositionX, randomPositionY);
		alienBatch.end();
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		updateJoyStick();
		joystick.drawShapeRenderer(srJoyStick);
		laserButton.drawShapeRenderer(srButton);
	}

	@Override
	public void update(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.step(delta, 8, 6);
	}

	/*
	 * Function that updates the movement or the joy stick by getting its state and
	 * implementing that movement to the rocket
	 */
	public void updateJoyStick() {
		// Joy stick code, for it to update
		cameraJoyStick.unproject(mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0));
		joystick.update(mouse.x, mouse.y);

		// Code for the rocket to move around the screen
		switch (joystick.getState()) {
		case 1:
			if (positionX < Gdx.graphics.getWidth()) {
				positionX += 1;
			}
			this.rocket = new Texture("Characters/Rocket/rocketR.png");
			break;
		case 2:
			if (positionY < Gdx.graphics.getHeight()) {
				positionY += 1;
			}
			this.rocket = new Texture("Characters/Rocket/rocketU.png");
			break;
		case 3:
			if (positionX < Gdx.graphics.getHeight()) {
				positionX -= 1;
			}
			this.rocket = new Texture("Characters/Rocket/rocketL.png");
			break;
		case 4:
			if (positionY < Gdx.graphics.getHeight()) {
				positionY -= 1;
			}
			this.rocket = new Texture("Characters/Rocket/rocketD.png");
			break;
		}
	}

	/*
	 * Function that instance all the needed code for the joy stick
	 */
	public void JoyStickCreation() {
		cameraJoyStick = new OrthographicCamera(1000, 1000);
		cameraJoyStick.setToOrtho(false, 500, 250);

		joystick = new JoyStick(30, 30, 20);
		srJoyStick = new ShapeRenderer();
		srJoyStick.setProjectionMatrix(cameraJoyStick.combined);

		mouse = new Vector3();
	}

	/*
	 * Function that instances all the needed code for the fire button
	 */
	public void ButtonCreation() {
		cameraButton = new OrthographicCamera(1000, 1000);
		cameraButton.setToOrtho(false, 500, 250);

		laserButton = new Laser(450, 30, 20);
		srButton = new ShapeRenderer();
		srButton.setProjectionMatrix(cameraButton.combined);
	}

}

package Screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.myfirstgdx.gdgame.GDGame;

import Characters_Classes.Character;
import Characters_Classes.EnemyGroup;
import Characters_Classes.Rocket;
import Utilities.JoyStick;
import Utilities.Laser;

public class Game extends Screens {
	// WORLD PROPERTIES
	World world;
	Box2DDebugRenderer renderer;

	// BACKGROUND PROPERTIES

	// CHARACTERS PROPERTIES
	ArrayList<Character> enemies = new ArrayList<Character>();
	Group group = new Group();
	Rocket rocket;
	Character alien;
	Texture rocketImage;
	float positionX;
	float positionY;
	Vector2 rocketPosition;

	EnemyGroup enemies2;
	Texture scrotImage;
	float randomPositionX;
	float randomPositionY;

	// GAME PROPERTIES
	Timer timer;
	int points;

	// JOY STICK PROPERTIES
	OrthographicCamera cameraJoyStick;
	ShapeRenderer srJoyStick;
	JoyStick joystick;
	Vector3 mouse;

	// BUTTON PROPERTIES
	Laser laser;
	TextureRegionDrawable buttonImage;
	ImageButton fireButton;

	public Game(final GDGame game) {
		super(game);
		Vector2 gravity = new Vector2(0, 0);
		world = new World(gravity, true);
		renderer = new Box2DDebugRenderer();

		// Joy Stick and fire button creation
		JoyStickCreation();
		joystick.drawShapeRenderer(srJoyStick);
//		ButtonCreation();
//		laserButton.drawShapeRenderer(srButton);

		// Rocket and alien creation
		rocketImage = new Texture("Characters/Rocket/rocketU.png");
//		positionX = Gdx.graphics.getWidth() / 2 - rocketImage.getWidth() / 2;
//		positionY = Gdx.graphics.getHeight() / 2 - rocketImage.getHeight() / 2;
		rocketPosition = new Vector2(positionX, positionY);

		rocket = new Rocket(new Texture("Characters/Rocket/rocketU.png"), "rocket",
				Gdx.graphics.getWidth() / 2 - rocketImage.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - rocketImage.getHeight() / 2, true, 1);

		timer = new Timer();
		timer.scheduleTask(new Timer.Task() {
			@Override
			public void run() {
				EnemiesCreation();
//				enemies2 = new EnemyGroup();
			}
		}, 1, 1);

	}

	@Override
	public void draw(float delta) {
		// World code
		oCamUI.update();
		spriteBatch.setProjectionMatrix(oCamUI.combined);

		oCamBox2D.update();
		renderer.render(world, oCamBox2D.combined);

		// Character creation
		spriteBatch = new SpriteBatch();
		DrawRocket();
		DrawAliens();

		stage.act();
		stage.draw();

	}

	@Override
	public void render(float delta) {
		super.render(delta);
		updateJoyStick();
		joystick.drawShapeRenderer(srJoyStick);
		DrawAliens();
//		
//		rocketPosition.x = rocket.getX();
//		rocketPosition.y = rocket.getY();
//		enemies2.setPlayerPosition(rocketPosition);

//		// Actualiza la nave y los láseres
//		float delta2 = Gdx.graphics.getDeltaTime();
//		rocket.update(delta);
//
//		// Dibuja la nave y los láseres
//		SpriteBatch batch = (SpriteBatch) stage.getBatch();
//		batch.begin();
//		DrawRocket();
//		rocket.DrawLasers(batch);
//		batch.end();
//
//		// Dibuja el Stage
//		stage.act(delta);
//		stage.draw();
	}

	@Override
	public void update(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.step(delta, 8, 6);
		updateJoyStick();

	}

	/**
	 *
	 * */
	public void EnemiesCreation() {
		randomPositionX = (float) (Math.random() * Gdx.graphics.getWidth() + 1);
		randomPositionY = (float) (Math.random() * Gdx.graphics.getHeight() + 1);

		Character alien = new Character(new Texture("Characters/aliens/scrot1.png"), "alien",
				MathUtils.random(Gdx.graphics.getWidth()), 
				MathUtils.random(Gdx.graphics.getHeight()), true, 1);
		enemies.add(alien);
		group.addActor(alien);
//
//		MoveToAction action = new MoveToAction();
//		action.setPosition(alien.getX(), alien.getY());
//		action.setDuration(1f);
//
//		alien.addAction(action);

		for (Actor actor : group.getChildren()) {
			if (actor instanceof Character) {
				alien = (Character) actor;

				// Configurar una acción de movimiento aleatorio
				MoveToAction action = new MoveToAction();
				action.setPosition(rocket.getPositionX(), rocket.getPositionY());
				action.setDuration(1f);
				alien.addAction(action);
			}
		}
	}

	public void DrawAliens() {
		for (Character alien : enemies) {
			spriteBatch.begin();
			spriteBatch.draw(alien.getImage(), alien.getPositionX(), alien.getPositionY());
			spriteBatch.end();
		}
	}

	public void DrawRocket() {
		spriteBatch.begin();
		spriteBatch.draw(rocket.getImage(), rocket.getPositionX(), rocket.getPositionY());
		spriteBatch.end();

		rocket.DrawLasers(spriteBatch);
	}

	// TODO if the character is alive and the area that covers
	// the character is the same as the one that covers an alien
	// the rocket explodes
	public void Colision(Character rocket) {
//		foreach(Character ch  enemies){

//		}
//		if(rocket.overlaps(rocket)) {

//		}

	}

	/**
	 * Updates the movement or the joy stick by getting its state (function in the
	 * joy stick class) and implementing that movement to the rocket by changing the
	 * image and moving the rocket
	 */
	public void updateJoyStick() {
		// Joy stick code, for it to update
		cameraJoyStick.unproject(mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0));
		joystick.update(mouse.x, mouse.y);

		// Code for the rocket to move around the screen
		switch (joystick.getState()) {
		case 1:
			if (positionX < Gdx.graphics.getWidth()) {
				rocket.setPositionX(rocket.getPositionX() + 1);
			}
			rocket.setImage(new Texture("Characters/Rocket/rocketR.png"));
			break;
		case 2:
			if (positionY < Gdx.graphics.getHeight()) {
				rocket.setPositionY(rocket.getPositionY() + 1);
			}
			rocket.setImage(new Texture("Characters/Rocket/rocketU.png"));
			break;
		case 3:
			if (positionX < Gdx.graphics.getHeight()) {
				rocket.setPositionX(rocket.getPositionX() - 1);
			}
			rocket.setImage(new Texture("Characters/Rocket/rocketL.png"));
			break;
		case 4:
			if (positionY < Gdx.graphics.getHeight()) {
				rocket.setPositionY(rocket.getPositionY() - 1);
			}
			rocket.setImage(new Texture("Characters/Rocket/rocketD.png"));
			break;
		}
	}

	/**
	 * Function that instance all the needed code for the joy stick Code like the
	 * camera, shape render and the projection matrix
	 */
	public void JoyStickCreation() {
		cameraJoyStick = new OrthographicCamera(1000, 1000);
		cameraJoyStick.setToOrtho(false, 500, 250);

		joystick = new JoyStick(30, 30, 20);
		srJoyStick = new ShapeRenderer();
		srJoyStick.setProjectionMatrix(cameraJoyStick.combined);

		mouse = new Vector3();
	}

	/**
	 * Function that instances all the needed code for the fire button Code like the
	 * camera, the shape render and the projection matrix
	 */
	public void ButtonCreation() {
		buttonImage = new TextureRegionDrawable(new TextureRegion(new Texture("Utilities/fireButton.png")));
		fireButton = new ImageButton(buttonImage);
		fireButton.setPosition(SCREEN_WIDTH - 100, 30);
		fireButton.setSize(60, 60);
		fireButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				rocket.Fire();
			}
		});
		stage.addActor(fireButton);
	}

}

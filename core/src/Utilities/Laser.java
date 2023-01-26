package Utilities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Laser extends Button {
	// PROPERTIES
	private Vector2 position;
	private Texture image;
	private boolean alive;
	Circle laserButton;
	Button laser = new Button();

	// BUILDER
	// additional code for trying if it work to have a button  
	public Laser() {
		super(new ButtonStyle());
		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// handle the button click here
			}
		});
	}
	
	public Laser(float x, float y, float radius) {
		laserButton = new Circle(x, y, radius);
	}

	public Laser(Vector2 initialPosition, Texture image) {
		this.position = initialPosition;
		this.image = image;
		this.alive = true;
	}

	
	// SETTERS / GETTERS
	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Texture getImage() {
		return image;
	}

	public void setImage(Texture image) {
		this.image = image;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	// FUNCTIONS
	public void updateLaser() {
		if (position.y + image.getHeight() < 0) {
			alive = false;
		}

		if (position.y > 600) {
			alive = false;
		}

		if (alive) {
			position.y -= 10;
		}
	}

	public void render(ShapeRenderer renderer) {
		renderer.setColor(Color.WHITE);

		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.circle(laserButton.x, laserButton.y, laserButton.radius);
		renderer.end();
	}

}

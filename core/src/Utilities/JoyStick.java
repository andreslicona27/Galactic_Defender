package Utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class JoyStick extends Actor {
	// PROPERTIES
	Circle externalCircle;
	Circle internalCircle;

	// BUILDER
	public JoyStick(float x, float y, float radius) {
		externalCircle = new Circle(x, y, radius);
		internalCircle = new Circle(x, y, radius / 2);
	}

	// FUNCTIONS
	public void update(float x, float y) {
		if (Gdx.input.isTouched()) {
			if (internalCircle.contains(x, y)) {
				internalCircle.setPosition(x, y);

				if (internalCircle.x > externalCircle.x + externalCircle.radius) {
					internalCircle.x = externalCircle.x + externalCircle.radius;
				}
				if (internalCircle.y > externalCircle.y + externalCircle.radius) {
					internalCircle.y = externalCircle.y + externalCircle.radius;
				}
				if (internalCircle.x < externalCircle.x - externalCircle.radius) {
					internalCircle.x = externalCircle.x - externalCircle.radius;
				}
				if (internalCircle.y < externalCircle.y - externalCircle.radius) {
					internalCircle.y = externalCircle.y - externalCircle.radius;
				}
			}
		} else {
			internalCircle.setPosition(externalCircle.x, externalCircle.y);
		}
	}

	/**
	 * Function that returns a int depending on the direction in which the internal circle
	 * is touching with the external circle
	 *
	 * @return 1 if its on the right, 2 it its up, 3 if its on the left and 4 if its down
	 * */
	public int getState() {

		if (internalCircle.x > externalCircle.x + 10) {
			return 1;
		} else if (internalCircle.y > externalCircle.y + 10) {
			return 2;
		} else if (internalCircle.x + 10 < externalCircle.x) {
			return 3;
		} else if (internalCircle.y + 10 < externalCircle.y) {
			return 4;
		} else {
			return 0;
		}
	}


	/**
	 * For it to draw the necessary code of the shaperender of the joystick
	 * */
	public void drawShapeRenderer(ShapeRenderer renderer) {
		renderer.setColor(Color.WHITE);

		renderer.begin(ShapeRenderer.ShapeType.Line);
		renderer.circle(externalCircle.x, externalCircle.y, externalCircle.radius);
		renderer.end();

		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.circle(internalCircle.x, internalCircle.y, internalCircle.radius);
		renderer.end();
	}

}

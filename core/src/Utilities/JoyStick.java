package Utilities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class JoyStick extends Actor{
	Circle externalCircle;
	Circle internalCircle;
	
	public JoyStick(float x, float y, float radius){
		externalCircle = new Circle(x, y, radius);
		internalCircle = new Circle(x, y, radius/2);
	}
	
	
	public void update(float x, float y) {
		if (Gdx.input.isTouched()) {
			if (internalCircle.contains(x, y)) {
				internalCircle.setPosition(x,y);
			
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
			internalCircle.setPosition(externalCircle.x,externalCircle.y);
		}
	}
	
	public int getState() {
		if (internalCircle.x > externalCircle.x) {
			return 1;
		}
		else if (internalCircle.y > externalCircle.y) {
			return 2;
		} 
		else if (internalCircle.x < externalCircle.x) {
			return 3;
		}
		else if (internalCircle.y < externalCircle.y) {
			return 4;
		}
		return 0;
	}
	
	public void render(ShapeRenderer renderer) {
		renderer.setColor(Color.WHITE);
		
		renderer.begin(ShapeRenderer.ShapeType.Line);
		renderer.circle(externalCircle.x, externalCircle.y, externalCircle.radius);
		renderer.end();
		
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.circle(internalCircle.x,  internalCircle.y,  internalCircle.radius);
		renderer.end();
	}
	
	
	
}

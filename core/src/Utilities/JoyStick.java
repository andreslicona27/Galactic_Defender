package Utilities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class JoyStick extends Actor{
	Circle circle;
	Circle circle2;
	
	public JoyStick(float x, float y, float radius){
		circle = new Circle(x, y, radius);
		circle2 = new Circle(x, y, radius/2);
	}
	
	
	public void update(float x, float y) {
		if (Gdx.input.isTouched()) {
			if (circle2.contains(x, y)) {
				circle2.setPosition(x,y);
			
				if (circle2.x > circle.x + circle.radius) {
					circle2.x = circle.x + circle.radius;
				} 
				if (circle2.y > circle.y + circle.radius) {
					circle2.y = circle.y + circle.radius;
				}
				if (circle2.x < circle.x - circle.radius) {
					circle2.x = circle.x - circle.radius;
				}
				if (circle2.y < circle.y - circle.radius) {
					circle2.y = circle.y - circle.radius;
				}
			}
		} else {
			circle2.setPosition(circle.x,circle.y);
		}
	}
	
	public int getState() {
		if (circle2.x > circle.x) {
			return 1;
		}
		else if (circle2.y > circle.y) {
			return 2;
		} 
		else if (circle2.x < circle.x) {
			return 3;
		}
		else if (circle2.y < circle.y) {
			return 4;
		}
		return 0;
	}
	
	public void render(ShapeRenderer renderer) {
		renderer.setColor(Color.GREEN);
		renderer.begin(ShapeRenderer.ShapeType.Line);
		renderer.circle(circle.x, circle.y, circle.radius);
		renderer.end();
		
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.circle(circle2.x,  circle2.y,  circle2.radius);
		renderer.end();
	}
	
	
	
}

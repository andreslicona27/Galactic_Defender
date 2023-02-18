package Characters_Classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class EnemyGroup extends Group {
	private Vector2 playerPosition = new Vector2();

	public EnemyGroup() {
		Character enemy = new Character(new Texture("Characters/aliens/scrot1.png"), "enemy",
				(float) (Math.random() * Gdx.graphics.getWidth() + 1),
				(float) (Math.random() * Gdx.graphics.getHeight() + 1), true, 1);
		addActor(enemy);
	}

	public void setPlayerPosition(Vector2 position) {
		this.playerPosition.set(position);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		for (Actor actor : getChildren()) {
			if (actor instanceof Character) {
				Character enemy = (Character) actor;

				// Calculate the direction to the ship
				Vector2 direction = playerPosition.cpy().sub(enemy.getX(), enemy.getY()).nor();

				// Move the enemy with the calculate direction
				float speed = 100; // Speed of the enemy
				enemy.moveBy(direction.x * speed * delta, direction.y * speed * delta);
			}
		}
	}

}

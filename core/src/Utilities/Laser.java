package Utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Laser {
	 private static final float VELOCIDAD_BALA = 500; // Speed of the laser in pixels per second
	    private TextureRegion buttonImage;
	    private Vector2 position;
	    private Vector2 speed;
	    

	    public Laser(float x, float y) {
	        this.buttonImage = new TextureRegion(new Texture("Utilities/Laser.png"));
	        this.position = new Vector2(x - buttonImage.getRegionWidth() / 2, y);
	        this.speed = new Vector2(0, VELOCIDAD_BALA);
	    }

	    public void UpdateLaser(float delta) {
	        position.mulAdd(speed, delta);
	    }

	    public void DrawLaser(SpriteBatch batch) {
	    	batch.begin();
	        batch.draw(buttonImage, position.x, position.y);
	        batch.end();
	    }

	    public Vector2 getPosicion() {
	        return position;
	    }
}

package Utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Background extends Actor{

	  	private Texture texture;
	    private SpriteBatch batch;
	    
	    public Background(Texture texture) {
	        this.texture = texture;
	        batch = new SpriteBatch();
	    }
	    
	    @Override
	    public void draw(Batch batch, float parentAlpha) {
	        batch.draw(texture, 0, 0);
	    }
}

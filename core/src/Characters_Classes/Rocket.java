package Characters_Classes;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Utilities.Laser;

public class Rocket extends Character{
	private ArrayList<Laser> lasers;
	
	// BUILDER
	public Rocket(Texture image, String name, float positionX, float positionY, boolean alive, int numLives) {
		super(image, name, positionX, positionY, alive, numLives);
		 this.lasers = new ArrayList<Laser>();
	}
	
	// FUNCTIONS 
	 public void update(float delta) {
	        // updates the laser
	        for (Laser l : lasers) {
	            l.UpdateLaser(delta);
	        }

	        for (int i = lasers.size() - 1; i >= 0; i--) {
	            Laser l = lasers.get(i);
	            if (l.getPosicion().y > Gdx.graphics.getHeight()) {
	                lasers.remove(lasers.get(i));
	            }
	        }
	    }

	    public void DrawLasers(SpriteBatch batch) {
	        for (Laser l : lasers) {
	            l.DrawLaser(batch);
	        }
	    }

	    public void Fire() {
	        Laser l = new Laser(getX() + getWidth() / 2, getY() + getHeight());
	        lasers.add(l);
	    }
	
	

}

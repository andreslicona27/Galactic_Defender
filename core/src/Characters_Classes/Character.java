package Characters_Classes;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Character extends Actor{
	 // PROPERTIES
	private Texture image;
	private String name;
    private float positionX;
    private float positionY;
    private boolean alive;
    private int numLives;
    
    // BUILDER
    public Character(Texture image, String name, float positionX, float positionY, boolean alive, int numLives) {
        this.image = image;
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
        this.alive = true;
        this.numLives = numLives;
    }
    
    // GETTER AND SETTERS
    public Texture getImage() {
		return image;
	}

	public void setImage(Texture image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPositionX() {
		return positionX;
	}

	public void setPositionX(float positionX) {
		this.positionX = positionX;
	}

	public float getPositionY() {
		return positionY;
	}
	
	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public int getNumLives() {
		return numLives;
	}

	public void setNumLives(int numLives) {
		this.numLives = numLives;
	}


	
    // FUNCTIONS
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(image, getX(), getY());
    }
    
    @Override
    public void act(float delta) {
        super.act(delta);
    }
    
    
    /** Function that would be resting on life of the characters
     * If the lives are less then 0 it change the boolean to false 
     * so the character its dead 
     * */
    public void dead(Character character) {
    	this.numLives--;
    	if(this.numLives < 0) {
    		this.alive = false;
    	}
    }
    
    /**
     * Function that would be use in some characters to define the way in which
     * they would be attacking 
     * */
    public void attack() {
    	
    }
}

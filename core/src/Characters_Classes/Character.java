package Characters_Classes;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Character {
	 // PROPERTIES
    private Texture image;
    private Sprite sprite;
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

        this.alive = true;
        this.numLives = numLives;
    }

  


    // FUNCTIONS
    /** Function that would be resting on life of the characters
     * If the lives are less then 0 it change the boolean to false 
     * so the character its dead 
     * */
    public void dead() {
    	numLives--;
    	if(numLives < 0) {
    		alive = false;
    	}
    }
    
    /**
     * Function that would be use in some characters to define the way in which
     * they would be attacking 
     * */
    public void attack() {
    	
    }
}

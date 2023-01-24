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
    private int height;
    private int width;
    private boolean alive;
    private int numLives;

    // BUILDER
    public Character(Texture image, String name, float positionX, float positionY, int height, int width, boolean alive, int numLives) {
        this.image = image;
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
        this.height = height;
        this.width = width;
        this.alive = true;
        this.numLives = numLives;
    }

  
	// SETTERS AND GETTERS
    public Texture getImage(){
        return image;
    }

    public void setImage(Texture image){
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    

    public boolean getAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    // FUNCTIONS
    /* Function that would be resting on life of the characters
     * If the lives are less then 0 it change the boolean to false 
     * so the character its dead 
     * */
    public void dead() {
    	numLives--;
    	if(numLives < 0) {
    		alive = false;
    	}
    }
    
    /* Function that would be use in some characters to define the way in which 
     * they would be attacking 
     * */
    public void attack() {
    	
    }
}

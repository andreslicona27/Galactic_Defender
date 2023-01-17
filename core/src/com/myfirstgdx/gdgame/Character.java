package com.myfirstgdx.gdgame;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Character {
	 // PROPERTIES
    private Texture image;
    private Sprite sprite;
    private String name;
    private int positionX;
    private int positionY;
    private int height;
    private int width;
    private String attack;
    private boolean alive;

    // BUILDER
    public Character(Texture image, String name, int positionX, int positionY, int height, int width, String attack, boolean alive) {
        this.image = image;
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
        this.height = height;
        this.width = width;
        this.attack = attack;
        this.alive = true;
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

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
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

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }
    

    public boolean getgetAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    // FUNCTIONS
}

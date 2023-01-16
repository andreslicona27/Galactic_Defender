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
    private int width;
    private int height;
    private String attack;

    // BUILDER
    public Character(Texture image, String name, int positionX, int positionY, int width, int height, String attack) {
        this.image = image;
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
        this.attack = attack;
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

    // FUNCTIONS
}

package com.example.galactic_defender.Characters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Represents a Shot object in the game.
 * Extends the Character class.
 */
public class Shot extends Character{

    InputStream input_stream;
    AssetManager assets_manager;
    Canvas canvas;
    public Point position;
    Bitmap shot_asset;
    Bitmap shot_image;
    public Bitmap scale_shot_image;
    Rect hide_box;
    int displacement;
    float angle;


    /**
     * Constructs a new Shot object.
     *
     * @param context the context of the application
     * @param screen_width the width of the screen
     * @param screen_height the height of the screen
     * @param spaceship_x the x-coordinate of the spaceship
     * @param spaceship_y the y-coordinate of the spaceship
     * @param spaceship_angle the angle of the spaceship
     */
    public Shot(Context context, int screen_width, int screen_height, int spaceship_x, int spaceship_y, float spaceship_angle) {
        super(context, screen_width, screen_height);
        this.position = new Point(spaceship_x, spaceship_y);
        this.angle = spaceship_angle;
        this.displacement = screen_height / 100;
        this.assets_manager = context.getAssets();

        try {
            this.input_stream = assets_manager.open("characters/laser.png");
            this.shot_asset = BitmapFactory.decodeStream(input_stream);

        } catch (IOException e) {
            Log.i("assets", "problem getting the asset");
            throw new RuntimeException(e);
        }

        this.shot_image = Bitmap.createBitmap(this.shot_asset, 0, 0, this.shot_asset.getWidth(), this.shot_asset.getHeight());
        this.scale_shot_image = Bitmap.createScaledBitmap(this.shot_image, screen_height/20, screen_height/20, true);

        updateHideBox();
    }


    /**
     * Obtains the rectangle which contains the enemy image
     *
     * @return The Rect at the borders of the image
     */
    public Rect getHideBox() {
        return this.hide_box;
    }

    /**
     * Draws the shot on the canvas.
     *
     * @param canvas The canvas on which to draw the shot.
     */
    @Override
    public void draw(Canvas canvas) {
        this.canvas = canvas;
        canvas.save();
        canvas.rotate(this.angle, this.hide_box.centerX(), this.hide_box.centerY());
        canvas.drawBitmap(this.scale_shot_image, this.position.x, this.position.y, null);
        canvas.drawRect(this.hide_box, this.border_paint);
        canvas.restore();
    }

    /**
     * Moves the shot based on the specified screen dimensions and velocity.
     */
    @Override
    public void move(){
        if (this.angle >= 0 && this.angle <= 22) {
            this.position.x = this.position.x + this.displacement;
        }
        if(this.angle > 22 && this.angle <= 67){
            this.position.x = this.position.x + this.displacement;
            this.position.y = this.position.y + this.displacement;
        }
        if(this.angle > 67 && this.angle <= 112){
            this.position.y = this.position.y + this.displacement;
        }
        if(this.angle > 112 && this.angle <= 157){
            this.position.x = this.position.x - this.displacement;
            this.position.y = this.position.y + this.displacement;
        }
        if(this.angle > 157 && this.angle <= 202){
            this.position.x = this.position.x - this.displacement;
        }
        if(this.angle > 202 && this.angle <= 247){
            this.position.x = this.position.x - this.displacement;
            this.position.y = this.position.y - this.displacement;
        }
        if(this.angle > 247 && this.angle <= 292){
            this.position.y = this.position.y - this.displacement;
        }
        if(this.angle > 337){
            this.position.x = this.position.x + this.displacement;
        }

//        this.position.y = this.position.y + this.displacement;
        updateHideBox();
    }


    /**
     * Updates the hide box position based on the current position and shot.
     * The hide box represents the bounding box of the shot.
     */
    @Override
    public void updateHideBox(){
        this.hide_box = new Rect(this.position.x, this.position.y, this.position.x + this.scale_shot_image.getWidth(),
                this.position.y + this.scale_shot_image.getHeight());
    }


    /**
     * Checks for collision between the hide box of this object and the provided hide box.
     *
     * @param hide_box The hide box to check collision against.
     * @return true if collision occurs, false otherwise.
     */
    @Override
    public boolean collision(Rect hide_box){
        return this.hide_box.intersect(hide_box);
    }


    /**
     * Manages the limits of the shot's position within the screen borders.
     */
    public boolean wallCollision(){
        return this.position.x + this.scale_shot_image.getWidth() >= screen_width || this.position.x <= 0 || this.position.y + this.scale_shot_image.getHeight() >= screen_height || this.position.y <= 0;

    }

}

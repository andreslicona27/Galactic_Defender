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
 *
 * @author [Andres Licona]
 * @version [1.0]
 * @since [05-24-2023]
 */
public class Shot extends Character{


    /**
     * Represents an asset for a shot.
     */
    Bitmap shot_asset;

    /**
     * Represents an image for a shot.
     */
    Bitmap shot_image;

    /**
     * Represents a rectangular area for the limits of the shot.
     */
    Rect hide_box;

    /**
     * Represents the position of th shot in a coordinate system.
     */
    public Point position;

    /**
     * Represents a scaled image for the shot.
     */
    public Bitmap scale_shot_image;

    /**
     * Represents the amount of displacement that the shot would make
     */
    int displacement;

    /**
     * Represents the angle of rotation it which the shot would move
     */
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
        this.displacement = screen_height / 50;
        this.assets_manager = context.getAssets();

        // Assets
        try {
            this.input_stream = assets_manager.open("characters/laser.png");
            this.shot_asset = BitmapFactory.decodeStream(input_stream);

        } catch (IOException e) {
            Log.i("assets", "problem getting the asset");
            throw new RuntimeException(e);
        }

        this.shot_image = Bitmap.createBitmap(this.shot_asset, 0, 0, this.shot_asset.getWidth(), this.shot_asset.getHeight());
        this.scale_shot_image = Bitmap.createScaledBitmap(this.shot_image, screen_height/35, screen_height/35, true);

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
        canvas.drawBitmap(this.scale_shot_image, this.position.x - (float)this.scale_shot_image.getWidth()/2, this.position.y, null);
        canvas.drawRect(this.hide_box, this.border_paint);
    }

    /**
     * Moves the shot based on the specified screen dimensions and velocity.
     */
    @Override
    public void move(){
        float angleInRadians = (float) Math.toRadians(angle);
        float deltaX = displacement * (float) Math.sin(angleInRadians);
        float deltaY = -displacement * (float) Math.cos(angleInRadians);

        this.position.x += deltaX;
        this.position.y += deltaY;
        updateHideBox();
    }


    /**
     * Updates the hide box position based on the current position and shot.
     * The hide box represents the bounding box of the shot.
     */
    @Override
    public void updateHideBox(){
        this.hide_box = new Rect(this.position.x - this.scale_shot_image.getWidth()/2,
                this.position.y,
                this.position.x + this.scale_shot_image.getWidth()/2,
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

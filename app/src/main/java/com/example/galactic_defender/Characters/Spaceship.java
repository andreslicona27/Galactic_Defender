package com.example.galactic_defender.Characters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;

import java.io.IOException;

/**
 * The Spaceship class represents an spaceship character in the game.
 * It encapsulates the spaceship´s image, position, and movement behavior.
 * The spaceship can move within the specified screen dimensions.
 * It also provides access to the spaceship's image dimensions.
 *
 * @author [Andres Licona]
 * @version [1.0]
 * @since [05-17-2023]
 **/
public class Spaceship extends Character {

    Bitmap spaceship1_asset, spaceship1_image;
    Bitmap spaceship2_asset, spaceship2_image;
    Bitmap spaceship3_asset, spaceship3_image;
    Bitmap spaceship4_asset, spaceship4_image;
    Bitmap[] spaceship = new Bitmap[4];
    Bitmap rotatedBitmap;
    Rect hide_box;
    public Point position;
    int velocity = screen_height / 80;
    public boolean move_player = false;
    int current_frame = 0;
    int frame_count;
    float speed_x, speed_y;

    /**
     * Constructs an instance of the Spaceship class.
     *
     * @param context       The context of the application.
     * @param screen_width  The width of the screen.
     * @param screen_height The height of the screen.
     * @exception RuntimeException If there is a problem obtaining the assets
     */
    public Spaceship(Context context, int screen_width, int screen_height) {
        super(context, screen_width, screen_height);

        try {
            this.input_stream = assets_manager.open("characters/spaceships/spaceship1.png");
            this.spaceship1_asset = BitmapFactory.decodeStream(input_stream);
            this.input_stream = assets_manager.open("characters/spaceships/spaceship2.png");
            this.spaceship2_asset = BitmapFactory.decodeStream(input_stream);
            this.input_stream = assets_manager.open("characters/spaceships/spaceship3.png");
            this.spaceship3_asset = BitmapFactory.decodeStream(input_stream);
            this.input_stream = assets_manager.open("characters/spaceships/spaceship4.png");
            this.spaceship4_asset = BitmapFactory.decodeStream(input_stream);

        } catch (IOException e) {
            Log.i("assets", "problem getting the asset");
            throw new RuntimeException(e);
        }

        // spaceship
        this.spaceship1_image = Bitmap.createBitmap(this.spaceship1_asset, 0, 0, this.spaceship1_asset.getWidth(), this.spaceship1_asset.getHeight());
        this.spaceship2_image = Bitmap.createBitmap(this.spaceship2_asset, 0, 0, this.spaceship2_asset.getWidth(), this.spaceship2_asset.getHeight());
        this.spaceship3_image = Bitmap.createBitmap(this.spaceship3_asset, 0, 0, this.spaceship3_asset.getWidth(), this.spaceship3_asset.getHeight());
        this.spaceship4_image = Bitmap.createBitmap(this.spaceship4_asset, 0, 0, this.spaceship4_asset.getWidth(), this.spaceship4_asset.getHeight());

        this.spaceship[0] = Bitmap.createScaledBitmap(this.spaceship1_image, screen_height/6,
                screen_height/4, true);
        this.spaceship[1] = Bitmap.createScaledBitmap(this.spaceship2_image, screen_height/6,
                screen_height/4, true);
        this.spaceship[2] = Bitmap.createScaledBitmap(this.spaceship3_image, screen_height/6,
                screen_height/4, true);
        this.spaceship[3] = Bitmap.createScaledBitmap(this.spaceship4_image, screen_height/6,
                screen_height/4, true);

        this.frame_count = this.spaceship.length;

        this.position = new Point(screen_width / 2 - this.spaceship[current_frame].getWidth() / 2,
                screen_height / 2 - this.spaceship[current_frame].getHeight() / 2);

        updateHideBox();
    }

    // GETTERS

    /**
     * Obtains the scale image of the spaceship
     *
     * @return The scale image of the spaceship as Bitmap
     */
    public Bitmap getSpaceshipImage() {
        return this.spaceship[current_frame];
    }

    /**
     * Obtains the width of the spaceship image
     *
     * @return Width of the spaceship image as int
     */
    public int getSpaceshipWidth() {
        return this.spaceship[current_frame].getWidth();
    }

    /**
     * Obtains the width of the spaceship image
     *
     * @return Width of the spaceship image as int
     */
    public int getSpaceshipHeight() {
        return this.spaceship[current_frame].getHeight();
    }

    // FUNCTIONS

    /**
     * Draws the spaceship on the canvas and manages its position within the borders.
     *
     * @param canvas The canvas on which to draw the spaceship.
     */
    @Override
    public void draw(Canvas canvas) {
        // Manage if the spaceship its in the borders
        if (this.position.x > screen_width - this.spaceship[current_frame].getWidth()) {
            this.position.x = screen_width - this.spaceship[current_frame].getWidth();
        } else if (this.position.x < 0) {
            this.position.y = 0;
        }

        if (this.position.y > screen_height - this.spaceship[current_frame].getHeight()) {
            this.position.y = screen_height - this.spaceship[current_frame].getHeight();
        } else if (this.position.y < 0) {
            this.position.y = 0;
        }

        // Draw elements
        canvas.drawBitmap(this.spaceship[current_frame], position.x, position.y, null);
        canvas.drawRect(this.hide_box, this.border_paint);
        updateHideBox();
    }

    /**
     * Updates the hide box position based on the current position and spaceship frame.
     * The hide box represents the bounding box of the spaceship.
     */
    @Override
    public void updateHideBox() {
        this.hide_box = new Rect(this.position.x, this.position.y,
                this.position.x + this.spaceship[current_frame].getWidth(),
                this.position.y + this.spaceship[current_frame].getHeight());
    }

    /**
     * Checks for collision between the hide box of this object and the provided hide box.
     *
     * @param hide_box The hide box to check collision against.
     * @return true if collision occurs, false otherwise.
     */
    @Override
    public boolean collision(Rect hide_box) {
        return this.hide_box.intersect(hide_box);
    }

    /**
     * Updates the animation frame of the spaceship.
     */
    @Override
    public void updateAnimation() {
        if (this.current_frame == this.frame_count - 1) {
            this.current_frame = 0;
        }
        this.current_frame++;
    }


    /**
     * Moves the player based on the provided last touch difference.
     *
     * @param last_touch_difference The difference between the current and last touch positions as
     *                            a PointF object.
     */
    public void playerMovement(PointF last_touch_difference) {
        if (!move_player) {
            return;
        }

        float ratio = Math.abs(last_touch_difference.x) / Math.abs(last_touch_difference.y);
        double angle = Math.atan(ratio);  // return the value in radians

        this.speed_x = (float) Math.cos(angle);
        this.speed_y = (float) Math.sin(angle);

        matrix.postRotate((float) Math.toDegrees(angle));
        this.spaceship[current_frame] = Bitmap.createBitmap(this.spaceship[current_frame], 0, 0,
                this.spaceship[current_frame].getWidth(),
                this.spaceship[current_frame].getHeight(), matrix, true);

        if (last_touch_difference.x < 0) {
            this.speed_x *= -1;
        }
        if (last_touch_difference.y < 0) {
            this.speed_y *= -1;
        }
        this.position.x += this.speed_x * this.velocity;
        this.position.y += this.speed_y * this.velocity;
    }

}

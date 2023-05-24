package com.example.galactic_defender.Characters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import java.io.InputStream;

/**
 * The Character class represents a game character in the Galactic Defender game.
 *
 * @author [Andres Licona]
 * @version [1.0]
 * @since [05-16-2023]
 */
public class Character {

    /**
     * Represents the attribute of context in the character class
     */
    Context context;
    /**
     * Represents the input stream for it to read data from a concrete source
     */
    InputStream input_stream;
    /**
     * Represents the asset manager.for it to manage the assets
     */
    AssetManager assets_manager;
    /**
     * Represents the paint used for the borders
     */
    Paint border_paint;
    /**
     * Represents the width of the screen
     */
    int screen_width;
    /**
     * Represents the height of the screen
     */
    int screen_height;

    /**
     * Constructs an instance of the Character class
     *
     * @param context       The context of the application.
     * @param screen_width  The width of the screen.
     * @param screen_height The height of the screen.
     */
    public Character(Context context, int screen_width, int screen_height) {
        this.context = context;
        this.screen_width = screen_width;
        this.screen_height = screen_height;
        this.assets_manager = context.getAssets();

        // Border paint for the hide box of the characters
        this.border_paint = new Paint();
        this.border_paint.setColor(Color.RED);
        this.border_paint.setStyle(Paint.Style.STROKE);
        this.border_paint.setStrokeWidth(5);
    }

    /**
     * Draws the character on the canvas.
     *
     * @param canvas The canvas to draw on.
     */
    public void draw(Canvas canvas) {
    }

    /**
     * Updates the position of the character.
     */
    public void move(){

    }

    /**
     * Updates the hide box of the character.
     */
    public void updateHideBox() {
    }

    /**
     * Checks for collision with the given hide box.
     *
     * @param hide_box The hide box to check collision with.
     * @return True if a collision occurs, false otherwise.
     */
    public boolean collision(Rect hide_box) {
        return false;
    }

    /**
     * Updates the animation of the character.
     */
    public void updateAnimation() {

    }
}
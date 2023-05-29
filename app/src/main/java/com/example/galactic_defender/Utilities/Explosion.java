package com.example.galactic_defender.Utilities;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

import com.example.galactic_defender.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * The Explosion class represents an explosion animation. It loads a series of bitmap frames
 * representing the explosion animation and provides a method to draw the explosion on a canvas.
 */
public class Explosion {

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
     * Bitmap array that would manage all the explosions images
     */
    Bitmap[] explosion;
    /**
     * Represents the position of the explosion
     */
    Point position;
    /**
     * Integer that would represent the current image of the explosion
     */
    public int explosion_frame;

    /**
     * Constructs an instance of the Explosion class.
     *
     * @param context       The Android application context.
     * @param spaceship_x   The x-coordinate of the spaceship where the explosion occurs.
     * @param spaceship_y   The y-coordinate of the spaceship where the explosion occurs.
     * @exception RuntimeException If there is a problem obtaining the assets
     */
    public Explosion(Context context, int spaceship_x, int spaceship_y) {
        this.context = context;
        this.explosion = new Bitmap[9];
        this.position = new Point(spaceship_x, spaceship_y);
        this.assets_manager = context.getAssets();
        try {
            this.input_stream = assets_manager.open("utilities/explosion/explosion0.png");
            this.explosion[0] = BitmapFactory.decodeStream(input_stream);
            this.input_stream = assets_manager.open("utilities/explosion/explosion1.png");
            this.explosion[1] = BitmapFactory.decodeStream(input_stream);
            this.input_stream = assets_manager.open("utilities/explosion/explosion2.png");
            this.explosion[2] = BitmapFactory.decodeStream(input_stream);
            this.input_stream = assets_manager.open("utilities/explosion/explosion3.png");
            this.explosion[3] = BitmapFactory.decodeStream(input_stream);
            this.input_stream = assets_manager.open("utilities/explosion/explosion4.png");
            this.explosion[4] = BitmapFactory.decodeStream(input_stream);
            this.input_stream = assets_manager.open("utilities/explosion/explosion5.png");
            this.explosion[5] = BitmapFactory.decodeStream(input_stream);
            this.input_stream = assets_manager.open("utilities/explosion/explosion6.png");
            this.explosion[6] = BitmapFactory.decodeStream(input_stream);
            this.input_stream = assets_manager.open("utilities/explosion/explosion7.png");
            this.explosion[7] = BitmapFactory.decodeStream(input_stream);
            this.input_stream = assets_manager.open("utilities/explosion/explosion8.png");
            this.explosion[8] = BitmapFactory.decodeStream(input_stream);

        } catch (IOException e) {
            Log.i("assets", "problem getting the asset");
            throw new RuntimeException(e);
        }

        this.explosion_frame = 0;
    }

    /**
     * Draws the explosion animation on the given canvas at the specified coordinates.
     *
     * @param canvas       The canvas on which to draw the explosion.
     */
    public void drawExplosion(Canvas canvas){
        canvas.drawBitmap(this.explosion[explosion_frame], this.position.x, this.position.y, null);
    }

    /**
     * Updates the animation frame of the explosion.
     */
    public void updateAnimation(){
        if(this.explosion_frame < explosion.length-1){
            this.explosion_frame++;
        }
    }
}

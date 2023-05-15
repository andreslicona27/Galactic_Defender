package com.example.galactic_defender.Characters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import com.example.galactic_defender.R;

import java.util.Random;

public class Spaceship {
    Context context;
    Bitmap spaceship, scale_spaceship;
    public Point position;
    public int screen_width, screen_height;
    public int velocity = 5;
    public boolean is_alive = true;

    /**
     * Constructs an instance of the Spaceship class.
     *
     * @param context The context of the application.
     * @param screen_width The width of the screen.
     * @param screen_height The height of the screen.
     */
    public Spaceship(Context context, int screen_width, int screen_height){
        this.context = context;
        this.screen_width = screen_width;
        this.screen_height = screen_height;
        // TODO we have to do this with sprites and change the java doc and in the drawing function
        this.spaceship = BitmapFactory.decodeResource(context.getResources(), R.drawable.battleship);
        this.scale_spaceship = Bitmap.createScaledBitmap(this.spaceship, screen_height/8,
                screen_height/6, true);
        this.position = new Point(screen_width/2 - this.scale_spaceship.getWidth(),
                screen_height/2 - this.scale_spaceship.getHeight());
    }

    // GETTERS
    /**
     * Obtains the scale image of the spaceship
     *
     * @return The scale image of the spaceship as Bitmap
     */
    public Bitmap GetSpaceshipImage(){
        return this.scale_spaceship;
    }

    /**
     * Obtains the width of the spaceship image
     *
     * @return Width of the spaceship image as int
     */
    public int GetSpaceshipWidth(){
        return this.scale_spaceship.getWidth();
    }

    /**
     * Obtains the width of the spaceship image
     *
     * @return Width of the spaceship image as int
     */
    public int GetSpaceshipHeight(){return this.scale_spaceship.getHeight();}

    // FUNCTIONS
    /**
     * Draws the spaceship on the canvas.
     *
     * @param canvas The canvas on which the spaceship should be drawn.
     */
    public void DrawSpaceship(Canvas canvas) {
        if (this.is_alive) {
            if(this.position.x > screen_width - this.GetSpaceshipWidth()) {
                this.position.x = screen_width - this.GetSpaceshipWidth();
            } else if (this.position.x < 0) {
                this.position.y = 0;
            }

            if(this.position.y > screen_height - this.GetSpaceshipHeight()){
                this.position.y = screen_height - this.GetSpaceshipHeight();
            } else if(this.position.y < 0) {
                this.position.y = 0;
            }
            canvas.drawBitmap(this.scale_spaceship, this.position.x, this.position.y, null);
        }
    }

}

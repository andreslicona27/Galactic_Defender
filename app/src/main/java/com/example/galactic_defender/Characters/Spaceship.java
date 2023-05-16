package com.example.galactic_defender.Characters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.example.galactic_defender.R;

public class Spaceship extends Character{

    Bitmap spaceship, spaceship2, scale_spaceship;
    Bitmap[] flames;
    Rect hide_box;
    public Point position;
    public int velocity = screen_height/80;
    public boolean is_alive = true;

    /**
     * Constructs an instance of the Spaceship class.
     *
     * @param context The context of the application.
     * @param screen_width The width of the screen.
     * @param screen_height The height of the screen.
     */
    public Spaceship(Context context, int screen_width, int screen_height){
        super(context, screen_width, screen_height);

        this.spaceship = BitmapFactory.decodeResource(context.getResources(), R.drawable.battleship);
//        this.flames[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.flame1);
//        this.flames[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.flame2);

        this.scale_spaceship = Bitmap.createScaledBitmap(this.spaceship, screen_height/8,
                screen_height/6, true);
        this.position = new Point(screen_width/2 - this.scale_spaceship.getWidth(),
                screen_height/2 - this.scale_spaceship.getHeight());

        updateHideBox();
    }

    // GETTERS
    /**
     * Obtains the scale image of the spaceship
     *
     * @return The scale image of the spaceship as Bitmap
     */
    public Bitmap getSpaceshipImage(){
        return this.scale_spaceship;
    }

    /**
     * Obtains the width of the spaceship image
     *
     * @return Width of the spaceship image as int
     */
    public int getSpaceshipWidth(){
        return this.scale_spaceship.getWidth();
    }

    /**
     * Obtains the width of the spaceship image
     *
     * @return Width of the spaceship image as int
     */
    public int getSpaceshipHeight(){return this.scale_spaceship.getHeight();}

    // FUNCTIONS
    @Override
    public void draw(Canvas canvas){
        if (this.is_alive) {
            if(this.position.x > screen_width - this.getSpaceshipWidth()) {
                this.position.x = screen_width - this.getSpaceshipWidth();
            } else if (this.position.x < 0) {
                this.position.y = 0;
            }

            if(this.position.y > screen_height - this.getSpaceshipHeight()){
                this.position.y = screen_height - this.getSpaceshipHeight();
            } else if(this.position.y < 0) {
                this.position.y = 0;
            }
            canvas.drawBitmap(this.scale_spaceship, position.x, position.y, null);
            canvas.drawRect(hide_box, border_paint);
        }
    }

    @Override
    public void updateHideBox(){
        this.hide_box = new Rect(this.position.x, this.position.y,
                this.position.x + this.scale_spaceship.getWidth(),
                this.position.y + this.scale_spaceship.getHeight());
    }

    @Override
    public boolean collision(Rect hide_box){
        return this.hide_box.intersect(hide_box);
    }

}

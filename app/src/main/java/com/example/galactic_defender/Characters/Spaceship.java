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
    Bitmap spaceship;
    Bitmap scale_spaceship;
    Random random;
    public Point position;
    public int screen_width;
    public int screen_height;
    public int velocity = 5;
    public boolean is_alive = true;

    // BUILDER
    public Spaceship(Context context, int screen_width, int screen_height){
        this.context = context;
        this.screen_width = screen_width;
        this.screen_height = screen_height;
        this.spaceship = BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket1);
        this.scale_spaceship = Bitmap.createScaledBitmap(this.spaceship, screen_height/10,
                screen_height/8, true);
        this.random = new Random();
        this.position = new Point(screen_width/2 - this.scale_spaceship.getWidth(),
                screen_height/2 - this.scale_spaceship.getHeight());
    }

    // GETTERS
    public Bitmap GetSpaceshipImage(){
        return this.scale_spaceship;
    }
    public int GetSpaceshipWidth(){
        return spaceship.getWidth();
    }
    public int GetSpaceshipHeight(){
        return spaceship.getHeight();
    }

    // FUNCTIONS
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

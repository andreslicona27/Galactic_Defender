package com.example.galactic_defender.Characters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import com.example.galactic_defender.R;

import java.util.Random;

public class Rocket {
    Context context;
    Bitmap rocket;
    Bitmap scale_rocket;
    Random random;
    public Point position;
    public int screen_width;
    public int screen_height;
    public int rocket_velocity = 5;
    public boolean is_alive = true;

    // BUILDER
    public Rocket(Context context, int screen_width, int screen_height){
        this.context = context;
        this.screen_width = screen_width;
        this.screen_height = screen_height;
        this.rocket = BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket1);
        this.scale_rocket = Bitmap.createScaledBitmap(this.rocket, screen_height/10,
                screen_height/10, true);
        this.random = new Random();
        this.position = new Point(screen_width / 2, screen_height / 2);
    }

    // GETTERS
    public Bitmap GetRocketImage(){
        return this.scale_rocket;
    }
    public int GetRocketWidth(){
        return rocket.getWidth();
    }

    public int GetRocketHeight(){
        return rocket.getHeight();
    }

    // FUNCTIONS
    public void DrawRocket(Canvas canvas) {
        if (this.is_alive) {
            if(this.position.x > screen_width - this.GetRocketWidth()) {
                this.position.x = screen_width - this.GetRocketWidth();
            } else if (this.position.x < 0) {
                this.position.y = 0;
            }

            if(this.position.y > screen_height - this.GetRocketHeight()){
                this.position.y = screen_height - this.GetRocketHeight();
            } else if(this.position.y < 0) {
                this.position.y = 0;
            }
            canvas.drawBitmap(this.scale_rocket, this.position.x, this.position.y, null);
        }
    }

}

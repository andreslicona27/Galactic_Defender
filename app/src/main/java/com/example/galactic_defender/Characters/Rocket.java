package com.example.galactic_defender.Characters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.galactic_defender.R;

import java.util.Random;

public class Rocket {
    Bitmap rocket;
    Context context;
    Random random;
    public int rocketX;
    public int rocketY;
    int rocket_velocity;
    public boolean is_alive = true;

    public Rocket(Context context){
        this.context = context;
        rocket = BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket1);
        random = new Random();
    }

    public Bitmap getRocket(){
        return rocket;
    }

    public int getRocketWidth(){
        return rocket.getWidth();
    }

    public int getRocketHeight(){
        return rocket.getHeight();
    }
}

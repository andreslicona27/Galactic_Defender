package com.example.galactic_defender.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.galactic_defender.R;

public class Explosion {
    Bitmap[] explosion = new Bitmap[9];
    public int explosion_frame;
    public int explosionX;
    public int explosionY;

    public Explosion(Context context, int explosionX, int explosionY){
        explosion[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion0);
        explosion[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion1);
        explosion[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion2);
        explosion[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion3);
        explosion[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion4);
        explosion[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion5);
        explosion[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion6);
        explosion[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion7);
        explosion[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion8);
        explosion_frame = 0;
        this.explosionX = explosionX;
        this.explosionY = explosionY;
    }

    public Bitmap getExplosion(int explosion_frame){
        return explosion[explosion_frame];
    }
}

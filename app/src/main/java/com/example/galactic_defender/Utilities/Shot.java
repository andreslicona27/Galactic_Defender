package com.example.galactic_defender.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.galactic_defender.R;

public class Shot {
    Bitmap shot;
    Context context;
    public int shotX;
    public int shotY;

    public Shot(Context context, int shotX, int shotY) {
        this.context = context;
        shot = BitmapFactory.decodeResource(context.getResources(), R.drawable.shot);
        this.shotX = shotX;
        this.shotY = shotY;
    }

    public Bitmap getShot(){
        return shot;
    }
}

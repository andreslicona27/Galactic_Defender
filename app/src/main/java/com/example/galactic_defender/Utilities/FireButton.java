package com.example.galactic_defender.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.example.galactic_defender.R;

public class FireButton {
    Context context;
    Bitmap shot;
    Bitmap scale_shot_image;
    Paint paint;
    public Point position;
    int radius = 100;
    int screen_width, screen_height;
    int palette_color = Color.parseColor("#F2C055");

    public FireButton(Context context, int screen_width, int screen_height){
        this.context = context;
        this.screen_width = screen_width;
        this.screen_height = screen_height;
        this.paint = new Paint();
        this.paint.setColor(palette_color);
    }

    public FireButton(Context context, int shot_x, int shot_y, int screen_width, int screen_height) {
        this.context = context;
        this.screen_width = screen_width;
        this.screen_height = screen_height;

        this.shot = BitmapFactory.decodeResource(context.getResources(), R.drawable.shot);
        this.scale_shot_image = Bitmap.createScaledBitmap(this.shot,
                screen_width/10, screen_height/15,true);
        this.position = new Point(shot_x, shot_y);
    }

    // GETTERS
    public Bitmap getShot(){
        return this.scale_shot_image;
    }
    public int GetShotWidth(){
        return this.scale_shot_image.getWidth();
    }
    public int GetShotHeight(){
        return this.scale_shot_image.getHeight();
    }

    // FUNCTIONS
    public void DrawFireButton(Canvas canvas){
        canvas.drawCircle((float)this.screen_width/20*18, (float)this.screen_height/20*16, radius
                , this.paint);
    }


}

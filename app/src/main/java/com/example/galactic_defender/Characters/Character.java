package com.example.galactic_defender.Characters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Character {

    Context context;
    Paint border_paint;
    int screen_width, screen_height;

    public Character(Context context, int screen_width, int screen_height){
        this.context = context;
        this.screen_width = screen_width;
        this.screen_height = screen_height;

        this.border_paint = new Paint();
        this.border_paint.setColor(Color.RED);
        this.border_paint.setStyle(Paint.Style.STROKE);
        this.border_paint.setStrokeWidth(5);
    }

    public void draw(Canvas canvas){
    }

    public void updateHideBox(){
    }

    public boolean collision(Rect hide_box){
        return false;
    }
}

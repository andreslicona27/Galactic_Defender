package com.example.galactic_defender.Characters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import java.io.InputStream;

public class Character {

    Context context;
    InputStream input_stream;
    AssetManager assets_manager;
    Matrix matrix;
    Paint border_paint;
    int screen_width, screen_height;

    /**
     * Constructs an instance of the Character class
     *
     * @param context       The context of the application.
     * @param screen_width  The width of the screen.
     * @param screen_height The height of the screen.
     */
    public Character(Context context, int screen_width, int screen_height){
        this.context = context;
        this.screen_width = screen_width;
        this.screen_height = screen_height;
        this.assets_manager = context.getAssets();

        this.matrix = new Matrix();

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

    public void updateAnimation(){

    }
}

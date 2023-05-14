package com.example.galactic_defender.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import com.example.galactic_defender.R;
import com.example.galactic_defender.Scenes.SceneGame;

public class FireButton {
    Context context;
    Bitmap shot;
    Bitmap scale_shot_image;
    Paint paint;
    SceneGame scene_game;
    public Point position;
    int radius = 100;
    int screen_width, screen_height;
    int center_x, center_y;
    int shot_velocity = 8;
    int palette_color = Color.parseColor("#F2C055");
    boolean shot_fired = false;

    public FireButton(SceneGame scene_game,Context context, int screen_width, int screen_height){
        this.scene_game = scene_game;
        this.context = context;
        this.screen_width = screen_width;
        this.screen_height = screen_height;
        this.center_x = screen_width/10;
        this.center_y = screen_height/15;
        this.paint = new Paint();
        this.paint.setColor(palette_color);
    }

    public FireButton(Context context, int shot_x, int shot_y, int screen_width, int screen_height) {
        this.context = context;
        this.screen_width = screen_width;
        this.screen_height = screen_height;

        this.shot = BitmapFactory.decodeResource(context.getResources(), R.drawable.shot);
        this.scale_shot_image = Bitmap.createScaledBitmap(this.shot,
                this.center_x, this.center_y,true);
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

    public void FireShot(Canvas canvas, Point ship_position, int ship_width, int ship_height){
        int pos_x = ship_position.x - ship_width/2 - this.scale_shot_image.getWidth();
        int pos_y = ship_position.y + ship_height + this.scale_shot_image.getHeight();
        canvas.drawBitmap(this.scale_shot_image, pos_x, pos_y, null);
    }

    public boolean TouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            float x = event.getRawX();
            float y = event.getRawY();

            float distance = (float) Math.sqrt(Math.pow(center_x - x, 2) + Math.pow(center_y - y, 2));

            return distance <= this.radius;
        }
        return false;
    }

}

package com.example.galactic_defender.Utilities;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.galactic_defender.R;
import com.example.galactic_defender.Scenes.SceneGame;

import java.io.IOException;
import java.io.InputStream;

public class FireButton {

    Context context;
    InputStream input_stream;
    AssetManager assets_manager;
    Bitmap shot, scale_shot_image, images;
    Paint paint;
    SceneGame scene_game;
    public Point position;
    int radius = 100;
    int screen_width, screen_height;
    int center_x, center_y;
    int shot_velocity = 8;
    int images_width;
    int images_height;
    int row = 1, column = 0;

    public FireButton(SceneGame scene_game,Context context, int screen_width, int screen_height){
        this.scene_game = scene_game;
        this.context = context;
        this.screen_width = screen_width;
        this.screen_height = screen_height;
        this.center_x = screen_width/10;
        this.center_y = screen_height/15;

        this.paint = new Paint();
        this.paint.setColor(ContextCompat.getColor(context, R.color.main_yellow));
        this.assets_manager = context.getAssets();

        try{
            this.input_stream = assets_manager.open("utilities/projectiles.png");
            this.images = BitmapFactory.decodeStream(input_stream);

        } catch (IOException e) {
            Log.i("assets", "problem getting the asset");
            throw new RuntimeException(e);
        }

        this.images_width = this.images.getWidth();
        this.images_height = this.images.getHeight();
        this.scale_shot_image = Bitmap.createBitmap(this.images, 0,this.images_height/4,
                this.images_width/4, this.images_height/4);
    }

    public FireButton(Context context, int shot_x, int shot_y, int screen_width, int screen_height) {
        this.context = context;
        this.screen_width = screen_width;
        this.screen_height = screen_height;

        try{
            this.assets_manager = context.getAssets();
            this.input_stream = assets_manager.open("utilities/shot.png");
            this.shot = BitmapFactory.decodeStream(input_stream);

        } catch (IOException e) {
            Log.i("assets", "problem getting the asset");
            throw new RuntimeException(e);
        }

        this.scale_shot_image = Bitmap.createScaledBitmap(this.shot,
                this.center_x, this.center_y,true);
        this.position = new Point(shot_x, shot_y);
    }

    // GETTERS
    public Bitmap getShot(){
        return this.scale_shot_image;
    }
    public int getShotWidth(){
        return this.scale_shot_image.getWidth();
    }
    public int getShotHeight(){
        return this.scale_shot_image.getHeight();
    }

    // FUNCTIONS
    public void drawFireButton(Canvas canvas){
        canvas.drawCircle((float)this.screen_width/20*18, (float)this.screen_height/20*16, radius
                , this.paint);
    }

    public void fireShot(Canvas canvas, Point ship_position, int ship_width, int ship_height){
        int pos_x = ship_position.x - ship_width/2 - this.scale_shot_image.getWidth();
        int pos_y = ship_position.y + ship_height + this.scale_shot_image.getHeight();
        canvas.drawBitmap(this.scale_shot_image, pos_x, pos_y, null);
    }

    public void updateImage(){
        int pos_x = (column % 4) * this.images_height/4;
        int pos_y = row * this.images_width / 4;
        this.scale_shot_image = Bitmap.createBitmap(this.images, pos_x, pos_y, this.images_width / 4,
                this.images_height/ 4);

    }

    public void touchEvent(MotionEvent event){
        float x = event.getRawX();
        float y = event.getRawY();
        int action = event.getActionMasked();

        if(action == MotionEvent.ACTION_DOWN){
            float side_a = Math.abs(x - this.center_x);
            float side_b = Math.abs(y - this.center_y);
            float hypotenuse = (float)Math.hypot(side_a, side_b);

            if(hypotenuse <= this.radius){
                Log.i("test", "we try to fire the gun");
                updateImage();
            }
//            float distance = (float) Math.sqrt(Math.pow(center_x - x, 2) + Math.pow(center_y - y, 2));

        }
    }

}

package com.example.galactic_defender.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.galactic_defender.R;

public class Scene {
    // TODO add media player in here for me to have access to it from the other scenes and pause it

    Context context;
    Paint paint;
    Rect back_button;
    Bitmap back_button_image;
    public int scene_number = -1;
    int screen_height, screen_width;

    public Scene(Context context, int screen_height, int screen_width, int scene_number) {
        this.context = context;
        this.scene_number = scene_number;
        this.screen_height = screen_height;
        this.screen_width = screen_width;

        paint = new Paint();
        paint.setTextSize(screen_height / 10);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);

        this.back_button_image = BitmapFactory.decodeResource(context.getResources(), R.drawable.home_icon);
        this.back_button = new Rect(screen_width/10, screen_height/35, screen_width/10*2
                , screen_height/35*2);
    }

    public void Draw(Canvas canvas) {
        if (scene_number != 1 && scene_number != 3) {
            canvas.drawBitmap(back_button_image, null, back_button, paint);
        }
    }

    public void UpdatePhysics() {

    }

    public int onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if(scene_number != 1 && scene_number != 3){
            if(back_button.contains(x, y)){
                return 1;
            }
        }

        return -1;
    }
}

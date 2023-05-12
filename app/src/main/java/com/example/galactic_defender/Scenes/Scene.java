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
    Bitmap scale_button_image;
    public int scene_number = -1;
    int screen_height, screen_width;
    int BACKGROUND = Color.parseColor("#2E5266");
    int TITLE_COLOR = Color.parseColor("#D3D0CB");



    public Scene(Context context, int screen_height, int screen_width, int scene_number) {
        this.context = context;
        this.scene_number = scene_number;
        this.screen_height = screen_height;
        this.screen_width = screen_width;

        this.paint = new Paint();
        this.paint.setAlpha(240);
        this.paint.setTextSize(screen_height/10);
        this.paint.setAntiAlias(true);
        this.paint.setTextAlign(Paint.Align.CENTER);
        this.paint.setColor(TITLE_COLOR);

        this.back_button_image = BitmapFactory.decodeResource(context.getResources(), R.drawable.home_icon);
        this.scale_button_image = Bitmap.createScaledBitmap(this.back_button_image,
                screen_width/8, screen_width/8, true);
        this.back_button = new Rect(screen_width/20, screen_height/12, screen_width/20+50
                , screen_height/12+50);
    }

    public void Draw(Canvas canvas) {
        canvas.drawColor(BACKGROUND);
        if (scene_number != 1 && scene_number != 3) {
            canvas.drawBitmap(scale_button_image, null, back_button, null);
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

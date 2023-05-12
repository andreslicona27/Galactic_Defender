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

public class SceneMenu extends Scene{

    Paint paint;
    Rect records_button, information_button, play_button,credits_button,settings_button;
    Bitmap records_image, information_image, play_image, credits_image,
            settings_image;
    Bitmap scale_records_image, scale_information_image, scale_play_image, scale_credits_image,
            scale_settings_image;
    int scene_number = 1;
    int screen_height, screen_width;

    public SceneMenu(Context context, int screen_height, int screen_width, int scene_number) {
        super(context, screen_height, screen_width, scene_number);
        this.scene_number = scene_number;
        this.screen_height = screen_height;
        this.screen_width = screen_width;
        paint = new Paint();

        // Button images
        this.records_image = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.trophy_icon);
        this.information_image = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.information_icon);
        this.play_image = BitmapFactory.decodeResource(context.getResources(), R.drawable.play_icon);
        this.credits_image = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.credits_icon);
        this.settings_image = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.settings_icon);

        // Button scale images
        this.scale_records_image = Bitmap.createScaledBitmap(this.records_image,
                screen_width/8, screen_width/8, true);
        this.scale_information_image = Bitmap.createScaledBitmap(this.information_image,
                screen_width/8, screen_width/8, true);
        this.scale_play_image = Bitmap.createScaledBitmap(this.play_image,
                screen_width/8, screen_width/8, true);
        this.scale_credits_image = Bitmap.createScaledBitmap(this.credits_image,
                screen_width/8, screen_width/8, true);
        this.scale_settings_image = Bitmap.createScaledBitmap(this.settings_image,
                screen_width/8, screen_width/8, true);

        // Button rectangles
        this.records_button = new Rect(screen_width/13*2, screen_height/6*4, screen_width/13*3,
                screen_height/6*5);
        this.information_button = new Rect(screen_width/13*4, screen_height/6*4,
                screen_width/13*5, screen_height/6*5);
        this.play_button = new Rect(screen_width/13*6, screen_height/6*4, screen_width/13*7,
                screen_height/6*5);
        this.credits_button = new Rect(screen_width/13*8, screen_height/6*4, screen_width/13*9,
                screen_height/6*5);
        this.settings_button = new Rect(screen_width/13*10, screen_height/6*4, screen_width/13*11,
                screen_height/6*5);

    }

    public void Draw(Canvas canvas){
        super.Draw(canvas);

        canvas.drawBitmap(scale_records_image, null, records_button, null);
        canvas.drawBitmap(scale_information_image, null, information_button, null);
        canvas.drawBitmap(scale_play_image, null, play_button, null);
        canvas.drawBitmap(scale_credits_image, null, credits_button, null);
        canvas.drawBitmap(scale_settings_image, null, settings_button, null);
    }

    public int onTouchEvent(MotionEvent event){
        int x = (int) event.getX();
        int y = (int) event.getY();
        int aux = (int) super.onTouchEvent(event);

        if(aux != scene_number && aux != -1){
            return aux;
        }

        if(records_button.contains(x, y)){
            return 2;
        }
        else if(play_button.contains(x, y)){
            return 3;
        }
        else if(credits_button.contains(x, y)){
            return 4;
        }
        else if(settings_button.contains(x, y)){
            return 5;
        }
        else if(information_button.contains(x, y)){
            return 8;
        }

        return this.scene_number;
    }
}

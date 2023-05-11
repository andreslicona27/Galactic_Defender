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
    Rect records_button, play_button,credits_button,settings_button;
    Bitmap records_button_image, play_button_image, credits_button_image, settings_button_image;
    Bitmap scale_records_image, scale_play_image, scale_credits_image, scale_settings_image;
    int scene_number = 1;
    int screen_height, screen_width;
    int BACKGROUND = Color.parseColor("#2E5266");

    public SceneMenu(Context context, int screen_height, int screen_width, int scene_number) {
        super(context, screen_height, screen_width, scene_number);
        this.scene_number = scene_number;
        this.screen_height = screen_height;
        this.screen_width = screen_width;
        paint = new Paint();

        // Button images
        this.records_button_image = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.trophy_icon);
        this.play_button_image = BitmapFactory.decodeResource(context.getResources(), R.drawable.play_icon);
        this.credits_button_image = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.credits_icon);
        this.settings_button_image = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.settings_icon);

        // Button scale images
        this.scale_records_image = Bitmap.createScaledBitmap(this.records_button_image,
                screen_width/8, screen_width/8, true);
        this.scale_play_image = Bitmap.createScaledBitmap(this.play_button_image,
                screen_width/8, screen_width/8, true);
        this.scale_credits_image = Bitmap.createScaledBitmap(this.credits_button_image,
                screen_width/8, screen_width/8, true);
        this.scale_settings_image = Bitmap.createScaledBitmap(this.settings_button_image,
                screen_width/8, screen_width/8, true);

        // Button rectangles
        this.records_button = new Rect(screen_width/11*2, screen_height/6*4, screen_width/11*3,
                screen_height/6*5);
        this.play_button = new Rect(screen_width/11*4, screen_height/6*4, screen_width/11*5,
                screen_height/6*5);
        this.credits_button = new Rect(screen_width/11*6, screen_height/6*4, screen_width/11*7,
                screen_height/6*5);
        this.settings_button = new Rect(screen_width/11*8, screen_height/6*4, screen_width/11*9,
                screen_height/6*5);

    }

    public void Draw(Canvas canvas){
        super.Draw(canvas);
        canvas.drawColor(BACKGROUND);

        canvas.drawBitmap(scale_records_image, null, records_button, null);
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

        return this.scene_number;
    }
}

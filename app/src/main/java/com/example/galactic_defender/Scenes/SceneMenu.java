package com.example.galactic_defender.Scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.galactic_defender.R;

public class SceneMenu extends Scene{

    Paint paint;
    Rect records_button, play_button,credits_button,settings_button;
    int scene_number = 1;
    int screen_height, screen_width;

    public SceneMenu(Context context, int screen_height, int screen_width, int scene_number) {
        super(context, screen_height, screen_width, scene_number);
        this.scene_number = scene_number;
        this.screen_height = screen_height;
        this.screen_width = screen_width;

        paint = new Paint();
        records_button = new Rect(screen_width/3, screen_height/5, screen_width/3*2,
                screen_height/5+200);
        play_button = new Rect(screen_width/4*2, screen_height/5, screen_width/4+200,
                screen_height/5+200);
        credits_button = new Rect(screen_width/5*4, screen_height/5, screen_width/5+200,
                screen_height/5+200);
        settings_button = new Rect(screen_width/10*15, screen_height/35, screen_width/10*17,
                screen_height/35*4);
    }

    public void Draw(Canvas canvas){
        super.Draw(canvas);
        canvas.drawColor(Color.GREEN);

        canvas.drawRect(records_button,paint);
        canvas.drawRect(play_button,paint);
        canvas.drawRect(credits_button,paint);
        canvas.drawRect(settings_button,paint);
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

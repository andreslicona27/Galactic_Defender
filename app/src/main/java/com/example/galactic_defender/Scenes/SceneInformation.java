package com.example.galactic_defender.Scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

public class SceneInformation extends Scene{

    int scene_number = 8;
    public SceneInformation(Context context, int screen_height, int screen_width, int scene_number) {
        super(context, screen_height, screen_width, scene_number);
    }

    public void Draw(Canvas canvas){
        super.Draw(canvas);
        canvas.drawText("How To Play", screen_width/10*5, screen_height/6, paint);
    }
}
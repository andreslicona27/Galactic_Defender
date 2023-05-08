package com.example.galactic_defender.Scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

public class SceneGameOver extends Scene{

    int scene_number = 7;
    int BACKGROUND = Color.parseColor("#2E5266");

    public SceneGameOver(Context context, int screen_height, int screen_width, int scene_number) {
        super(context, screen_height, screen_width, scene_number);
    }


    public void Draw(Canvas canvas){
        canvas.drawColor(BACKGROUND);
        super.Draw(canvas);
    }
}

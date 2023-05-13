package com.example.galactic_defender.Scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.galactic_defender.R;

public class SceneGameOver extends Scene{

    int scene_number = 7;

    public SceneGameOver(Context context, int screen_height, int screen_width, int scene_number) {
        super(context, screen_height, screen_width, scene_number);
    }

    public void Draw(Canvas canvas){
        super.Draw(canvas);
        canvas.drawText(context.getString(R.string.gameOver_title), screen_width/10*5,
                screen_height/6, paint);
    }
}

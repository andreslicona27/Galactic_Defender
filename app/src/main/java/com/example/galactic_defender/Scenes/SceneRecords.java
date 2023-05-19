package com.example.galactic_defender.Scenes;

import android.content.Context;
import android.graphics.Canvas;

import com.example.galactic_defender.R;

public class SceneRecords extends Scene{

    int scene_number = 2;

    /**
     * Constructs an instance of the SceneRecords class.
     *
     * @param context The context of the application.
     * @param screen_height The height of the screen.
     * @param screen_width The width of the screen.
     * @param scene_number The number identifying the scene.
     */
    public SceneRecords(Context context, int screen_height, int screen_width, int scene_number) {
        super(context, screen_height, screen_width, scene_number);
    }

    /**
     * Draws the scene on the canvas.
     *
     * @param canvas The canvas on which the scene should be drawn.
     */
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawText(context.getString(R.string.records_title), (float)screen_width/10*5,
                (float)screen_height/6, title_paint);
    }
}

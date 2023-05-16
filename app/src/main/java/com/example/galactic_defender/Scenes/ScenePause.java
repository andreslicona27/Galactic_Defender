package com.example.galactic_defender.Scenes;

import android.content.Context;
import android.graphics.Canvas;

import com.example.galactic_defender.R;

/**
 * The ScenePause class represents the pause scene in the game.
 * It extends the Scene class and adds functionality specific to the pause scene.
 * The pause scene displays the pause title and inherits the drawing and touch event handling
 * from the parent class.
 *
 * @author [Andres Licona]
 * @version [1.0]
 * @since [05-04-2023]
 */
public class ScenePause extends Scene {

    int scene_number = 6;

    /**
     * Constructs an instance of the ScenePause class.
     *
     * @param context The context of the application.
     * @param screen_height The height of the screen.
     * @param screen_width The width of the screen.
     * @param scene_number The number identifying the scene.
     */
    public ScenePause(Context context, int screen_height, int screen_width, int scene_number) {
        super(context, screen_height, screen_width, scene_number);
    }

    /**
     * Draws the scene on the canvas.
     *
     * @param canvas The canvas on which the scene should be drawn.
     */
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawText(context.getString(R.string.pause_title), screen_width/10*5, screen_height/6, title_paint);
    }
}

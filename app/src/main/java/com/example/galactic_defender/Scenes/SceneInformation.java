package com.example.galactic_defender.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.example.galactic_defender.GalacticDefender;
import com.example.galactic_defender.R;

import java.io.IOException;

/**
 * The SceneInformation class represents the information scene in the game.
 * It extends the Scene class and adds functionality specific to the information scene.
 * The information scene displays the information title and inherits the drawing and touch event
 * handling
 * from the parent class.
 *
 * @author [Andres Licona]
 * @version [1.0]
 * @since [05-04-2023]
 */
public class SceneInformation extends Scene {

    Bitmap english_asset, spanish_asset;
    Bitmap information_english, information_spanish;
    int scene_number = 8;


    /**
     * Constructs an instance of the SceneInformation class.
     *
     * @param context       The context of the application.
     * @param screen_height The height of the screen.
     * @param screen_width  The width of the screen.
     * @param scene_number  The number identifying the scene.
     */
    public SceneInformation(Context context, int screen_height, int screen_width, int scene_number) {
        super(context, screen_height, screen_width, scene_number);

        try {
            this.input_stream = assets_manager.open("images/how_to_play/information_english.png");
            this.english_asset = BitmapFactory.decodeStream(input_stream);
            this.input_stream = assets_manager.open("images/how_to_play/information_spanish.png");
            this.spanish_asset = BitmapFactory.decodeStream(input_stream);

        } catch (IOException e) {
            Log.i("assets", "problem getting the asset");
            throw new RuntimeException(e);
        }

        this.information_english = Bitmap.createScaledBitmap(this.english_asset, screen_width+10, screen_height+10,
                true);
        this.information_spanish = Bitmap.createScaledBitmap(this.spanish_asset, screen_width+10,
                screen_height+10, true);
    }

    /**
     * Draws the scene on the canvas.
     *
     * @param canvas The canvas on which the scene should be drawn.
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawText(context.getString(R.string.information_title),
                (float)screen_width/2 - title_paint.measureText(context.getString(R.string.information_title))/2,
                (float) screen_height / 6, title_paint);

        if(GalacticDefender.language.equals("en")){
            canvas.drawBitmap(this.information_english, null, new Rect(canvas.getWidth()/10,
                    canvas.getHeight()/5,
                    canvas.getWidth() - canvas.getWidth()/10,
                    canvas.getHeight()), null);
        } else {
            canvas.drawBitmap(this.information_spanish, null, new Rect(0, canvas.getHeight()/5, canvas.getWidth(),
                    canvas.getHeight()), null);
        }

    }


}

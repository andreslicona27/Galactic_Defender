package com.example.galactic_defender.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.example.galactic_defender.R;
import com.example.galactic_defender.Utilities.Background;

import java.io.IOException;
import java.util.ArrayList;

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

    Background[] english_instructions = new Background[3];
    Background[] spanish_instructions = new Background[3];
    Background bg_english1, bg_english2, bg_english3;
    Bitmap english_asset1, english_asset2, english_asset3;
    Bitmap spanish_asset1, spanish_asset2, spanish_asset3;
    int scene_number = 8;
    int current_image = 0;
    boolean move = false;


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
            this.input_stream = assets_manager.open("images/how_to_play/htp_english1.png");
            this.english_asset1 = BitmapFactory.decodeStream(input_stream);
            this.input_stream = assets_manager.open("images/how_to_play/htp_english2.png");
            this.english_asset2 = BitmapFactory.decodeStream(input_stream);
            this.input_stream = assets_manager.open("images/how_to_play/htp_english3.png");
            this.english_asset3 = BitmapFactory.decodeStream(input_stream);

            this.input_stream = assets_manager.open("images/how_to_play/htp_spanish1.png");
            this.spanish_asset1 = BitmapFactory.decodeStream(input_stream);
            this.input_stream = assets_manager.open("images/how_to_play/htp_spanish2.png");
            this.spanish_asset2 = BitmapFactory.decodeStream(input_stream);
            this.input_stream = assets_manager.open("images/how_to_play/htp_spanish3.png");
            this.spanish_asset3 = BitmapFactory.decodeStream(input_stream);

        } catch (IOException e) {
            Log.i("assets", "problem getting the asset");
            throw new RuntimeException(e);
        }

        this.bg_english1 = new Background(Bitmap.createScaledBitmap(this.english_asset1, screen_width + 10, screen_height + 10, true),
                (float) screen_height / 5,
                (float) screen_width);
        this.bg_english2 = new Background(Bitmap.createScaledBitmap(this.english_asset2, screen_width + 10, screen_height + 10, true),
                (float) screen_height,
                (float) screen_width);
        this.bg_english3 = new Background(Bitmap.createScaledBitmap(this.english_asset3, screen_width + 10, screen_height + 10, true),
                (float) screen_height,
                (float) screen_width);

        this.english_instructions[0] = bg_english1;
        this.english_instructions[1] = bg_english2;
        this.english_instructions[2] = bg_english3;
    }

    /**
     * Draws the scene on the canvas.
     *
     * @param canvas The canvas on which the scene should be drawn.
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawText(context.getString(R.string.information_title), (float) screen_width / 10 * 5,
                (float) screen_height / 6, title_paint);

        if (move) {
            canvas.drawBitmap(english_instructions[0].image, english_instructions[0].position.x,
                    english_instructions[0].position.y, null);
        }
    }

    @Override
    public int onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            move = true;
            if(current_image >= english_instructions.length - 1){
                current_image = 0;
            }
            current_image++;
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            move = false;
        }
        return 0;
    }
}

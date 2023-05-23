package com.example.galactic_defender.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.example.galactic_defender.R;

import java.io.IOException;

/**
 * The SceneMenu class represents the menu scene in the game.
 * It extends the Scene class and adds functionality specific to the menu scene.
 * The menu scene displays the game logo, title, and various buttons for different actions.
 * It inherits the drawing and touch event handling from the parent class.
 *
 * @author [Andres Licona]
 * @version [1.0]
 * @since [05-04-2023]
 */
public class SceneMenu extends Scene{

    Bitmap logo;
    Bitmap scale_logo;
    Rect logo_rect;
    Rect records_button, information_button, play_button,credits_button,settings_button;
    Bitmap records_image, information_image, play_image, credits_image,
            settings_image;
    Bitmap scale_records_image, scale_information_image, scale_play_image, scale_credits_image,
            scale_settings_image;
    int scene_number = 1;
    int screen_height, screen_width;

    /**
     * Constructs an instance of the SceneMenu class.
     *
     * @param context The context of the application.
     * @param screen_height The height of the screen.
     * @param screen_width The width of the screen.
     * @param scene_number The number identifying the scene.
     * @exception RuntimeException If there is a problem obtaining the assets
     */
    public SceneMenu(Context context, int screen_height, int screen_width, int scene_number) {
        super(context, screen_height, screen_width, scene_number);
        this.scene_number = scene_number;
        this.screen_height = screen_height;
        this.screen_width = screen_width;

        // Logo
        this.logo = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_icon);
        this.scale_logo = Bitmap.createScaledBitmap(this.logo, screen_width/5, screen_width/5,
                true);
        this.logo_rect = new Rect(screen_width/8, screen_height/7, screen_width/8*3,
                screen_height/7*4);

        // Button images
        try{
            this.input_stream = assets_manager.open("button_icons/records_icon.png");
            this.records_image = BitmapFactory.decodeStream(input_stream);

            this.input_stream = assets_manager.open("button_icons/information_icon.png");
            this.information_image = BitmapFactory.decodeStream(input_stream);

            this.input_stream = assets_manager.open("button_icons/play_icon.png");
            this.play_image = BitmapFactory.decodeStream(input_stream);

            this.input_stream = assets_manager.open("button_icons/credits_icon.png");
            this.credits_image = BitmapFactory.decodeStream(input_stream);

            this.input_stream = assets_manager.open("button_icons/settings_icon.png");
            this.settings_image = BitmapFactory.decodeStream(input_stream);

        } catch (IOException e) {
            Log.i("assets", "problem getting the asset");
            throw new RuntimeException(e);
        }

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

    /**
     * Draws the scene on the canvas.
     *
     * @param canvas The canvas on which the scene should be drawn.
     */
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawBitmap(scale_logo, null, logo_rect, null);
        title_paint.setTextSize((float) screen_height/6);
//        title_paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Galactic", (float) screen_width/5*2,
                (float) screen_height/7*2,
                title_paint);
        canvas.drawText("Defender", (float) screen_width/5*2,
                (float) screen_height/7*3+50,
                title_paint);

        canvas.drawBitmap(scale_records_image, null, records_button, null);
        canvas.drawBitmap(scale_information_image, null, information_button, null);
        canvas.drawBitmap(scale_play_image, null, play_button, null);
        canvas.drawBitmap(scale_credits_image, null, credits_button, null);
        canvas.drawBitmap(scale_settings_image, null, settings_button, null);
    }

    /**
     * Handles touch events on the scene.
     *
     * @param event The MotionEvent representing the touch event.
     * @return The result of the touch event handling.
     *         Returns the corresponding scene number based on the touched button or the result from the super class's onTouchEvent.
     *         Returns -1 if no button was touched and no result was obtained from the super class's onTouchEvent.
     */
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

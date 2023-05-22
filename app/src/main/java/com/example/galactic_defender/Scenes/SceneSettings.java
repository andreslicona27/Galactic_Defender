package com.example.galactic_defender.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.example.galactic_defender.GalacticDefender;
import com.example.galactic_defender.R;

import java.io.IOException;

public class SceneSettings extends Scene {

    GalacticDefender gd_manager;
    Rect sounds_button, music_button, language_button;

    Bitmap sounds_on_icon, sounds_off_icon, music_on_icon, music_off_icon, spanish_icon,
            english_icon;

    Bitmap scale_sounds_on_image, scale_sounds_off_image, scale_music_on_image,
            scale_music_off_image, scale_spanish_image, scale_english_image;

    int scene_number = 5;
    int screen_height, screen_width;

    /**
     * Constructs an instance of the SceneSettings class.
     *
     * @param context The context of the application.
     * @param screen_height The height of the screen.
     * @param screen_width The width of the screen.
     * @param scene_number The number identifying the scene.
     * @exception RuntimeException If there is a problem obtaining the assets
     */
    public SceneSettings(Context context, int screen_height, int screen_width, int scene_number) {
        super(context, screen_height, screen_width, scene_number);
        this.scene_number = scene_number;
        this.screen_height = screen_height;
        this.screen_width = screen_width;
        this.gd_manager = new GalacticDefender(context);

        // Button Images
        try{
            this.input_stream = assets_manager.open("button_icons/sound_on_icon.png");
            this.sounds_on_icon = BitmapFactory.decodeStream(input_stream);

            this.input_stream = assets_manager.open("button_icons/sound_off_icon.png");
            this.sounds_off_icon = BitmapFactory.decodeStream(input_stream);

            this.input_stream = assets_manager.open("button_icons/music_on_icon.png");
            this.music_on_icon = BitmapFactory.decodeStream(input_stream);

            this.input_stream = assets_manager.open("button_icons/music_off_icon.png");
            this.music_off_icon = BitmapFactory.decodeStream(input_stream);

            this.input_stream = assets_manager.open("button_icons/spanish_icon.png");
            this.spanish_icon = BitmapFactory.decodeStream(input_stream);

            this.input_stream = assets_manager.open("button_icons/english_icon.png");
            this.english_icon = BitmapFactory.decodeStream(input_stream);

        } catch (IOException e) {
            Log.i("assets", "problem getting the asset");
            throw new RuntimeException(e);
        }

        // Button scale images
        this.scale_sounds_on_image = Bitmap.createScaledBitmap(this.sounds_on_icon,
                screen_width/8, screen_width/8, true);
        this.scale_sounds_off_image = Bitmap.createScaledBitmap(this.sounds_off_icon,
                screen_width/8, screen_width/8, true);
        this.scale_music_on_image = Bitmap.createScaledBitmap(this.music_on_icon,
                screen_width/8, screen_width/8, true);
        this.scale_music_off_image = Bitmap.createScaledBitmap(this.music_off_icon,
                screen_width/8, screen_width/8, true);
        this.scale_spanish_image = Bitmap.createScaledBitmap(this.spanish_icon,
                screen_width/8, screen_width/8, true);
        this.scale_english_image = Bitmap.createScaledBitmap(this.english_icon,
                screen_width/8, screen_width/8, true);

        // Button rectangles
        this.sounds_button = new Rect(screen_width/13*4, screen_height/6*3,
                screen_width/13*5, screen_height/6*4);
        this.music_button = new Rect(screen_width/13*6, screen_height/6*3, screen_width/13*7,
                screen_height/6*4);
        this.language_button = new Rect(screen_width/13*8, screen_height/6*3,
                screen_width/13*9, screen_height/6*4);
    }

    /**
     * Draws the scene on the canvas.
     *
     * @param canvas The canvas on which the scene should be drawn.
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawText(context.getString(R.string.settings_title),
                (float)screen_width/2 - title_paint.measureText(context.getString(R.string.settings_title))/2,
                (float)screen_height/6,
                title_paint);

        canvas.drawBitmap(GalacticDefender.sounds_button_image, null, sounds_button, null);
        canvas.drawBitmap(GalacticDefender.music_button_image, null, music_button, null);
        canvas.drawBitmap(GalacticDefender.language_button_image, null, language_button, null);
    }


    /**
     * Handles touch events on the scene.
     *
     * @param event The MotionEvent representing the touch event.
     * @return The result of the touch event handling.
     *         Returns the corresponding scene number based on the touched button or the result from the super class's onTouchEvent.
     *         Returns the current scene number if no button was touched.
     */
    public int onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int aux = (int) super.onTouchEvent(event);

        if (aux != scene_number && aux != -1) {
            return aux;
        }

        if (sounds_button.contains(x, y)) {
            GalacticDefender.sounds_button_image = (GalacticDefender.sounds_button_image.equals(sounds_on_icon) ? sounds_off_icon
                    : sounds_on_icon);

        }
        if (music_button.contains(x, y)) {
            if(GalacticDefender.music_button_image.equals(music_on_icon)){
                GalacticDefender.music_button_image = music_off_icon;
                GalacticDefender.background_music.stop();
            } else {
                GalacticDefender.music_button_image = music_on_icon;
                GalacticDefender.background_music.start();
            }
        }
        if (language_button.contains(x, y)) {
            if(GalacticDefender.language.equals("en")){
                gd_manager.changeLanguage("es");
                GalacticDefender.language_button_image = english_icon;
            } else {
                gd_manager.changeLanguage("en");
                GalacticDefender.language_button_image = spanish_icon;
                GalacticDefender.language = GalacticDefender.configuration.locale.getLanguage();
            }
        }
        return this.scene_number;
    }
}

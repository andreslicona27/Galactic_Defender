package com.example.galactic_defender.Scenes;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.GridLayout;

import com.example.galactic_defender.GalacticDefender;
import com.example.galactic_defender.R;

import java.io.IOException;


/**
 * The SceneSettings class represents a settings scene in the Galactic Defender game.
 * It allows the player to adjust sound, music, and language settings.
 *
 * @author [Andres Licona]
 * @version [1.0]
 * @since [05-04-2023]
 */
public class SceneSettings extends Scene {

    /**
     * Use to access the changeLanguage function.
     */
    GalacticDefender gd_manager;

    /**
     * Rectangle area that represents the sound button.
     */
    Rect sounds_button;

    /**
     * Rectangle area that represents the music button.
     */
    Rect music_button;

    /**
     * Rectangle area that represents the language button.
     */
    Rect language_button;

    /**
     * Current image in the sound button.
     */
    Bitmap sounds_button_image;

    /**
     * Current image in the music button.
     */
    Bitmap music_button_image;

    /**
     * Current image in the language button.
     */
    Bitmap language_button_image;

    /**
     * Sound on asset.
     */
    Bitmap sounds_on_icon;

    /**
     * Sound off asset.
     */
    Bitmap sounds_off_icon;

    /**
     * Music on asset.
     */
    Bitmap music_on_icon;

    /**
     * Music off asset.
     */
    Bitmap music_off_icon;

    /**
     * Spanish language asset.
     */
    Bitmap spanish_icon;

    /**
     * English language asset
     */
    Bitmap english_icon;

    /**
     * Sound on image escalated
     */
    Bitmap scale_sounds_on_image;

    /**
     * Sound off image escalated
     */
    Bitmap scale_sounds_off_image;

    /**
     * Music on image escalated
     */
    Bitmap scale_music_on_image;

    /**
     * Music off image escalated
     */
    Bitmap scale_music_off_image;

    /**
     * Spanish language image escalated
     */
    Bitmap scale_spanish_image;

    /**
     * English language image escalated
     */
    Bitmap scale_english_image;

    /**
     * Variable that stores the language value of the shared preferences object.
     */
    String language;

    /**
     * Variable that store the sound effects value of the shared preferences object.
     */
    boolean sound_effects;

    /**
     * Variable that stores the music value of the shared preferences object.
     */
    boolean music;

    /**
     * Represents the scene number of the class.
     */
    int scene_number = 5;

    /**
     * Represents the screen height.
     */
    int screen_height;

    /**
     * Represents the screen width.
     */
    int screen_width;

    /**
     * Constructs an instance of the SceneSettings class.
     *
     * @param context       The context of the application.
     * @param screen_height The height of the screen.
     * @param screen_width  The width of the screen.
     * @param scene_number  The number identifying the scene.
     * @throws RuntimeException If there is a problem obtaining the assets
     */
    public SceneSettings(Context context, int screen_height, int screen_width, int scene_number) {
        super(context, screen_height, screen_width, scene_number);
        this.scene_number = scene_number;
        this.screen_height = screen_height;
        this.screen_width = screen_width;
        this.gd_manager = new GalacticDefender(context);

        // Shared Values
        this.sound_effects = GalacticDefender.shared_preferences.getBoolean("soundEnable", true);
        this.music = GalacticDefender.shared_preferences.getBoolean("musicEnable", true);
        this.language = GalacticDefender.shared_preferences.getString("language", "en");

        // Button Images
        try {
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
                screen_width / 8, screen_width / 8, true);
        this.scale_sounds_off_image = Bitmap.createScaledBitmap(this.sounds_off_icon,
                screen_width / 8, screen_width / 8, true);
        this.scale_music_on_image = Bitmap.createScaledBitmap(this.music_on_icon,
                screen_width / 8, screen_width / 8, true);
        this.scale_music_off_image = Bitmap.createScaledBitmap(this.music_off_icon,
                screen_width / 8, screen_width / 8, true);
        this.scale_spanish_image = Bitmap.createScaledBitmap(this.spanish_icon,
                screen_width / 8, screen_width / 8, true);
        this.scale_english_image = Bitmap.createScaledBitmap(this.english_icon,
                screen_width / 8, screen_width / 8, true);

        // Buttons rectangles
        this.sounds_button = new Rect(screen_width / 13 * 4, screen_height / 6 * 3,
                screen_width / 13 * 5, screen_height / 6 * 4);
        this.music_button = new Rect(screen_width / 13 * 6, screen_height / 6 * 3, screen_width / 13 * 7,
                screen_height / 6 * 4);
        this.language_button = new Rect(screen_width / 13 * 8, screen_height / 6 * 3,
                screen_width / 13 * 9, screen_height / 6 * 4);

        // Buttons images
        this.sounds_button_image = this.sound_effects ? this.sounds_on_icon : this.sounds_off_icon;
        this.music_button_image = this.music ? this.music_on_icon : this.music_off_icon;
        this.language_button_image = this.language.equals("en") ? this.english_icon : spanish_icon;
    }

    /**
     * Draws the scene on the canvas.
     *
     * @param canvas The canvas on which the scene should be drawn.
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawText(context.getString(R.string.settings_title),
                (float) screen_width / 2 - title_paint.measureText((String) context.getString(R.string.settings_title)) / 2,
                (float) screen_height / 6,
                title_paint);

        canvas.drawBitmap(this.sounds_button_image, null, sounds_button, null);
        canvas.drawBitmap(this.music_button_image, null, music_button, null);
        canvas.drawBitmap(this.language_button_image, null, language_button, null);
    }


    /**
     * Handles touch events on the scene.
     *
     * @param event The MotionEvent representing the touch event.
     * @return The result of the touch event handling.
     * Returns the corresponding scene number based on the touched button or the result from the super class's onTouchEvent.
     * Returns the current scene number if no button was touched.
     */
    public int onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int aux = (int) super.onTouchEvent(event);

        if (aux != scene_number && aux != -1) {
            return aux;
        }

        SharedPreferences.Editor editor = GalacticDefender.shared_preferences.edit();
        if (sounds_button.contains(x, y)) {
            this.sounds_button_image = GalacticDefender.soundEnabled ? sounds_on_icon : sounds_off_icon;
            GalacticDefender.soundEnabled = !GalacticDefender.soundEnabled;
            editor.putBoolean("soundEnable", GalacticDefender.soundEnabled);
            editor.apply();
        }

        if (music_button.contains(x, y)) {
            if (GalacticDefender.musicEnabled) {
                this.music_button_image = this.music_off_icon;
                editor.putBoolean("musicEnable", false);

                GalacticDefender.background_music.stop();
                GalacticDefender.musicEnabled = false;
            } else {
                this.music_button_image = this.music_on_icon;
                editor.putBoolean("musicEnable", true);

                GalacticDefender.background_music.start();
                GalacticDefender.musicEnabled = true;
            }
            editor.apply();
        }

        if (language_button.contains(x, y)) {
            if (this.language.equals("en")) {
                gd_manager.changeLanguage("es");
                this.language_button_image = english_icon;
                editor.putString("language", "es");
            } else {
                gd_manager.changeLanguage("en");
                this.language_button_image = spanish_icon;
                this.language = GalacticDefender.configuration.locale.getLanguage();
                editor.putString("language", "en");
            }
            editor.apply();
        }
        return this.scene_number;
    }
}

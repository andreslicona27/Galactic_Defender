package com.example.galactic_defender.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.galactic_defender.GalacticDefender;
import com.example.galactic_defender.R;

public class SceneSettings extends Scene {

    GalacticDefender gd_manager;
    Rect sounds_button, music_button, language_button;
//    Bitmap sounds_button_image = BitmapFactory.decodeResource(context.getResources(), R.drawable.sound_on_icon);
//    Bitmap music_button_image = BitmapFactory.decodeResource(context.getResources(), R.drawable.music_on_icon);
//    Bitmap language_button_image = BitmapFactory.decodeResource(context.getResources(), R.drawable.spanish_icon);

    Bitmap sounds_on_icon, sounds_off_icon, music_on_icon, music_off_icon, spanish_icon,
            english_icon;

    Bitmap scale_sounds_on_image, scale_sounds_off_image, scale_music_on_image,
            scale_music_off_image, scale_spanish_image, scale_english_image;

    int scene_number = 5;
    int screen_height, screen_width;

    public SceneSettings(Context context, int screen_height, int screen_width, int scene_number) {
        super(context, screen_height, screen_width, scene_number);
        this.scene_number = scene_number;
        this.screen_height = screen_height;
        this.screen_width = screen_width;
        this.gd_manager = new GalacticDefender(context);

        // Button Images
        this.sounds_on_icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.sound_on_icon);
        this.sounds_off_icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.sound_off_icon);
        this.music_on_icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.music_on_icon);
        this.music_off_icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.music_off_icon);
        this.spanish_icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.spanish_icon);
        this.english_icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.english_icon);

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

    public void Draw(Canvas canvas) {
        super.Draw(canvas);
        canvas.drawText(context.getString(R.string.settings_title), screen_width/10*5, screen_height/6,
                title_paint);

        canvas.drawBitmap(gd_manager.sounds_button_image, null, sounds_button, null);
        canvas.drawBitmap(gd_manager.music_button_image, null, music_button, null);
        canvas.drawBitmap(gd_manager.language_button_image, null, language_button, null);
    }

    public int onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int aux = (int) super.onTouchEvent(event);

        if (aux != scene_number && aux != -1) {
            return aux;
        }

        if (sounds_button.contains(x, y)) {
            gd_manager.sounds_button_image = (gd_manager.sounds_button_image.equals(sounds_on_icon) ? sounds_off_icon
                    : sounds_on_icon);

        }
        if (music_button.contains(x, y)) {
            if(gd_manager.music_button_image.equals(music_on_icon)){
                gd_manager.music_button_image = music_off_icon;
                gd_manager.background_music.stop();
            } else {
                gd_manager.music_button_image = music_on_icon;
                gd_manager.background_music.start();
            }
        }
        if (language_button.contains(x, y)) {
            if(gd_manager.language_button_image.equals(spanish_icon)){
                gd_manager.language_button_image = english_icon;
                gd_manager.ChangeLanguage("es");
            } else {
                gd_manager.language_button_image = spanish_icon;
                gd_manager.ChangeLanguage("en");
            }

        }

        return this.scene_number;
    }
}

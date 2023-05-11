package com.example.galactic_defender.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.galactic_defender.R;

public class SceneSettings extends Scene {

    Paint paint;
    Rect sounds_button, music_button, language_button;
    Bitmap sounds_button_image = BitmapFactory.decodeResource(context.getResources(), R.drawable.sound_on_icon);
    Bitmap music_button_image = BitmapFactory.decodeResource(context.getResources(), R.drawable.music_on_icon);
    Bitmap language_button_image = BitmapFactory.decodeResource(context.getResources(), R.drawable.spanish_icon);

    Bitmap sounds_on_icon, sounds_off_icon, music_on_icon, music_off_icon, spanish_icon,
            english_icon;

    Bitmap scale_sounds_on_image, scale_sounds_off_image, scale_music_on_image,
            scale_music_off_image, scale_spanish_image, scale_english_image;

    int scene_number = 5;
    int screen_height, screen_width;
    int BACKGROUND = Color.parseColor("#2E5266");

    public SceneSettings(Context context, int screen_height, int screen_width, int scene_number) {
        super(context, screen_height, screen_width, scene_number);
        this.scene_number = scene_number;
        this.screen_height = screen_height;
        this.screen_width = screen_width;
        paint = new Paint();

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
        this.sounds_button = new Rect(screen_width/11*3, screen_height/6*2,
                screen_width/11*4, screen_height/6*3);
        this.music_button = new Rect(screen_width/11*5, screen_height/6*2, screen_width/11*6,
                screen_height/6*3);
        this.language_button = new Rect(screen_width/11*7, screen_height/6*2,
                screen_width/11*8, screen_height/6*3);
    }

    public void Draw(Canvas canvas) {
        canvas.drawColor(BACKGROUND);
        super.Draw(canvas);

        canvas.drawBitmap(sounds_button_image, null, sounds_button, null);
        canvas.drawBitmap(music_button_image, null, music_button, null);
        canvas.drawBitmap(language_button_image, null, language_button, null);
    }

    public int onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int aux = (int) super.onTouchEvent(event);

        if (aux != scene_number && aux != -1) {
            return aux;
        }

        if (sounds_button.contains(x, y)) {
            sounds_button_image = (sounds_button_image.equals(sounds_on_icon) ? sounds_off_icon : sounds_on_icon);

        }
        if (music_button.contains(x, y)) {
            music_button_image = (music_button_image.equals(music_on_icon) ? music_off_icon : music_on_icon);

        }
        if (language_button.contains(x, y)) {
            language_button_image = (language_button_image.equals(spanish_icon) ? english_icon : spanish_icon);
        }

        return this.scene_number;
    }
}

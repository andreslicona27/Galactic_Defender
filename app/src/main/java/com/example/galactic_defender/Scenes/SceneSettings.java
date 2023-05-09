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

    Bitmap sounds_on_icon;
    Bitmap sounds_off_icon;
    Bitmap music_on_icon;
    Bitmap music_off_icon;
    Bitmap spanish_icon;
    Bitmap english_icon;

    int scene_number = 5;
    int screen_height, screen_width;
    int BACKGROUND = Color.parseColor("#2E5266");

    public SceneSettings(Context context, int screen_height, int screen_width, int scene_number) {
        super(context, screen_height, screen_width, scene_number);
        this.scene_number = scene_number;
        this.screen_height = screen_height;
        this.screen_width = screen_width;
        paint = new Paint();

        sounds_on_icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.sound_on_icon);
        sounds_off_icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.sound_off_icon);
        music_on_icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.music_on_icon);
        music_off_icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.music_off_icon);
        spanish_icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.spanish_icon);
        english_icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.english_icon);

        this.sounds_button = new Rect(screen_width / 10 * 2, screen_height / 5, screen_width / 10 * 3, screen_height / 5 * 2);
        this.music_button = new Rect(screen_width / 10 * 4, screen_height / 5, screen_width / 10 * 5, screen_height / 5 * 2);
        this.language_button = new Rect(screen_width / 10 * 6, screen_height / 5, screen_width / 10 * 7, screen_height / 5 * 2);
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

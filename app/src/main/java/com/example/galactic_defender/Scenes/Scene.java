package com.example.galactic_defender.Scenes;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.galactic_defender.GalacticDefender;
import com.example.galactic_defender.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * The Scene class represents a scene in the game.
 * It encapsulates the scene's graphics, touch event handling, and physics update.
 * Each scene has a unique scene number and can be drawn on a canvas.
 * The scene can also handle touch events and provide information about the touch event handling
 * result.
 *
 * @author [Andres Licona]
 * @version [1.0]
 * @since [05-04-2023]
 */
public class Scene {

    Context context;
    InputStream input_stream;
    AssetManager assets_manager;
    Typeface font;
    Paint title_paint;
    Paint window_paint;
    Rect back_button;
    RectF base;
    RectF home_button, resume_button;
    Bitmap back_button_image;
    Bitmap scale_button_image;
    public int scene_number = -1;
    int screen_height, screen_width;
    boolean game_over = false;

    /**
     * Constructs an instance of the Scene class.
     *
     * @param context       The context of the application.
     * @param screen_height The height of the screen.
     * @param screen_width  The width of the screen.
     * @param scene_number  The number identifying the scene.
     */
    public Scene(Context context, int screen_height, int screen_width, int scene_number) {
        this.context = context;
        this.scene_number = scene_number;
        this.screen_height = screen_height;
        this.screen_width = screen_width;

        this.font = Typeface.createFromAsset(context.getAssets(), "font/russo_one.ttf");
        this.title_paint = new Paint();
        this.title_paint.setAlpha(240);
        this.title_paint.setTypeface(font);
        this.title_paint.setAntiAlias(true);
        this.title_paint.setColor(Color.WHITE);
        this.title_paint.setTextSize((float) screen_height / 10);
        this.title_paint.setTextAlign(Paint.Align.CENTER);

        this.window_paint = new Paint();
        base = new RectF((float) screen_width / 5, (float) screen_height / 5, (float) screen_width / 5 * 4,
                (float) screen_height / 5 * 4);

        try {
            this.assets_manager = context.getAssets();
            this.input_stream = assets_manager.open("button_icons/home_icon.png");
            this.back_button_image = BitmapFactory.decodeStream(input_stream);

        } catch (IOException e) {
            Log.i("assets", "problem getting the asset");
            throw new RuntimeException(e);
        }

        this.scale_button_image = Bitmap.createScaledBitmap(this.back_button_image,
                screen_width / 8, screen_width / 8, true);
        this.back_button = new Rect(screen_width / 20, screen_height / 12, screen_width / 20 + 50
                , screen_height / 12 + 50);


    }

    /**
     * Draws the scene on the canvas.
     *
     * @param canvas The canvas on which the scene should be drawn.
     */
    public void draw(Canvas canvas) {
        canvas.drawColor(ContextCompat.getColor(context, R.color.main_blue));
        if (scene_number != 1 && scene_number != 3) {
            canvas.drawBitmap(scale_button_image, null, back_button, null);
        }
    }

    /**
     * Updates the physics of the scene.
     * This function is responsible for handling the physics calculations and updating the scene accordingly.
     * Add any physics-related logic or calculations within this function.
     */
    public void updatePhysics() {

    }

    /**
     * Handles the touch events on the scene.
     *
     * @param event The MotionEvent representing the touch event.
     * @return The result of the touch event handling.
     * Returns 1 if the back button was touched (in scenes other than 1 and 3), otherwise returns -1.
     */
    public int onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (scene_number != 1 && scene_number != 3) {
            if (back_button.contains(x, y)) {
                return 1;
            }
        }
        return -1;
    }

    public void onDestroy() {

    }

    public void gameWindow(Canvas canvas, boolean game_over) {
        this.game_over = game_over;
        // Base Rectangle
        this.window_paint.setStyle(Paint.Style.FILL);
        this.title_paint.setTextAlign(Paint.Align.CENTER);
        this.window_paint.setColor(ContextCompat.getColor(context, R.color.secondary_blue));
        canvas.drawRoundRect(base, 20f, 20f, window_paint);

        // Title Text
        this.window_paint.setColor(ContextCompat.getColor(context, R.color.main_yellow));
        this.window_paint.setTypeface(font);
        this.window_paint.setTextSize((float) screen_height / 8);
        if (game_over) {
            canvas.drawText((String) context.getText(R.string.game_over_title),
                    (float) screen_width / 5 * 2,
                    (float) screen_height / 7 * 2, this.window_paint);
            canvas.drawText(String.valueOf(GalacticDefender.score), (float) screen_width / 5 * 2,
                    (float) screen_height / 7 * 3,
                    this.window_paint);
        } else {
            canvas.drawText((String) context.getText(R.string.pause_title), (float) screen_width / 5 * 2,
                    (float) screen_height / 7 * 3, this.window_paint);
        }

        // Buttons
        window_paint.setColor(ContextCompat.getColor(context, R.color.main_yellow));
        canvas.drawRoundRect(this.home_button, 20f, 20f, window_paint);
        canvas.drawRoundRect(this.resume_button, 20f, 20f, window_paint);

        // Buttons Text
        window_paint.setColor(ContextCompat.getColor(context, R.color.secondary_blue));
        window_paint.setTextSize((float) screen_height / 15);
        if (!game_over) {
            canvas.drawText((String) context.getString(R.string.home_button),
                    (float) screen_width / 7 * 4, (float) screen_height / 12 * 8, window_paint);
        } else {
            canvas.drawText((String) context.getString(R.string.home_button), (float) screen_width / 7 * 2, (float) screen_height / 12 * 8, window_paint);
            canvas.drawText((String) context.getString(R.string.resume_button), (float) screen_width / 7 * 4, (float) screen_height / 12 * 8, window_paint);
        }


    }
}

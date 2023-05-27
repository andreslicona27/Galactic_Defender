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

    /**
     * The context object that provides access to the application-specific resources and operations.
     */
    Context context;

    /**
     * The input stream used for reading data.
     */
    InputStream input_stream;

    /**
     * The asset manager used for accessing application's asset files.
     */
    AssetManager assets_manager;

    /**
     * The Typeface object representing a custom font.
     */
    Typeface font;

    /**
     * represents the paint in charge of the titles of the game.
     */
    Paint title_paint;

    /**
     * Represents the paint use in the drawing of the pause and game over window.
     */
    Paint window_paint;

    /**
     * Rectangle representing the back button.
     */
    Rect back_button;

    /**
     * Rectangle with floating-point coordinates representing the base of the pause anf game over window.
     */
    RectF base;

    /**
     * Rectangle representing the first home button.
     */
    RectF home_button1;

    /**
     * Rectangle representing the second home button.
     */
    RectF home_button2;

    /**
     * Rectangle representing the resume button.
     */
    RectF resume_button;

    /**
     * The asset for the back button image.
     */
    Bitmap back_button_image;

    /**
     * The image for the back button escalated
     */
    Bitmap scale_button_image;

    /**
     * Represents the scene number of the class.
     */
    public int scene_number = -1;

    /**
     * Represents the height of the screen.
     */
    int screen_height;

    /**
     * Represents the width of the screen.
     */
    int screen_width;

    /**
     * Constructs an instance of the Scene class.
     *
     * @param context       The context of the application.
     * @param screen_height The height of the screen.
     * @param screen_width  The width of the screen.
     * @param scene_number  The number identifying the scene.
     * @exception RuntimeException If there is a problem obtaining the assets
     */
    public Scene(Context context, int screen_height, int screen_width, int scene_number) {
        this.context = context;
        this.scene_number = scene_number;
        this.screen_height = screen_height;
        this.screen_width = screen_width;

        // Paint for the titles of the scenes
        this.font = Typeface.createFromAsset(context.getAssets(), "font/russo_one.ttf");
        this.title_paint = new Paint();
        this.title_paint.setAlpha(240);
        this.title_paint.setTypeface(font);
        this.title_paint.setAntiAlias(true);
        this.title_paint.setColor(Color.WHITE);
        this.title_paint.setTextSize((float) screen_height / 10);

        // Paint for the text int he window of pause and game over
        this.window_paint = new Paint();
        this.window_paint.setAlpha(240);
        this.window_paint.setTypeface(font);
        this.window_paint.setAntiAlias(true);
        this.window_paint.setStyle(Paint.Style.FILL);
        this.window_paint.setColor(ContextCompat.getColor(context, R.color.secondary_blue));
        base = new RectF((float) screen_width / 5, (float) screen_height / 5, (float) screen_width / 5 * 4,(float) screen_height / 5 * 4);

        try {
            this.assets_manager = context.getAssets();
            this.input_stream = assets_manager.open("button_icons/home_icon.png");
            this.back_button_image = BitmapFactory.decodeStream(input_stream);

        } catch (IOException e) {
            Log.i("assets", "problem getting the asset");
            throw new RuntimeException(e);
        }

        // Back button of the scenes
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


    /**
     * Displays a pause window on the canvas.
     *
     * @param canvas The canvas on which to draw the pause window.
     */
    public void pauseWindow(Canvas canvas) {
        // Base Rectangle
        canvas.drawRoundRect(base, 20f, 20f, window_paint);

        // Title Text
        this.window_paint.setColor(ContextCompat.getColor(context, R.color.main_yellow));
        this.window_paint.setTextSize((float) screen_height / 8);
        canvas.drawText((String) context.getText(R.string.pause_title),
                (float) screen_width / 2 - window_paint.measureText((String) context.getText(R.string.pause_title))/2,
                (float) screen_height / 7 * 3, this.window_paint);

        // Buttons
        canvas.drawRoundRect(this.home_button1, 20f, 20f, window_paint);
        canvas.drawRoundRect(this.resume_button, 20f, 20f, window_paint);

        // Buttons Text
        window_paint.setColor(ContextCompat.getColor(context, R.color.secondary_blue));
        window_paint.setTextSize((float) screen_height / 15);
        canvas.drawText((String) context.getString(R.string.home_button), (float) screen_width / 7 * 2, (float) screen_height / 12 * 8, window_paint);
        canvas.drawText((String) context.getString(R.string.resume_button), (float) screen_width / 7 * 4, (float) screen_height / 12 * 8, window_paint);
    }

    /**
     * Displays a game over window on the canvas.
     *
     * @param canvas The canvas on which to draw the pause window.
     */
    public void gameOverWindow(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        // Base Rectangle
        canvas.drawRoundRect(base, 20f, 20f, window_paint);

        // Title Text
        this.window_paint.setColor(ContextCompat.getColor(context, R.color.main_yellow));
        this.window_paint.setTextSize((float) screen_height / 8);
        canvas.drawText((String) context.getText(R.string.game_over_title),
                ((float) screen_width / 2) - window_paint.measureText((String) context.getText(R.string.game_over_title)) / 2,
                (float) screen_height / 8 * 3, this.window_paint);
        canvas.drawText(String.valueOf(GalacticDefender.score),
                (float) screen_width / 2 - window_paint.measureText(String.valueOf(GalacticDefender.score)) / 2,
                (float) screen_height / 8 * 4,
                this.window_paint);

        // Buttons
        canvas.drawRoundRect(this.home_button2, 20f, 20f, window_paint);

        // Buttons Text
        window_paint.setColor(ContextCompat.getColor(context, R.color.secondary_blue));
        window_paint.setTextSize((float) screen_height / 15);
        canvas.drawText((String) context.getString(R.string.home_button),
                (float) screen_width / 2 - window_paint.measureText((String) context.getString(R.string.home_button)) / 2,
                (float) screen_height / 12 * 8,
                window_paint);

    }
}

package com.example.galactic_defender.Utilities;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.galactic_defender.R;
import com.example.galactic_defender.Scenes.SceneGame;

import java.io.IOException;
import java.io.InputStream;


/**
 * The FireButton class represents a button used for firing in the game.
 * It allows the player to perform shooting actions by tapping on the button.
 */
public class FireButton {

    Context context;
    InputStream input_stream;
    AssetManager assets_manager;
    Bitmap scale_shot_image, images;
    Paint paint;
    SceneGame scene_game;
    int radius = 100;
    int screen_width, screen_height;
    int center_x, center_y;
    int shot_velocity = 8;
    int images_width;
    int images_height;
    int row = 1, column = 0;


    /**
     * Constructs a FireButton object.
     *
     * @param scene_game    The SceneGame instance to which this button belongs
     * @param context       The context of the application
     * @param screen_width  The width of the screen
     * @param screen_height The height of the screen
     */
    public FireButton(SceneGame scene_game, Context context, int screen_width, int screen_height) {
        this.scene_game = scene_game;
        this.context = context;
        this.screen_width = screen_width;
        this.screen_height = screen_height;
        this.center_x = screen_width / 10;
        this.center_y = screen_height / 15;

        this.paint = new Paint();
        this.paint.setColor(ContextCompat.getColor(context, R.color.main_yellow));
        this.assets_manager = context.getAssets();

        try {
            this.input_stream = assets_manager.open("utilities/projectiles.png");
            this.images = BitmapFactory.decodeStream(input_stream);

        } catch (IOException e) {
            Log.i("assets", "problem getting the asset");
            throw new RuntimeException(e);
        }

        this.images_width = this.images.getWidth();
        this.images_height = this.images.getHeight();
        this.scale_shot_image = Bitmap.createBitmap(this.images, 0, this.images_height / 4,
                this.images_width / 4, this.images_height / 4);
    }


    // FUNCTIONS

    /**
     * Draws the fire button on the canvas.
     *
     * @param canvas the canvas on which to draw the fire button
     */
    public void drawFireButton(Canvas canvas) {
        canvas.drawCircle((float) this.screen_width / 20 * 18, (float) this.screen_height / 20 * 16, radius
                , this.paint);
    }

    /**
     * Updates the image of the fire button based on the current row and column values
     */
    public void updateImage() {
        int pos_x = (column % 4) * this.images_height / 4;
        int pos_y = row * this.images_width / 4;
        this.scale_shot_image = Bitmap.createBitmap(this.images, pos_x, pos_y, this.images_width / 4,
                this.images_height / 4);

    }

    /**
     * Handles touch events on the fire button.
     *
     * @param event the motion event representing the touch
     */
    public void touchEvent(MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();
        int action = event.getActionMasked();

        if (action == MotionEvent.ACTION_DOWN) {
            float side_a = Math.abs(x - this.center_x);
            float side_b = Math.abs(y - this.center_y);
            float hypotenuse = (float) Math.hypot(side_a, side_b);

            if (hypotenuse <= this.radius) {
                Log.i("test", "we try to fire the gun");
                updateImage();
            }
        }
    }

}
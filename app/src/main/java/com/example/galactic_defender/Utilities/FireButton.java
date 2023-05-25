package com.example.galactic_defender.Utilities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.galactic_defender.Characters.Shot;
import com.example.galactic_defender.Characters.Spaceship;
import com.example.galactic_defender.R;
import com.example.galactic_defender.Scenes.SceneGame;


/**
 * The FireButton class represents a button used for firing in the game.
 * It allows the player to perform shooting actions by tapping on the button.
 */
public class FireButton {

    /**
     * Represents the attribute of context in the character class
     */
    Context context;
    /**
     * Represents a spaceship and is use to access the position attribute of spaceship
     */
    Spaceship spaceship;
    /**
     * Represents a scene of the game and is use to access the arraylist of the spaceships shots
     */
    SceneGame scene_game;
    /**
     * Represents the paint used for drawing.
     */
    public Paint paint;
    /**
     * Represents the radius of the fire button.
     */
    int radius;
    /**
     * Represents the width of the screen
     */
    int screen_width;
    /**
     * Represents the height of the screen
     */
    int screen_height;
    /**
     * Represents the center  of the fire button in the x coordinate
     */
    int center_x;
    /**
     * Represents the center  of the fire button in the y coordinate
     */
    int center_y;


    /**
     * Constructs a FireButton object.
     *
     * @param scene_game    The scene_game instance to which this button belongs
     * @param spaceship     The spaceship instance for it to access to the spaceship position
     * @param context       The context of the application
     * @param screen_width  The width of the screen
     * @param screen_height The height of the screen
     */
    public FireButton(SceneGame scene_game, Spaceship spaceship, Context context, int screen_width, int screen_height) {
        this.scene_game = scene_game;
        this.spaceship = spaceship;
        this.context = context;
        this.screen_width = screen_width;
        this.screen_height = screen_height;
        this.center_x = screen_width / 10;
        this.center_y = screen_height / 15;
        this.radius = 100;

        this.paint = new Paint();
        this.paint.setColor(ContextCompat.getColor(context, R.color.main_yellow));
    }


    /**
     * Draws the fire button on the canvas.
     *
     * @param canvas the canvas on which to draw the fire button
     */
    public void drawFireButton(Canvas canvas) {
        canvas.drawCircle((float) this.screen_width / 20 * 18, (float) this.screen_height / 20 * 16, radius, this.paint);
    }


    /**
     * Handles touch events on the fire button.
     *
     * @param event the motion event representing the touch
     */
    public void touchEvent(MotionEvent event) {
        int pointer_index = event.getActionIndex();
        float x = event.getX(pointer_index);
        float y = event.getY(pointer_index);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                // Calculate if the touch on the screen was inside the circle
                int circle_x = this.screen_width - this.radius - 10;
                int circle_y = this.screen_height - this.radius - 10;
                int distance = (int) Math.sqrt(Math.pow(circle_x - x, 2) + Math.pow(circle_y - y, 2));

                if (distance <= this.radius) {
                    Shot shot = new Shot(context, screen_width, screen_height, this.spaceship.position.x, this.spaceship.position.y,
                            this.spaceship.spaceship_angle);
                    this.scene_game.spaceship_shots.add(shot);
                }
                break;
        }
    }

}
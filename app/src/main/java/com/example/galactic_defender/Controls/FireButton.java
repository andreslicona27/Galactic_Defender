package com.example.galactic_defender.Controls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.galactic_defender.Characters.Shot;
import com.example.galactic_defender.Characters.Spaceship;
import com.example.galactic_defender.R;
import com.example.galactic_defender.Scenes.SceneGame;


/**
 * The FireButton class represents a button used for firing in the game.
 * It allows the player to perform shooting actions by tapping on the button.
 *
 * @author [Andres Licona]
 * @version [1.0]
 * @since [05-10-2023]
 */
public class FireButton extends Control {

    /**
     * Represents a scene of the game and is use to access the arraylist of the spaceships shots
     */
    SceneGame scene_game;

    /**
     * The MediaPlayer object for playing the sound effect when a laser is shot.
     */
    MediaPlayer laser_shot;

    /**
     * Represents the circle area of the fire button
     */
    Circle fire_button;

    /**
     * Represents the paint used for drawing.
     */
    public Paint fire_button_paint;

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
        super(spaceship, context, screen_width, screen_height);
        this.scene_game = scene_game;

        // Media player asset
        this.laser_shot = MediaPlayer.create(context.getApplicationContext(), R.raw.laser_shot);

        // Paint
        this.fire_button_paint = new Paint();
        this.fire_button_paint.setColor(ContextCompat.getColor(context, R.color.main_yellow));

        // Fire button
        this.fire_button = new Circle(new PointF((float) this.screen_width / 20 * 18, (float) this.screen_height / 20 * 16), common_radius);
    }


    /**
     * Draws the fire button on the canvas.
     *
     * @param canvas the canvas on which to draw the fire button
     */
    public void draw(Canvas canvas) {
        canvas.drawCircle(this.fire_button.getCenter().x, this.fire_button.getCenter().y, this.fire_button.getRadius(), this.fire_button_paint);
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
                if (this.fire_button.contains(x, y)) {
                    Shot shot = new Shot(context, screen_width, screen_height, this.spaceship.position.x, this.spaceship.position.y,
                            this.spaceship.spaceship_angle);
                    this.scene_game.spaceship_shots.add(shot);
                    this.laser_shot.start();
                }
                break;
        }

    }

}
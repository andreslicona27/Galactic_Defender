package com.example.galactic_defender.Utilities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Space;


import androidx.core.content.ContextCompat;

import com.example.galactic_defender.Characters.Spaceship;
import com.example.galactic_defender.R;
import com.example.galactic_defender.Scenes.SceneGame;


/**
 * The JoyStick class represents a joystick control used in a game scene.
 * It allows the player to control the movement of the player character.
 *
 * @author [Andres Licona]
 * @version [1.0]
 * @since [05-10-2023]
 */
public class JoyStick {

    SceneGame scene_game;
    Spaceship spaceship;
    Paint outer_paint, inner_paint;
    Point inner_circle_position;
    Canvas canvas;
    float center_x;
    float center_y;
    float outer_radius = 100;
    float inner_radius = 40;
    float touch_x, touch_y;
    int screen_width, screen_height;

    // BUILDER

    /**
     * Constructs an instance of the JoyStick class.
     *
     * @param spaceship     The class that it would referred
     * @param context       The context of the application.
     * @param screen_width  The width of the screen.
     * @param screen_height The height of the screen.
     */
    public JoyStick(Spaceship spaceship, Context context, int screen_width, int screen_height) {
        this.spaceship = spaceship;
        this.screen_width = screen_width;
        this.screen_height = screen_height;

        this.outer_paint = new Paint();
        this.outer_paint.setStyle(Paint.Style.STROKE);
        this.outer_paint.setStrokeWidth(5);
        this.outer_paint.setColor(ContextCompat.getColor(context, R.color.main_yellow));

        this.inner_paint = new Paint();
        this.inner_paint.setColor(ContextCompat.getColor(context, R.color.main_yellow));

        this.center_x = (float) this.screen_width / 20 * 2;
        this.center_y = (float) this.screen_width / 20 * 16;
        this.inner_circle_position = new Point(this.screen_width / 20 * 2, this.screen_height / 20 * 16);
    }


    /**
     * Function that draws the joystick
     *
     * @param canvas for it to  draw the graphics in the canvas
     */
    public void drawJoystick(Canvas canvas) {
        this.canvas = canvas;
        canvas.drawCircle((float) this.screen_width / 20 * 2, (float) this.screen_height / 20 * 16, outer_radius, outer_paint);
        canvas.drawCircle(this.inner_circle_position.x, this.inner_circle_position.y, inner_radius, inner_paint);
    }

    /**
     * Handles touch events for the joystick.
     *
     * @param event The MotionEvent object representing the touch event.
     */
    public void touchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex(); // obtains the action finger
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);

        this.center_x = (float) this.screen_width / 20 * 2;
        this.center_y = (float) this.screen_height / 20 * 16;

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                // Trigonometry
                float side_a = Math.abs(x - center_x);
                float side_b = Math.abs(y - center_y);
                float hypotenuse = (float) Math.hypot(side_a, side_b);

                if (hypotenuse <= outer_radius) {
                    touch_x = x;
                    touch_y = y;
                }

                break;
            case MotionEvent.ACTION_MOVE:
                touch_x = event.getX(pointerIndex);
                touch_y = event.getY(pointerIndex);

                float difference_x = center_x - touch_x;
                float difference_y = center_y - touch_y;

                float as = Math.abs(x - this.center_x);
                float es = Math.abs(y - this.center_y);
                float is = (float) Math.hypot(as, es);

//                if (is <= outer_radius) {
//                    canvas.drawCircle((int) touch_x, (int) touch_y, inner_radius, inner_paint);
//                } else {
//                    canvas.drawCircle(center_x, center_y, inner_radius, inner_paint);
//                }
                this.spaceship.setPlayerMoveTrue(new PointF(difference_x, difference_y));

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                this.spaceship.setPlayerMoveFalse();
                break;
        }
    }


}

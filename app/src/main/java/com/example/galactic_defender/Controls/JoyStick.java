package com.example.galactic_defender.Controls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;


import androidx.core.content.ContextCompat;

import com.example.galactic_defender.Characters.Spaceship;
import com.example.galactic_defender.R;


/**
 * The JoyStick class represents a joystick control used in a game scene.
 * It allows the player to control the movement of the player character.
 *
 * @author [Andres Licona]
 * @version [1.0]
 * @since [05-10-2023]
 */
public class JoyStick extends Control {

    /**
     * The outer paint of the joystick
     */
    Paint outer_paint;

    /**
     * The position of the inner circle
     */
    Point inner_circle_position;

    /**
     * Represents the circle area of the outer part of the joystick
     */
    Circle joystick;

    /**
     * The x coordinate of the center of the joystick
     */
    float center_x;

    /**
     * The y coordinate of the center of the joystick
     */
    float center_y;

    /**
     * Constants that defines radius of the inner circle of the joystick
     */
    float inner_radius = 40;


    /**
     * Constructs an instance of the JoyStick class.
     *
     * @param spaceship     The class that it would referred
     * @param context       The context of the application.
     * @param screen_width  The width of the screen.
     * @param screen_height The height of the screen.
     */
    public JoyStick(Spaceship spaceship, Context context, int screen_width, int screen_height) {
        super(spaceship, context, screen_width, screen_height);
        // Outer paint
        this.outer_paint = new Paint();
        this.outer_paint.setStyle(Paint.Style.STROKE);
        this.outer_paint.setStrokeWidth(5);
        this.outer_paint.setColor(ContextCompat.getColor(context, R.color.main_yellow));

        // Position values
        this.center_x = (float) this.screen_width / 20 * 2;
        this.center_y = (float) this.screen_height / 20 * 16;
        this.inner_circle_position = new Point(this.screen_width / 20 * 2, this.screen_height / 20 * 16);

        // Outer circle of the joystick
        this.joystick = new Circle(new PointF((float) this.screen_width / 20 * 2,(float) this.screen_height / 20 * 16), common_radius);
    }


    /**
     * Function that draws the joystick
     *
     * @param canvas for it to  draw the graphics in the canvas
     */
    public void draw(Canvas canvas) {
        canvas.drawCircle(this.joystick.getCenter().x, this.joystick.getCenter().y, this.joystick.getRadius(), outer_paint);
        canvas.drawCircle(this.inner_circle_position.x, this.inner_circle_position.y, this.inner_radius, common_paint);
    }

    /**
     * Handles touch events for the joystick.
     *
     * @param event The MotionEvent object representing the touch event.
     */
    public void touchEvent(MotionEvent event) {
        float touch_x;
        float touch_y;
        int pointerIndex = event.getActionIndex(); // obtains the action finger
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_MOVE:
                if(this.joystick.contains(x, y)){
                    // PLAYER MOVEMENT
                    touch_x = event.getX(pointerIndex);
                    touch_y = event.getY(pointerIndex);

                    float difference_x = center_x - touch_x;
                    float difference_y = center_y - touch_y;
                    this.spaceship.setPlayerMoveTrue(new PointF(difference_x, difference_y));

                    // JOYSTICK MOVEMENT
                    float angle = (float) Math.atan2(center_y - y, center_x - x);
                    float distance = (float) Math.sqrt(Math.pow(center_x - x, 2) + Math.pow(center_y - y, 2));

                    if (distance <= common_radius) {
                        // Calculates the inner circle position in the outer circle
                        this.inner_circle_position.x = (int) (center_x - (float) Math.cos(angle) * distance);
                        this.inner_circle_position.y = (int) (center_y - (float) Math.sin(angle) * distance);
                    } else {
                        // Limits the inner circle to the border of th outer circle in the case that the finer is out of both circles
                        this.inner_circle_position.x = (int) (center_x - (float) Math.cos(angle) * common_radius);
                        this.inner_circle_position.y = (int) (center_y - (float) Math.sin(angle) * common_radius);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                this.spaceship.setPlayerMoveFalse();

                // Returns inner circle to the initial position
                this.inner_circle_position.x = (int) this.center_x;
                this.inner_circle_position.y = (int) this.center_y;
                break;
        }
    }
}

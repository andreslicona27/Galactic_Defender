package com.example.galactic_defender.Controls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.galactic_defender.Characters.Spaceship;
import com.example.galactic_defender.R;

/**
 * The Control class represents a game control like joystick or fire button in the Galactic Defender game.
 *
 * @author [Andres Licona]
 * @version [1.0]
 * @since [05-28-2023]
 */
public class Control {

    /**
     * Represents the attribute of context in the control class.
     */
    Context context;

    /**
     * Represents a spaceship and is use to access the some spaceship attributes.
     */
    Spaceship spaceship;

    /**
     * Represents the paint used for drawing.
     */
    public Paint common_paint;

    /**
     * Represents the radius of the controls.
     */
    int common_radius;

    /**
     * Represents the width of the screen.
     */
    int screen_width;

    /**
     * Represents the height of the screen.
     */
    int screen_height;

    /**
     * Constructs an instance of the Control class.
     *
     * @param spaceship     The spaceship instance to accessed the spaceship attributes.
     * @param context       The context of the application.
     * @param screen_width  The width of the screen.
     * @param screen_height The height of the screen.
     */
    public Control(Spaceship spaceship, Context context, int screen_width, int screen_height){
        this.spaceship = spaceship;
        this.context = context;
        this.screen_width = screen_width;
        this.screen_height = screen_height;
        this.common_radius = 100;

        this.common_paint = new Paint();
        this.common_paint.setColor(ContextCompat.getColor(context, R.color.main_yellow));
    }

    /**
     * Draws the control on the canvas.
     *
     * @param canvas The canvas to draw on.
     */
    public void draw(Canvas canvas){

    }

    /**
     * Handles the touch events on the element.
     *
     * @param event The MotionEvent representing the touch event.
     */
    public void touchEvent(MotionEvent event){

    }


}

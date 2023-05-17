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


import androidx.core.content.ContextCompat;

import com.example.galactic_defender.R;
import com.example.galactic_defender.Scenes.SceneGame;

public class JoyStick {

    SceneGame scene_game;
    public Rect joystick_rect;
    Paint outer_paint, inner_paint, test_paint;
    Point inner_circle_position;
    float center_x;
    float center_y;
    float outer_radius = 100;
    float inner_radius = 50;
    float touch_x, touch_y;
    int screen_width, screen_height;
    boolean touch_down;

    // BUILDER
    public JoyStick(SceneGame scene_game, Context context, int screen_width, int screen_height) {
        this.scene_game = scene_game;
        this.screen_width = screen_width;
        this.screen_height = screen_height;

        this.outer_paint = new Paint();
        this.outer_paint.setStyle(Paint.Style.STROKE);
        this.outer_paint.setStrokeWidth(5);
        this.outer_paint.setColor(ContextCompat.getColor(context, R.color.main_yellow));

        this.inner_paint = new Paint();
        this.inner_paint.setColor(ContextCompat.getColor(context, R.color.main_yellow));

        this.center_x = (float) this.screen_width/20*2;
        this.center_y = (float) this.screen_width/20*16;
        this.inner_circle_position = new Point(this.screen_width/20*2, this.screen_height/20*16);
    }

    // FUNCTIONS
    /**
     * Function that draws the joystick
     * @param canvas for it to  draw the graphics in the canvas
     * */
    public void drawJoystick(Canvas canvas) {
        canvas.drawCircle((float)this.screen_width/20*2, (float)this.screen_height/20*16, outer_radius, outer_paint);
        canvas.drawCircle(this.inner_circle_position.x, this.inner_circle_position.y, inner_radius, inner_paint);
    }

    public void touchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();
        int action = event.getActionMasked();
        this.center_x = (float) this.screen_width/20*2;
        this.center_y = (float) this.screen_width/20*16;

        Log.i("test", "joystick touch ");
        switch (action){
            case MotionEvent.ACTION_DOWN:
                Log.i("test", "down ");

                // Trigonometry
                float side_a = Math.abs(x - center_x);
                float side_b = Math.abs(y - center_y);
                float hypotenuse = (float)Math.hypot(side_a, side_b);

                if(hypotenuse <= outer_radius){
                    touch_down = true;
                    touch_x = x;
                    touch_y = y;
                }

                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("test", "move");
                if(touch_down){
                    touch_x = event.getX();
                    touch_y = event.getY();
                    Log.i("test", "movement");

                    float difference_x = touch_x - center_x;
                    float difference_y = touch_y - center_y;

                    if(this.inner_circle_position.x <= touch_x && this.inner_circle_position.y <= touch_y){
                        this.inner_circle_position = new Point((int) touch_x, (int) touch_y);
                    } else {
                        this.inner_circle_position = new Point((int) this.center_x, (int) this.center_y);
                    }

                    this.scene_game.setPlayerMoveTrue(new PointF(difference_x, difference_y));
                }
                break;
            case MotionEvent.ACTION_UP:
                touch_down = false;
                this.scene_game.setPlayerMoveFalse();
                break;
        }
    }


}


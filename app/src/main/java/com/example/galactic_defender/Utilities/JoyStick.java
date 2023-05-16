package com.example.galactic_defender.Utilities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;


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
    int palette_color = Color.parseColor("#F2C055");
    boolean touch_down;

    // BUILDER


    public JoyStick(SceneGame scene_game, int screen_width, int screen_height) {
        this.scene_game = scene_game;
        this.screen_width = screen_width;
        this.screen_height = screen_height;

        this.outer_paint = new Paint();
        this.outer_paint.setStyle(Paint.Style.STROKE);
        this.outer_paint.setStrokeWidth(5);
        this.outer_paint.setColor(palette_color);

        this.inner_paint = new Paint();
        this.inner_paint.setColor(palette_color);

        this.inner_circle_position = new Point(this.screen_width/20*2, this.screen_height/20*16);

        // test
        this.test_paint = new Paint();
        this.test_paint.setColor(Color.RED);
    }

    // FUNCTIONS
    /**
     * Function that draws the joystick
     * @param canvas for it to  draw the graphics in the canvas
     * */
    public void drawJoystick(Canvas canvas) {
        canvas.drawCircle((float)this.screen_width/20*2, (float)this.screen_height/20*16, outer_radius, outer_paint);
        canvas.drawCircle((float)this.screen_width/20*2, (float)this.screen_height/20*16,
                inner_radius, inner_paint);
    }

    public boolean TouchEvent(MotionEvent event){
        Log.i("rrrrr", "TouchEvent: ");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();

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
                if(touch_down){
                    touch_x = event.getX();
                    touch_y = event.getY();
                    Log.i("TAG", "we send the message");

                    float difference_x = touch_x - center_x;
                    float difference_y = touch_y - center_y;

                    this.scene_game.setPlayerMoveTrue(new PointF(difference_x, difference_y));
                    return true;
                }
            case MotionEvent.ACTION_UP:
                touch_down = false;
                this.scene_game.setPlayerMoveFalse();
                return false;

        }
        return false;
    }


}


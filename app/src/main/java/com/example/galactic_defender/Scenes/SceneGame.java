package com.example.galactic_defender.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import com.example.galactic_defender.Characters.Enemy;
import com.example.galactic_defender.Characters.Spaceship;
import com.example.galactic_defender.R;
import com.example.galactic_defender.Utilities.Explosion;
import com.example.galactic_defender.Utilities.JoyStick;
import com.example.galactic_defender.Utilities.FireButton;
import com.example.galactic_defender.Utilities.VibrateManager;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class SceneGame extends Scene {

    Canvas canvas;
    Bitmap pause_button_image;
    Rect pause_button;
    Paint score_paint;
    PointF last_touch_difference;
    Timer enemy_timer;
    Random random;
    JoyStick joystick;
    FireButton fire_button;
    Spaceship spaceship;
    Explosion explosion;
    VibrateManager vibrator;
    ArrayList<Enemy> enemies;
    ArrayList<FireButton> spaceship_shots;
    ArrayList<Explosion> explosions;
    int time_per_enemy = 5000;
    int scene_number = 3;
    int points = 0;
    boolean enemy_explosion = false;
    boolean move_player = false;
    boolean shot_fired = false;


    /**
     * Constructs an instance of the SceneGame class.
     *
     * @param context       The context of the application.
     * @param screen_height The height of the screen.
     * @param screen_width  The width of the screen.
     * @param scene_number  The number identifying the scene.
     */
    public SceneGame(Context context, int screen_height, int screen_width, int scene_number) {
        super(context, screen_height, screen_width, scene_number);
        this.random = new Random();
        this.vibrator = new VibrateManager(context);

        this.joystick = new JoyStick(this, screen_width, screen_height);
        this.fire_button = new FireButton(this, context, screen_width, screen_height);

        this.spaceship_shots = new ArrayList<>();
        this.explosions = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.spaceship = new Spaceship(context, screen_width, screen_height);

        // Timer Properties
        this.enemy_timer = new Timer();
        this.enemy_timer.schedule(new EnemyTask(), 1000, time_per_enemy);

        // Score Properties
        this.score_paint = new Paint();
        this.score_paint.setColor(Color.RED);
        this.score_paint.setTextSize(screen_height / 10);
        this.score_paint.setTextAlign(Paint.Align.LEFT);

        // Pause Button
        this.pause_button_image = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.pause_icon);
        this.pause_button = new Rect(screen_width / 15 * 13, screen_height / 10, screen_width / 15 * 14
                , screen_height / 10 + 50);


    }

    /**
     * Draws the scene on the canvas.
     *
     * @param canvas The canvas on which the scene should be drawn.
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.canvas = canvas;

        this.spaceship.draw(canvas);
        drawEnemies();

        this.joystick.drawJoystick(canvas);
        this.fire_button.drawFireButton(canvas);
        canvas.drawBitmap(pause_button_image, null, pause_button, null);
    }

    @Override
    public void updatePhysics() {
        for (int i = enemies.size() - 1; i >= 0; i--) {
            if (this.spaceship.collision(enemies.get(i).getHide_box())) {
                // TODO code for the game over
                enemies.remove(i);
                this.vibrator.vibrate();
            }
            // TODO create collision with shots
        }
        playerMovement();
    }


    @Override
    public int onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int pointerIndex = event.getActionIndex();
        int pointerID = event.getPointerId(pointerIndex);
        int x = (int) event.getX();
        int y = (int) event.getY();

        this.joystick.TouchEvent(event);
        if (pause_button.contains(x, y)) {
            return 6;
        }

//        if(this.joystick.joystick_rect.contains(x, y)){
//            Log.i("TAG", "we work");
//            if (this.joystick.TouchEvent(event)) {
//                Log.i("TAG", "we try to move");
//                this.move_player = true;
//            } else {
//                this.move_player = false;
//            }
//        }

        if (this.fire_button.touchEvent(event)) {
            Log.i("TAG", "we try to fire");
            this.fire_button.fireShot(this.canvas, this.spaceship.position,
                    this.spaceship.getSpaceshipWidth(),
                    this.spaceship.getSpaceshipHeight());
        }


        return super.onTouchEvent(event);
    }


    ////////////////////////////  PLAYER FUNCTIONS ////////////////////////////

    public void playerMovement() {
        if (!move_player) {
            return;
        }
        Log.i("TAG", "we are moving");

        float ratio = Math.abs(last_touch_difference.x) / Math.abs(last_touch_difference.y);
        double angle = Math.atan(ratio);  // return the value in radians

        float speed_x = (float) Math.cos(angle);
        float speed_y = (float) Math.sin(angle);


        // try this to rotate the image
//        Matrix matrix = new Matrix();
//        matrix.postRotate((float) Math.toDegrees(angle));
//        Bitmap rotatedBitmap = Bitmap.createBitmap(this.rocket.GetRocketImage(), 0, 0, this.rocket.GetRocketWidth(),
//                this.rocket.GetRocketHeight(), matrix, true);

        // Changing image
        if (speed_x > speed_y) {
            if (last_touch_difference.x > 0) {
                // TODO add image to face right
            } else {
                // TODO add image to face left
            }
        } else {
            if (last_touch_difference.y > 0) {
                // TODO add image to face down
            } else {
                // TODO add image to fce up
            }
        }

        if (last_touch_difference.x < 0) {
            speed_x *= -1;
        }
        if (last_touch_difference.y < 0) {
            speed_y *= -1;
        }
        this.spaceship.position.x += speed_x * this.spaceship.velocity;
        this.spaceship.position.y += speed_y * this.spaceship.velocity;
    }

    public void setPlayerMoveTrue(PointF last_touch_difference) {
        move_player = true;
        this.last_touch_difference = last_touch_difference;
    }

    public void setPlayerMoveFalse() {
        move_player = false;
    }

    ////////////////////////////  ENEMY FUNCTIONS ////////////////////////////
    private class EnemyTask extends java.util.TimerTask {
        @Override
        public void run() {
            Enemy enemy = new Enemy(context, screen_width, screen_height);
            enemies.add(enemy);
        }
    }

    public void drawEnemies() {
        for (Enemy enemy : enemies) {
            enemy.draw(canvas);
            enemy.moveEnemy(screen_height / 80);
            // TODO try to get all the functions into enemy
        }
    }

    //////////////////////////// GENERAL FUNCTIONS ////////////////////////////

}



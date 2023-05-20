package com.example.galactic_defender.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.galactic_defender.Characters.Enemy;
import com.example.galactic_defender.Characters.Spaceship;
import com.example.galactic_defender.GalacticDefender;
import com.example.galactic_defender.R;
import com.example.galactic_defender.Utilities.JoyStick;
import com.example.galactic_defender.Utilities.FireButton;
import com.example.galactic_defender.Utilities.VibrateManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class SceneGame extends Scene {

    MediaPlayer enemy_dies, spaceship_explosion, laser_shot;
    Bitmap pause_button_image;
    Rect pause_button;
    Paint score_paint;
    PointF last_touch_difference;
    Timer timer;
    Random random;
    JoyStick joystick;
    FireButton fire_button;
    Spaceship spaceship;
    VibrateManager vibrator;
    ArrayList<Enemy> enemies;
    ArrayList<FireButton> spaceship_shots;
    int time_per_enemy = 5000;
    int scene_number = 3;
    int score = 0;
    float speed_x, speed_y;
    boolean move_player = false;
    boolean pause = false;


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

        // Controls
        this.joystick = new JoyStick(this, context, screen_width, screen_height);
        this.fire_button = new FireButton(this, context, screen_width, screen_height);

        // Characters
        this.spaceship_shots = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.spaceship = new Spaceship(context, screen_width, screen_height);

        // Timer Properties
        this.timer = new Timer();
        this.timer.schedule(new EnemyTask(), 1000, time_per_enemy);
        this.timer.schedule(new Animation(), 300, 300);

        // Score Properties
        this.score_paint = new Paint();
        this.score_paint.setColor( ContextCompat.getColor(context, R.color.main_yellow));
        this.score_paint.setTextSize((float)screen_height / 10);
        this.score_paint.setTextAlign(Paint.Align.CENTER);

        // Sound Effects
        this.enemy_dies = MediaPlayer.create(context.getApplicationContext(), R.raw.alien_dead);
        this.spaceship_explosion = MediaPlayer.create(context.getApplicationContext(), R.raw.spaceship_explosion);
        this.laser_shot = MediaPlayer.create(context.getApplicationContext(), R.raw.laser_shot);

        // Pause Button and window
        try{
            this.assets_manager = context.getAssets();
            this.input_stream = assets_manager.open("button_icons/pause_icon.png");
            this.pause_button_image = BitmapFactory.decodeStream(input_stream);

        } catch (IOException e) {
            Log.i("assets", "problem getting the asset");
            throw new RuntimeException(e);
        }
        this.pause_button = new Rect(screen_width / 15 * 13, screen_height / 10, screen_width / 15 * 14
                , screen_height / 10 + 50);


        if(game_over){
            home_button = new RectF((float)screen_width/9*4, (float)screen_height/7*4,
                    (float)screen_width/9*6, (float)screen_height/7*5);
        } else {
            home_button = new RectF((float)screen_width/9*2, (float)screen_height/7*4, (float)screen_width/9*4, (float)screen_height/7*5);
            resume_button = new RectF((float)screen_width/9*5, (float)screen_height/7*4, (float)screen_width/9*7, (float)screen_height/7*5);
        }
    }

    /**
     * Draws the scene on the canvas.
     *
     * @param canvas The canvas on which the scene should be drawn.
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // Characters
        this.spaceship.draw(canvas);
        drawEnemies(canvas);

        // Controls
        this.joystick.drawJoystick(canvas);
        this.fire_button.drawFireButton(canvas);
        canvas.drawBitmap(pause_button_image, null, pause_button, null);

        // Score
        canvas.drawText(GalacticDefender.score + "", (float)screen_width/10,
                (float)screen_height/10+50,
                score_paint);

        // Manage the pause
        if(pause){
            gameWindow(canvas, false);
        }
    }

    @Override
    public void updatePhysics() {
        if(!pause) {
            for (int i = enemies.size() - 1; i >= 0; i--) {
                if (this.spaceship.collision(enemies.get(i).getHide_box())) {
                    // TODO code for the game over
                    enemies.remove(i);
                    this.vibrator.vibrate();
                    enemy_dies.start();

                    // Code to insert the score in the database
//                    InsertScore insert_score = new InsertScore(this.context);
//                    insert_score.insertScore(GalacticDefender.score);

                }
                // TODO create collision with shots
            }
            playerMovement();
        }
    }


    @Override
    public int onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked(); // manage multitouch
        int pointerIndex = event.getActionIndex(); // obtains the action finger
        int pointerID = event.getPointerId(pointerIndex); // obtains the id pointer associate to
        // the action
        float x = event.getX();
        float y = event.getY();

        if (this.pause_button.contains((int) x, (int) y)) {
            this.pause = true;
        }
        if(resume_button.contains((int)x, (int)y)){
            this.pause = false;
        }
        if(home_button.contains((int)x, (int)y)){
            return 1;
        }

        this.joystick.touchEvent(event);
        this.fire_button.touchEvent(event);

        if(action == MotionEvent.ACTION_MOVE){
            Log.i("test", "we detect the moving");
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        enemy_dies.release();
    }

    ////////////////////////////  PLAYER FUNCTIONS ////////////////////////////

    public void playerMovement() {
        if (!move_player) {
            return;
        }
        Log.i("TAG", "we are moving");
        float ratio = Math.abs(last_touch_difference.x) / Math.abs(last_touch_difference.y);
        double angle = Math.atan(ratio);  // return the value in radians

        this.speed_x = (float) Math.cos(angle);
        this.speed_y = (float) Math.sin(angle);

        // try this to rotate the image
//        Matrix matrix = new Matrix();
//        matrix.postRotate((float) Math.toDegrees(angle));
//        Bitmap rotatedBitmap = Bitmap.createBitmap(this.rocket.GetRocketImage(), 0, 0, this.rocket.GetRocketWidth(),
//                this.rocket.GetRocketHeight(), matrix, true);


        if (last_touch_difference.x < 0) {
            this.speed_x *= -1;
        }
        if (last_touch_difference.y < 0) {
            this.speed_y *= -1;
        }
        this.spaceship.position.x += this.speed_x * this.spaceship.velocity;
        this.spaceship.position.y += this.speed_y * this.spaceship.velocity;
    }

    public void setPlayerMoveTrue(PointF last_touch_difference) {
        this.move_player = true;
        this.last_touch_difference = last_touch_difference;
    }

    public void setPlayerMoveFalse() {
        this.move_player = false;
    }
    ////////////////////////////  ENEMY FUNCTIONS ////////////////////////////
    private class EnemyTask extends java.util.TimerTask {
        @Override
        public void run() {
            if(!pause){
                Enemy enemy = new Enemy(context, screen_width, screen_height);
                enemies.add(enemy);
            }
        }

    }

    public void drawEnemies(Canvas canvas) {
        for (Enemy enemy : enemies) {
            enemy.draw(canvas);
            if(!pause){
                enemy.moveEnemy(screen_height / 80);
                // TODO try to get all the functions into enemy
            }
        }
    }

    //////////////////////////// GENERAL FUNCTIONS ////////////////////////////
    private class Animation extends java.util.TimerTask {
        @Override
        public void run() {
            spaceship.updateAnimation();
            for (Enemy enemy: enemies) {
                enemy.updateAnimation();
            }
        }

    }

}



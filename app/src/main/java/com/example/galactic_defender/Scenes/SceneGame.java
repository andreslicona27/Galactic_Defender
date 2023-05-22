package com.example.galactic_defender.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;

import com.example.galactic_defender.Characters.Enemy;
import com.example.galactic_defender.Characters.Spaceship;
import com.example.galactic_defender.GalacticDefender;
import com.example.galactic_defender.R;
import com.example.galactic_defender.Utilities.Explosion;
import com.example.galactic_defender.Utilities.Hardware;
import com.example.galactic_defender.Utilities.JoyStick;
import com.example.galactic_defender.Utilities.FireButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

public class SceneGame extends Scene {

    Hardware hardware;
    MediaPlayer enemy_dies, spaceship_explosion, laser_shot;
    Bitmap pause_button_image;
    Rect pause_button;
    Canvas canvas;
    PointF last_touch_difference;
    Timer timer;
    Explosion explosion;
    JoyStick joystick;
    FireButton fire_button;
    Spaceship spaceship;
    ArrayList<Enemy> enemies;
    ArrayList<FireButton> spaceship_shots;
    int time_per_enemy = 5000;
    int scene_number = 3;
    boolean pause = false;
    boolean game_over = false;


    /**
     * Constructs an instance of the SceneGame class.
     *
     * @param context       The context of the application.
     * @param screen_height The height of the screen.
     * @param screen_width  The width of the screen.
     * @param scene_number  The number identifying the scene.
     * @exception RuntimeException If there is a problem obtaining the assets
     */
    public SceneGame(Context context, int screen_height, int screen_width, int scene_number) {
        super(context, screen_height, screen_width, scene_number);

        // Hardware
        this.hardware = new Hardware(context);

        // Controls
        this.joystick = new JoyStick(this, context, screen_width, screen_height);
        this.fire_button = new FireButton(this, context, screen_width, screen_height);
        this.explosion = new Explosion(context);

        // Characters
        this.spaceship_shots = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.spaceship = new Spaceship(context, screen_width, screen_height);

        // Timer Properties
        this.timer = new Timer();
        this.timer.schedule(new EnemyTask(), 500, time_per_enemy);
        this.timer.schedule(new Animation(), 300, 300);

        // Sound Effects
        this.enemy_dies = MediaPlayer.create(context.getApplicationContext(), R.raw.alien_dead);
        this.spaceship_explosion = MediaPlayer.create(context.getApplicationContext(), R.raw.spaceship_explosion);
        this.laser_shot = MediaPlayer.create(context.getApplicationContext(), R.raw.laser_shot);

        // Pause Button and window
        try {
            this.assets_manager = context.getAssets();
            this.input_stream = assets_manager.open("button_icons/pause_icon.png");
            this.pause_button_image = BitmapFactory.decodeStream(input_stream);

        } catch (IOException e) {
            Log.i("assets", "problem getting the asset");
            throw new RuntimeException(e);
        }
        this.pause_button = new Rect(screen_width / 15 * 13, screen_height / 10, screen_width / 15 * 14
                , screen_height / 10 + 50);


        home_button1 = new RectF((float) screen_width / 9 * 2, (float) screen_height / 7 * 4, (float) screen_width / 9 * 4, (float) screen_height / 7 * 5);
        resume_button = new RectF((float) screen_width / 9 * 5, (float) screen_height / 7 * 4, (float) screen_width / 9 * 7, (float) screen_height / 7 * 5);
        home_button2 = new RectF((float) screen_width / 10 * 3, (float) screen_height / 7 * 4,
                (float) screen_width / 10 * 7, (float) screen_height / 7 * 5);
    }

    /**
     * Draws the scene on the canvas.
     *
     * @param canvas The canvas on which the scene should be drawn.
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.canvas = canvas;

        // Characters
        this.spaceship.draw(canvas);
        drawEnemies(canvas);

        // Controls
        this.joystick.drawJoystick(canvas);
        this.fire_button.drawFireButton(canvas);
        canvas.drawBitmap(pause_button_image, null, pause_button, null);

        // Score
        canvas.drawText(GalacticDefender.score + "", (float) screen_width / 10,
                (float) screen_height / 10 + 50,
                title_paint);

        // Manage the pause
        if (pause) {
            pauseWindow(canvas);
        }
        // Manage the game over
        if (game_over) {
            this.explosion.drawExplosion(canvas, this.spaceship.position.x, this.spaceship.position.y);
            if(this.explosion.explosion_frame == 8){
                 gameOverWindow(canvas);
                 releaseResources();
             }
        }
    }

    @Override
    public void updatePhysics() {
        if (!pause && !game_over) {
            for (int i = enemies.size() - 1; i >= 0; i--) {
                if (this.spaceship.collision(enemies.get(i).getHide_box())) {
                    // TODO code for the game over
                    this.game_over = true;
                    enemies.remove(i);
                    this.hardware.vibrate();

                    // Code to insert the score in the database
//                    InsertScore insert_score = new InsertScore(this.context);
//                    insert_score.insertScore(GalacticDefender.score);

                }
                // TODO create collision with shots
                enemy_dies.start();
            }
            this.spaceship.playerMovement(last_touch_difference);
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

        // Buttons touch management
        if (this.pause_button.contains((int) x, (int) y)) {
            this.pause = true;
        }
        if (resume_button.contains((int) x, (int) y)) {
            this.pause = false;
        }
        if (home_button1.contains((int) x, (int) y) || home_button2.contains((int) x, (int) y)) {
            return 1;
        }

        // Controls management
        this.joystick.touchEvent(event);
        this.fire_button.touchEvent(event);

        return super.onTouchEvent(event);
    }


    ////////////////////////////  PLAYER FUNCTIONS ////////////////////////////

    public void setPlayerMoveTrue(PointF last_touch_difference) {
        this.spaceship.move_player = true;
        this.last_touch_difference = last_touch_difference;
    }

    public void setPlayerMoveFalse() {
        this.spaceship.move_player = false;
    }

    ////////////////////////////  ENEMY FUNCTIONS ////////////////////////////
    public void drawEnemies(Canvas canvas) {
        for (Enemy enemy : enemies) {
            enemy.draw(canvas);
            if (!pause  && !game_over) {
                enemy.moveEnemy(screen_height / 80);
                // TODO try to get all the functions into enemy
            }
        }
    }


    //////////////////////////// GENERAL FUNCTIONS ////////////////////////////
    public void releaseResources() {
        this.enemy_dies.release();
        this.spaceship_explosion.release();
        this.laser_shot.release();
        this.timer.cancel();
    }


    //////////////////////////// TIMER CLASSES ////////////////////////////
    private class Animation extends java.util.TimerTask {
        @Override
        public void run() {
            spaceship.updateAnimation();
            for (Enemy enemy : enemies) {
                enemy.updateAnimation();
            }
        }
    }
    private class EnemyTask extends java.util.TimerTask {
        @Override
        public void run() {
            if (!pause && !game_over) {
                Enemy enemy = new Enemy(context, screen_width, screen_height);
                enemies.add(enemy);
            }
        }
    }
}



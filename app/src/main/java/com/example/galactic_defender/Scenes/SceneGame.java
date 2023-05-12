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
import com.example.galactic_defender.Characters.Rocket;
import com.example.galactic_defender.MainActivity;
import com.example.galactic_defender.R;
import com.example.galactic_defender.Utilities.Explosion;
import com.example.galactic_defender.Utilities.JoyStick;
import com.example.galactic_defender.Utilities.FireButton;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class SceneGame extends Scene {

    MainActivity vibrator;
    Canvas canvas; //  we need for the creation of enemies in the timer
    Bitmap pause_button_image;
    Rect pause_button;
    Paint score_paint;
    PointF last_touch_difference;
    Timer enemy_timer;
    Random random;
    JoyStick joystick;
    FireButton fire_button;
    Rocket rocket;
    Explosion explosion;
    ArrayList<Enemy> enemies;
    ArrayList<FireButton> rocket_shots;
    ArrayList<Explosion> explosions;
    int time_per_enemy = 5000;
    int scene_number = 3;
    int points = 0;
    boolean enemy_explosion = false;
    boolean move_player;


    public SceneGame(Context context, int screen_height, int screen_width, int scene_number) {
        super(context, screen_height, screen_width, scene_number);
        this.random = new Random();
        this.vibrator = new MainActivity();
        this.joystick = new JoyStick(this, screen_width, screen_height);
        this.fire_button = new FireButton(context, screen_width, screen_height);

        this.rocket_shots = new ArrayList<>();
        this.explosions = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.rocket = new Rocket(context, screen_width, screen_height);

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
        this.pause_button = new Rect(screen_width/15*13, screen_height/10, screen_width/15*14
                , screen_height/10+50);


    }

    public void Draw(Canvas canvas) {
        super.Draw(canvas);
        this.canvas = canvas;

        this.rocket.DrawRocket(canvas);
        DrawEnemies();
        PlayerMovement();
        RocketShotsManagement(canvas);
        ExplosionsManagement(canvas);

        this.joystick.DrawJoystick(canvas);
        this.fire_button.DrawFireButton(canvas);
        canvas.drawBitmap(pause_button_image, null, pause_button, null);
    }

    @Override
    public int onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int pointerIndex = event.getActionIndex();
        int pointerID = event.getPointerId(pointerIndex);
        int x = (int) event.getX();
        int y = (int) event.getY();
        joystick.TouchEvent(event);

        if (pause_button.contains(x, y)) {
            return 6;
        }
        return joystick.TouchEvent(event);
//        return super.onTouchEvent(event);
    }


    ////////////////////////////  PLAYER FUNCTIONS ////////////////////////////

    public void PlayerMovement() {
        if (!move_player) {
            return;
        }

        float ratio = Math.abs(last_touch_difference.x) / Math.abs(last_touch_difference.y);
        double angle = Math.atan(ratio);  // return the value in radians

        float speed_x = (float) Math.cos(angle);
        float speed_y = (float) Math.sin(angle);

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
        // multiply the rocket speed by something that would become delta that i don´t know what
        // it can be
        this.rocket.position.x += speed_x + this.rocket.rocket_velocity;
        this.rocket.position.y += speed_y + this.rocket.rocket_velocity;

    }

    public void SetPlayerMoveTrue(PointF last_touch_difference) {
        move_player = true;
        this.last_touch_difference = last_touch_difference;
    }

    public void SetPlayerMoveFalse() {
        move_player = false;
    }

    public void RocketShotsManagement(Canvas canvas) {
        for (int i = 0; i < rocket_shots.size(); i++) {
            FireButton rocket_shot = rocket_shots.get(i);
            rocket_shot.position.y -= 15;
            canvas.drawBitmap(rocket_shot.getShot(), rocket_shot.position.x, rocket_shot.position.y,
                    null);

            for (Enemy enemy : enemies) {
                if ((rocket_shot.position.x >= enemy.direction_x) && rocket_shot.position.x <= enemy.direction_x + enemy.GetEnemyWidth()
                        && (rocket_shot.position.y <= enemy.GetEnemyHeight())
                        && (rocket_shot.position.y >= enemy.direction_y)) {
                    points++;
                    rocket_shots.remove(i);
                    explosion = new Explosion(context, enemy.direction_x, enemy.direction_y);
                    explosions.add(explosion);
                } else if (rocket_shots.get(i).position.y <= 0) {
                    rocket_shots.remove(i);
                }
            }
        }
    }

    ////////////////////////////  ENEMY FUNCTIONS ////////////////////////////
    private class EnemyTask extends java.util.TimerTask {
        @Override
        public void run() {
            AddEnemy(canvas);
        }
    }

    private void AddEnemy(Canvas canvas) {
        // TODO add the control of addition depending of the amount of points
        Enemy enemy = new Enemy(context, screen_width, screen_height);
        enemies.add(enemy);
    }

    public void DrawEnemies() {
        for (Enemy enemy : enemies) {
            if (!enemy_explosion) {
                canvas.drawBitmap(enemy.GetEnemyImage(), enemy.position.x, enemy.position.y, null);
                EnemyMovementManagement();
            }
        }
    }

    private void EnemyMovementManagement() {
        if (enemies.size() > 0) {
            for (Enemy enemy : enemies) {
                enemy.MoveEnemy(screen_height, screen_width, 2);

                if(RocketAndEnemyCollision(enemy.position.x, enemy.position.y, enemy.GetEnemyImage(), rocket.position.x, rocket.position.y, rocket.GetRocketImage())){
                    Log.i("TAG", "we have collision");
                    this.vibrator.Vibrate();
                }

                // TODO create the collision of aliens with the rocket shots
                if(ShotsAndEnemyCollision(enemy.position.x, enemy.position.y, enemy.GetEnemyImage())){
                    Log.i("TAG", "we have shot the enemy");
                }
            }
        }
    }

    private boolean RocketAndEnemyCollision(int enemy_x, int enemy_y, Bitmap enemy, int rocket_x,
     int rocket_y, Bitmap rocket) {
        Rect rect1 = new Rect(enemy_x, enemy_y, enemy_x + enemy.getWidth(), enemy_y + enemy.getHeight());
        Rect rect2 = new Rect(rocket_x, rocket_y, rocket_x + rocket.getWidth(),
                rocket_y + rocket.getHeight());

        return Rect.intersects(rect1, rect2);
    }
        //////////////////////////// GENERAL FUNCTIONS ////////////////////////////

        public void ExplosionsManagement (Canvas canvas){
            if (explosions.size() > 1) {
                for (int i = 0; i < explosions.size(); i++) {
                    if (canvas != null) {
                        canvas.drawBitmap(explosions.get(i).getExplosion(explosions.get(i).explosion_frame),
                                explosions.get(i).explosionX, explosions.get(i).explosionY, null);
                    }
                    explosions.get(i).explosion_frame++;
                    if (explosions.get(i).explosion_frame > 8) {
                        explosions.remove(i);
                    }
                }
            }
        }

        private boolean ShotsAndEnemyCollision(int enemy_x, int enemy_y, Bitmap enemy){
            for(FireButton shot : rocket_shots){
                Rect rect1 = new Rect(enemy_x, enemy_y, enemy_x + enemy.getWidth(), enemy_y + enemy.getHeight());
                Rect rect2 = new Rect(shot.position.x, shot.position.y,
                        shot.position.x + shot.GetShotWidth(),
                        shot.position.y + shot.GetShotHeight());
                // TODO add the code for it no eliminate the enemy and eliminate the shot of the
                //  scene
                return Rect.intersects(rect1, rect2);
            }
            return false;
        }


    }

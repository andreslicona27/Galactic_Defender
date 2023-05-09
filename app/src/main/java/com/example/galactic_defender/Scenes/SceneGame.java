package com.example.galactic_defender.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.galactic_defender.Characters.Enemy;
import com.example.galactic_defender.Characters.Rocket;
import com.example.galactic_defender.R;
import com.example.galactic_defender.Utilities.Explosion;
import com.example.galactic_defender.Utilities.Shot;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class SceneGame extends Scene{

    Canvas canvas; //  we need for the creation of enemies in the timer
    Bitmap pause_button_image;
    Rect pause_button;
    Paint score_paint;
    Timer enemy_timer;
    Random random;
    Rocket rocket;
    Shot shot;
    Explosion explosion;
    ArrayList<Shot> rocket_shots;
    ArrayList<Enemy> enemies;
    ArrayList<Explosion> explosions;
    int time_per_enemy = 5000;
    int scene_number = 3;
    int BACKGROUND = Color.parseColor("#2E5266");
    int points = 0;
    boolean enemy_explosion = false;

    public SceneGame(Context context, int screen_height, int screen_width, int scene_number) {
        super(context, screen_height, screen_width, scene_number);
        this.random = new Random();
        this.rocket_shots = new ArrayList<>();
        this.explosions = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.rocket = new Rocket(context);
        this.enemy_timer = new Timer();
        this.enemy_timer.schedule(new EnemyTask(), 1000, time_per_enemy);
//        to create enemies every x amount of time

        // score properties
        this.score_paint = new Paint();
        this.score_paint.setColor(Color.RED);
        this.score_paint.setTextSize(screen_height/10);
        this.score_paint.setTextAlign(Paint.Align.LEFT);

        this.pause_button_image = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.pause_icon);
        this.pause_button = new Rect(screen_width/20, screen_height/10, screen_width/20*2
                , screen_height/10*2);

    }


    public void Draw(Canvas canvas){
        super.Draw(canvas);
        this.canvas = canvas;
        canvas.drawColor(BACKGROUND);
        for(Enemy enemy : enemies){
            if (!enemy_explosion) {
                canvas.drawBitmap(enemy.getEnemy_image(), enemy.position.x, enemy.position.y, null);
                EnemyMovementManagement();
            }
        }

//        DrawRocket(canvas);
        RocketShotsManagement(canvas);

        ExplosionsManagement(canvas);
        canvas.drawBitmap(pause_button_image, null, pause_button, null);
    }

    @Override
    public int onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();
        int pointerID = event.getPointerId(pointerIndex);
        int action = event.getActionMasked();
        int x = (int) event.getX();
        int y = (int) event.getY();

        if(pause_button.contains(x, y)){
            return 6;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                return 0;
        }
        return super.onTouchEvent(event);
    }

    private class EnemyTask extends java.util.TimerTask {
        @Override
        public void run(){
            AddEnemy(canvas);
        }
    }

    private void EnemyMovementManagement() {
        if(enemies.size() > 0){
            for (Enemy enemy : enemies) {
                enemy.MoveEnemy(screen_height, screen_width, 5); // leave velocity t try
                // something different later

                // TODO create the rectangle to handle the collision of an enemy with the rocket
            }
        }
    }

    private void AddEnemy(Canvas canvas) {
        // TODO add the control of addition depending of the amount of points
        Enemy enemy = new Enemy(context);
        enemies.add(enemy);
//
    }

    private void DrawRocket(Canvas canvas) {
        if (rocket.is_alive) {
            if (rocket.rocketX > screen_width - rocket.getRocketWidth()) {
                rocket.rocketX = screen_width - rocket.getRocketWidth();
            } else if (rocket.rocketX < 0) {
                rocket.rocketX = 0;
            }
            canvas.drawBitmap(rocket.getRocket(), rocket.rocketX, rocket.rocketY, null);
        }
    }

    public void RocketShotsManagement(Canvas canvas) {
        for (int i = 0; i < rocket_shots.size(); i++) {
            Shot rocket_shot = rocket_shots.get(i);
            rocket_shot.shotY -= 15;
            canvas.drawBitmap(rocket_shot.getShot(), rocket_shot.shotX, rocket_shot.shotY, null);

            for(Enemy enemy : enemies) {
                if ((rocket_shot.shotX >= enemy.directionX) && rocket_shot.shotX <= enemy.directionX + enemy.getEnemyWidth()
                        && (rocket_shot.shotY <= enemy.getEnemyHeight())
                        && (rocket_shot.shotY >= enemy.directionY)) {
                    points++;
                    rocket_shots.remove(i);
                    explosion = new Explosion(context, enemy.directionX, enemy.directionY);
                    explosions.add(explosion);
                } else if (rocket_shots.get(i).shotY <= 0) {
                    rocket_shots.remove(i);
                }
            }
        }
    }

    public void ExplosionsManagement(Canvas canvas) {
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
}

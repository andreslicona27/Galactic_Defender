package com.example.galactic_defender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.galactic_defender.Characters.Enemy;
import com.example.galactic_defender.Characters.Rocket;
import com.example.galactic_defender.Scenes.Scene;
import com.example.galactic_defender.Scenes.SceneCredits;
import com.example.galactic_defender.Scenes.SceneGame;
import com.example.galactic_defender.Scenes.SceneGameOver;
import com.example.galactic_defender.Scenes.SceneMenu;
import com.example.galactic_defender.Scenes.ScenePause;
import com.example.galactic_defender.Scenes.SceneRecords;
import com.example.galactic_defender.Scenes.SceneSettings;
import com.example.galactic_defender.Utilities.Explosion;
import com.example.galactic_defender.Utilities.Shot;

import java.util.ArrayList;
import java.util.Random;

public class GalacticDefender extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder surface_holder;
    Context context;
    Scene current_scene;
    Hilo game_thread;
    Bitmap background, life_image;
    Handler handler;
    Canvas canvas;
    Paint score_paint;
    Random random;
    Rocket rocket;
    Enemy enemy;
    Explosion explosion;
    ArrayList<Shot> enemy_shots, rocket_shots;
    ArrayList<Explosion> explosions;
    ArrayList<Enemy> enemies;
    long UPDATE_MILLIS = 30;
    int screen_height, screen_width;
    int new_scene;
    int points;
    int TEXT_SIZE = 80;
    boolean playing = true;
    boolean thread_working = false; // Control of the thread
    boolean enemy_explosion = false;

    public GalacticDefender(Context context) {
        super(context);
        this.surface_holder = getHolder();
        this.surface_holder.addCallback(this);
        this.context = context;
        this.game_thread = new Hilo();

        this.random = new Random();
        this.enemy_shots = new ArrayList<>();
        this.rocket_shots = new ArrayList<>();
        this.explosions = new ArrayList<>();
        this.enemies = new ArrayList<>();

        this.rocket = new Rocket(context);
        this.enemy = new Enemy(context);
//        this.background = BitmapFactory.decodeResource(context.getResources(),R.drawable.background);
        this.handler = new Handler();

        // score properties
        this.score_paint = new Paint();
        this.score_paint.setColor(Color.RED);
        this.score_paint.setTextSize(TEXT_SIZE);
        this.score_paint.setTextAlign(Paint.Align.LEFT);
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
//        canvas = surface_holder.lockCanvas();
//        canvas.drawText("Pt: " + points, 0, TEXT_SIZE, score_paint);
//
//        EnemyMovementManagement();
//        AddEnemy();
//        if (!enemy_explosion) {
//            canvas.drawBitmap(enemy.getEnemy(), enemy.enemyX, enemy.enemyY, null);
//        }
//
//        DrawRocket(canvas);
//        RocketShotsManagement(canvas);
//
//        ExplosionsManagement(canvas);
//
//         // see if I really need this
////        if (!playing) {
////            handler.postDelayed(runnable, UPDATE_MILLIS);
////        }
//        surface_holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        this.screen_width = width;
        this.screen_height = height;
        current_scene = new SceneMenu(context, screen_height, screen_width, 1);

        if (game_thread.getState() == Thread.State.NEW) {
            game_thread.start();
        }
        if (game_thread.getState() == Thread.State.TERMINATED) {
            game_thread = new Hilo();
            game_thread.start();
        }
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        this.playing = false;
        try {
            game_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();
        int pointerID = event.getPointerId(pointerIndex);
        int action = event.getActionMasked();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                new_scene = current_scene.onTouchEvent(event);
                ChangeScene(new_scene);
                return true;
            case MotionEvent.ACTION_UP:
                if (rocket_shots.size() < 3) {
                    Shot rocket_shot = new Shot(context, rocket.rocketX + rocket.getRocketWidth() / 2, rocket.rocketY);
                    rocket_shots.add(rocket_shot);
                }
        }
        return super.onTouchEvent(event);
    }


    public void ChangeScene(int change_scene) {
        if (current_scene.scene_number != new_scene) {
            switch (new_scene) {
                case 1:
                    current_scene = new SceneMenu(context, screen_height, screen_width, 1);
                    break;
                case 2:
                    current_scene = new SceneRecords(context, screen_height, screen_width, 2);
                    break;
                case 3:
                    current_scene = new SceneGame(context, screen_height, screen_width, 3);
                    break;
                case 4:
                    current_scene = new SceneCredits(context, screen_height, screen_width, 4);
                    break;
                case 5:
                    current_scene = new SceneSettings(context, screen_height, screen_width, 5);
                    break;
                case 6:
                    current_scene = new ScenePause(context, screen_height, screen_width, 6);
                    break;
                case 7:
                    current_scene = new SceneGameOver(context, screen_height, screen_width, 7);
                    break;
            }
        }
    }

    private void EnemyMovementManagement() {
        if(enemies.size() > 0){
            for (Enemy enemy : enemies) {
                enemy.MoveEnemy();

                // TODO try collision with borders
                if (enemy.enemyX + enemy.getEnemyWidth() >= screen_width || enemy.enemyY + enemy.getEnemyHeight() >= screen_height
                        || enemy.enemyX <= 0 || enemy.enemyY <= 0) {
                    enemy.enemy_velocity *= -1;
                }

                // TODO create the rectangle to handle the collision of an enemy with the rocket
            }
        }
    }

    private void AddEnemy() {
        // TODO add timer for it to add enemies every certain amount of time
        // TODO add the control of addition depending of the amount of points
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

            if ((rocket_shot.shotX >= enemy.enemyX) && rocket_shot.shotX <= enemy.enemyX + enemy.getEnemyWidth()
                    && (rocket_shot.shotY <= enemy.getEnemyHeight())
                    && (rocket_shot.shotY >= enemy.enemyY)) {
                points++;
                rocket_shots.remove(i);
                explosion = new Explosion(context, enemy.enemyX, enemy.enemyY);
                explosions.add(explosion);
            } else if (rocket_shots.get(i).shotY <= 0) {
                rocket_shots.remove(i);
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

    public class Hilo extends Thread {
        @Override
        public void run() {
            super.run();
            Canvas canvas = null;
            while (playing) {
                canvas = null;
                try {
                    if (!surface_holder.getSurface().isValid()) continue;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        canvas = surface_holder.lockHardwareCanvas();
                    }
                    if (canvas == null) canvas = surface_holder.lockCanvas();

                    synchronized (surface_holder) {
                        current_scene.UpdatePhysics();
                        current_scene.Draw(canvas);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        surface_holder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

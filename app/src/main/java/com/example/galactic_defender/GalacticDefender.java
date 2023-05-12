package com.example.galactic_defender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.galactic_defender.Scenes.Scene;
import com.example.galactic_defender.Scenes.SceneCredits;
import com.example.galactic_defender.Scenes.SceneGame;
import com.example.galactic_defender.Scenes.SceneGameOver;
import com.example.galactic_defender.Scenes.SceneInformation;
import com.example.galactic_defender.Scenes.SceneMenu;
import com.example.galactic_defender.Scenes.ScenePause;
import com.example.galactic_defender.Scenes.SceneRecords;
import com.example.galactic_defender.Scenes.SceneSettings;

public class GalacticDefender extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder surface_holder;
    Context context;
    Scene current_scene;
    Hilo game_thread;
    Bitmap background;
    Handler handler;
    Canvas canvas;
    long UPDATE_MILLIS = 30;
    int screen_height, screen_width;
    int new_scene;
    boolean playing = true;
    boolean thread_working = false; // Control of the thread

    public GalacticDefender(Context context) {
        super(context);
        this.surface_holder = getHolder();
        this.surface_holder.addCallback(this);
        this.context = context;
        this.game_thread = new Hilo();
        this.handler = new Handler();
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

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

        if (action == MotionEvent.ACTION_DOWN) {
            new_scene = current_scene.onTouchEvent(event);
            ChangeScene(new_scene);
            return true;
        }
        return super.onTouchEvent(event);
    }


    public void ChangeScene(int change_scene) {
        if (current_scene.scene_number != change_scene) {
            switch (change_scene) {
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
                case 8:
                    current_scene = new SceneInformation(context, screen_height, screen_width, 8);
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

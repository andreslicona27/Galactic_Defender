package com.example.galactic_defender;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.galactic_defender.Scenes.Scene;
import com.example.galactic_defender.Scenes.SceneCredits;
import com.example.galactic_defender.Scenes.SceneGame;
import com.example.galactic_defender.Scenes.SceneGameOver;
import com.example.galactic_defender.Scenes.SceneMenu;
import com.example.galactic_defender.Scenes.ScenePause;
import com.example.galactic_defender.Scenes.SceneRecords;
import com.example.galactic_defender.Scenes.SceneSettings;

public class GalacticDefender extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder surface_holder;
    Context context;
    Scene current_scene;
    Hilo game_thread;
    int screen_height, screen_width;
    int new_scene;
    boolean playing = true;
    public GalacticDefender(Context context) {
        super(context);
        this.surface_holder = getHolder();
        this.surface_holder.addCallback(this); // y se indica donde van las funciones callback
        this.context = context;
        game_thread = new Hilo();
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        this.screen_width = width;
        this.screen_height = height;
        current_scene = new SceneMenu(context, screen_width, screen_height, 1);

        if (game_thread.getState() == Thread.State.NEW){
            game_thread.start();
        }
        if (game_thread.getState() == Thread.State.TERMINATED) {
            game_thread = new Hilo();
            game_thread.start();
        }
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        this.playing=false;
        try {
            game_thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();
        int pointerID = event.getPointerId(pointerIndex);
        int accion = event.getActionMasked();
        int x=(int)event.getX();
        int y=(int)event.getY();

        switch (accion){
            case MotionEvent.ACTION_DOWN:
                new_scene = current_scene.onTouchEvent(event);
                ChangeScene(new_scene);
                return true;
        }
        return super.onTouchEvent(event);
    }


    public void ChangeScene(int change_scene){
        if (current_scene.scene_number != new_scene){
            switch (new_scene) {
                case 1:
                    current_scene = new SceneMenu(context, 1, screen_width, screen_height);
                    break;
                case 2:
                    current_scene = new SceneRecords(context, 2, screen_width, screen_height);
                    break;
                case 3:
                    current_scene = new SceneGame(context, 3, screen_width, screen_height);
                    break;
                case 4:
                    current_scene = new SceneCredits(context, 4, screen_width, screen_height);
                    break;
                case 5:
                    current_scene = new SceneSettings(context, 5, screen_width, screen_height);
                    break;
                case 6:
                    current_scene = new ScenePause(context, 6, screen_width, screen_height);
                    break;
                case 7:
                    current_scene = new SceneGameOver(context, 7, screen_width, screen_height);
                    break;
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

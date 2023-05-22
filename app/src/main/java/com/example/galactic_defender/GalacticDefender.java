package com.example.galactic_defender;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.galactic_defender.Scenes.Scene;
import com.example.galactic_defender.Scenes.SceneCredits;
import com.example.galactic_defender.Scenes.SceneGame;
import com.example.galactic_defender.Scenes.SceneInformation;
import com.example.galactic_defender.Scenes.SceneMenu;
import com.example.galactic_defender.Scenes.SceneRecords;
import com.example.galactic_defender.Scenes.SceneSettings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class GalacticDefender extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder surface_holder;
    Context context;
    InputStream input_stream;
    AssetManager assets_manager;
    Scene current_scene;
    Hilo game_thread;
    Handler handler;
    int screen_height, screen_width;
    int new_scene;
    boolean playing = true;
    boolean thread_working = false; // Control of the thread

    /////////////// OTHER CLASSES USES /////////////////
    public static Configuration configuration;
    public static String language;
    public static MediaPlayer background_music;
    public static Bitmap sounds_button_image;
    public static Bitmap music_button_image;
    public static Bitmap language_button_image;
    public static int score = 0;


    /**
     * Constructs an instance of the SceneSettings class.
     *
     * @param context The context of the application.
     * @exception RuntimeException If there is a problem obtaining the assets
     */
    public GalacticDefender(Context context) {
        super(context);
        this.surface_holder = getHolder();
        this.surface_holder.addCallback(this);
        this.context = context;
        this.game_thread = new Hilo();
        this.handler = new Handler();

        // Background Music
        background_music = MediaPlayer.create(this.getContext(), R.raw.background_music);
        background_music.setLooping(true);
        background_music.start();

        try{
            this.assets_manager = context.getAssets();
            this.input_stream = assets_manager.open("button_icons/sound_on_icon.png");
            sounds_button_image = BitmapFactory.decodeStream(input_stream);

            this.input_stream = assets_manager.open("button_icons/music_on_icon.png");
            music_button_image = BitmapFactory.decodeStream(input_stream);

            this.input_stream = assets_manager.open("button_icons/spanish_icon.png");
//            language_button_image = BitmapFactory.decodeStream(input_stream);

        } catch (IOException e) {
            Log.i("assets", "problem getting the asset");
            throw new RuntimeException(e);
        }

        // Language
        changeLanguage("en");
        configuration = getResources().getConfiguration();
        language = configuration.locale.getLanguage();
        language_button_image = BitmapFactory.decodeStream(input_stream);
    }


    ////////////////////////////  OVERRIDE FUNCTIONS  ////////////////////////////
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
        background_music.stop();
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
            changeScene(new_scene);
            return true;
        }
        return super.onTouchEvent(event);
    }


    ////////////////////////////  EXTRA FUNCTIONS  ////////////////////////////
    public void changeScene(int change_scene) {
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
                case 7:
                case 8:
                    current_scene = new SceneInformation(context, screen_height, screen_width, 8);
            }
        }
    }

    /**
     * Sets the language of the system
     *
     * @param cod_language Identification code of the new language
     */
    public void changeLanguage(String cod_language) {
        Resources res=context.getResources();
        DisplayMetrics dm=res.getDisplayMetrics();
        android.content.res.Configuration conf=res.getConfiguration();
        conf.locale=new Locale(cod_language.toLowerCase());
        res.updateConfiguration(conf, dm);
    }

    //////////////////////////// THREAD CLASS ////////////////////////////
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
                        current_scene.updatePhysics();
                        current_scene.draw(canvas);
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
        public void SetWorking(boolean flag) {
            thread_working = flag;
        }
    }
}

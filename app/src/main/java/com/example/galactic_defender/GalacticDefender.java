package com.example.galactic_defender;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.util.DisplayMetrics;
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

import java.util.Locale;

/**
 * The main class representing the Galactic Defender game.
 *
 * @author [Andres Licona]
 * @version [1.0]
 * @since [05-04-2023]
 */
public class GalacticDefender extends SurfaceView implements SurfaceHolder.Callback {

    /**
     * The SurfaceHolder object for managing the game's surface.
     */
    final SurfaceHolder surface_holder;

    /**
     * The SharedPreferences object for storing and retrieving game preferences.
     */
    public static SharedPreferences shared_preferences;

    /**
     * The context used for accessing resources and services.
     */
    Context context;

    /**
     * The current scene being displayed in the game.
     */
    Scene current_scene;

    /**
     * The game thread responsible for running the game logic.
     */
    Hilo game_thread;

    /**
     * The handler used for communication between threads.
     */
    Handler handler;

    /**
     * Represents the height of the screen.
     */
    int screen_height;

    /**
     * represents the width of the screen
     */
    int screen_width;

    /**
     * The identifier for the new scene to be transitioned to.
     */
    int new_scene;

    /**
     * Flag indicating whether the game is currently being played.
     */
    boolean playing;

    /**
     * Flag indicating the status of the game thread.
     */
    boolean thread_working;

    /////////////// USED IN OTHER CLASSES /////////////////

    /**
     * The MediaPlayer object for playing background music.
     */
    public static MediaPlayer background_music;

    /**
     * Flag indicating whether sound effects are enabled.
     */
    public static boolean sound_enabled = true;

    /**
     * Flag indicating whether background music is enabled.
     */
    boolean music_enabled = true;

    /**
     * The selected language for the game.
     */
    String language;

    /**
     * The current score in the game.
     */
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

        // Booleans variables that manage the game
        this.playing = true;
        this.thread_working = false;

        // Shared values
        shared_preferences = context.getApplicationContext().getSharedPreferences("SettingsValues", Context.MODE_PRIVATE);
        sound_enabled = shared_preferences.getBoolean("sound_enabled", true);
        music_enabled = shared_preferences.getBoolean("music_enabled", true);
        this.language = shared_preferences.getString("language","en");

        // Background Music
        background_music = MediaPlayer.create(this.getContext(), R.raw.background_music);
        background_music.setLooping(true);
        if(music_enabled){
            background_music.start();
        } else {
            background_music.stop();
        }

        // Language
        changeLanguage(language);
    }


    ////////////////////////////  OVERRIDE FUNCTIONS  ////////////////////////////

    /**
     * Called when the surface is created.
     * This method is invoked when the SurfaceHolder is created.
     * @param holder The SurfaceHolder object associated with the surface.
     */
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        sound_enabled = shared_preferences.getBoolean("soundEnabled", true);
        music_enabled = shared_preferences.getBoolean("musicEnabled", true);
        language = shared_preferences.getString("language", "en");
    }

    /**
     * Called when the surface dimensions or format change.
     * This method is invoked when the size or format of the surface changes.
     *
     * @param holder The SurfaceHolder object associated with the surface.
     * @param format The new PixelFormat of the surface.
     * @param width The new width of the surface.
     * @param height The new height of the surface.
     */
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

    /**
     * Called when the surface is destroyed.
     * This method is invoked when the SurfaceHolder is destroyed.
     *
     * @param holder The SurfaceHolder object associated with the surface.
     */
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        this.playing = false;
        background_music.stop();
        background_music.release();
        try {
            game_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method overrides the onTouchEvent() method from the superclass and handles the touch
     * events
     *
     * @param event The motion event representing the touch event
     * @return True if the touch event is handled, false otherwise
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();

        if (action == MotionEvent.ACTION_DOWN) {
            new_scene = current_scene.onTouchEvent(event);
            changeScene(new_scene);
            return true;
        } else {
            current_scene.onTouchEvent(event);
        }
        return true;
    }


    ////////////////////////////  EXTRA FUNCTIONS  ////////////////////////////

    /**
     * Changes the current scene based on the given scene number.
     *
     * @param change_scene The scene number to change to.
     */
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

    /**
     * The Hilo class extends Thread and represents a thread that performs rendering operations on a Canvas.
     */
    public class Hilo extends Thread {

        /**
         * Overrides the run method of the Thread class.
         * This method is responsible for executing the rendering operations on the Canvas.
         */
        @Override
        public void run() {
            super.run();
            long time_sleep = 0; // Time that the thread sleeps
            final int FPS = 50; // Our objective
            final int TPS = 1000000000; // Ticks per second for the function nanoTime()
            final int TEMPORAL_FRAGMENT = TPS / FPS; // Space in which we would do all the repeated stuff

            long reference_time = System.nanoTime();

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
                reference_time += TEMPORAL_FRAGMENT;
                // The time that sleeps would be the next one menus the current (Finish to draw and update)
                time_sleep = reference_time - System.nanoTime();

                // If it take a lot of time, we sleep
                if (time_sleep > 0) {
                    try {
                        Thread.sleep(time_sleep / 1000000); // Conversion to ms
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        /**
         * Sets the working flag of the thread.
         *
         * @param flag the value to set the working flag to
         */
        public void SetWorking(boolean flag) {
            thread_working = flag;
        }
    }
}

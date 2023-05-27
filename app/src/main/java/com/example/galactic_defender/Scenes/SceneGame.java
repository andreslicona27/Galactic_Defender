package com.example.galactic_defender.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.galactic_defender.Characters.Enemy;
import com.example.galactic_defender.Characters.Shot;
import com.example.galactic_defender.Characters.Spaceship;
import com.example.galactic_defender.GalacticDefender;
import com.example.galactic_defender.MainActivity;
import com.example.galactic_defender.R;
import com.example.galactic_defender.Utilities.Explosion;
import com.example.galactic_defender.Utilities.Hardware;
import com.example.galactic_defender.Utilities.JoyStick;
import com.example.galactic_defender.Utilities.FireButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

public class SceneGame extends Scene {

    Timer timer;
    Hardware hardware;
    MediaPlayer enemy_dies, spaceship_explosion, laser_shot;
    Bitmap pause_button_image;
    Rect pause_button;
    Explosion[] explosions;
    JoyStick joystick;
    FireButton fire_button;
    Spaceship spaceship;
    ArrayList<Enemy> enemies;
    public ArrayList<Shot> spaceship_shots;
    int time_per_enemy;
    boolean pause = false;
    boolean game_over = false;


    /**
     * Constructs an instance of the SceneGame class.
     *
     * @param context       The context of the application.
     * @param screen_height The height of the screen.
     * @param screen_width  The width of the screen.
     * @param scene_number  The number identifying the scene.
     * @throws RuntimeException If there is a problem obtaining the assets
     */
    public SceneGame(Context context, int screen_height, int screen_width, int scene_number) {
        super(context, screen_height, screen_width, scene_number);
        GalacticDefender.score = 0;

        // Hardware
        this.hardware = new Hardware(context);

        // Characters
        this.spaceship_shots = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.spaceship = new Spaceship(context, screen_width, screen_height);

        // Controls
        this.joystick = new JoyStick(this.spaceship, context, screen_width, screen_height);
        this.fire_button = new FireButton(this, this.spaceship, context, screen_width, screen_height);
        this.explosions = new Explosion[5];

        // Timer Properties
        this.time_per_enemy = 3000;
        this.timer = new Timer();
        this.timer.schedule(new EnemyAdditionTask(), 500, time_per_enemy);
        this.timer.schedule(new AnimationsTask(), 300, 300);
        this.timer.schedule(new FireButtonChange(), 10000, 10000);

        // Sound Effects
        this.enemy_dies = MediaPlayer.create(context.getApplicationContext(), R.raw.alien_dead);
        this.spaceship_explosion = MediaPlayer.create(context.getApplicationContext(), R.raw.spaceship_explosion);
        this.laser_shot = MediaPlayer.create(context.getApplicationContext(), R.raw.laser_shot);

        // Get Resources
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


        // Pause and game over buttons
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
        // Characters
        this.spaceship.draw(canvas);
        drawEnemies(canvas);
        drawShots(canvas);

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
            for (Explosion value : explosions) {
                value.drawExplosion(canvas);
                if (value.explosion_frame == 8) {
                    gameOverWindow(canvas);
                    releaseResources();
                }
            }
        }
    }


    /**
     * Updates the physics of the game.
     * This method is called to update the game physics, including collision detection,
     * game over condition, enemy removal, vibration, and score insertion.
     */
    @Override
    public void updatePhysics() {
        if (!pause && !game_over) {
            for (int i = enemies.size() - 1; i >= 0; i--) {
                if (this.spaceship.collision(enemies.get(i).getHideBox())) {
                    this.explosions[0] = new Explosion(context, this.spaceship.position.x, this.spaceship.position.y);
                    this.explosions[1] = new Explosion(context, this.spaceship.position.x + 50, this.spaceship.position.y + 50);
                    this.explosions[2] = new Explosion(context, this.spaceship.position.x + 60, this.spaceship.position.y + 100);
                    this.explosions[3] = new Explosion(context, this.spaceship.position.x, this.spaceship.position.y + 75);
                    this.explosions[4] = new Explosion(context, this.spaceship.position.x + 75, this.spaceship.position.y);
                    this.game_over = true;
                    if (GalacticDefender.soundEnabled) {
                        this.hardware.vibrate();
                        this.spaceship_explosion.start();
                    }
                    if(GalacticDefender.score > 0){
                        MainActivity.record_data_base.insertScore(GalacticDefender.score);
                    }
                }
                for (int j = spaceship_shots.size() - 1; j >= 0; j--) {
                    if (enemies.get(i).collision(spaceship_shots.get(j).getHideBox())) {
                        this.enemies.remove(i);
                        this.spaceship_shots.remove(j);
                        GalacticDefender.score += 10;
                        if (GalacticDefender.soundEnabled) {
                            this.enemy_dies.start();
                            this.hardware.vibrate();
                        }
                    }
                    if (spaceship_shots.get(j).wallCollision()) {
                        spaceship_shots.remove(j);
                    }
                }
            }
            this.spaceship.move();
            this.hardware.verifiedMovement(this);
        }
    }


    /**
     * Overrides the onTouchEvent method from the superclass to handle touch events.
     *
     * @param event The MotionEvent representing the touch event.
     * @return Returns an integer value indicating the result of the touch event.
     */
    @Override
    public int onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        // Controls management
        this.joystick.touchEvent(event);
        this.fire_button.touchEvent(event);

        // Buttons touch management
        if (this.pause_button.contains((int) x, (int) y)) {
            this.pause = true;
        }
        if (this.pause || this.game_over) {
            if (this.resume_button.contains((int) x, (int) y)) {
                this.pause = false;
            }
            if (this.home_button1.contains((int) x, (int) y) || this.home_button2.contains((int) x, (int) y)) {
                releaseResources();
                enemies.clear();
                return 1;
            }
        }
        return super.onTouchEvent(event);
    }

    //////////////////////////// GENERAL FUNCTIONS ////////////////////////////

    /**
     * Draws the enemies on the given canvas.
     *
     * @param canvas The canvas to draw on.
     */
    public void drawEnemies(Canvas canvas) {
        for (Enemy enemy : enemies) {
            enemy.draw(canvas);
            if (!pause && !game_over) {
                enemy.move();
            }
        }
    }

    /**
     * Draws the shots fired by the spaceship on the given canvas.
     *
     * @param canvas The canvas to draw on.
     */
    public void drawShots(Canvas canvas) {
        for (Shot shot : spaceship_shots) {
            shot.draw(canvas);
            if (!pause && !game_over) {
                shot.move();
            }
        }
    }

    /**
     * Releases the resources used by the game.
     * This includes releasing sound effects, clearing lists, and stopping the timer.
     */
    public void releaseResources() {
        // Release sound effects
        this.enemy_dies.release();
        this.spaceship_explosion.release();
        this.laser_shot.release();

        // Clear lists
        this.spaceship_shots.clear();
        this.enemies.clear();

        // Stop timer
        this.timer.cancel();
    }

    /**
     * Remove the enemies if the fire button color is white.
     * Clears enemies list and increases the score with the corresponding points .
     * Changes the fire button color to yellow.
     * All is the "power" of the accelerometer
     */
    public void removeEnemies() {
        if (this.fire_button.paint.getColor() == Color.WHITE) {
            GalacticDefender.score += enemies.size() * 10;
            enemies.clear();
            this.fire_button.paint.setColor(ContextCompat.getColor(context, R.color.main_yellow));
        }
    }

    //////////////////////////// TIMER CLASSES ////////////////////////////

    /**
     * A private class representing an animation task for a game.
     * This class extends the java.util.TimerTask class.
     */
    private class AnimationsTask extends java.util.TimerTask {
        /**
         * The run method that is executed when the task is scheduled to run.
         * It updates the animation of the spaceship and all the enemies.
         */
        @Override
        public void run() {
            spaceship.updateAnimation();
            for (Enemy enemy : enemies) {
                enemy.updateAnimation();
            }
            if (game_over) {
                for (Explosion value : explosions) {
                    value.updateAnimation();
                }
            }
        }
    }

    /**
     * A private class representing an enemy task for a game.
     * This class extends the java.util.TimerTask class.
     */
    private class EnemyAdditionTask extends java.util.TimerTask {
        /**
         * The run method that is executed when the task is scheduled to run.
         * It creates a new enemy object and adds it to the list of enemies, if the game is not
         * paused and not over.
         */
        @Override
        public void run() {
            if (!pause && !game_over) {
                Enemy enemy = new Enemy(context, screen_width, screen_height);
                enemies.add(enemy);
            }
        }
    }

    /**
     * A private class representing a change of paint color task in the FireButton class.
     * This class extends the java.util.TimerTask class.
     */
    private class FireButtonChange extends java.util.TimerTask {
        /**
         * The run method that is executed when the task is scheduled to run.
         * It verifies if the color of the FireButton class paint is the main_yellow.
         * If it is it changes it to white
         */
        @Override
        public void run() {
            if (fire_button.paint.getColor() == ContextCompat.getColor(context, R.color.main_yellow)) {
                fire_button.paint.setColor(Color.WHITE);
            }
        }
    }

}
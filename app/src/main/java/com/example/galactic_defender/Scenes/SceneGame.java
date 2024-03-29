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
import com.example.galactic_defender.Controls.JoyStick;
import com.example.galactic_defender.Controls.FireButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

/**
 * The SceneGame class represents the game scene in the Galactic Defender game.
 * It handles the gameplay mechanics, including player input, character movement, collision detection,
 * score tracking, and rendering.
 *
 * @author [Andres Licona]
 * @version [1.0]
 * @since [05-04-2023]
 */
public class SceneGame extends Scene {

    /**
     * The Timer object for managing game timing.
     */
    Timer timer;

    /**
     * The Hardware object for managing hardware-related operations.
     */
    Hardware hardware;

    /**
     * The MediaPlayer object for playing the sound effect when an enemy dies.
     */
    MediaPlayer enemy_dies;

    /**
     * The MediaPlayer object for playing the sound effect when the spaceship explodes.
     */
    MediaPlayer spaceship_explosion;

    /**
     * The image of the pause button.
     */
    Bitmap pause_button_image;

    /**
     * The rectangle representing the pause button's position and size.
     */
    Rect pause_button;

    /**
     * An array of Explosion objects representing the ongoing explosions in the game.
     */
    Explosion explosion;

    /**
     * The JoyStick object for controlling the spaceship's movement.
     */
    JoyStick joystick;

    /**
     * The FireButton object for triggering the spaceship's shooting action.
     */
    FireButton fire_button;

    /**
     * The Spaceship object representing the player's spaceship.
     */
    Spaceship spaceship;

    /**
     * An ArrayList of Enemy objects representing the enemies in the game.
     */
    ArrayList<Enemy> enemies;

    /**
     * An ArrayList of Shot objects representing the shots fired by the spaceship.
     */
    public ArrayList<Shot> spaceship_shots;

    /**
     * The time duration between each enemy appearance.
     */
    int time_per_enemy;

    /**
     * Flag indicating whether the game is currently paused.
     */
    boolean pause = false;

    /**
     * Flag indicating whether the game is over.
     */
    boolean game_over = false;

    /**
     * Current score of the player in the game
     */
    int current_score;


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
        this.current_score = 0;

        // Hardware
        this.hardware = new Hardware(context, this);

        // Characters
        this.spaceship_shots = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.spaceship = new Spaceship(context, screen_width, screen_height);

        // Controls
        this.joystick = new JoyStick(this.spaceship, context, screen_width, screen_height);
        this.fire_button = new FireButton(this, this.spaceship, context, screen_width, screen_height);

        // Timer Properties
        this.time_per_enemy = 2500;
        this.timer = new Timer();
        this.timer.schedule(new EnemyAdditionTask(), 500, time_per_enemy);
        this.timer.schedule(new AnimationsTask(), 300, 300);
        this.timer.schedule(new FireButtonChange(), 10000, 15000);

        // Sound Effects
        this.enemy_dies = MediaPlayer.create(context.getApplicationContext(), R.raw.alien_dead);
        this.spaceship_explosion = MediaPlayer.create(context.getApplicationContext(), R.raw.spaceship_explosion);

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
        this.home_button1 = new RectF((float) screen_width / 9 * 2, (float) screen_height / 7 * 4, (float) screen_width / 9 * 4,
                (float) screen_height / 7 * 5);
        this.resume_button = new RectF((float) screen_width / 9 * 5, (float) screen_height / 7 * 4, (float) screen_width / 9 * 7,
                (float) screen_height / 7 * 5);
        this.home_button2 = new RectF((float) screen_width / 10 * 3, (float) screen_height / 7 * 4, (float) screen_width / 10 * 7,
                (float) screen_height / 7 * 5);
    }

    /**
     * Draws the scene on the canvas.
     *
     * @param canvas The canvas on which the scene should be drawn.
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
        // Characters
        drawShots(canvas);
        drawEnemies(canvas);
        this.spaceship.draw(canvas);

        // Controls
        this.joystick.draw(canvas);
        this.fire_button.draw(canvas);
        canvas.drawBitmap(pause_button_image, null, pause_button, null);

        // Score
        canvas.drawText(current_score + "", (float) screen_width / 10, (float) screen_height / 10 + 50, title_paint);

        // Manage the game over
        if (game_over) {
            this.home_button2 = new RectF((float) screen_width / 10 * 3, (float) screen_height / 7 * 4, (float) screen_width / 10 * 7, (float) screen_height / 7 * 5);
            this.explosion.drawExplosion(canvas);
            if (explosion.explosion_frame == 8) {
                GalacticDefender.score = this.current_score;
                gameOverWindow(canvas);
                releaseResources();
            }
        }

        // Manage the pause
        if (pause) {
            pauseWindow(canvas);
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
                    this.explosion = new Explosion(context, this.spaceship.position.x, this.spaceship.position.y);
                    this.game_over = true;
                    if (GalacticDefender.sound_enabled) {
                        this.hardware.vibrate();
                        this.spaceship_explosion.start();
                    }
                    if(this.current_score > 0){
                        MainActivity.record_data_base.insertScore(this.current_score);
                    }
                }
                for (int j = spaceship_shots.size() - 1; j >= 0; j--) {
                    if (enemies.get(i).collision(spaceship_shots.get(j).getHideBox())) {
                        this.enemies.remove(i);
                        this.spaceship_shots.remove(j);
                        this.current_score += 10;
                        if (GalacticDefender.sound_enabled) {
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
        if(this.pause){
            this.home_button2 = new RectF(0,0,0,0);
            if (this.resume_button.contains((int) x, (int) y)) {
                this.pause = false;
            }
            if(this.home_button1.contains((int) x, (int) y) ){
                releaseResources();
                return 1;
            }
        }
        if (this.game_over) {
            if (this.home_button2.contains((int) x, (int) y)) {
                releaseResources();
                return 1;
            }
        }
        return super.onTouchEvent(event);
    }

    //////////////////////////// GENERAL FUNCTIONS ////////////////////////////

    /**
     * Draws the enemies on the given canvas and manage their speed depending of the players score.
     *
     * @param canvas The canvas to draw on.
     */
    public void drawEnemies(Canvas canvas) {
        for (Enemy enemy : enemies) {
            enemy.draw(canvas);

            // Increase the speed depending on the score of the player
            if(this.current_score <= 200){
                enemy.speed = screen_height/100;
            } else if (this.current_score <= 400){
                enemy.speed = screen_height/75;
            } else if(this.current_score <= 600){
                enemy.speed = screen_height/50;
            } else if(this.current_score <= 800){
                enemy.speed = screen_height/25;
            } else{
                enemy.speed = screen_height/10;
            }

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

        // Clear lists
        this.spaceship_shots.clear();
        this.enemies.clear();

        // Stop timer
        this.timer.cancel();

        // Hardware
        this.hardware.pause();
    }

    /**
     * Remove the enemies if the fire button color is white.
     * Clears enemies list and increases the score with the corresponding points .
     * Changes the fire button color to yellow.
     * All is the "power" of the accelerometer
     */
    public void removeEnemies() {
        if (this.fire_button.fire_button_paint.getColor() == Color.WHITE) {
            this.current_score += enemies.size() * 10;
            this.enemies.clear();
            this.fire_button.fire_button_paint.setColor(ContextCompat.getColor(context, R.color.main_yellow));
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
                explosion.updateAnimation();
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
            if (fire_button.fire_button_paint.getColor() == ContextCompat.getColor(context, R.color.main_yellow)) {
                fire_button.fire_button_paint.setColor(Color.WHITE);
            }
        }
    }

}
package com.example.galactic_defender.Characters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.opengl.Matrix;
import android.util.Log;

import java.io.IOException;
import java.util.Random;

/**
 * The Enemy class represents an enemy character in the game.
 * It encapsulates the enemy's image, position, and movement behavior.
 * The enemy can move within the specified screen dimensions and has a randomly generated initial
 * position.
 * It also provides access to the enemy's image dimensions.
 *
 * @author [Andres Licona]
 * @version [1.0]
 * @since [05-17-2023]
 **/
public class Enemy extends Character {

    Rect hide_box;
    Bitmap enemy1_asset, enemy1_image;
    Bitmap enemy2_asset, enemy2_image;
    Bitmap enemy3_asset, enemy3_image;
    Bitmap enemy4_asset, enemy4_image;
    Bitmap[] enemy = new Bitmap[4];
    Random random;
    public Point position;
    public int direction_x, direction_y;
    int pos_x;
    int pos_y;
    int current_frame;
    int frame_count;
    int angle;

    /**
     * Constructs an instance of the Enemy class.
     *
     * @param context       The context of the application.
     * @param screen_width  The width of the screen.
     * @param screen_height The height of the screen.
     * @exception RuntimeException If there is a problem obtaining the assets
     */
    public Enemy(Context context, int screen_width, int screen_height) {
        super(context, screen_width, screen_height);

        try {
            this.input_stream = assets_manager.open("characters/enemy/enemy1.png");
            this.enemy1_asset = BitmapFactory.decodeStream(input_stream);
            this.input_stream = assets_manager.open("characters/enemy/enemy2.png");
            this.enemy2_asset = BitmapFactory.decodeStream(input_stream);
            this.input_stream = assets_manager.open("characters/enemy/enemy3.png");
            this.enemy3_asset = BitmapFactory.decodeStream(input_stream);
            this.input_stream = assets_manager.open("characters/enemy/enemy4.png");
            this.enemy4_asset = BitmapFactory.decodeStream(input_stream);

        } catch (IOException e) {
            Log.i("assets", "problem getting the asset");
            throw new RuntimeException(e);
        }

        this.enemy1_image = Bitmap.createBitmap(this.enemy1_asset, 0, 0, this.enemy1_asset.getWidth(), this.enemy1_asset.getHeight());
        this.enemy2_image = Bitmap.createBitmap(this.enemy2_asset, 0, 0, this.enemy2_asset.getWidth(), this.enemy2_asset.getHeight());
        this.enemy3_image = Bitmap.createBitmap(this.enemy3_asset, 0, 0, this.enemy3_asset.getWidth(), this.enemy3_asset.getHeight());
        this.enemy4_image = Bitmap.createBitmap(this.enemy4_asset, 0, 0, this.enemy4_asset.getWidth(), this.enemy4_asset.getHeight());

        this.enemy[0] = Bitmap.createScaledBitmap(this.enemy1_image, screen_height/5,
                screen_height/3, true);
        this.enemy[1] = Bitmap.createScaledBitmap(this.enemy2_image, screen_height/5,
                screen_height/3, true);
        this.enemy[2] = Bitmap.createScaledBitmap(this.enemy3_image, screen_height/5,
                screen_height/3, true);
        this.enemy[3] = Bitmap.createScaledBitmap(this.enemy4_image, screen_height/5,
                screen_height/3, true);

        this.frame_count = this.enemy.length;
        this.random = new Random();
        this.current_frame = 0;
        this.direction_x = generateRandomDirection();
        this.direction_y = generateRandomDirection();
        generateRandomPosition();
        updateHideBox();
    }


    /**
     * Obtains the rectangle which contains the enemy image
     *
     * @return The Rect at the borders of the image
     */
    public Rect getHide_box() {
        return this.hide_box;
    }


    /**
     * Draws the enemy on the canvas.
     *
     * @param canvas The canvas on which to draw the spaceship.
     */
    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.enemy[current_frame], position.x, position.y, null);
        canvas.drawRect(this.hide_box, this.border_paint);
    }

    /**
     * Updates the hide box position based on the current position and enemy frame.
     * The hide box represents the bounding box of the enemy.
     */
    @Override
    public void updateHideBox() {
        this.hide_box = new Rect(this.position.x, this.position.y,
                this.position.x + this.enemy[current_frame].getWidth(),
                this.position.y + this.enemy[current_frame].getHeight());
    }

    /**
     * Checks for collision between the hide box of this object and the provided hide box.
     *
     * @param hide_box The hide box to check collision against.
     * @return true if collision occurs, false otherwise.
     */
    @Override
    public boolean collision(Rect hide_box) {
        return this.hide_box.intersect(hide_box);
    }

    /**
     * Updates the animation frame of the spaceship.
     */
    @Override
    public void updateAnimation() {
        if (this.current_frame == this.frame_count - 1) {
            this.current_frame = 0;
        }
        this.current_frame++;
    }

    /**
     * Moves the enemy based on the specified screen dimensions and velocity.
     *
     * @param velocity The velocity at which the enemy should move.
     */
    public void moveEnemy(int velocity) {
        int posX = this.position.x + (velocity * this.direction_x);
        int posY = this.position.y + (velocity * this.direction_y);
        this.position = new Point(posX, posY);

        if (this.position.x + this.enemy[current_frame].getWidth() >= screen_width || this.position.x <= 0) {
            this.direction_x *= -1;
        }

        if (this.position.y + this.enemy[current_frame].getHeight() >= screen_height || this.position.y <= 0) {
            this.direction_y *= -1;
        }
        updateHideBox();
    }

    /**
     * Generates random direction
     *
     * @return The randomly generated direction (-1 or 1)
     */
    private int generateRandomDirection() {
        int direction = random.nextInt(2);
        if (direction == 0) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * Generates a random position for the enemy between the borders of the screen
     */
    private void generateRandomPosition() {
        int side = random.nextInt(4);
        int MARGIN = 1;

        switch (side) {
            case 0: // UP
                this.pos_x = random.nextInt(screen_width - this.enemy[current_frame].getWidth() - MARGIN);
                this.pos_y = this.enemy[current_frame].getHeight() - MARGIN;
                break;
            case 1: // RIGHT
                this.pos_x = screen_width - this.enemy[current_frame].getWidth() - MARGIN;
                this.pos_y = random.nextInt(screen_height - this.enemy[current_frame].getHeight() - MARGIN);
                break;
            case 2: // DOWN
                this.pos_x = random.nextInt(screen_width - this.enemy[current_frame].getWidth() - MARGIN);
                this.pos_y = screen_height - this.enemy[current_frame].getHeight() - MARGIN;
                break;
            case 3: // LEFT
                this.pos_x = this.enemy[current_frame].getWidth() - MARGIN;
                this.pos_y = random.nextInt(screen_height - this.enemy[current_frame].getHeight() - MARGIN);
                break;
            default:
                this.pos_x = 0;
                this.pos_y = 0;
                break;
        }
        this.position = new Point(pos_x, pos_y);
    }
}

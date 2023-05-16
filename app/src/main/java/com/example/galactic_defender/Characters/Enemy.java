package com.example.galactic_defender.Characters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.example.galactic_defender.R;

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
    Bitmap enemy_image;
    Bitmap scale_enemy;
    Random random;
    public Point position;
    public int direction_x, direction_y;
    int pos_x;
    int pos_y;

    /**
     * Constructs an instance of the Enemy class.
     *
     * @param context       The context of the application.
     * @param screen_width  The width of the screen.
     * @param screen_height The height of the screen.
     */
    public Enemy(Context context, int screen_width, int screen_height) {
        super(context, screen_width, screen_height);

        // TODO we have to do this with sprites and change the java doc then
        this.enemy_image = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_icon);
        this.scale_enemy = Bitmap.createScaledBitmap(enemy_image, screen_height / 10,
                screen_height / 10, true);

        this.random = new Random();
        this.direction_x = generateRandomDirection();
        this.direction_y = generateRandomDirection();

        generateRandomPosition();
        updateHideBox();
    }


    // GETTERS
    public Rect getHide_box() {
        return this.hide_box;
    }

    /**
     * Obtains the scale image of the enemy
     *
     * @return The scale image of the enemy as Bitmap
     */
    public Bitmap getEnemyImage() {
        return this.scale_enemy;
    }

    /**
     * Obtains the width of the enemy image
     *
     * @return Width of the enemy image as int
     */
    public int getEnemyWidth() {
        return this.scale_enemy.getWidth();
    }

    /**
     * Obtains the height of the enemy image
     *
     * @return Height of the enemy image as int
     */
    public int getEnemyHeight() {
        return this.scale_enemy.getHeight();
    }


    // FUNCTIONS
    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.scale_enemy, position.x, position.y, null);
        canvas.drawRect(this.hide_box, this.border_paint);
    }

    @Override
    public void updateHideBox() {
        this.hide_box = new Rect(this.position.x, this.position.y,
                this.position.x + this.scale_enemy.getWidth(),
                this.position.y + this.scale_enemy.getHeight());
    }

    @Override
    public boolean collision(Rect hide_box) {
        return this.hide_box.intersect(hide_box);
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

        if (this.position.x + this.scale_enemy.getWidth() >= screen_width || this.position.x <= 0) {
            this.direction_x *= -1;
        }

        if (this.position.y + this.scale_enemy.getHeight() >= screen_height || this.position.y <= 0) {
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
                this.pos_x = random.nextInt(screen_width - this.scale_enemy.getWidth() - MARGIN);
                this.pos_y = this.scale_enemy.getHeight() - MARGIN;
                break;
            case 1: // RIGHT
                this.pos_x = screen_width - this.scale_enemy.getWidth() - MARGIN;
                this.pos_y = random.nextInt(screen_height - this.scale_enemy.getHeight() - MARGIN);
                break;
            case 2: // DOWN
                this.pos_x = random.nextInt(screen_width - this.scale_enemy.getWidth() - MARGIN);
                this.pos_y = screen_height - this.scale_enemy.getHeight() - MARGIN;
                break;
            case 3: // LEFT
                this.pos_x = this.scale_enemy.getWidth() - MARGIN;
                this.pos_y = random.nextInt(screen_height - this.scale_enemy.getHeight() - MARGIN);
                break;
            default:
                this.pos_x = 0;
                this.pos_y = 0;
                break;
        }
        this.position = new Point(pos_x, pos_y);
    }

}

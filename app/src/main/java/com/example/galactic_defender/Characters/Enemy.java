package com.example.galactic_defender.Characters;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;

import com.example.galactic_defender.R;

import java.util.Random;

public class Enemy {
    Context context;
    Bitmap enemy_image;
    Bitmap scale_enemy;
    Random random;
    public Point position;
    public int direction_x, direction_y;
    int screen_width, screen_height;
    public int pos_x, pos_y;
    public int enemy_velocity = 2;

    // BUILDER
    public Enemy(Context context, int screen_width, int screen_height) {
        this.context = context;
        this.screen_width = screen_width;
        this.screen_height = screen_height;
        this.enemy_image = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_icon);
        this.scale_enemy = Bitmap.createScaledBitmap(enemy_image, screen_height/10,
                screen_height/10,true);

        this.random = new Random();
        this.direction_x = GenerateRandomDirection();
        this.direction_y = GenerateRandomDirection();
        GenerateRandomPosition();
    }


    // GETTERS

    public Bitmap GetEnemyImage(){return this.scale_enemy;}
    public int GetEnemyWidth(){
        return this.scale_enemy.getWidth();
    }

    public int GetEnemyHeight(){
        return this.scale_enemy.getHeight();
    }


    // FUNCTIONS

    public void MoveEnemy(int screen_height, int screen_width, int velocity){
        int posX = this.position.x + (enemy_velocity * this.direction_x);
        int posY = this.position.y + (enemy_velocity * this.direction_y);
        this.position = new Point(posX, posY);

        if (this.position.x + this.scale_enemy.getWidth() >= screen_width || this.position.x <= 0 ) {
            this.direction_x *= -1;
        }

        if(this.position.y + this.scale_enemy.getHeight() >= screen_height || this.position.y <= 0){
            this.direction_y *= -1;
        }
    }

    private int GenerateRandomDirection() {
        int direction = random.nextInt(2);
        if(direction == 0){
            return 1;
        } else {
            return -1;
        }
    }

    public void GenerateRandomPosition(){
        int side = random.nextInt(4);
        int MARGIN = 5;

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
        Log.d(TAG, pos_x  + ":" + pos_y);
        this.position = new Point(pos_x, pos_y);
    }
}

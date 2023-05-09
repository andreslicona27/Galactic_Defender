package com.example.galactic_defender.Characters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.example.galactic_defender.R;

import java.util.Random;

public class Enemy {
    Context context;
    Bitmap enemy_image;
    public Point position;
    Random random;
    public int directionX = 1;
    public int directionY = 1;
    public int enemy_velocity = 2;

    public Enemy(Context context) {
        this.context = context;
        this.enemy_image = BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket2);
        this.random = new Random();
        int number = random.nextInt(10);
        this.position = new Point(number, number+2);
    }


    public int getEnemyWidth(){
        return enemy_image.getWidth();
    }

    public int getEnemyHeight(){
        return enemy_image.getHeight();
    }

    public Bitmap getEnemy_image(){return  enemy_image;}


    public void MoveEnemy(int screen_height, int screen_width, int velocity){
        int posX = this.position.x + (enemy_velocity * this.directionX);
        int posY = this.position.y + (enemy_velocity * this.directionY);
        this.position = new Point(posX, posY);

        if (this.position.x + this.enemy_image.getWidth() >= screen_width || this.position.x <= 0 ) {
            this.directionX *= -1;
        }

        if(this.position.y + this.enemy_image.getHeight() >= screen_height || this.position.y <= 0){
            this.directionY *= -1;
        }
    }
}

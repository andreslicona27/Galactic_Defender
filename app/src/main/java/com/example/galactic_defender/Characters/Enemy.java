package com.example.galactic_defender.Characters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.galactic_defender.R;

import java.util.Random;

public class Enemy {
    Bitmap enemy;
    Context context;
    Random random;
    public int enemyX;
    public int enemyY;
    public int enemy_velocity = 5;

    public Enemy(Context context) {
        this.context = context;
        enemy = BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket2);
        random = new Random();
        Reset_Enemy();
    }

    public Bitmap getEnemy(){
        return enemy;
    }

    public int getEnemyWidth(){
        return enemy.getWidth();
    }

    public int getEnemyHeight(){
        return enemy.getHeight();
    }

    private void Reset_Enemy() {
        enemyX = 200 + random.nextInt(400);
        enemyY = 0;
        enemy_velocity = 14 + random.nextInt(10);
    }

    public void MoveEnemy(){
        this.enemyX += this.enemyX * this.enemy_velocity;
        this.enemyY += this.enemyY * this.enemy_velocity;
        //        enemy.enemyX += enemy.enemy_velocity;
    }
}

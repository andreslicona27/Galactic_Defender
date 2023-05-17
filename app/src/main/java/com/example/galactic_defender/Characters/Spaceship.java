package com.example.galactic_defender.Characters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import java.io.IOException;

public class Spaceship extends Character {

    Bitmap spaceship1_asset, spaceship1_image;
    Bitmap spaceship2_asset, spaceship2_image;
    Bitmap spaceship3_asset, spaceship3_image;
    Bitmap spaceship4_asset, spaceship4_image;
    Bitmap[] spaceship = new Bitmap[4];
    Rect hide_box;
    public Point position;
    public int velocity = screen_height / 80;
    public boolean is_alive = true;
    int current_frame = 0;
    int frame_count = 0;

    /**
     * Constructs an instance of the Spaceship class.
     *
     * @param context       The context of the application.
     * @param screen_width  The width of the screen.
     * @param screen_height The height of the screen.
     */
    public Spaceship(Context context, int screen_width, int screen_height) {
        super(context, screen_width, screen_height);

        try {
            this.input_stream = assets_manager.open("characters/spaceships/spaceship1.png");
            this.spaceship1_asset = BitmapFactory.decodeStream(input_stream);
            this.input_stream = assets_manager.open("characters/spaceships/spaceship2.png");
            this.spaceship2_asset = BitmapFactory.decodeStream(input_stream);
            this.input_stream = assets_manager.open("characters/spaceships/spaceship3.png");
            this.spaceship3_asset = BitmapFactory.decodeStream(input_stream);
            this.input_stream = assets_manager.open("characters/spaceships/spaceship4.png");
            this.spaceship4_asset = BitmapFactory.decodeStream(input_stream);

        } catch (IOException e) {
            Log.i("assets", "problem getting the asset");
            throw new RuntimeException(e);
        }

        // spaceship
        this.spaceship1_image = Bitmap.createBitmap(this.spaceship1_asset, 0, 0, this.spaceship1_asset.getWidth(), this.spaceship1_asset.getHeight());
        this.spaceship2_image = Bitmap.createBitmap(this.spaceship2_asset, 0, 0, this.spaceship2_asset.getWidth(), this.spaceship2_asset.getHeight());
        this.spaceship3_image = Bitmap.createBitmap(this.spaceship3_asset, 0, 0, this.spaceship3_asset.getWidth(), this.spaceship3_asset.getHeight());
        this.spaceship4_image = Bitmap.createBitmap(this.spaceship4_asset, 0, 0, this.spaceship4_asset.getWidth(), this.spaceship4_asset.getHeight());

        this.spaceship[0] = Bitmap.createScaledBitmap(this.spaceship1_image, screen_height/6,
                screen_height/4, true);
        this.spaceship[1] = Bitmap.createScaledBitmap(this.spaceship2_image, screen_height/6,
                screen_height/4, true);
        this.spaceship[2] = Bitmap.createScaledBitmap(this.spaceship3_image, screen_height/6,
                screen_height/4, true);
        this.spaceship[3] = Bitmap.createScaledBitmap(this.spaceship4_image, screen_height/6,
                screen_height/4, true);

        this.frame_count = this.spaceship.length;

        this.position = new Point(screen_width / 2 - this.spaceship[current_frame].getWidth() / 2,
                screen_height / 2 - this.spaceship[current_frame].getHeight() / 2);

        updateHideBox();
    }

    // GETTERS

    /**
     * Obtains the scale image of the spaceship
     *
     * @return The scale image of the spaceship as Bitmap
     */
    public Bitmap getSpaceshipImage() {
        return this.spaceship[current_frame];
    }

    /**
     * Obtains the width of the spaceship image
     *
     * @return Width of the spaceship image as int
     */
    public int getSpaceshipWidth() {
        return this.spaceship[current_frame].getWidth();
    }

    /**
     * Obtains the width of the spaceship image
     *
     * @return Width of the spaceship image as int
     */
    public int getSpaceshipHeight() {
        return this.spaceship[current_frame].getHeight();
    }

    // FUNCTIONS
    @Override
    public void draw(Canvas canvas) {
        // Manage if the spaceship its in the borders
        if (this.position.x > screen_width - this.spaceship[current_frame].getWidth()) {
            this.position.x = screen_width - this.spaceship[current_frame].getWidth();
        } else if (this.position.x < 0) {
            this.position.y = 0;
        }

        if (this.position.y > screen_height - this.spaceship[current_frame].getHeight()) {
            this.position.y = screen_height - this.spaceship[current_frame].getHeight();
        } else if (this.position.y < 0) {
            this.position.y = 0;
        }

        // Draw elements
        canvas.drawBitmap(this.spaceship[current_frame], position.x, position.y, null);
        canvas.drawRect(this.hide_box, this.border_paint);
        updateHideBox();
    }

    @Override
    public void updateHideBox() {
        this.hide_box = new Rect(this.position.x, this.position.y,
                this.position.x + this.spaceship[current_frame].getWidth(),
                this.position.y + this.spaceship[current_frame].getHeight());
    }

    @Override
    public boolean collision(Rect hide_box) {
        return this.hide_box.intersect(hide_box);
    }

    @Override
    public void updateAnimation() {
        Log.i("test", this.spaceship[current_frame] + "");
        if (this.current_frame == this.frame_count - 1) {
            this.current_frame = 0;
        }
        this.current_frame++;
    }

}

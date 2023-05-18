package com.example.galactic_defender.Utilities;

import android.graphics.Bitmap;
import android.graphics.PointF;

public class Background {
    public PointF position;
    public Bitmap image;

    public Background(Bitmap image, float x, float y) {
        this.image = image;
        this.position = new PointF(x, y);
    }
    public Background(Bitmap image, int screen_height) {
        this(image, 0, screen_height - image.getHeight());
    }
    public void mover(int velocity) {
        position.y += velocity;
    }

}

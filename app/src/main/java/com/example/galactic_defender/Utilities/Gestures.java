package com.example.galactic_defender.Utilities;

import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.core.view.GestureDetectorCompat;

public class Gestures extends  GestureDetector.SimpleOnGestureListener {

    GestureDetectorCompat gesture_detector;

    public Gestures(){
    }

    @Override
    public boolean onDown(MotionEvent e) {
        // Lógica para cuando se realiza un toque en la pantalla
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // Lógica para cuando se realiza un gesto de deslizamiento rápido
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // Lógica para cuando se mantiene presionado durante un tiempo
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        // Lógica para cuando se realiza un deslizamiento con los dedos
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // Lógica para cuando se realiza una pulsación larga
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // Lógica para cuando se realiza un toque simple en la pantalla
        return true;
    }


}

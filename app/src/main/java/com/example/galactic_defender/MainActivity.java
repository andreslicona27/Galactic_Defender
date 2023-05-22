package com.example.galactic_defender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.view.WindowManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.galactic_defender.Utilities.Gestures;

public class MainActivity extends AppCompatActivity {

    Vibrator vibrator;
    SensorManager sensor_manager;
    Sensor accelerometer;
    GestureDetectorCompat gesture_detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        com.example.galactic_defender.GalacticDefender galactic_defender =
                new GalacticDefender(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        this.sensor_manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.accelerometer = sensor_manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        this.gesture_detector = new GestureDetectorCompat(this, new Gestures());


        if (Build.VERSION.SDK_INT < 16) { // previous versions of Jelly Bean
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else { // equal or superior versions of Jelly Bean
            final int flags = View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION // Hides navigation bar
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Hides navigation bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            final View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(flags);
            decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(flags);
                }
            });
        }
        getSupportActionBar().hide(); // Hides the action bar
        galactic_defender.setKeepScreenOn(true);
        setContentView(galactic_defender);
    }



}
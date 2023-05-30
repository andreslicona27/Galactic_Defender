package com.example.galactic_defender.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.galactic_defender.Scenes.SceneGame;

/**
 * This class provides hardware-related functionalities such as vibration and sensor data.
 * It provides a method for vibrating the device.
 * And it also handles the accelerometer sensor and provides methods to start, stop, and handle
 * sensor events.
 */
public class Hardware extends AppCompatActivity implements SensorEventListener{

    /**
     * Represents a vibrator that can generate haptic feedback.
     */
    Vibrator vibrator;

    /**
     * Manages sensor-related operations, such as registering and listening for sensor events.
     */
    SensorManager sensor_manager;

    /**
     * Represents an accelerometer sensor that measures acceleration forces applied to the device.
     */
    public Sensor accelerometer;

    /**
     * An array to store the gravity components (x, y, z) measured by the accelerometer sensor.
     * The gravity values represent the force applied to the device due to gravity.
     */
    float[] gravity;

    /**
     * An array to store the linear acceleration components (x, y, z) calculated by subtracting the gravity components
     * from the accelerometer readings. Linear acceleration values represent the actual acceleration experienced by the device,
     * excluding the force of gravity.
     */
    float[] linear_acceleration;

    /**
     * Represents the scene class for it to access a function
     */
    SceneGame scene_game;

    /**
     * Constructor for the Hardware class.
     * Initializes the vibrator and sensor manager.
     * @param context The context of the application.
     */
    public Hardware(Context context, SceneGame scene_game){
        this.scene_game = scene_game;

        this.vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        this.sensor_manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.accelerometer = sensor_manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        resume();

        this.gravity = new float[3];
        this.linear_acceleration = new float[3];
    }

    /**
     * Called when sensor values have changed.
     *
     * @param event The sensor event.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        final float alpha = (float) 0.8;
        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];

        double magnitude = Math.sqrt(
                linear_acceleration[0] * linear_acceleration[0] +
                        linear_acceleration[1] * linear_acceleration[1] +
                        linear_acceleration[2] * linear_acceleration[2]);

        double threshold = 7.0;
        if (magnitude >= threshold) {
            scene_game.removeEnemies();
        }
    }
    /**
     * Called when the accuracy of a sensor has changed.
     *
     * @param sensor The sensor that has changed accuracy.
     * @param accuracy The new accuracy value.
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Called when the activity is resumed and becomes visible to the user.
     * This method registers the sensor listener to receive updates from the accelerometer sensor.
     */
    public void resume() {
        sensor_manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * Called when the activity is paused and is no longer visible to the user.
     * This method unregisters the sensor listener to stop receiving updates from the accelerometer sensor.
     */
    public void pause() {
        sensor_manager.unregisterListener(this, accelerometer);
    }


    /**
     * Vibrates the device
     */
    public void vibrate() {
        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.vibrator.vibrate(VibrationEffect.createOneShot(100,
                        VibrationEffect.DEFAULT_AMPLITUDE));

            } else {
                this.vibrator.vibrate(100);
            }
        }
    }


}

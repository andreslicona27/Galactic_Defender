package com.example.galactic_defender.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class provides hardware-related functionalities such as vibration and sensor data.
 * It provides a method for vibrating the device.
 * And it also handles the accelerometer sensor and provides methods to start, stop, and handle
 * sensor events.
 */
public class Hardware extends AppCompatActivity implements SensorEventListener{

    Vibrator vibrator;
    SensorManager sensor_manager;
    Sensor accelerometer;
    float[] gravity = new float[3];
    float[] linear_acceleration = new float[3];

    /**
     * Constructor for the Hardware class.
     * Initializes the vibrator and sensor manager.
     * @param context The context of the application.
     */
    public Hardware(Context context){
        this.vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        this.sensor_manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.accelerometer = sensor_manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    /**
     * Starts listening to sensor events.
     */
    public void start() {
        if(this.sensor_manager != null){
            this.sensor_manager.registerListener(this, this.accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    /**
     * Stops listening to sensor events.
     */
    public void stop() {
        if(this.sensor_manager != null){
            this.sensor_manager.unregisterListener(this);
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
     * Called when sensor values have changed.
     *
     * @param event The sensor event.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float xAxis = event.values[0];
            float yAxis = event.values[1];
            float zAxis = event.values[2];
            // TODO stun the enemies with the values of the axis

            final float alpha = (float) 0.8;

            // Isolate the force of gravity with the low-pass filter.
            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

            // Remove the gravity contribution with the high-pass filter.
            linear_acceleration[0] = event.values[0] - gravity[0];
            linear_acceleration[1] = event.values[1] - gravity[1];
            linear_acceleration[2] = event.values[2] - gravity[2];
        }
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

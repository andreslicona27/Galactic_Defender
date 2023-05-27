package com.example.galactic_defender.Scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.galactic_defender.GalacticDefender;
import com.example.galactic_defender.MainActivity;
import com.example.galactic_defender.R;

import java.util.List;

/**
 * The SceneRecords class represents a scene for displaying records.
 * It extends the Scene class and provides methods for drawing the scene on a canvas.
 *
 * @author [Andres Licona]
 * @version [1.0]
 * @since [05-04-2023]
 */
public class SceneRecords extends Scene{

    /**
     * List of high scores.
     */
    List<Integer> high_scores;

    /**
     * Paint object for drawing scores.
     */
    Paint scores_paint;

    /**
     * Paint object for ir to draw the button.
     */
    Paint button_paint;

    /**
     * RectF object representing the remove info button.
     */
    RectF remove_info_button;


    /**
     * Constructs an instance of the SceneRecords class.
     *
     * @param context The context of the application.
     * @param screen_height The height of the screen.
     * @param screen_width The width of the screen.
     * @param scene_number The number identifying the scene.
     */
    public SceneRecords(Context context, int screen_height, int screen_width, int scene_number) {
        super(context, screen_height, screen_width, scene_number);

        // Score paint properties
        this.scores_paint = new Paint();
        this.scores_paint.setColor(Color.WHITE);
        this.scores_paint.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/russo_one.ttf"));
        this.scores_paint.setTextSize((float)screen_height/15);

        // Button paint properties
        this.button_paint = new Paint();
        this.button_paint.setColor(ContextCompat.getColor(context, R.color.main_yellow));

        // Button
        this.remove_info_button = new RectF((float)screen_width / 4, (float)screen_height/7*5,
                (float)screen_width/4*3, (float)screen_height / 7 * 6);

        // Get high scores
        this.high_scores = MainActivity.record_data_base.getTopScores();
    }

    /**
     * Draws the scene on the canvas.
     *
     * @param canvas The canvas on which the scene should be drawn.
     */
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawText(context.getString(R.string.records_title),
                (float)screen_width/2 - title_paint.measureText((String)context.getString(R.string.records_title))/2,
                (float)screen_height/6, title_paint);


        if(high_scores.size() == 0){
            canvas.drawText((String)context.getString(R.string.no_records_message),
                    (float)this.screen_width/2 - this.scores_paint.measureText((String)context.getString(R.string.no_records_message))/2,
                    (float)screen_height / 2, this.scores_paint);
        } else {
            canvas.drawRoundRect(this.remove_info_button, 20f, 20f, button_paint);
            canvas.drawText((String) context.getString(R.string.remove_info_button),
                    (float)screen_width/2 - this.scores_paint.measureText((String) context.getString(R.string.remove_info_button)) / 2,
                    (float)screen_height/5*4, this.scores_paint);

            // Show records
            canvas.drawText("1 - " + this.high_scores.get(0),
                    (float)this.screen_width/2 - this.scores_paint.measureText((String)"1 - " + this.high_scores.get(0))/2,
                    (float)this.screen_height/2 - (float)this.screen_height/10,
                    scores_paint);
            if(high_scores.size() > 1){
                canvas.drawText("2 - " + this.high_scores.get(1),
                        (float)this.screen_width/2 -this.scores_paint.measureText((String)"2 - " + this.high_scores.get(1))/2,
                    (float)this.screen_height/2,
                        scores_paint);
                if(high_scores.size() > 2){
                    canvas.drawText("3 - " + this.high_scores.get(2),
                            (float)this.screen_width/2 - this.scores_paint.measureText((String)"3 - " + this.high_scores.get(2))/2,
                            (float)this.screen_height/2 + (float)this.screen_height/10,
                            scores_paint);
                }
            }
        }
    }

    /**
     * Handles touch events on the scene.
     *
     * @param event The MotionEvent representing the touch event.
     * @return The result of the touch event handling.
     * Returns the corresponding scene number based on the touched button or the result from the super class's onTouchEvent.
     * Returns the current scene number if no button was touched.
     */
    @Override
    public int onTouchEvent(MotionEvent event){
        int x = (int) event.getX();
        int y = (int) event.getY();
        int aux = (int) super.onTouchEvent(event);

        if (aux != scene_number && aux != -1) {
            return aux;
        }

        if (this.remove_info_button.contains(x, y)) {
            MainActivity.record_data_base.deleteAllRecords();
        }
        return this.scene_number;
    }

}

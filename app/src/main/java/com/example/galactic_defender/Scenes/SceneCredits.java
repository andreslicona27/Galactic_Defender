package com.example.galactic_defender.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.example.galactic_defender.GalacticDefender;
import com.example.galactic_defender.R;

import java.io.IOException;


/**
 * The SceneCredits class represents the credits scene in the game.
 * It extends the Scene class and adds functionality specific to the credits scene.
 * The credits scene displays the credits title and inherits the drawing and touch event handling
 * from the parent class.
 *
 * @author [Andres Licona]
 * @version [1.0]
 * @since [05-04-2023]
 */
public class SceneCredits extends Scene{

    /**
     * The asset of the english image.
     */
    Bitmap english_asset;

    /**
     * The asset of the spanish image.
     */
    Bitmap spanish_asset;

    /**
     * English credits image.
     */
    Bitmap credits_english;

    /**
     * Spanish credits image.
     */
    Bitmap credits_spanish;

    /**
     * Constructs an instance of the SceneCredits class.
     *
     * @param context The context of the application.
     * @param screen_height The height of the screen.
     * @param screen_width The width of the screen.
     * @param scene_number The number identifying the scene.
     * @exception RuntimeException If there is a problem obtaining the assets
     */
    public SceneCredits(Context context, int screen_height, int screen_width, int scene_number) {
        super(context, screen_height, screen_width, scene_number);

        // Assets
        try{
            this.input_stream = assets_manager.open("images/credits/credits_english.png");
            this.english_asset = BitmapFactory.decodeStream(input_stream);
            this.input_stream = assets_manager.open("images/credits/credits_spanish.png");
            this.spanish_asset = BitmapFactory.decodeStream(input_stream);

        } catch (IOException e) {
            Log.i("assets", "problem getting the asset");
            throw new RuntimeException(e);
        }

        // Images
        this.credits_english = Bitmap.createScaledBitmap(this.english_asset, screen_width+10, screen_height+10, true);
        this.credits_spanish = Bitmap.createScaledBitmap(this.spanish_asset, screen_width+10, screen_height+10, true);
    }

    /**
     * Draws the scene on the canvas.
     *
     * @param canvas The canvas on which the scene should be drawn.
     */
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawText(context.getString(R.string.credits_title),
                (float)screen_width/2 - title_paint.measureText((String)context.getString(R.string.credits_title))/2,
                (float)screen_height/6, title_paint);

        if(GalacticDefender.language.equals("en")){
            canvas.drawBitmap(this.credits_english, null, new Rect(canvas.getWidth()/10,
                    canvas.getHeight()/5, canvas.getWidth() - canvas.getWidth()/10, canvas.getHeight()), null);
        } else {
            canvas.drawBitmap(this.credits_spanish, null, new Rect(canvas.getWidth()/10,
                    canvas.getHeight()/5, canvas.getWidth() - canvas.getWidth()/10,
                    canvas.getHeight()), null);
        }
    }
}

package com.example.galactic_defender.DataBase;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * The RecordDataBase class is responsible for managing the game database,
 * including creating the table, inserting scores, retrieving top scores,
 * and deleting all records.
 *
 * @author [Andres Licona]
 * @version [1.0]
 * @since [05-19-2023]
 */
public class RecordDataBase extends SQLiteOpenHelper {

    /**
     * The name of the game database.
     */
    private static final String DATABASE_NAME = "my_game_database.db";

    /**
     * The name of the table for high scores.
     */
    public static final String TABLE = "high_scores";

    /**
     * The version of the game database.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * The context used for accessing the game database.
     */
    Context context;

    /**
     * Constructs a new instance of the RecordDataBase class.
     *
     * @param context The context used for accessing the game database.
     */
    public RecordDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "score INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE);
        onCreate(db);
    }

    /**
     * Inserts a score into the high scores table.
     *
     * @param score The score to be inserted.
     */
    public void insertScore(int score) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("score", score);
            db.insert(TABLE, null, values);
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("data base test", "Insert problem " + e.toString());
        }
    }

    /**
     * Retrieves the top scores from the high scores table.
     *
     * @return A list of the top scores.
     */
    public List<Integer> getTopScores() {
        List<Integer> top_scores = new ArrayList<>();

        try{
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "SELECT DISTINCT score FROM " + TABLE + " ORDER BY score DESC LIMIT 3";
            Cursor cursor = db.rawQuery(query, null);

            while (cursor.moveToNext()) {
                int score = cursor.getInt(cursor.getColumnIndexOrThrow("score"));
                top_scores.add(score);
            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("data base test", "getting the scores problem " + e.toString());
        }

        return top_scores;
    }

    /**
     * Deletes all records from the high scores table.
     */
    public void deleteAllRecords() {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE, null, null);

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("data base test", "deleting record problem " + e.toString());
        }
    }
}
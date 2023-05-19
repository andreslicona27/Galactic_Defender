package com.example.galactic_defender.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class RecordDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_game_database.db";
    public static final String TABLE = "high_scores";
    private static final int DATABASE_VERSION = 1;

    public RecordDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "score INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE);
        onCreate(db);
    }

    public void saveHighScore(int score) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("score", score);

        db.insert("high_scores", null, values);
    }

}
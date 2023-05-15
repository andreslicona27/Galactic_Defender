package com.example.galactic_defender.Utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class RecordDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_game.db";
    private static final int DATABASE_VERSION = 1;

    public RecordDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveHighScore(String playerName, int score) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("player_name", playerName);
        values.put("score", score);

        db.insert("high_scores", null, values);
    }

}

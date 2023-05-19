package com.example.galactic_defender.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class HighScores extends RecordDataBase {

    Context context;
    long id = 0;

    public HighScores(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertScore(int score) {
        try {
            RecordDataBase database = new RecordDataBase(context);
            SQLiteDatabase db = database.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("score", score);

            long id = db.insert(TABLE, null, values);

        } catch (Exception e) {
            e.toString();
        }

        return id;
    }

//    public RecordDataBase seeScores() {
//        RecordDataBase rdb = new RecordDataBase(context);
//        SQLiteDatabase db = rdb.getWritableDatabase();
//
//        Record score = null;
//        Cursor record_cursor;
//
//        record_cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE id = " + id + " LIMIT 1",
//                null);
//
//        if (record_cursor.moveToFirst()) {
//            score = new Record();
//            score.setId(record_cursor.getInt(0));
//            score.setInt(record_cursor.getIt(1));
//        }
//        record_cursor.close();
//        return score;
//    }
}

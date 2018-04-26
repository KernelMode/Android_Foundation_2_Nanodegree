package com.example.ankur.habittracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ankur on 17-03-2018.
 */

public class HabitReaderHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "HabitReader.db";

    public  HabitReaderHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + HabitContract.HabitEntry.TABLE_NAME + " (" +
                HabitContract.HabitEntry.COLUMN_HABIT + " VARCHAR(20), " +
                HabitContract.HabitEntry.COLUMN_HABIT_STATUS + " INT)"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + HabitContract.HabitEntry.TABLE_NAME);
        onCreate(db);
    }

    public void insertH(String habit, int status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_HABIT, habit);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_STATUS, status);
        db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);
        db.close();
    }

    public Cursor readH(){
        SQLiteDatabase db = getReadableDatabase();
        String[] p = {HabitContract.HabitEntry.COLUMN_HABIT, HabitContract.HabitEntry.COLUMN_HABIT_STATUS};
        return db.query(HabitContract.HabitEntry.TABLE_NAME, p , null,null,null,null,null);
    }
}

package com.example.ankur.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HabitReaderHelper reader = new HabitReaderHelper(MainActivity.this);
        reader.insertH("Playing", 0);
        reader.insertH("Programming", 0);
        Cursor c = reader.readH();
        while(c.moveToNext()){
            Log.v("TRACKER", "HABIT: " + c.getString(0) + ", STATUS : " + c.getInt(1));
        }
    }
}

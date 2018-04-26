package com.example.ankur.habittracker;

import android.provider.BaseColumns;

/**
 * Created by Ankur on 17-03-2018.
 */

public final class HabitContract {
    private HabitContract(){}

    public static class HabitEntry implements BaseColumns{
        public static final String TABLE_NAME = "HabitTracker";
        public static final String COLUMN_HABIT = "Habit";
        public static final String COLUMN_HABIT_STATUS = "Status";
    }
}

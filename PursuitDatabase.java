package com.vipromos.pursuit;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Pursuit.class}, version = 1)
public abstract class PursuitDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "pursuit12.db";

    private static PursuitDatabase mPursuitDatabase;

    // Singleton
    public static PursuitDatabase getInstance(Context context) {
        if (mPursuitDatabase == null) {
            mPursuitDatabase = Room.databaseBuilder(context, PursuitDatabase.class,
                    DATABASE_NAME).allowMainThreadQueries().build();
        }
        return mPursuitDatabase;
    }

    public abstract PursuitDao pursuitDao();
}
package com.A_23_59.hypernote.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(version = 2,exportSchema = false,entities = {Task.class} )
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase appDataBase;

    public static AppDataBase getAppDataBase(Context context){
        if (appDataBase==null) {
            appDataBase = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "db_note")
                    .allowMainThreadQueries().build();
        }
        return appDataBase;
    }

    public abstract TaskDao getTaskDao();
}

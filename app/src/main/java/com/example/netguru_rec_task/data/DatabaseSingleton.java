package com.example.netguru_rec_task.data;

import android.content.Context;

import androidx.room.Room;

public class DatabaseSingleton {

    private static DatabaseSingleton instance = null;
    private AppDatabase database;

    private DatabaseSingleton(Context context) {
        database = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class,
                "shopping-list.db")
                .fallbackToDestructiveMigration() // TODO remove it later!
                .build();
    }

    public static DatabaseSingleton Instance(Context context) {
        if (instance == null) {
            synchronized (DatabaseSingleton.class) {
                instance = new DatabaseSingleton(context);
            }
        }
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public ShopListItemDao shopListItemDao() {
        return database.shopListItemDao();
    }
}

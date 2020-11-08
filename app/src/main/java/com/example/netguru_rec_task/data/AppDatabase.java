package com.example.netguru_rec_task.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.netguru_rec_task.models.GroceryItem;
import com.example.netguru_rec_task.models.ShopListItem;

@Database(entities = {
        ShopListItem.class,
        GroceryItem.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ShopListItemDao shopListItemDao();

    public abstract GroceryItemDao groceryItemDao();
}

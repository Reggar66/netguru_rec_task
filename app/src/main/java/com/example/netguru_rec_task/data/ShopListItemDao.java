package com.example.netguru_rec_task.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.netguru_rec_task.models.ShopListItem;

import java.util.List;

@Dao
public interface ShopListItemDao {

    @Insert
    void insertAll(ShopListItem... shopListItems);

    @Delete
    void delete(ShopListItem shopListItem);

    @Query("SELECT * FROM shop_lists WHERE archived = :isArchived")
    LiveData<List<ShopListItem>> liveDataGetAllShopLists(boolean isArchived);
}

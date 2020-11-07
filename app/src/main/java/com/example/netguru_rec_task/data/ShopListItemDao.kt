package com.example.netguru_rec_task.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.netguru_rec_task.models.ShopListItem

@Dao
interface ShopListItemDao {
    @Insert
    suspend fun insertAll(vararg shopListItems: ShopListItem?)

    @Delete
    suspend fun delete(shopListItem: ShopListItem?)

    @Query("SELECT * FROM shop_lists WHERE archived = :isArchived")
    fun liveDataGetAllShopLists(isArchived: Boolean): LiveData<List<ShopListItem>>
}
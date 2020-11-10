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

    @Delete
    suspend fun deleteList(listToDelete: List<ShopListItem>)

    @Query("DELETE FROM groceries_list WHERE shopping_list_id = :parentListId")
    suspend fun deleteGroceriesFromParentList(parentListId: Int)

    @Query("SELECT * FROM shop_lists WHERE archived = :isArchived ORDER BY timestamp ASC")
    fun liveDataGetAllShopLists(isArchived: Boolean): LiveData<List<ShopListItem>>

    @Query("UPDATE shop_lists SET archived = :isArchived WHERE id = :listId")
    suspend fun updateArchivedStatus(isArchived: Boolean, listId: Int)
}
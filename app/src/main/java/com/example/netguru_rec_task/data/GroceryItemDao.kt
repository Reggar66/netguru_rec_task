package com.example.netguru_rec_task.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.netguru_rec_task.models.GroceryItem
import com.example.netguru_rec_task.models.ShopListItem

@Dao
interface GroceryItemDao {

    @Insert
    suspend fun insertAll(vararg groceryItem: GroceryItem)

    @Delete
    suspend fun delete(groceryItem: GroceryItem)

    @Query("SELECT * FROM groceries_list WHERE shopping_list_id = :shopListId ORDER BY completed ASC, timestamp ASC")
    fun liveDataGetAllGroceriesForShopList(shopListId: Int): LiveData<List<GroceryItem>>

    @Query("SELECT * FROM shop_lists WHERE id = :shopListId")
    suspend fun getParentShopList(shopListId: Int): ShopListItem

    @Query("UPDATE groceries_list SET completed = :isCompleted WHERE id = :groceryId")
    suspend fun updateCompletionStatus(isCompleted: Boolean, groceryId: Int)

}
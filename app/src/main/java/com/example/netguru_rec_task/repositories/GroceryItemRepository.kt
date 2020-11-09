package com.example.netguru_rec_task.repositories

import androidx.lifecycle.LiveData
import com.example.netguru_rec_task.data.GroceryItemDao
import com.example.netguru_rec_task.models.GroceryItem
import com.example.netguru_rec_task.models.ShopListItem

class GroceryItemRepository(
    private val groceryItemDao: GroceryItemDao,
    private val shopListId: Int
) {
    val allGroceryItems: LiveData<List<GroceryItem>> =
        groceryItemDao.liveDataGetAllGroceriesForShopList(shopListId)


    suspend fun insert(groceryItem: GroceryItem) {
        groceryItemDao.insertAll(groceryItem)
    }

    suspend fun getParentShopList(shopListId: Int): ShopListItem {
        return groceryItemDao.getParentShopList(shopListId)
    }
}
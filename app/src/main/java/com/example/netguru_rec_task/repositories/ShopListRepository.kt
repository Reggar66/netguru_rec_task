package com.example.netguru_rec_task.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.netguru_rec_task.data.DatabaseSingleton
import com.example.netguru_rec_task.data.ShopListItemDao
import com.example.netguru_rec_task.models.ShopListItem

class ShopListRepository(private val shopListItemDao: ShopListItemDao) {

    val allShopLists: LiveData<List<ShopListItem>> = shopListItemDao.liveDataGetAllShopLists(false)
    val allArchivedShopLists: LiveData<List<ShopListItem>> = shopListItemDao.liveDataGetAllShopLists(true)

    suspend fun insert(shopListItem: ShopListItem) {
        shopListItemDao.insertAll(shopListItem)
    }

    suspend fun updateArchivedStatus(isArchived: Boolean, listId: Int) {
        shopListItemDao.updateArchivedStatus(isArchived, listId)
    }
}
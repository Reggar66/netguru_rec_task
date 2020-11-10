package com.example.netguru_rec_task.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.netguru_rec_task.data.DatabaseSingleton
import com.example.netguru_rec_task.models.ShopListItem
import com.example.netguru_rec_task.repositories.ShopListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShopListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ShopListRepository
    val allShopLists: LiveData<List<ShopListItem>>
    val allArchivedShopLists: LiveData<List<ShopListItem>>

    init {
        val shopListDao = DatabaseSingleton.Instance(application).shopListItemDao()
        repository = ShopListRepository(shopListDao)
        allShopLists = repository.allShopLists
        allArchivedShopLists = repository.allArchivedShopLists
    }

    fun insert(shopListItem: ShopListItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(shopListItem)
    }

    fun updateArchivedStatus(isArchived: Boolean, selectedList: List<ShopListItem>) =
        viewModelScope.launch(Dispatchers.IO) {
            for (shopList in selectedList) {
                repository.updateArchivedStatus(isArchived, shopList.id)
            }
        }
}
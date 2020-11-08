package com.example.netguru_rec_task.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.netguru_rec_task.data.DatabaseSingleton
import com.example.netguru_rec_task.models.GroceryItem
import com.example.netguru_rec_task.repositories.GroceryItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroceriesViewModel(
    application: Application,
    shopListId: Int
) : AndroidViewModel(application) {
    private val repository: GroceryItemRepository
    val allGroceriesForShopList: LiveData<List<GroceryItem>>

    init {
        val groceriesDao = DatabaseSingleton.Instance(application).groceryItemDao()
        repository = GroceryItemRepository(groceriesDao, shopListId)
        allGroceriesForShopList = repository.allGroceryItems
    }

    fun insert(groceryItem: GroceryItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(groceryItem)
    }

    /**
     * Factory
     */

    class GroceriesViewModelFactory(val application: Application, val listItemId: Int) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GroceriesViewModel(application, listItemId) as T
        }
    }
}
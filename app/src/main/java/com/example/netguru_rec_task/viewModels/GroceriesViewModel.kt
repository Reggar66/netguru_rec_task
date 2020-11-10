package com.example.netguru_rec_task.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.netguru_rec_task.data.DatabaseSingleton
import com.example.netguru_rec_task.models.GroceryItem
import com.example.netguru_rec_task.models.ShopListItem
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

    fun delete(groceryItems: List<GroceryItem>) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(groceryItems)
    }

    suspend fun getParentShopList(shopListId: Int): ShopListItem {
        return repository.getParentShopList(shopListId)
    }

    fun updateCompletionStatus(isCompleted: Boolean, groceryId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCompletionStatus(isCompleted, groceryId)
        }

    fun updateGroceriesNumbers(shopListId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            val groceries = allGroceriesForShopList.value
            val numb = groceries?.size ?: 0
            var done = 0

            if (groceries != null) {
                for (grocery in groceries) {
                    if (grocery.isCompleted)
                        done++
                }
            }
            repository.updateGroceriesNumbers(numb, done, shopListId)
        }

    /**
     * Factory
     */

    class GroceriesViewModelFactory(
        private val application: Application,
        private val listItemId: Int
    ) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GroceriesViewModel(application, listItemId) as T
        }
    }
}
package com.example.netguru_rec_task.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.netguru_rec_task.models.GroceryItem

class GroceryDiffUtilCallback(
    private val oldGroceries: List<GroceryItem>,
    private val newGroceries: List<GroceryItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldGroceries.size
    }

    override fun getNewListSize(): Int {
        return newGroceries.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldGroceries[oldItemPosition].id == newGroceries[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldGroceries[oldItemPosition]
        val newItem = newGroceries[newItemPosition]
        return oldItem.itemName == newItem.itemName &&
                oldItem.isCompleted == newItem.isCompleted &&
                oldItem.quantity == newItem.quantity
    }

}
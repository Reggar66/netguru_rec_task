package com.example.netguru_rec_task.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.netguru_rec_task.models.ShopListItem

class ShopListDiffUtilCallback(
    private val oldShopList: List<ShopListItem>,
    private val newShopList: List<ShopListItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldShopList.size
    }

    override fun getNewListSize(): Int {
        return newShopList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldShopList[oldItemPosition].id == newShopList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldShopList[oldItemPosition]
        val newItem = newShopList[newItemPosition]
        return oldItem.listName == newItem.listName &&
                oldItem.groceriesNumber == newItem.groceriesNumber &&
                oldItem.groceriesDone == newItem.groceriesDone &&
                oldItem.isArchived == newItem.isArchived
    }
}
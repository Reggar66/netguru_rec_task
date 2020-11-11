package com.example.netguru_rec_task.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.netguru_rec_task.R
import com.example.netguru_rec_task.models.ShopListItem
import com.example.netguru_rec_task.utils.ShopListDiffUtilCallback
import com.google.android.material.color.MaterialColors

class ShoppingListAdapter :
    RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {


    private lateinit var context: Context
    private var shopListItems: ArrayList<ShopListItem> = ArrayList()
    var selectionMode = false
        private set
    private var colorSelection: Int = 0

    var onItemClickListener: OnItemClickListener? = null
    var onItemLongClickListener: OnItemLongClickListener? = null

    interface OnItemClickListener {
        fun onItemClickListener(shoppingList: ShopListItem)
    }

    interface OnItemLongClickListener {
        fun onItemLongClickListener(shoppingList: ShopListItem)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewListName: TextView =
            itemView.findViewById(R.id.fragment_shoppingListItem_textView_name)
        val textViewGroceriesCounter: TextView =
            itemView.findViewById(R.id.fragment_shoppingListItem_textView_counter)

        init {
            itemView.setOnClickListener {
                if (selectionMode) {
                    manageSelection()
                } else {
                    onItemClickListener?.onItemClickListener(shopListItems[adapterPosition])
                }
            }

            itemView.setOnLongClickListener {
                if (selectionMode) {
                    selectionMode = false
                    deselectAll()
                } else {
                    selectionMode = true
                    shopListItems[adapterPosition].isSelected = true
                    notifyItemChanged(adapterPosition)
                }

                onItemLongClickListener?.onItemLongClickListener(shopListItems[adapterPosition])
                return@setOnLongClickListener true
            }
        }

        private fun manageSelection() {
            val shopList = shopListItems[adapterPosition]
            if (!shopList.isSelected) {
                shopList.isSelected = true
                itemView.setBackgroundColor(colorSelection)
            } else {
                shopList.isSelected = false
                itemView.background = null
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShoppingListAdapter.ViewHolder {
        context = parent.context
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.fragment_shoping_list_item, parent, false)

        // Theme color to use as selection color
        colorSelection = MaterialColors.getColor(itemView, R.attr.colorPrimaryVariant)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = shopListItems[position]
        holder.textViewListName.text = item.listName
        holder.textViewGroceriesCounter.text =
            context.getString(R.string.groceries_done, item.groceriesDone, item.groceriesNumber)

        if (item.isSelected) {
            holder.itemView.setBackgroundColor(colorSelection)
        } else {
            holder.itemView.background = null
        }
    }

    override fun getItemCount() = shopListItems.size

    fun setShopList(shopListItems: List<ShopListItem>) {
        val shopListDiffUtilCallback = ShopListDiffUtilCallback(this.shopListItems, shopListItems)
        val diffResult = DiffUtil.calculateDiff(shopListDiffUtilCallback)

        this.shopListItems.clear()
        this.shopListItems.addAll(shopListItems)

        diffResult.dispatchUpdatesTo(this)
    }

    private fun deselectAll() {
        for ((index, shopList) in shopListItems.withIndex()) {
            if (shopList.isSelected) {
                shopList.isSelected = false
                notifyItemChanged(index)
            }
        }
    }

    fun quitSelection() {
        selectionMode = false
        deselectAll()
    }

    fun getSelectedShopLists(): List<ShopListItem> {
        val listToDelete = ArrayList<ShopListItem>()
        for (shopList in shopListItems) {
            if (shopList.isSelected)
                listToDelete.add(shopList)
        }
        return listToDelete
    }
}
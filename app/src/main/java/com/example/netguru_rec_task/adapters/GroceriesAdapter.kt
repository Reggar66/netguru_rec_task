package com.example.netguru_rec_task.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.netguru_rec_task.R
import com.example.netguru_rec_task.models.GroceryItem
import com.example.netguru_rec_task.utils.GroceryDiffUtilCallback
import com.google.android.material.color.MaterialColors

class GroceriesAdapter() :
    RecyclerView.Adapter<GroceriesAdapter.ViewHolder>() {

    private lateinit var context: Context
    private val groceries = ArrayList<GroceryItem>()
    private var selectionMode = false
    private var colorSelection: Int = -1

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClickListener(groceriesItem: GroceryItem, position: Int)
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
                    onItemClickListener?.onItemClickListener(
                        groceries[adapterPosition],
                        adapterPosition
                    )
                }

            }

            itemView.setOnLongClickListener {
                if (selectionMode) {
                    selectionMode = false
                    deselectAll()
                } else {
                    selectionMode = true
                    groceries[adapterPosition].isSelected = true
                    notifyItemChanged(adapterPosition)
                }
                return@setOnLongClickListener true
            }
        }

        private fun manageSelection() {
            val item = groceries[adapterPosition]
            if (!item.isSelected) {
                item.isSelected = true
                itemView.setBackgroundColor(colorSelection)
            } else {
                item.isSelected = false
                itemView.background = null
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.fragment_shoping_list_item, parent, false)

        // Theme color to use as selection color
        colorSelection = MaterialColors.getColor(itemView, R.attr.colorPrimaryVariant)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = groceries[position]
        holder.textViewListName.text = item.itemName
        if (item.isCompleted && !selectionMode) {
            holder.textViewListName.apply {
                paintFlags = paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            }
            holder.textViewGroceriesCounter.text = context.getString(R.string.completed)
        } else {
            holder.textViewGroceriesCounter.text = item.quantity.toString()
            holder.textViewListName.apply {
                paintFlags = paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        if (item.isSelected) {
            holder.itemView.setBackgroundColor(colorSelection)
        } else {
            holder.itemView.background = null
        }
    }

    override fun getItemCount(): Int {
        return groceries.size
    }

    fun setGroceries(groceries: List<GroceryItem>) {
        val groceryDiffUtilCallback = GroceryDiffUtilCallback(this.groceries, groceries)
        val diffResult = DiffUtil.calculateDiff(groceryDiffUtilCallback)

        this.groceries.clear()
        this.groceries.addAll(groceries)

        diffResult.dispatchUpdatesTo(this)
    }

    private fun deselectAll() {
        for ((index, grocery) in groceries.withIndex()) {
            if (grocery.isSelected) {
                grocery.isSelected = false
                notifyItemChanged(index)
            }
        }
    }
}
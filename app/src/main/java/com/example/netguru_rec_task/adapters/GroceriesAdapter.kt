package com.example.netguru_rec_task.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.netguru_rec_task.R
import com.example.netguru_rec_task.models.GroceryItem

class GroceriesAdapter() :
    RecyclerView.Adapter<GroceriesAdapter.ViewHolder>() {

    private val groceries = ArrayList<GroceryItem>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewListName: TextView =
            itemView.findViewById(R.id.fragment_shoppingListItem_textView_name)
        val textViewGroceriesCounter: TextView =
            itemView.findViewById(R.id.fragment_shoppingListItem_textView_counter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_shoping_list_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = groceries[position]
        holder.textViewListName.text = item.itemName
        holder.textViewGroceriesCounter.text = item.quantity.toString()
    }

    override fun getItemCount(): Int {
        return groceries.size
    }

    fun setGroceries(groceries: List<GroceryItem>) {
        this.groceries.clear()
        this.groceries.addAll(groceries)
        //TODO DiffUtil
        notifyDataSetChanged()
    }
}
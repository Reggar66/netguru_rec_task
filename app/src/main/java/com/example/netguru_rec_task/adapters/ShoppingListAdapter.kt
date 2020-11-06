package com.example.netguru_rec_task.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.netguru_rec_task.R
import com.example.netguru_rec_task.models.ShopListItem

class ShoppingListAdapter(private val shopListItems: ArrayList<ShopListItem>) :
    RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewListName: TextView =
            itemView.findViewById(R.id.fragment_shoppingListItem_textView_name)
        val textViewGroceriesCounter: TextView =
            itemView.findViewById(R.id.fragment_shoppingListItem_textView_counter)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShoppingListAdapter.ViewHolder {

        context = parent.context
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.fragment_shoping_list_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = shopListItems[position]
        holder.textViewListName.text = item.listName
        holder.textViewGroceriesCounter.text =
            """Groceries done ${item.groceriesDone}/${item.groceriesNumber}"""
    }

    override fun getItemCount() = shopListItems.size
}
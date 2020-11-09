package com.example.netguru_rec_task.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.netguru_rec_task.R
import com.example.netguru_rec_task.models.ShopListItem
import com.example.netguru_rec_task.utils.ShopListDiffUtilCallback
import com.example.netguru_rec_task.viewModels.ShopListViewModel

class ShoppingListAdapter(
    private val viewModel: ShopListViewModel
) :
    RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {


    private lateinit var context: Context
    private var shopListItems: ArrayList<ShopListItem> = ArrayList()
    var onItemClickListener: OnItemClickListener? = null

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
                onItemClickListener?.onItemClickListener(shopListItems[adapterPosition])
            }

            itemView.setOnLongClickListener {

                val listItem = shopListItems[layoutPosition]
                viewModel.updateArchivedStatus(!listItem.isArchived, listItem.id)
                if (!listItem.isArchived) {
                    Toast.makeText(context, "Archived", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Restored", Toast.LENGTH_SHORT).show()
                }
                return@setOnLongClickListener true
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

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = shopListItems[position]
        holder.textViewListName.text = item.listName
        holder.textViewGroceriesCounter.text =
                // TODO extract to string resources
            """Groceries done ${item.groceriesDone}/${item.groceriesNumber}"""
    }

    override fun getItemCount() = shopListItems.size

    fun setShopList(shopListItems: List<ShopListItem>) {
        val shopListDiffUtilCallback = ShopListDiffUtilCallback(this.shopListItems, shopListItems)
        val diffResult = DiffUtil.calculateDiff(shopListDiffUtilCallback)

        this.shopListItems.clear()
        this.shopListItems.addAll(shopListItems)

        diffResult.dispatchUpdatesTo(this)
    }
}
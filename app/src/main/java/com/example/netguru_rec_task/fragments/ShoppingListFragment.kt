package com.example.netguru_rec_task.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netguru_rec_task.R
import com.example.netguru_rec_task.adapters.ShoppingListAdapter
import com.example.netguru_rec_task.models.ShopListItem
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ShoppingListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var itemList = ArrayList<ShopListItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewManager = LinearLayoutManager(requireContext())
        viewAdapter = ShoppingListAdapter(itemList)

        recyclerView =
            view.findViewById<RecyclerView>(R.id.fragment_shoppingList_recyclerView).apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        LinearLayoutManager.VERTICAL
                    )
                )
            }

        val fabAddShoppingList =
            view.findViewById<FloatingActionButton>(R.id.fragment_shoppingList_fab)
        fabAddShoppingList.setOnClickListener {
            // TODO shopping list creation
            for (i in 1..5) {
                itemList.add(ShopListItem("Sample list $i", System.currentTimeMillis()))
                viewAdapter.notifyItemInserted(itemList.size - 1)
            }
        }
    }
}
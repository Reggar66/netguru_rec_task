package com.example.netguru_rec_task.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netguru_rec_task.R
import com.example.netguru_rec_task.adapters.ShoppingListAdapter
import com.example.netguru_rec_task.viewModels.ShopListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ArchivedListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: ShoppingListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: ShopListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ShopListViewModel::class.java)
        return inflater.inflate(R.layout.fragment_shopping_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewManager = LinearLayoutManager(requireContext())
        recyclerViewAdapter = ShoppingListAdapter()
        val fabAddShoppingList =
            view.findViewById<FloatingActionButton>(R.id.fragment_shoppingList_fab)
        fabAddShoppingList.hide()

        recyclerView =
            view.findViewById<RecyclerView>(R.id.fragment_shoppingList_recyclerView).apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = recyclerViewAdapter
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        LinearLayoutManager.VERTICAL
                    )
                )
            }

        viewModel.allArchivedShopLists.observe(viewLifecycleOwner, Observer { list ->
            recyclerViewAdapter.setShopList(list)
        })
    }
}
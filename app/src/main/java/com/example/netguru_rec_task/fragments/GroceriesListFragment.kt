package com.example.netguru_rec_task.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netguru_rec_task.R
import com.example.netguru_rec_task.adapters.GroceriesAdapter
import com.example.netguru_rec_task.models.GroceryItem
import com.example.netguru_rec_task.models.ShopListItem
import com.example.netguru_rec_task.viewModels.GroceriesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class GroceriesListFragment : Fragment() {

    val args: GroceriesListFragmentArgs by navArgs()

    lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: GroceriesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: GroceriesViewModel
    private lateinit var parentShopList: ShopListItem

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Retrieve ViewModel
        viewModel = ViewModelProvider(
            this,
            GroceriesViewModel.GroceriesViewModelFactory(
                requireActivity().application,
                args.shopListId
            )
        ).get(GroceriesViewModel::class.java)

        // Retrieve parent shopList and set fragment name
        viewModel.viewModelScope.launch {
            parentShopList = viewModel.getParentShopList(args.shopListId)
            requireActivity().title = parentShopList.listName
        }

        return inflater.inflate(R.layout.fragment_shopping_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewManager = LinearLayoutManager(requireContext())
        recyclerViewAdapter = GroceriesAdapter()
        val fabAddGrocery =
            view.findViewById<FloatingActionButton>(R.id.fragment_shoppingList_fab)
        fabAddGrocery.setOnClickListener {
            // TODO show dialog/fragment for grocery creation
            viewModel.insert(GroceryItem("Sample", 2, args.shopListId))
        }

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
        viewModel.allGroceriesForShopList.observe(viewLifecycleOwner, Observer { groceries ->
            recyclerViewAdapter.setGroceries(groceries)
        })

    }
}
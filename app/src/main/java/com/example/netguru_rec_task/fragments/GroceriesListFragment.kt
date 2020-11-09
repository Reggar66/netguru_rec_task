package com.example.netguru_rec_task.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class GroceriesListFragment : Fragment(),
    GroceriesAdapter.OnItemClickListener {

    private val args: GroceriesListFragmentArgs by navArgs()

    lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: GroceriesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: GroceriesViewModel
    private lateinit var parentShopList: ShopListItem
    private lateinit var bottomSheet: View

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

        // Bottom sheet
        bottomSheet = view.findViewById(R.id.bottomSheet)
        bottomSheet.visibility = View.VISIBLE
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        val editTextName = view.findViewById<EditText>(R.id.bottomSheet_editText_name)
        val editTextQuantity = view.findViewById<EditText>(R.id.bottomSheet_editText_quantity)
        val fabApplyGrocery =
            view.findViewById<FloatingActionButton>(R.id.bottomSheet_floatingActionButton_apply)
        fabApplyGrocery.setOnClickListener(OnApplyListener(editTextName, editTextQuantity))
        val fabAddGrocery =
            view.findViewById<FloatingActionButton>(R.id.fragment_shoppingList_fab)
        fabAddGrocery.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            editTextName.requestFocus()
        }
        val imageViewClose = view.findViewById<ImageView>(R.id.bottomSheet_imageView_hide)
        imageViewClose.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        // RecyclerView setup
        viewManager = LinearLayoutManager(requireContext())
        recyclerViewAdapter = GroceriesAdapter()
        recyclerViewAdapter.onItemClickListener = this
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

        // Observing data and updating UI
        viewModel.allGroceriesForShopList.observe(viewLifecycleOwner, Observer { groceries ->
            recyclerViewAdapter.setGroceries(groceries)
            viewModel.updateGroceriesNumbers(args.shopListId)
        })

    }

    override fun onItemClickListener(groceriesItem: GroceryItem, position: Int) {
        viewModel.updateCompletionStatus(!groceriesItem.isCompleted, groceriesItem.id)
    }

    /**
     * Inner classes
     */

    inner class OnApplyListener(
        private val editTextName: EditText,
        private val editTextQuantity: EditText
    ) :
        View.OnClickListener {
        override fun onClick(p0: View?) {
            val name = editTextName.text.toString()
            val quantity = editTextQuantity.text.toString().toInt()
            viewModel.insert(
                GroceryItem(
                    name,
                    System.currentTimeMillis(),
                    quantity,
                    args.shopListId
                )
            )

            editTextName.text.clear()
            editTextQuantity.text.clear()
            editTextName.requestFocus()
        }

    }
}
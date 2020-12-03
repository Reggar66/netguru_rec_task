package com.example.netguru_rec_task.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netguru_rec_task.ConfirmBottomDialog
import com.example.netguru_rec_task.R
import com.example.netguru_rec_task.ShoppingListBottomSheetDialog
import com.example.netguru_rec_task.adapters.ShoppingListAdapter
import com.example.netguru_rec_task.models.ShopListItem
import com.example.netguru_rec_task.viewModels.ShopListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ShoppingListFragment : Fragment(), ShoppingListAdapter.OnItemClickListener,
    ShoppingListAdapter.OnItemLongClickListener,
    ShoppingListBottomSheetDialog.OnButtonClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: ShoppingListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: ShopListViewModel

    private lateinit var fabAddShoppingList: FloatingActionButton
    private lateinit var fabArchive: FloatingActionButton

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
        recyclerViewAdapter.onItemClickListener = this
        recyclerViewAdapter.onItemLongClickListener = this

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

        viewModel.allShopLists.observe(viewLifecycleOwner, { list ->
            recyclerViewAdapter.setShopList(list)
        })

        fabAddShoppingList = view.findViewById(R.id.fragment_shoppingList_fab)
        fabAddShoppingList.setOnClickListener {
            val bottomDialog = ShoppingListBottomSheetDialog()
            bottomDialog.listener = this
            bottomDialog.show(parentFragmentManager, "BOTTOM_SHEET_DIALOG_SHOPPING_LIST")
        }

        fabArchive = view.findViewById(R.id.fragment_shoppingList_fab_archive)
        fabArchive.setOnClickListener(OnArchiveListener())
    }

    override fun onItemClickListener(shoppingList: ShopListItem) {
        val action =
            MainFragmentDirections.actionMainFragmentToGroceriesListFragment(shoppingList.id)
        val navController = findNavController()
        navController.navigate(action)
    }

    override fun onItemLongClickListener(shoppingList: ShopListItem) {
        manageButtonsVisibility()
    }

    private fun manageButtonsVisibility() {
        if (recyclerViewAdapter.selectionMode) {
            fabArchive.show()
            fabAddShoppingList.hide()
        } else {
            fabArchive.hide()
            fabAddShoppingList.show()
        }
    }

    override fun onApplyClickListener(name: String, time: Long, dialog: Dialog?) {
        viewModel.insert(ShopListItem(name, time))
        dialog?.dismiss()
    }

    /**
     * OnClickListener for archive list
     */
    private inner class OnArchiveListener : View.OnClickListener,
        ConfirmBottomDialog.OnButtonClickListener {
        override fun onClick(p0: View?) {
            val confirmDialog = ConfirmBottomDialog()
            confirmDialog.onButtonClickListener = this
            confirmDialog.show(parentFragmentManager, "BOTTOM_DIALOG_CONFIRM")
        }

        override fun onPositiveClickListener(dialog: Dialog?) {
            viewModel.updateArchivedStatus(true, recyclerViewAdapter.getSelectedShopLists())
            recyclerViewAdapter.quitSelection()
            manageButtonsVisibility()
            dialog?.dismiss()
        }

        override fun onNegativeClickListener(dialog: Dialog?) {
            dialog?.dismiss()
        }
    }
}
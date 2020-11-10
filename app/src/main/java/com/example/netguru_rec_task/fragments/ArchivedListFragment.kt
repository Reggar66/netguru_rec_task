package com.example.netguru_rec_task.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netguru_rec_task.ConfirmBottomDialog
import com.example.netguru_rec_task.R
import com.example.netguru_rec_task.adapters.ShoppingListAdapter
import com.example.netguru_rec_task.models.ShopListItem
import com.example.netguru_rec_task.viewModels.ShopListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ArchivedListFragment : Fragment(),
    ShoppingListAdapter.OnItemClickListener,
    ShoppingListAdapter.OnItemLongClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: ShoppingListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: ShopListViewModel

    private lateinit var fabDelete: FloatingActionButton
    private lateinit var fabRestore: FloatingActionButton

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

        // Recycler View setup
        viewManager = LinearLayoutManager(requireContext())
        recyclerViewAdapter = ShoppingListAdapter()
        recyclerViewAdapter.onItemLongClickListener = this
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

        val fabAddShoppingList =
            view.findViewById<FloatingActionButton>(R.id.fragment_shoppingList_fab)
        fabAddShoppingList.visibility = View.GONE

        fabDelete = view.findViewById(R.id.fragment_shoppingList_fab_delete)
        fabDelete.setOnClickListener(OnDeleteButtonListener())

        fabRestore = view.findViewById(R.id.fragment_shoppingList_fab_archive)
        fabRestore.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_unarchive_24
            )
        )
        fabRestore.setOnClickListener(OnUnArchiveListener())

        viewModel.allArchivedShopLists.observe(viewLifecycleOwner, Observer { list ->
            recyclerViewAdapter.setShopList(list)
        })
    }

    private fun manageButtonsVisibility() {
        if (recyclerViewAdapter.selectionMode) {
            fabDelete.show()
            fabRestore.show()
        } else {
            fabDelete.hide()
            fabRestore.hide()
        }
    }

    override fun onItemClickListener(shoppingList: ShopListItem) {
        val action =
            MainFragmentDirections.actionMainFragmentToGroceriesListFragment(
                shoppingList.id,
                shoppingList.isArchived
            )
        val navController = findNavController()
        navController.navigate(action)
    }

    override fun onItemLongClickListener(shoppingList: ShopListItem) {
        manageButtonsVisibility()
    }

    private inner class OnUnArchiveListener : View.OnClickListener,
        ConfirmBottomDialog.OnButtonClickListener {
        override fun onClick(p0: View?) {
            val confirmDialog = ConfirmBottomDialog()
            confirmDialog.onButtonClickListener = this
            confirmDialog.show(parentFragmentManager, "BOTTOM_DIALOG_CONFIRM")
        }

        override fun onPositiveClickListener(dialog: Dialog?) {
            viewModel.updateArchivedStatus(false, recyclerViewAdapter.getSelectedShopLists())
            recyclerViewAdapter.quitSelection()
            manageButtonsVisibility()
            dialog?.dismiss()
        }

        override fun onNegativeClickListener(dialog: Dialog?) {
            dialog?.dismiss()
        }
    }

    private inner class OnDeleteButtonListener : View.OnClickListener,
        ConfirmBottomDialog.OnButtonClickListener {
        override fun onClick(p0: View?) {
            val confirmDialog = ConfirmBottomDialog()
            confirmDialog.onButtonClickListener = this
            confirmDialog.show(parentFragmentManager, "BOTTOM_DIALOG_CONFIRM")
        }

        override fun onPositiveClickListener(dialog: Dialog?) {
            viewModel.deleteList(recyclerViewAdapter.getSelectedShopLists())
            recyclerViewAdapter.quitSelection()
            manageButtonsVisibility()
            dialog?.dismiss()
        }

        override fun onNegativeClickListener(dialog: Dialog?) {
            dialog?.dismiss()
        }
    }
}
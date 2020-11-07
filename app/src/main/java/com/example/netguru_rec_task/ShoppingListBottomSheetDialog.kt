package com.example.netguru_rec_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.netguru_rec_task.models.ShopListItem
import com.example.netguru_rec_task.viewModels.ShopListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ShoppingListBottomSheetDialog(private val viewModel: ShopListViewModel) :
    BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_dialog_create_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextName = view.findViewById<EditText>(R.id.bottomSheetDialog_editText_name)
        editTextName.requestFocus()

        val fabApply =
            view.findViewById<FloatingActionButton>(R.id.bottomSheetDialog_floatingActionButton_apply)

        fabApply.setOnClickListener {
            val name = editTextName.text.toString()
            val time = System.currentTimeMillis()
            viewModel.insert(ShopListItem(name, time))
            dialog?.dismiss()
        }
    }
}
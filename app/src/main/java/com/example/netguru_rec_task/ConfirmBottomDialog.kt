package com.example.netguru_rec_task

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ConfirmBottomDialog : BottomSheetDialogFragment() {

    var onButtonClickListener: OnButtonClickListener? = null

    interface OnButtonClickListener {
        fun onPositiveClickListener(dialog: Dialog?)
        fun onNegativeClickListener(dialog: Dialog?)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_dialog_confirm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonYes = view.findViewById<Button>(R.id.bottomDialog_button_yes)
        buttonYes.setOnClickListener {
            onButtonClickListener?.onPositiveClickListener(dialog)
        }

        val buttonNo = view.findViewById<Button>(R.id.bottomDialog_button_no)
        buttonNo.setOnClickListener {
            onButtonClickListener?.onNegativeClickListener(dialog)
        }
    }
}
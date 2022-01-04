package com.example.capturevoca

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import com.example.capturevoca.databinding.CustomdialogAddFileFabBinding

class CustomDialog(context: Context) {
    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    fun setOnClickListener(listener: OnDialogClickListener)
    {
        onClickListener = listener
    }

    fun showDialog()
    {
        dialog.setContentView(R.layout.customdialog_add_file_fab)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        val edit_name = dialog.findViewById<EditText>(R.id.customdialog_add_file_fab_edittext)

        dialog.findViewById<Button>(R.id.customdialog_add_file_fab_cancelButton).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.customdialog_add_file_fab_okButton).setOnClickListener {
            onClickListener.onClicked(edit_name.text.toString())
            dialog.dismiss()
        }

    }

    interface OnDialogClickListener
    {
        fun onClicked(name: String)
    }
}
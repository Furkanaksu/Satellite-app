package com.furkan.satellite_app.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.furkan.satellite_app.R
import java.util.ArrayList

class Progress {
    companion object {
        private val dialogs: MutableList<LoadingDialog> = ArrayList<LoadingDialog>()
        fun show(context: Context?) {
            context?.let {
                for (dialog in dialogs) {
                    if (dialog.context == context && dialog.isShowing) return
                }
                val loadingDialog = LoadingDialog(context)
                loadingDialog.show()
                dialogs.add(loadingDialog)
            }
        }

        fun dismiss() {
            val it: MutableIterator<LoadingDialog> = dialogs.iterator()
            while (it.hasNext()) {
                val loadingDialog: LoadingDialog = it.next()
                try {
                    loadingDialog.dismiss()
                } catch (ignored: Exception) {
                } finally {
                    it.remove()
                }
            }
        }
    }
}


class LoadingDialog(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        val window = window
        setContentView(R.layout.layout_loading_dialog)
        if (window != null) {
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.setDimAmount(0.2f)
        }
    }
}
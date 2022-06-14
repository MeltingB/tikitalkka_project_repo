package com.meltingb.tikitalkka.utils

import android.content.Context
import com.meltingb.base.ui.utils.dialog.MessageDialogBuilder
import com.meltingb.tikitalkka.R

object Common {

    fun Context.showOkButtonDialog(message: String, buttonText: String? = null, block: (() -> Unit)? = null) {
        MessageDialogBuilder(this, R.layout.dialog_one_button).apply {
            initView(R.id.tvMessage, R.id.btnOk)
            setCancelable(false)
            setMessage(message)
            setLeftButton(buttonText ?: getString(R.string.ok)) {
                dismiss()
                if (block != null) block()
            }
            create()
        }.show(0.8)
    }

}

package com.meltingb.tikitalkka.utils

import android.content.Context
import android.content.Intent
import android.os.Build
import com.meltingb.base.ui.utils.dialog.MessageDialogBuilder
import com.meltingb.tikitalkka.BuildConfig
import com.meltingb.tikitalkka.R

object Common {

    fun Context.showOkButtonDialog(
        message: String,
        buttonText: String? = null,
        block: (() -> Unit)? = null
    ) {
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

    fun Context.showErrorDialog(block: (() -> Unit)? = null) {
        MessageDialogBuilder(this, R.layout.dialog_two_button).apply {
            initView(R.id.tvMessage, R.id.btnLeft, R.id.btnRight)
            setCancelable(false)
            setMessage(getString(R.string.common_error_message))
            setLeftButton(getString(R.string.inquiry)) {
                dismiss()
                sendFeedBack(this@showErrorDialog)
            }
            setRightButton(getString(R.string.ok)) {
                dismiss()
                if (block != null) block()
            }
            create()
        }.show(0.8)
    }

    // 피드백 메일 보내기
    private fun sendFeedBack(context: Context) {
        val defaultMessage = "${context.getString(R.string.feedback_message)} \n\n" +
                "${context.getString(R.string.feedback_model)} ${Build.MODEL} \n" +
                "${context.getString(R.string.feedback_os_version)} ${Build.VERSION.SDK_INT} \n" +
                "${context.getString(R.string.feedback_app_version)} ${BuildConfig.VERSION_NAME} \n\n"

        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.feedback_title))
        emailIntent.putExtra(
            Intent.EXTRA_EMAIL,
            arrayOf(context.getString(R.string.feedback_email))
        )
        emailIntent.putExtra(Intent.EXTRA_TEXT, defaultMessage)
        emailIntent.type = "message/rfc822"
        context.startActivity(emailIntent)

    }
}

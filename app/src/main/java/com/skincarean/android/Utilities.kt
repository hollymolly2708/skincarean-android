package com.skincarean.android

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

object Utilities {
    fun customDialog(message: String?, context: Context) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.custom_dialog)
        val tvMessage = dialog.findViewById<TextView>(R.id.tv_dialog_message)
        val btnDialogOk = dialog.findViewById<Button>(R.id.btn_dialog_ok)
        message?.let { tvMessage.text = it }

        btnDialogOk.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    fun numberFormat(price :BigDecimal?) : String{
        val localeId  = Locale("in","ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeId)
        return formatRupiah.format(price)
    }

}
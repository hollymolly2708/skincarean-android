package com.skincarean.android

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.text.NumberFormat
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
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
        return if(price != null) {
            formatRupiah.format(price)
        }else{
            "Rp 0"
        }
    }


    fun clearGlideCache(context: Context) {
        // Hapus cache disk di thread background
        CoroutineScope(Dispatchers.IO).launch {
            Glide.get(context).clearDiskCache()
        }

        // Hapus cache memori di main thread
        Glide.get(context).clearMemory()
    }


    fun convertIso8601ToDate(isoDate: String): String {
        // Parse the ISO 8601 date string
        val offsetDateTime = OffsetDateTime.parse(isoDate)

        // Define the desired output format
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        // Format the OffsetDateTime to a string (convert to Jakarta timezone)
        return offsetDateTime.withOffsetSameInstant(ZoneOffset.ofHours(7)).format(formatter)
    }



}
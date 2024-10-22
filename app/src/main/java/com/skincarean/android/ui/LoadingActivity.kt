package com.skincarean.android.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.skincarean.android.R
import com.skincarean.android.ui.order.DetailOrderActivity

class LoadingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        Handler(Looper.getMainLooper()).postDelayed({
            val orderId = intent.getStringExtra(DetailOrderActivity.EXTRA_ORDER_ID) ?: run {
                Log.e("DetailOrderActivity", "Order ID is null")
                finish() // Opsional untuk menyelesaikan aktivitas jika data tidak valid
                return@postDelayed
            }
            val intent = Intent(this, DetailOrderActivity::class.java)
            Log.d("loadingActivity", orderId)
            intent.putExtra(DetailOrderActivity.EXTRA_ORDER_ID, orderId)
            startActivity(intent)
            finish()
        }, 4000)
    }
}